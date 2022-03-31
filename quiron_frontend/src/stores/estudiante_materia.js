import { defineStore } from 'pinia'
import axios from 'axios'

export const useEstudianteMateriaStore = defineStore('estudianteMateria', {
  state: () => ({
    estudianteMateria: null,
    estudianteMaterias: null
  }),

  getters: {
  },

  actions: {
    inscribirEstudiante (objeto) {
      return new Promise((resolve, reject) => {
        axios
          .post('http://localhost:9088/api/estudiante-materia/inscribir-materia/' + objeto.estudiante.id + '/' + objeto.materia.id)
          .then((result) => {
            resolve(result)
          })
          .catch((error) => {
            reject(error)
          })
      })
    },
    liberarEstudiante (objeto) {
      return new Promise((resolve, reject) => {
        axios
          .post('http://localhost:9088/api/estudiante-materia/libre-materia/' + objeto.estudiante.id + '/' + objeto.materia.id)
          .then((result) => {
            resolve(result)
          })
          .catch((error) => {
            reject(error)
          })
      })
    },
    aprobarEstudiante (objeto) {
      return new Promise((resolve, reject) => {
        axios
          .post('http://localhost:9088/api/estudiante-materia/aprobar-materia/' + objeto.estudiante.id + '/' + objeto.materia.id)
          .then((result) => {
            resolve(result)
          })
          .catch((error) => {
            reject(error)
          })
      })
    },
    getEstudianteMateriaPorIdEstudianteIdCarrera (objeto) {
      return new Promise((resolve, reject) => {
        axios
          .get('http://localhost:9088/api/estudiante-materia/buscar-por-id-estudiante-e-id-carrera/' + objeto.estudiante.id + '/' + objeto.carrera.id)
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
