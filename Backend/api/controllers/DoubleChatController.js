module.exports = {
    // startChat: async function (req, res) {
    //     const senderId = req.body.userId;
    //     const recipientId = req.body.recipientId;

    //     if(!recipientId)
    //         return res.json({message: 'Invlaid data request', err: 400})

    //     try {
    //         // Check if a chat room already exists between sender and recipient
    //         const chatRoom = await DoubleRoom.findOne({
    //             or: [
    //                 { user1: senderId, user2: recipientId },
    //                 { user1: recipientId, user2: senderId },
    //             ],
    //         });

    //         // Create a new chat room if it doesn't exist
    //         if (!chatRoom) {
    //             const newChatRoom = await DoubleRoom.create({
    //                 user1: senderId,
    //                 user2: recipientId,
    //                 name: 'double room'
    //             }).fetch();

    //             console.log('create room', newChatRoom)


    //             sails.sockets.broadcast(newChatRoom.id, 'newChatRoom', {
    //                 roomId: newChatRoom.id,
    //                 senderId: senderId,
    //                 recipientId: recipientId,
    //             });
    //         }

    //         sendMessage(req, res)
    //     } catch (error) {
    //         console.log(error.message)
    //         return res.json({ err: 500, message: error.message })
    //     }
    // },

    sendMessage: async function (req, res) {
        if (!req.isSocket)
            return res.json({ message: 'Need a socket request', err: 200 })
        let { roomId, userId: senderId, recipientId, content } = req.body

        console.log(senderId, content, recipientId)
        if (!senderId || !content || !recipientId)
            return res.json({ err: 400, message: 'Invalid data request' })

        console.log('send message')
        try {
            if (!roomId) {
                const chatRoom = await DoubleRoom.findOne({
                    or: [
                        { user1: senderId, user2: recipientId },
                        { user1: recipientId, user2: senderId },
                    ],
                });
                if (!chatRoom) {
                    const newChatRoom = await DoubleRoom.create({
                        user1: senderId,
                        user2: recipientId,
                        name: 'The room'
                    }).fetch();
                    roomId = newChatRoom.id
                    sails.sockets.broadcast(recipientId, "newChatRoom", newChatRoom)
                    sails.sockets.broadcast(senderId, "newChatRoom", newChatRoom)
                } else roomId = chatRoom.id
            }

            const chatMessage = await Message.create({
                user: senderId,
                content,
                doubleRoom: roomId
            }).fetch()
            await DoubleRoom.addToCollection(roomId, 'messages', chatMessage.id)

            sails.sockets.broadcast(senderId, 'receiveMessage', chatMessage);
            sails.sockets.broadcast(recipientId, 'receiveMessage', chatMessage);

            return res.json({ message: 'Send message success', err: 200 })
        } catch (error) {
            console.log(error.message)
            return res.json({ err: 500, message: error.message })
        }
    },

    getListMessage: async function (req, res) {
        const { roomId, userId, user1, user2 } = req.body

        const { page = 1, limit = 10 } = req.query
        const options = {
            skip: (page - 1) * limit,
            limit: 10
        }

        if (!userId)
            return res.json({ err: 400, message: 'Invalid roomId or userId' })
        if ((!user1 && !user2) && !roomId)
            return res.json({ err: 400, message: 'Invalid data request' })

        try {
            let whereClause = {
                id: roomId,
                or: [
                    { user1, user2 },
                    { user1: user2, user2: user1 },
                ]
            }
            if (!roomId) delete whereClause.id
            else delete whereClause.or

            let room = await DoubleRoom.findOne({ where: whereClause })
                .populate('messages', {
                    ...options,
                    sort: 'createdAt DESC',
                })

            if (!room) return res.json({ err: 404, message: 'Room not found' })

            return res.json({
                data: room.messages,
                message: 'Success',
                err: 200
            })

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
            }).sort('updatedAt DESC')

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