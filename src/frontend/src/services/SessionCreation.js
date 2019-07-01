import Api from './Api'

export default {
  async createSession (sessionData) {
    return Api().post('/seance/create', sessionData,
      {
        headers: {
          'Access-Control-Allow-Origin': '*'
        }
      }
    )
  }
}
