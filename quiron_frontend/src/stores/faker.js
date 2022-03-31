import { defineStore } from 'pinia'
import axios from 'axios'

export const useFakerStore = defineStore('faker', {
  state: () => ({
  }),

  getters: {
  },

  actions: {
    poblarMaterias () {
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
    poblarCarreras () {
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
    poblarEstudiantes () {
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
    poblarInscripciones () {
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
})
