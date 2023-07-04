module.exports = {
    async find(req, res) {
        const { page = 1, limit = 10 } = req.query
        const { nickName } = req.body

        const options = {
            skip: (page - 1) * limit,
            limit: parseInt(limit, 10)
        }

        try {
            if (!nickName) {
                return res.json({ message: 'Invalid nickName', err: 401 });
            }

            let listUser = await User.find({
                where: { nickName: { contains: nickName } },
                select: ['nickName'],
                ...options
            })

            return res.json({ err: 200, message: 'Success', listUser })
        } catch (err) {
            return res.json({ err: 500, message: err.message })
        }
    },


}