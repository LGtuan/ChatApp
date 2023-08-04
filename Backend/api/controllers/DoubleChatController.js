module.exports = {

    sendMessage: async function (req, res) {
        if (!req.isSocket)
            return res.json({ message: 'Need a socket request', err: 200 })
        let { roomId, userId: senderId, recipientId, content } = req.body
        if (!senderId || !content || !recipientId || !roomId)
            return res.json({ err: 400, message: 'Invalid data request' })

        try {
            const chatMessage = await Message.create({
                user: senderId,
                content,
                doubleRoom: roomId
            }).fetch()
            await DoubleRoom.addToCollection(roomId, 'messages', chatMessage.id)

            const sender = await User.findOne({
                where: { id: senderId },
                select: ['image', 'nickName']
            })
            chatMessage.user = sender
            sails.sockets.broadcast(senderId, 'receiveMessage', chatMessage);
            sails.sockets.broadcast(recipientId, 'receiveMessage', chatMessage);

            return res.json({ message: 'Send message success', err: 200 })
        } catch (error) {
            return res.json({ err: 500, message: error.message })
        }
    },

    getListMessage: async function (req, res) {
        let { roomId, userId, user2, skip = 0, limit = 20 } = req.body
        if (!user2 && !roomId) return res.json({ err: 400, message: 'Invalid data request' })

        try {
            let sendOptions = { roomId: '' }
            if (!roomId) {
                const chatRoom = await DoubleRoom.findOne({
                    or: [
                        { user1: userId, user2 },
                        { user1: user2, user2: userId }
                    ],
                });
                if (!chatRoom) {
                    const newChatRoom = await DoubleRoom.create({
                        user1: userId,
                        user2: user2,
                        name: 'The room'
                    }).fetch();
                    roomId = newChatRoom.id
                } else roomId = chatRoom.id
                sendOptions.roomId = roomId // need send roomId to client when roomId in data request null
            }

            const listUserInRoom = await User.find({
                where: { id: [userId, user2] },
                select: ['nickName', 'image']
            })
            if (listUserInRoom.length <= 1) return res.json({ err: 401, message: 'User not found' })

            let listMessage = await Message.find({ where: { doubleRoom: roomId } })

            let listNewMessage = []
            let to = listMessage.length - parseInt(skip)
            let from = to - limit < 0 ? 0 : to - limit
            for (let i = from; i < to; i++) {
                listNewMessage.push(listMessage[i])
            }

            for (message of listNewMessage) {
                if (message.user == listUserInRoom[0].id) {
                    message.user = listUserInRoom[0]
                } else message.user = listUserInRoom[1]
            }

            return res.json({ data: listNewMessage, message: 'Success', err: 200, ...sendOptions })
        } catch (error) {
            return res.json({ err: 500, message: error.message })
        }
    },

    getListRoom: async function (req, res) {
        const { userId } = req.body

        try {
            let listRoom = await DoubleRoom.find({
                or: [
                    { user1: userId },
                    { user2: userId }
                ]
            }).sort('updatedAt ASC')

            return res.json({ data: listRoom, message: 'Success', err: 200 })
        } catch (error) {
            console.log(error.message)
            return res.json({ err: 500, message: error.message })
        }
    },

    connectWithServer: async function (req, res) {
        if (!req.isSocket) return res.badRequest()

        const { userId } = req.body

        try {

            sails.sockets.join(req, userId)

            sails.sockets.broadcast(userId, { message: 'Join success' })

            return res.ok()
        } catch (error) {
            console.log(error.message)
            return res.serverError()
        }
    }
}