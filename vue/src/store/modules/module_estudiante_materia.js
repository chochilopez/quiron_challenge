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
