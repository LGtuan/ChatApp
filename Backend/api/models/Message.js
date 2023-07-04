module.exports = {
  attributes: {
    roomId: { model: 'Room', required: true },
    userId: { model: 'User', required: true },
    text: { type: 'string', required: true }

  },
};

