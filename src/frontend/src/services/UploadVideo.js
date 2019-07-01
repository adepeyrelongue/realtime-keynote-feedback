import Api from './Api'

export default {
  async uploadVideo (formData, componentRef) {
    return Api().post('/video/upload', formData,
      {
        headers: {
          'Access-Control-Allow-Origin': '*',
          'Content-Type': 'multipart/form-data'
        },
        onUploadProgress: function (progressEvent) {
          componentRef.uploadPercentage = parseInt(Math.round((progressEvent.loaded * 100) / progressEvent.total))
        }
      })
  }
}
