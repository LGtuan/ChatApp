module.exports = {

    async createRoom(req, res) {
        // if (!req.isSocket) {
        //     return res.json({ err: 400, message: 'Require socket requrest.' })
        // }
        const { userId, roomName = 'Room' } = req.body;
        try {
            const user = await User.findOne({ id: userId })
            if (!user) {
                return res.json({ err: 404, message: 'User not found' })
            }
            const newChatRoom = await Room.create({
                name: roomName,
                users: [userId]
            })
            const roomId = newChatRoom.id
            // sails.sockets.broadcast(
            //         roomId,
            //         {
            //             message: `${user.nickName} has create this room`
            //         }
            //     );
            return res.json({ err: 200, message: 'Create success', data: { roomId } })

        } catch (error) {
            return res.json({ err: 500, message: error.message })
        }

    },

    joinRoom: async function (req, res) {
        // if (!req.isSocket)
        //     return res.json({ err: 400, message: 'Require socket request.' })

        const { userId, roomId } = req.body;
        try {
            const user = await User.findOne({ id: userId })
            if (!user) return res.json({ err: 404, message: 'User not found' })

            const room = await Room.findOne({ id: roomId })
                .populate('users', { id: userId });
            if (!room) return res.json({ err: 404, message: 'Room not found' })
            if (room.users.length > 0) {
                return res.json({ err: 401, message: 'You have become member in this room' })
            }
            await Room.addToCollection(roomId, 'users', userId)

            // sails.sockets.broadcast(
            //     room,
            //     {
            //         message: `${user.nickName} had join this room`
            //     }
            // );

            return res.json({ err: 200, message: 'Join success' })
        } catch (error) {
            return res.json({ err: 500, message: error.message })
        }
    },

    leaveRoom: async function (req, res) {
        if (!req.isSocket)
            return res.json({ err: 400, message: 'Require socket request.' })

        const { userId, roomId } = req.body;
        try {
            const user = await User.findOne({ id: userId })
            if (!user) return res.json({ err: 404, message: 'User not found' })

            const room = await Room.findOne({ id: roomId })
            if (!room) return res.json({ err: 404, message: 'Room not found' })
            await Room.removeFromCollection(roomId, 'users', userId)

            sails.sockets.leave(req.socket, roomId, (error) => {
                if (error) return res.json({ err: 500, message: error.message })

                sails.sockets.broadcast(
                    room,
                    {
                        message: `${user.nickName} has leave this room`
                    }
                );

                return res.json({ err: 200, message: 'Leave success' })
            });
        } catch (error) {
            return res.json({ err: 500, message: error.message })
        }
    },

    findRoom: async function (req, res) {
        const { page = 1, limit = 10 } = req.query
        const options = {
            skip: (page - 1) * limit,
            limit: parseInt(limit, 10)
        }

        const { userId, roomName = '' } = req.body
        if (!userId) return res.json({ error: 401, message: 'Invalid userId' })

        try {
            const user = await User.findOne({ id: userId })
                .populate('rooms', {
                    where: {
                        name: { contains: roomName }
                    },
                    ...options
                })

            if (!user) return res.json({ err: 404, message: 'User not found' })

            return res.json({ err: 200, data: user.rooms ?? [], message: 'Get success' })
        } catch (error) {
            res.json({ err: 500, message: error.message })
        }
    },

    connectRoom: async function (req, res) {
        if (!req.isSocket) {
            return res.json({ err: 400, message: 'Require socket requrest.' })
        }
        const { roomId } = req.body
        if (!roomId) {
            return res.json({ err: 400, message: 'Invalid data request' })
        }

        try {
            sails.sockets.join(req.socket, roomId, (error) => {
                if (error) return res.json({ err: 500, message: error.message })
                console.log('join success')
                return res.json({ err: 200, message: 'connect success' })
            });
        } catch (error) {
            res.json({ err: 500, message: error.message })
        }
    }
};