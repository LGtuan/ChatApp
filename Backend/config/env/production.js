module.exports = {


  datastores: {

    default: {

    },

  },



  models: {
    migrate: 'safe',

  },


  blueprints: {
    shortcuts: false,
  },



  security: {

    cors: {
      // allowOrigins: [
      //   'https://example.com',
      // ]
    },

  },

  session: {

    cookie: {
      // secure: true,
      maxAge: 24 * 60 * 60 * 1000,  // 24 hours
    },

  },

  sockets: {

    onlyAllowOrigins: ["http://www.mydeployedapp.com", "http://mydeployedapp.com"]
  },

  log: {
    level: 'debug'
  },



  http: {

    cache: 365.25 * 24 * 60 * 60 * 1000, // One year

  },

  port: 80,

  custom: {
    baseUrl: 'https://example.com',
    internalEmailAddress: 'support@example.com',
  },

  hookTimeout: 40000
};
