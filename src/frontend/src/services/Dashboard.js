import Api from './Api'

export default {
  async getDashboard () {
    return Api().get('/analytics/get/dashboard', {
      headers: {
        'Access-Control-Allow-Origin': '*'
      }
    })
  }
}
