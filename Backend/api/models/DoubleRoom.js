
module.exports = {

    attributes: {
        name: { type: 'string', required: true },
        user1: { model: 'user', required: true },
        user2: { model: 'user', required: true },
        messages: {
            collection: 'message',
            via: 'doubleRoom',
        }
    },

};