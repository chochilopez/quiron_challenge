<template>
  <div class="row justify-center fuente9" v-if="!buscar">
    <div class="col-12">
      <q-form ref="formulario" @submit.prevent="onSubmit">
        <div class="row q-pa-md  items-baseline">
          <div class="col-4 col-md-2 q-pa-md">
            <q-input
              v-model="carrera.id"
              label="Id"
              outlined
              clearable
              disable
            >
              <template v-slot:before>
                <q-icon name="mdi-counter" />
              </template>
            </q-input>
          </div>
          <div class="col-8 col-md-4 q-pa-md">
            <q-input
              v-model="carrera.titulo"
              label="Título de la carrera"
              outlined
              counter
              maxlength="50"
              clearable
              :rules="[rules.required, rules.min, rules.max]"
            >
              <template v-slot:before>
                <q-icon name="mdi-school" />
              </template>
            </q-input>
          </div>
          <div class="col-8 col-md-4 q-pa-md">
            <q-select
              standout
              outlined
              v-model="carrera.grado"
              :options="comboGrados"
              option-label="nombre"
              option-value="id"
              label="Grado Académico"
              :dense="false"
              emit-value
              map-options
            >
              <template v-slot:before>
                <q-icon name="mdi-chevron-triple-up" />
              </template>
            </q-select>
          </div>
          <div class="col-4 col-md-2 q-pa-md">
            <q-btn type="submit" class="text-white bg-c-1" icon="save" :loading="cargando">
              <template v-slot:loading>
                <q-spinner-facebook />
              </template>
              <span class="q-pl-sm">
                Guardar Carrera
              </span>
            </q-btn>
          </div>
        </div>
      </q-form>
    </div>
  </div>
  <div class="row justify-center fuente9 q-pa-md" v-if="!buscar">
    <q-table
      :showing="!cargando"
      bordered
      title="Plan de Estudio"
      :columns="columnas"
      rows-per-page-label="Registros por pagina"
      no-data-label="Sin datos para mostrar"
      :pagination="paginacion"
      :filter="filter"
      hide-no-data
      :rows="materias"
      row-key="name"
    >
      <template v-slot:top-right>
        <q-input outlined dense debounce="300" v-model="filter" placeholder="Buscar">
          <template v-slot:append>
            <q-icon name="search" />
          </template>
        </q-input>
        <q-btn
          class="q-ml-md bg-c-4 text-black"
          icon-right="add_box"
          label="Agregar materia"
          no-caps
          @click="crear" />
      </template>
      <template v-slot:body-cell-imagen="icons">
        <q-td :props="icons">
          <q-icon :name="icons.row.icono" size="md"></q-icon>
        </q-td>
      </template>
      <template v-slot:body-cell-actions="props">
        <q-td :props="props">
          <q-btn @click="ver(props)" dense round flat class="text-c-1" icon="visibility">
            <q-tooltip anchor="top middle" self="bottom middle" :offset="[3, 3]">
              Ver
            </q-tooltip>
          </q-btn>
          <q-btn @click="editar(props)" dense round flat class="text-c-4" icon="edit">
            <q-tooltip anchor="top middle" self="bottom middle" :offset="[3, 3]">
              Editar
            </q-tooltip>
          </q-btn>
          <q-btn @click="eliminar(props)" dense round flat class="text-c-6" icon="delete">
            <q-tooltip anchor="top middle" self="bottom middle" :offset="[3, 3]">
              Eliminar
            </q-tooltip>
          </q-btn>
        </q-td>
      </template>
    </q-table>
    <q-inner-loading :showing="cargando">
      <q-spinner-puff class="text-c-1" size="6em" />
    </q-inner-loading>
  </div>
</template>

<script>
import { useStore } from 'vuex'
import { reactive, ref } from 'vue'

const paginacion = {
  rowsPerPage: 10,
  sortBy: 'id',
  descending: true
}

const columnas = [
  {
    name: 'id',
    label: 'Id Carrera',
    field: 'id',
    sortable: true,
    sortOrder: 'ad',
    align: 'left'
  },
  {
    name: 'nombre',
    label: 'Nombre',
    field: 'nombre',
    sortable: true,
    sortOrder: 'ad',
    align: 'left'
  },
  {
    name: 'actions',
    label: 'Acciones',
    field: '',
    align: 'center'
  }
]

export default {
  setup () {
    const store = useStore()
    const buscar = ref(false)
    const cargando = ref(false)
    const carrera = reactive({
      id: null,
      grado: '',
      planEstudio: []
    })
    const materia = reactive({
      id: null,
      nombre: ''
    })
    const comboGrados = ref([
      { id: 1, nombre: 'TITULO_PREGRADO' },
      { id: 2, nombre: 'TITULO_GRADO' },
      { id: 3, nombre: 'TITULO_POSTGRADO' }
    ])

    if (store.state.carrera.carrera !== null) {
      Object.assign(carrera, store.state.carrera.carrera)
      store.commit('setCarrera', null)
      buscar.value = false
    } else {
      buscar.value = true
    }

    buscar.value = false

    return {
      paginacion,
      columnas,

      buscar,
      comboGrados,
      cargando,

      carrera,
      materia,

      rules: {
        required: (v) => !!v || 'Debés completar el campo',
        min: (v) => v.length >= 8 || 'Al menos 8 carácteres',
        mix: (v) => v.length <= 50 || 'Al menos 8 carácteres'
      }
    }
  }
}
</script>
