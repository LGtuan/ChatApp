module.exports = {

    sendMessage: async function (req, res) {
        const { roomId, userId, content } = req.body;
        const message = await Message.create({
            content,
            userId,
            roomId
        })
        sails.sockets.broadcast(`room-${roomId}`, 'messageReceived', { message });
        return res.ok();
    },

};