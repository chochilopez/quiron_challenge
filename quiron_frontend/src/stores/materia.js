import { defineStore } from 'pinia'
import axios from 'axios'

export const useMateriaStore = defineStore('materia', {
  state: () => ({
    materia: null,
    materias: null
  }),

  getters: {
  },

  actions: {
    getCantidadMaterias () {
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
    getMaterias () {
      return new Promise((resolve, reject) => {
        axios
          .get('http://localhost:9088/api/materia/todas')
          .then((result) => {
            if (result.status === 200) {
              this.materias = result.data
            }
            resolve(result)
          })
          .catch((error) => {
            reject(error)
          })
      })
    },
    delMateria (id) {
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
    saveMateria (objeto) {
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

    getMateriaPorId (id) {
      return new Promise((resolve, reject) => {
        axios
          .get('http://localhost:9088/api/materia/buscar-por-id/' + id)
          .then((result) => {
            if (result.status === 200) {
              this.materia = result.data
            }
            resolve(result)
          })
          .catch((error) => {
            reject(error)
          })
      })
    }
  }
})
