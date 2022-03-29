import axios from 'axios'

export default {
  state: {
    carrera: null,
    carreras: null
  },
  mutations: {
    setCarrera (state, carrera) {
      state.carrera = carrera
    },
    setCarreras (state, carreras) {
      state.carreras = carreras
    }
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
    },
    getCarreras ({ commit, rootState }) {
      return new Promise((resolve, reject) => {
        axios
          .get('http://localhost:9088/api/carrera/todas')
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
    delCarrera ({ rootState }, id) {
      return new Promise((resolve, reject) => {
        axios
          .delete('http://localhost:9088/api/carrera/' + id)
          .then((result) => {
            resolve(result)
          })
          .catch((error) => {
            reject(error)
          })
      })
    },
    saveCarrera ({ rootState }, objeto) {
      return new Promise((resolve, reject) => {
        let ubicacion = 'http://localhost:9088/api/carrera'
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

    getCarreraPorId ({ commit, rootState }, id) {
      return new Promise((resolve, reject) => {
        axios
          .get('http://localhost:9088/api/carrera/buscar-por-id/' + id)
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
