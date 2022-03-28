import axios from 'axios'

export default {
  state: {
  },
  mutations: {
  },
  actions: {
    getCantidadCarreras ({ rootState }) {
      return new Promise((resolve, reject) => {
        axios
          .get('http://localhost:9088/api/carrera/cantidad')
          .then((result) => {
            resolve(result)
          })
          .catch((error) => {
            reject(error)
          })
      })
    }
  }
}
