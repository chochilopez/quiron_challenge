import axios from 'axios'

export default {
  state: {
  },
  mutations: {
  },
  actions: {
    getCantidadLeads ({ rootState }) {
      return new Promise((resolve, reject) => {
        axios
          .get('http://localhost:9088/api/estudiante/cantidad/ESTADO_LEAD')
          .then((result) => {
            resolve(result)
          })
          .catch((error) => {
            reject(error)
          })
      })
    },
    getCantidadEstudiantes ({ rootState }) {
      return new Promise((resolve, reject) => {
        axios
          .get('http://localhost:9088/api/estudiante/cantidad/ESTADO_ESTUDIANTE')
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
