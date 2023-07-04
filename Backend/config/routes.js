module.exports.routes = {


  '/': { view: 'pages/homepage' },

  // auth
  'POST /api/login': 'AuthController.login',
  'POST /api/logout': 'AuthController.logout',
  'POST /api/register': 'AuthController.register',

  // user
  'POST /api/user/find': 'UserController.find',


};
