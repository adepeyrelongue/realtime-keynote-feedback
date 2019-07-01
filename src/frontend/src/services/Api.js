import axios from 'axios'
import config from '../config'

export default () => {
  return axios.create({
    baseURL: config.baseURL,
    proxyHeaders: false,
    credentials: false
  })
}
