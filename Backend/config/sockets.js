module.exports.sockets = {

  transports: ['websocket', 'polling'],

  beforeConnect: function (handshake, proceed) {

    console.log("start connect")

    return proceed(undefined, true);

  },

  afterDisconnect: function (session, socket, done) {
    console.log('disconnect')
    return done();

  },

  // grant3rdPartyCookie: true,

};
