import Api from './Api'

export default {
  async getSeanceStatistics (id) {
    return Api().get('/analytics/get/data', {
      params: {
        id
      }
    })
  }
}
