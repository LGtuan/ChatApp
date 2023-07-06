/**
 * Policy Mappings
 * (sails.config.policies)
 *
 * Policies are simple functions which run **before** your actions.
 *
 * For more information on configuring policies, check out:
 * https://sailsjs.com/docs/concepts/policies
 */

module.exports.policies = {

  AuthController: {
    'logout': 'verifyToken'
  },
  UserController: {
    'find': 'verifyToken',
    'findFriend': 'verifyToken',
    'requestAddFriend': 'verifyToken',
    'confirmAddFriend': 'verifyToken',
    'recallAddFriend': 'verifyToken',
    'rejectAddFriend': 'verifyToken',
    'deleteFriend': 'verifyToken'
  },
  RoomController: {
    'createRoom': 'verifyToken',
    'findRoom': 'verifyToken'
  }

};
