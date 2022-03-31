import { defineStore } from 'pinia'
import axios from 'axios'

export const useEstudianteStore = defineStore('estudiante', {
  state: () => ({
    accionEstudiante: null,
    allEstudiantes: null,
    estudiante: null,
    estudiantes: null,
    lead: null,
    leads: null
  }),

  getters: {
  },

  actions: {
    getCantidadLeads () {
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
    getCantidadEstudiantes () {
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
    },
    getAllEstudiantes () {
      return new Promise((resolve, reject) => {
        axios
          .get('http://localhost:9088/api/estudiante/todas')
          .then((result) => {
            if (result.status === 200) {
              this.allEstudiantes = result.data
            }
            resolve(result)
          })
          .catch((error) => {
            reject(error)
          })
      })
    },
    getEstudiantes () {
      return new Promise((resolve, reject) => {
        axios
          .get('http://localhost:9088/api/estudiante/buscar-por-status/ESTADO_ESTUDIANTE')
          .then((result) => {
            if (result.status === 200) {
              this.estudiantes = result.data
            }
            resolve(result)
          })
          .catch((error) => {
            reject(error)
          })
      })
    },
    getLeads () {
      return new Promise((resolve, reject) => {
        axios
          .get('http://localhost:9088/api/estudiante/buscar-por-status/ESTADO_LEAD')
          .then((result) => {
            if (result.status === 200) {
              this.leads = result.data
            }
            resolve(result)
          })
          .catch((error) => {
            reject(error)
          })
      })
    },
    delEstudiante (id) {
      return new Promise((resolve, reject) => {
        axios
          .delete('http://localhost:9088/api/estudiante/' + id)
          .then((result) => {
            resolve(result)
          })
          .catch((error) => {
            reject(error)
          })
      })
    },
    saveEstudiante (objeto) {
      return new Promise((resolve, reject) => {
        let ubicacion = 'http://localhost:9088/api/estudiante'
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
    getEstudiantePorId (id) {
      return new Promise((resolve, reject) => {
        axios
          .get('http://localhost:9088/api/estudiante/buscar-por-id/' + id)
          .then((result) => {
            if (result.status === 200) {
              this.estudiante = result.data
            }
            resolve(result)
          })
          .catch((error) => {
            reject(error)
          })
      })
    },
    getEstudiantePorIdEstado (objeto) {
      return new Promise((resolve, reject) => {
        axios
          .get('http://localhost:9088/api/estudiante/buscar-por-id-y-estado/' + objeto.id + '/' + objeto.status)
          .then((result) => {
            if (result.status === 200) {
              this.estudiante = result.data
            }
            resolve(result)
          })
          .catch((error) => {
            reject(error)
          })
      })
    },
    setAccionEstudiante (accion) {
      this.accionEstudiante = accion
    },
    setEstudiante (estudiante) {
      this.estudiante = estudiante
    },
    setLead (lead) {
      this.lead = lead
    }
  }
})
