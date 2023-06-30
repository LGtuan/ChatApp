module.exports.http = {
  customMiddleware: function (app) {
    app.enable('trust proxy');
  },
};
