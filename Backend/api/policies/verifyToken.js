const jwt = require('jsonwebtoken')

module.exports = async function (req, res, next) {

    try {
        const token = req.headers.authorization && req.headers.authorization.split(' ')[1]

        if (!token) {
            return res.json({ message: 'Missing token', err: 400 })
        }

        jwt.verify(token, process.env.SECRET_KEY, (err, decodedToken) => {
            if (err) {
                return res.json({ message: err.message, err: 401 })
            }
            next()
        })
    } catch (err) {
        return res.json({ message: err.message, err: 500 })
    }
}