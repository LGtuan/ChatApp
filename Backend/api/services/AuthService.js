const jwt = require('jsonwebtoken')
const bcrypt = require('bcrypt')
require('dotenv').config()
var secretKey = process.env.SECRET_KEY

module.exports = {
    generateToken(payload) {
        const token = jwt.sign(payload, secretKey, { expiresIn: '3h' })
        return token
    },
    hashPassword(password) {
        const saltRounds = 10
        const salt = bcrypt.genSaltSync(saltRounds)
        const hashedPassword = bcrypt.hashSync(password, salt)
        return hashedPassword
    },
    checkPassword(password, hashedPassword) {
        return bcrypt.compareSync(password, hashedPassword)
    }
}