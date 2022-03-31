import axios from 'axios'

export default {
  state: {
    accionEstudiante: null,
    allEstudiantes: null,
    estudiante: null,
    estudiantes: null,
    lead: null,
    leads: null
  },
  mutations: {
    setAccionEstudiante (state, accionEstudiante) {
      state.accionEstudiante = accionEstudiante
    },
    setAllEstudiantes (state, allEstudiantes) {
      state.allEstudiantes = allEstudiantes
    },
    setEstudiante (state, estudiante) {
      state.estudiante = estudiante
    },
    setEstudiantes (state, estudiante) {
      state.estudiante = estudiante
    },
    setLead (state, lead) {
      state.lead = lead
    },
    setLeads (state, leads) {
      state.leads = leads
    }
  },
  actions: {
    getCantidadLeads ({ rootState }) {
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
    getCantidadEstudiantes ({ rootState }) {
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
    getAllEstudiantes ({ commit, rootState }) {
      return new Promise((resolve, reject) => {
        axios
          .get('http://localhost:9088/api/estudiante/todas')
          .then((result) => {
            if (result.status === 200) {
              commit('setAllEstudiantes', result.data)
            }
            resolve(result)
          })
          .catch((error) => {
            reject(error)
          })
      })
    },
    getEstudiantes ({ commit, rootState }) {
      return new Promise((resolve, reject) => {
        axios
          .get('http://localhost:9088/api/estudiante/buscar-por-status/ESTADO_ESTUDIANTE')
          .then((result) => {
            if (result.status === 200) {
              commit('setEstudiantes', result.data)
            }
            resolve(result)
          })
          .catch((error) => {
            reject(error)
          })
      })
    },
    getLeads ({ commit, rootState }) {
      return new Promise((resolve, reject) => {
        axios
          .get('http://localhost:9088/api/estudiante/buscar-por-status/ESTADO_LEAD')
          .then((result) => {
            if (result.status === 200) {
              commit('setLeads', result.data)
            }
            resolve(result)
          })
          .catch((error) => {
            reject(error)
          })
      })
    },
    delEstudiante ({ rootState }, id) {
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
    saveEstudiante ({ rootState }, objeto) {
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
    getEstudiantePorId ({ commit, rootState }, id) {
      return new Promise((resolve, reject) => {
        axios
          .get('http://localhost:9088/api/estudiante/buscar-por-id/' + id)
          .then((result) => {
            if (result.status === 200) {
              commit('setEstudiante', result.data)
            }
            resolve(result)
          })
          .catch((error) => {
            reject(error)
          })
      })
    },
    getEstudiantePorIdEstado ({ commit, rootState }, objeto) {
      return new Promise((resolve, reject) => {
        axios
          .get('http://localhost:9088/api/estudiante/buscar-por-id-y-estado/' + objeto.id + '/' + objeto.status)
          .then((result) => {
            if (result.status === 200) {
              commit('setEstudiante', result.data)
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
