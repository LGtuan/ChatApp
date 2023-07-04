const AuthService = require("../services/AuthService")
const moment = require("moment")

module.exports = {
    async login(req, res) {
        const { email, password } = req.body
        try {
            User.validate('email', email)
            User.validate('password', password)

            let userExisting = await User.findOne({ email });
            if (!userExisting) {
                return res.json({ message: 'Invalid email or password', err: 401 });
            }

            const hashedPassword = userExisting.password
            const isMatch = AuthService.checkPassword(password, hashedPassword)
            if (!isMatch) {
                return res.json({ message: 'Invalid email or password', err: 401 })
            }

            const token = AuthService.generateToken({ email })
            await User.updateOne({ email }).set({
                accessToken: token,
                lastLogin: moment.now()
            })

            return res.json({ message: 'Login success', err: 200 })
        } catch (err) {
            return res.json({ err: 500, message: err.message })
        }
    },

    async register(req, res) {
        const { email, password, confirmPassword, fullName } = req.body

        try {
            User.validate('email', email)
            User.validate('password', password)
            User.validate('fullName', fullName)

            if (password != confirmPassword) {
                return res.json({ message: 'Password is not match', err: 400 })
            }

            const existingUser = await User.findOne({ email })
            if (existingUser) {
                return res.json({ message: 'User already exist', err: 400 })
            }

            const newUser = await User.create({
                email,
                password: AuthService.hashPassword(password),
                fullName,
                nickName: fullName
            }).fetch()

            return res.json({ message: 'success', err: 200, ...newUser })
        } catch (error) {
            return res.json({ message: error.message, err: 500 })
        }
    },

    async logout(req, res) {

        const email = req.body.email
        try {
            let user = await User.findOne({ email });
            if (!user) {
                return res.json({ message: 'Invalid email', err: 401 });
            }

            await User.updateOne({ email }).set({
                accessToken: ''
            })

            return res.json({ message: 'Logout successful', err: 200 });
        } catch (err) {
            return res.json({ err: 500, message: err.message });
        }

    }
};

