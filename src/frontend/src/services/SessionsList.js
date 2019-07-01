import Api from './Api'

export default {
  async getSessionsList () {
    return Api().get('/seance/all', {
      headers: {
        'Access-Control-Allow-Origin': '*'
      }
    }
    )
  }
}
