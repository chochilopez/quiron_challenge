import axios from 'axios'

export default {
  state: {
    materia: null,
    materias: null
  },
  mutations: {
    setMateria (state, materia) {
      state.materia = materia
    },
    setMaterias (state, materias) {
      state.materias = materias
    }
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
    },
    getMaterias ({ commit, rootState }) {
      return new Promise((resolve, reject) => {
        axios
          .get('http://localhost:9088/api/materia/todas')
          .then((result) => {
            if (result.status === 200) {
              commit('setMaterias', result.data)
            }
            resolve(result)
          })
          .catch((error) => {
            reject(error)
          })
      })
    },
    delMateria ({ rootState }, id) {
      return new Promise((resolve, reject) => {
        axios
          .delete('http://localhost:9088/api/materia/' + id)
          .then((result) => {
            resolve(result)
          })
          .catch((error) => {
            reject(error)
          })
      })
    },
    saveMateria ({ rootState }, objeto) {
      return new Promise((resolve, reject) => {
        let ubicacion = 'http://localhost:9088/api/materia'
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

    getMateriaPorId ({ commit, rootState }, id) {
      return new Promise((resolve, reject) => {
        axios
          .get('http://localhost:9088/api/materia/buscar-por-id/' + id)
          .then((result) => {
            if (result.status === 200) {
              commit('setMateria', result.data)
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
