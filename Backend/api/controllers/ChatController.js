module.exports = {

    sendMessToOtherUser: async function (req, res) {
        if (!req.isSocket) return res.json({ err: 400, message: 'Invalid data request' })

        const { userId, roomId, content } = req.body
        if (!userId || !receiverId || !content || !roomId) {
            return res.json({ message: 'Invalid data request', err: 400 })
        }

        try {
            if (!roomId) {
                // const newChatRoom = await Room.create({
                //     name: roomName,
                //     users: [userId, receiverId]
                // })
                // roomId = newChatRoom.id
            }

            let message = await Message.create({
                room: roomId,
                user: userId,
                content
            })
            await Room.addToCollection(roomId, 'messages', message.id)

            sails.sockets.emit(roomId, 'message', { text: message });

            return res.json({ message: 'send success', err: 200 })
        } catch (error) {
            return res.json({ message: error.message, err: 500 })
        }


    },

    sendMessageInGroup: async function (req, res) {

        const { page = 1, limit = 10, } = req.query
        const options = {
            skip: (page - 1) * limit,
            limit: parseInt(limit, 10)
        }

        const { roomId, userId: senderId, content, receiverId } = req.body;
        if (!roomId || !senderId || !content) {
            return res.json({ message: 'Invalid data request', err: 400 });
        }
        try {
            let room = await Room.findOne({ id: roomId })
            if (!room) {
                if (!receiverId) {
                    return res.json({ message: 'Invalid data request', err: 400 });
                }
                const newChatRoom = await Room.create({
                    name: roomName,
                    users: [userId, senderId]
                })
                roomId = newChatRoom.id
            }

            const message = await Message.create({
                content,
                user: senderId,
                room: roomId
            })
            let messageId = message.id
            await Room.addToCollection(roomId, 'messages', messageId)

            sails.sockets.broadcast(`room-${roomId}`, 'messageReceived', { message });
            return res.json({ err: 200, message: 'success' })
        } catch (error) {
            return res.json({ message: error.message, err: 500 })
        }
    },

    getListMessage: async function (req, res) {
        const { roomId, userId } = req.body

        const { page = 1, limit = 10 } = req.query
        const options = {
            skip: (page - 1) * limit,
            limit: 10
        }

        if (!roomId || !userId) {
            return res.json({ err: 200, message: 'Invalid roomId or userId' })
        }


        try {
            let room = await Room.findOne({ id: roomId }).populate('messages', {
                ...options,
                sort: 'createdAt DESC',
            })
            if (!room) {
                return res.json({ err: 404, message: 'Room not found' })
            }

            return res.json({ data: room.messages, message: 'Success', err: 200 })

        } catch (error) {
            return res.json({ err: 500, message: error.message })
        }

    }
};