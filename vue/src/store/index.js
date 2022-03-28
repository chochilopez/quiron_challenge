import { store } from 'quasar/wrappers'
import { createStore } from 'vuex'

// Modulos
import carrera from './modules/module_carrera'
import estudiante from './modules/module_estudiante'
import estudianteCarrera from './modules/module_estudiante_carrera'
import estudianteMateria from './modules/module_estudiante_materia'
import faker from './modules/module_faker'
import materia from './modules/module_materia'

// Vuex persistent
import createPersistedState from 'vuex-persistedstate'
// js-cookie
import Cookies from 'js-cookie'

export default store(function (/* { ssrContext } */) {
  const Store = createStore({
    modules: {
      carrera,
      estudiante,
      estudianteCarrera,
      estudianteMateria,
      faker,
      materia
    },
    plugins: [createPersistedState({
      storage: {
        getItem: key => Cookies.get(key),
        setItem: (key, value) => Cookies.set(key, value, { expires: 1, secure: true }),
        removeItem: key => Cookies.remove(key)
      }
    })],

    strict: process.env.DEBUGGING
  })

  return Store
})
