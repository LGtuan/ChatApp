module.exports = {
  attributes: {
    room: { model: 'Room', required: false },
    user: { model: 'User', required: true },
    content: { type: 'string', required: true },
    doubleRoom: { model: 'DoubleRoom', required: false }
  },
};