module.exports.routes = {


  '/': { view: 'pages/homepage' },

  // auth
  'POST /api/user/login': 'AuthController.login',
  'POST /api/user/logout': 'AuthController.logout',
  'POST /api/user/register': 'AuthController.register',

  // user
  'POST /api/user/find': 'UserController.find',
  // 'POST /api/user/findFriend': 'UserController.findFriend',
  // 'POST /api/user/requestAddFriend': 'UserController.requestAddFriend',
  // 'POST /api/user/confirmAddFriend': 'UserController.confirmAddFriend',
  // 'POST /api/user/recallAddFriend': 'UserController.recallAddFriend',
  // 'POST /api/user/rejectAddFriend': 'UserController.rejectAddFriend',
  // 'POST /api/user/deleteFriend': 'UserController.deleteFriend',

  'POST /api/room/createRoom': 'RoomController.createRoom',
  'POST /api/room/findRoom': 'RoomController.findRoom',

  'POST /api/chat/getListMessage': 'ChatController.getListMessage',

  'POST /api/doubleChat/startChat': 'DoubleChatController.startChat',
  'POST /api/doubleChat/sendMessage': 'DoubleChatController.sendMessage',
  'POST /api/doubleChat/getListMessage': 'DoubleChatController.getListMessage',
  'POST /api/doubleChat/getListRoom': 'DoubleChatController.getListRoom',
  'POST /api/doubleChat/connectWithServer': 'DoubleChatController.connectWithServer',

};
