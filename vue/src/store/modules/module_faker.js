import axios from 'axios'

export default {
  state: {
  },
  mutations: {
  },
  actions: {
    poblarMaterias ({ rootState }) {
      return new Promise((resolve, reject) => {
        axios
          .post('http://localhost:9088/api/faker/materias')
          .then((result) => {
            resolve(result)
          })
          .catch((error) => {
            reject(error)
          })
      })
    },
    poblarCarreras ({ rootState }) {
      return new Promise((resolve, reject) => {
        axios
          .post('http://localhost:9088/api/faker/carreras')
          .then((result) => {
            resolve(result)
          })
          .catch((error) => {
            reject(error)
          })
      })
    },
    poblarEstudiantes ({ rootState }) {
      return new Promise((resolve, reject) => {
        axios
          .post('http://localhost:9088/api/faker/estudiantes')
          .then((result) => {
            resolve(result)
          })
          .catch((error) => {
            reject(error)
          })
      })
    },
    poblarInscripciones ({ rootState }) {
      return new Promise((resolve, reject) => {
        axios
          .post('http://localhost:9088/api/faker/estudiante-carrera')
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
