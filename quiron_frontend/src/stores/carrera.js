import { defineStore } from 'pinia'
import axios from 'axios'

export const useCarreraStore = defineStore('carrera', {
  state: () => ({
    accionCarrera: null,
    carrera: null,
    carreras: null
  }),

  getters: {
  },

  actions: {
    getCantidadCarreras () {
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
    getCarreras () {
      return new Promise((resolve, reject) => {
        axios
          .get('http://localhost:9088/api/carrera/todas')
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
    delCarrera (id) {
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
    saveCarrera (objeto) {
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
    getCarreraPorId (id) {
      return new Promise((resolve, reject) => {
        axios
          .get('http://localhost:9088/api/carrera/buscar-por-id/' + id)
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
    },
    delMateriaDeCarrera (objeto) {
      return new Promise((resolve, reject) => {
        axios
          .post('http://localhost:9088/api/carrera/quitar-materia/' + objeto.idCarrera + '/' + objeto.idMateria)
          .then((result) => {
            resolve(result)
          })
          .catch((error) => {
            reject(error)
          })
      })
    },
    agregarMateriaACarrera (objeto) {
      return new Promise((resolve, reject) => {
        axios
          .post('http://localhost:9088/api/carrera/agregar-materia/' + objeto.idCarrera + '/' + objeto.idMateria)
          .then((result) => {
            resolve(result)
          })
          .catch((error) => {
            reject(error)
          })
      })
    },
    setAccionCarrera (accion) {
      this.accionCarrera = accion
    },
    setCarrera (carrera) {
      this.carrera = carrera
    }
  }
})
