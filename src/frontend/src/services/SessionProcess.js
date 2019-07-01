import Api from './Api'

export default {
  async getSessionsInProcess () {
    return Api().get('/seance/get/seances-in-process')
  }
}
