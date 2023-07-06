module.exports = {
  attributes: {
    roomId: { model: 'Room', required: true },
    userId: { model: 'User', required: true },
    content: { type: 'string', required: true }

  },
};