import axios from 'axios'

export default {
  state: {
    estudianteCarrera: null,
    estudianteCarreras: null
  },
  mutations: {
    setEstudianteCarrera (state, estudianteCarrera) {
      state.estudianteCarrera = estudianteCarrera
    },
    setEstudianteCarreras (state, estudianteCarreras) {
      state.estudianteCarreras = estudianteCarreras
    }
  },
  actions: {
    getCantidadEstudianteCarrera ({ rootState }) {
      return new Promise((resolve, reject) => {
        axios
          .get('http://localhost:9088/api/estudiante-carrera/cantidad')
          .then((result) => {
            resolve(result)
          })
          .catch((error) => {
            reject(error)
          })
      })
    },
    getEstudianteCarreras ({ commit, rootState }) {
      return new Promise((resolve, reject) => {
        axios
          .get('http://localhost:9088/api/estudiante-carrera/todas')
          .then((result) => {
            if (result.status === 200) {
              commit('setCarreras', result.data)
            }
            resolve(result)
          })
          .catch((error) => {
            reject(error)
          })
      })
    },
    delEstudianteCarrera ({ rootState }, id) {
      return new Promise((resolve, reject) => {
        axios
          .delete('http://localhost:9088/api/estudiante-carrera/' + id)
          .then((result) => {
            resolve(result)
          })
          .catch((error) => {
            reject(error)
          })
      })
    },
    saveEstudianteCarrera ({ rootState }, objeto) {
      return new Promise((resolve, reject) => {
        let ubicacion = 'http://localhost:9088/api/estudiante-carrera'
        if (objeto.id > 0) {
          ubicacion = ubicacion + '/' + objeto.id
        }
        axios
          .put(ubicacion, objeto)
          .then((result) => {
            resolve(result)
          })
          .catch((error) => {
            reject(error)
          })
      })
    },
    getEstudianteCarreraPorIdEstudiante ({ commit, rootState }, id) {
      return new Promise((resolve, reject) => {
        axios
          .get('http://localhost:9088/api/estudiante-carrera/buscar-por-id-estudiante/' + id)
          .then((result) => {
            if (result.status === 200) {
              commit('setCarrera', result.data)
            }
            resolve(result)
          })
          .catch((error) => {
            reject(error)
          })
      })
    }
  }
}
