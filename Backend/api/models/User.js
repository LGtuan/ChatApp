module.exports = {

  attributes: {
    email: {
      type: 'string',
      unique: true,
      required: true,
      isEmail: true
    },
    password: {
      type: 'string',
      required: true,
      minLength: 6
    },
    fullName: {
      type: 'string',
      required: true,
      minLength: 4
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

