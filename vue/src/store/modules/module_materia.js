import axios from 'axios'

export default {
  state: {
  },
  mutations: {
  },
  actions: {
    getCantidadMaterias ({ rootState }) {
      return new Promise((resolve, reject) => {
        axios
          .get('http://localhost:9088/api/materia/cantidad')
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
