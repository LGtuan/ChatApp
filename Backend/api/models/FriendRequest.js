module.exports = {

  attributes: {

    senderId: { type: 'string', required: true },
    receiverId: { type: 'string', required: true },
    status: { type: 'string', defaultsTo: 'pending' },

  },

};

