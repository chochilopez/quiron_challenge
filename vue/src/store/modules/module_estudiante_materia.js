import axios from 'axios'

export default {
  state: {
    estudianteMateria: null,
    estudianteMaterias: null
  },
  mutations: {
    setEstudianteCarrera (state, estudianteMateria) {
      state.estudianteMateria = estudianteMateria
    },
    setEstudianteCarreras (state, estudianteMaterias) {
      state.estudianteMaterias = estudianteMaterias
    }
  },
  actions: {
    inscribirEstudiante ({ rootState }, objeto) {
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
    liberarEstudiante ({ rootState }, objeto) {
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
    aprobarEstudiante ({ rootState }, objeto) {
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
    getEstudianteMateriaPorIdEstudianteIdCarrera ({ rootState }, objeto) {
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
}
