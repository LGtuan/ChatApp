module.exports = {
    startChat: async function (req, res) {
        const senderId = req.body.userId;
        const recipientId = req.body.recipientId;
        console.log({ senderId, recipientId })

        try {

            // Check if a chat room already exists between sender and recipient
            const chatRoom = await DoubleRoom.findOne({
                or: [
                    { user1: senderId, user2: recipientId },
                    { user1: recipientId, user2: senderId },
                ],
            });

            // Create a new chat room if it doesn't exist
            if (!chatRoom) {
                const newChatRoom = await DoubleRoom.create({
                    user1: senderId,
                    user2: recipientId,
                    name: 'double room'
                }).fetch();

                console.log('create room', newChatRoom)

                // Join the new chat room
                sails.sockets.join(req, newChatRoom.id);

                sails.sockets.broadcast(newChatRoom.id, 'newChatRoom', {
                    roomId: newChatRoom.id,
                    senderId: senderId,
                    recipientId: recipientId,
                });
            } else {
                // Join the existing chat room
                sails.sockets.join(req, `chat:${chatRoom.id}`);
            }

            sendMessage(req, res)
        } catch (error) {
            console.log(error.message)
            return res.json({ err: 500, message: error.message })
        }
    },

    sendMessage: async function (req, res) {
        console.log('send message')
        let { roomId, userId: senderId, recipientId, content } = req.body

        if (!senderId || !content) {
            return res.json({ err: 400, message: 'Invalid data request' })
        }

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
                    name: 'double room'
                }).fetch();

                roomId = newChatRoom.id
            } else {
                roomId = chatRoom.id
            }

        }

        sails.sockets.join(req, roomId)

        try {
            const chatMessage = await Message.create({
                user: senderId,
                content,
                doubleRoom: roomId
            }).fetch()

            await DoubleRoom.addToCollection(roomId, 'messages', chatMessage.id)

            console.log('content', content)
            sails.sockets.broadcast(roomId, 'receiveMessage', chatMessage);

        } catch (error) {
            console.log(error.message)
            return res.json({ err: 500, message: error.message })
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
            console.log(roomId, userId)
            return res.json({ err: 400, message: 'Invalid roomId or userId' })
        }


        try {
            let room = await DoubleRoom.findOne({ id: roomId }).populate('messages', {
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

    },

    getListRoom: async function (req, res) {
        const { userId } = req.body

        try {
            let listRoom = await DoubleRoom.find({
                or: [
                    { user1: userId },
                    { user2: userId }
                ]
            })

            return res.json({ data: listRoom, message: 'Success', err: 200 })
        } catch (error) {
            return res.json({ err: 500, message: error.message })
        }
    }
}