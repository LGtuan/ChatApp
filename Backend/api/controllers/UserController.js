module.exports = {
    async find(req, res) {
        const { page = 1, limit = 10 } = req.query
        const { nickName, userId } = req.body

        const options = {
            skip: (page - 1) * limit,
            limit: parseInt(limit, 10)
        }

        try {
            let listUser = await User.find({
                where: {
                    nickName: { contains: nickName ?? '' },
                    id: { '!=': userId }
                },
                select: ['nickName'],
                ...options
            })

            return res.json({ err: 200, message: 'Success', data: listUser })
        } catch (error) {
            return res.json({ err: 500, message: error.message })
        }
    },

    // async findFriend(req, res) {
    //     const { page = 1, limit = 10 } = req.query
    //     const { userId, nickName } = req.body

    //     const options = {
    //         skip: (page - 1) * limit,
    //         limit: parseInt(limit, 10)
    //     }

    //     try {
    //         let user = await User.findOne({ id: userId })

    //         if (!user) {
    //             return res.json({ err: 401, meassage: 'User not found' })
    //         }

    //         let friendIds = user.friends ?? []

    //         let listFriend = await User.find({
    //             where: {
    //                 id: { in: friendIds ?? [] },
    //                 nickName: { contains: nickName ?? '' }
    //             },
    //             select: ['nickName'],
    //             ...options
    //         })

    //         return res.json({ err: 200, message: 'Success', data: listFriend })
    //     } catch (error) {
    //         return res.json({ err: 500, message: error.message })
    //     }
    // },

    // async requestAddFriend(req, res) {
    //     const { userId, receiverId } = req.body

    //     try {
    //         let sender = await User.findOne({ id: userId ?? '' })
    //         let receiver = await User.findOne({ id: receiverId ?? '' })

    //         if (!sender || !receiver) {
    //             return res.json({ err: 404, message: 'Data not found', })
    //         }

    //         let requestAdd = await FriendShip.findOne({
    //             senderId: userId,
    //             receiverId
    //         })
    //         if (requestAdd?.status == 'pending') {
    //             return res.json({ err: 401, message: `You have send request to ${receiver.nickName}` })
    //         } else if (requestAdd?.status == 'accept') {
    //             return res.json({ err: 401, message: `You have become friend with ${receiver.nickName}` })
    //         }

    //         await FriendShip.create({
    //             senderId: userId,
    //             receiverId
    //         })

    //         return res.json({ err: 200, message: 'Success' })
    //     } catch (error) {
    //         return res.json({ err: 500, message: error.message })
    //     }
    // },

    // async confirmAddFriend(req, res) {
    //     const { senderId, userId } = req.body

    //     try {
    //         let sender = await User.findOne({ id: senderId ?? '' })
    //         let receiver = await User.findOne({ id: userId ?? '' })

    //         if (!sender || !receiver) {
    //             return res.json({ err: 404, message: 'Data not found' })
    //         }

    //         let requestAdd = await FriendShip.findOne({ senderId, receiverId: userId })
    //         if (!requestAdd) {
    //             return res.json({ err: 401, message: `Invalid data request` })
    //         }
    //         if (requestAdd?.status == 'accept') {
    //             return res.json({ err: 401, message: `You have become friend with ${sender.nickName}` })
    //         }

    //         await FriendShip.updateOne({
    //             senderId,
    //             receiverId: userId
    //         }).set({
    //             status: 'accept'
    //         })

    //         return res.json({ err: 200, message: 'Success' })
    //     } catch (error) {
    //         return res.json({ err: 500, message: error.message })
    //     }
    // },

    // async recallAddFriend(req, res) {
    //     try {

    //     } catch (error) {
    //         return res.json({ err: 500, message: error.message })
    //     }
    // },

    // async rejectAddFriend(req, res) {
    //     try {

    //     } catch (error) {
    //         return res.json({ err: 500, message: error.message })
    //     }
    // },

    // async deleteFriend(req, res) {
    //     const { friendId, userId } = req.body

    //     try {
    //         let friend = await User.findOne({ id: friendId ?? '' })
    //         let user = await User.findOne({ id: userId ?? '' })

    //         if (!friend || !user) {
    //             return res.json({ err: 404, message: 'Data not found' })
    //         }

    //         let requestAdd = await FriendShip.findOne({ senderId: friendId, receiverId: userId })
    //         if (!requestAdd) {
    //             return res.json({ err: 401, message: `Invalid data request` })
    //         }
    //         if (requestAdd?.status == 'accept') {
    //             return res.json({ err: 401, message: `You have become friend with ${friend.nickName}` })
    //         }

    //         await FriendShip.updateOne({
    //             senderId: friendId,
    //             receiverId: userId
    //         }).set({
    //             status: 'accept'
    //         })

    //         return res.json({ err: 200, message: 'Success' })
    //     } catch (error) {
    //         return res.json({ err: 500, message: error.message })
    //     }
    // }

}