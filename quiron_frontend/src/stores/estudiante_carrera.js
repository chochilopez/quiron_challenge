import { defineStore } from 'pinia'
import axios from 'axios'

export const useEstudianteCarreraStore = defineStore('estudianteCarrera', {
  state: () => ({
    estudianteCarrera: null,
    estudianteCarreras: null
  }),

  getters: {
  },

  actions: {
    enrollEstudianteCarrera (objeto) {
      return new Promise((resolve, reject) => {
        axios
          .post('http://localhost:9088/api/estudiante-carrera/inscribir-estudiante/' + objeto.idEstudiante + '/' + objeto.idCarrera)
          .then((result) => {
            resolve(result)
          })
          .catch((error) => {
            reject(error)
          })
      })
    },
    getCantidadEstudianteCarrera () {
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
    getEstudianteCarreras () {
      return new Promise((resolve, reject) => {
        axios
          .get('http://localhost:9088/api/estudiante-carrera/todas')
          .then((result) => {
            if (result.status === 200) {
              this.carreras = result.data
            }
            resolve(result)
          })
          .catch((error) => {
            reject(error)
          })
      })
    },
    delEstudianteCarrera (id) {
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
    saveEstudianteCarrera (objeto) {
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
    getEstudianteCarreraPorIdEstudiante (id) {
      return new Promise((resolve, reject) => {
        axios
          .get('http://localhost:9088/api/estudiante-carrera/buscar-por-id-estudiante/' + id)
          .then((result) => {
            if (result.status === 200) {
              this.carrera = result.data
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
