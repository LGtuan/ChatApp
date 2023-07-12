module.exports = {

  attributes: {

    sender: { type: 'string', required: true },
    receiver: { type: 'string', required: true },
    status: { type: 'string', defaultsTo: 'pending' },

  },

};

