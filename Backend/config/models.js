module.exports.models = {

  schema: true,


  migrate: 'alter',


  attributes: {
    createdAt: { type: 'number', autoCreatedAt: true, },
    updatedAt: { type: 'number', autoUpdatedAt: true, },
    id: { type: 'string', columnName: '_id' },
  },


  dataEncryptionKeys: {
    default: 'tBf3ZtCrFZX7w0fch6EwtfD4tpOTTmkkHrBE4SdL/78='
  },


  cascadeOnDestroy: true


};
