const Joi = require('@hapi/joi')

module.exports = {

  attributes: {

    email: {
      type: 'string',
      unique: true,
      required: true,
      validate: (value) => {
        const schema = Joi.string().email().required()
        const error = schema.validate(value);
        if (error) {
          throw new Error('Invalid email')
        }
      }
    },

    password: {
      type: 'string',
      required: true,
      validate: (value) => {
        const schema = Joi.string().min(6).required()
        const error = schema.validate(value)
        if (error) {
          throw new Error('Passwor must be at least 6 characters long')
        }
      }
    },

    fullName: {
      type: 'string',
      required: true,
      validate: (value) => {
        const schema = Joi.string().min(2).required()
        const error = schema.validate(value)
        if (error) {
          throw new Error('Fullname must be at least 3 characters long')
        }
      }
    },

    phone: { type: 'string', },
    nickName: { type: 'string' },
    address: { type: 'string' },
    birthDay: { type: 'number' },
    access_token: { type: 'string' },
    lastLogin: { type: 'number' },
    rooms: {
      collection: 'room',
      via: 'users',
    }
  },

};

