<template>
  <div class="row justify-center fuente9">
    <div class="col-12">
      <q-form ref="letFormulario" @submit.prevent="asyncFunctionGuardarCarrera">
        <div class="row q-pa-md  items-baseline">
          <div class="col-4 col-md-2 q-pa-md">
            <q-input
              v-model="letCarrera.id"
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
              v-model="letCarrera.titulo"
              label="Título de la carrera"
              outlined
              counter
              maxlength="50"
              clearable
              :disable="letVer"
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
              v-model="letCarrera.grado"
              :options="constGrados"
              option-label="nombre"
              option-value="id"
              label="Grado Académico"
              :dense="false"
              emit-value
              :disable="letVer"
              map-options
            >
              <template v-slot:before>
                <q-icon name="mdi-chevron-triple-up" />
              </template>
            </q-select>
          </div>
          <div class="col-4 col-md-2 q-pa-md">
            <q-btn class="text-white bg-c-1" icon-right="save" :loading="letCargando" v-if="letEditar" label="Guardar" no-caps type="submit" />
            <q-btn class="text-white bg-c-1" icon-right="edit" :loading="letCargando" v-if="letVer" label="Editar" no-caps @click="functionAccionEditarCarrera" />
          </div>
        </div>
      </q-form>
    </div>
  </div>
  <div class="row justify-center fuente9 q-pa-md">
    <q-table
      :showing="!letCargando"
      bordered
      title="Plan de Estudio"
      :columns="constColumnas"
      rows-per-page-label="Registros por pagina"
      no-data-label="Sin datos para mostrar"
      :pagination="constPaginacion"
      :filter="letFilter"
      hide-no-data
      :rows="letCarrera.planEstudio"
      row-key="name"
    >
      <template v-slot:top-right>
        <q-input outlined dense debounce="300" v-model="letFilter" placeholder="Buscar">
          <template v-slot:append>
            <q-icon name="search" />
          </template>
        </q-input>
        <q-btn
          class="q-ml-md bg-c-4 text-black"
          icon-right="add_box"
          label="Agregar materia al plan"
          no-caps
          :disable="letVer"
          @click="functionAccionCrear" />
      </template>
      <template v-slot:body-cell-actions="props">
        <q-td :props="props">
          <q-btn @click="functionAccionEliminar(props)" dense round flat class="text-c-6" icon="delete" :disable="letVer">
            <q-tooltip anchor="top middle" self="bottom middle" class="bg-c-1" :offset="[3, 3]">
              Quitar materia del plan
            </q-tooltip>
          </q-btn>
        </q-td>
      </template>
    </q-table>
    <q-inner-loading :showing="letCargando">
      <q-spinner-puff class="text-c-1" size="6em" />
    </q-inner-loading>
  </div>

  <q-dialog v-model="dialogoQuitar" persistent transition-show="scale" transition-hide="scale">
    <q-card class="text-white">
      <q-bar class="bg-c-6">
        <div>Quitar materia del plan de estudio</div>
        <q-space />
        <q-btn dense flat icon="close" v-close-popup>
          <q-tooltip class="bg-c-1 text-white">Cerrar</q-tooltip>
        </q-btn>
      </q-bar>
      <q-card-section>
        <q-list dense bordered padding class="rounded-borders">

          <q-item clickable v-ripple class="q-ma-md">
            <q-item-section>
              <q-item-label class="text-black">{{ letMateria.id }}</q-item-label>
              <q-item-label caption>Id</q-item-label>
            </q-item-section>
          </q-item>

          <q-item clickable v-ripple class="q-ma-md">
            <q-item-section>
              <q-item-label class="text-black">{{ letMateria.nombre === null || letMateria.nombre === '' ? 'SIN DATOS' : letMateria.nombre }}</q-item-label>
              <q-item-label caption>Materia</q-item-label>
            </q-item-section>
          </q-item>

        </q-list>
        <div class="row justify-end">
          <q-btn v-close-popup stack class="text-white bg-c-1 q-ma-md" icon="mdi-delete-variant" @click="asyncFunctionBorrarMateriaDeCarrera" :loading="letCargando">
            <template v-slot:loading>
              <q-spinner-facebook />
            </template>
            QUITAR
          </q-btn>
        </div>
      </q-card-section>
    </q-card>
  </q-dialog>

  <q-dialog v-model="dialogoCrear" persistent transition-show="scale" transition-hide="scale">
    <q-card class="text-black">
      <q-bar class="bg-c-4">
        <div>Agregar materia al plan de estudio.</div>
        <q-space />
        <q-btn dense flat icon="close" v-close-popup>
          <q-tooltip class="bg-c-2 text-white">Cerrar</q-tooltip>
        </q-btn>
      </q-bar>
      <q-card-section>
        <q-table
          :showing="!letCargando"
          bordered
          title="Materias"
          :columns="constColumnas"
          rows-per-page-label="Registros por pagina"
          no-data-label="Sin datos para mostrar"
          :pagination="constPaginacion"
          :filter="letFilterMaterias"
          hide-no-data
          :rows="letMaterias"
          row-key="name"
        >
          <template v-slot:top-right>
            <q-input outlined dense debounce="300" v-model="letFilter" placeholder="Buscar">
              <template v-slot:append>
                <q-icon name="search" />
              </template>
            </q-input>
          </template>
          <template v-slot:body-cell-actions="props">
            <q-td :props="props">
              <q-btn @click="asyncFunctionAgregarMateriaACarrera(props)" dense round flat class="text-c-1" icon="mdi-plus-circle" :disable="letVer">
                <q-tooltip anchor="top middle" self="bottom middle" class="bg-c-1" :offset="[3, 3]">
                  Agregar materia del plan
                </q-tooltip>
              </q-btn>
            </q-td>
          </template>
        </q-table>
      </q-card-section>
    </q-card>
  </q-dialog>

</template>

<script>
import { useStore } from 'vuex'
import { reactive, ref } from 'vue'
import { servicioAlertas } from 'app/_helpers/alerta'
import { useRouter } from 'vue-router'

const constColumnas = [
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

const constGrados = [
  { id: 0, nombre: 'TITULO_PREGRADO' },
  { id: 1, nombre: 'TITULO_GRADO' },
  { id: 2, nombre: 'TITULO_POSTGRADO' }
]

const constPaginacion = {
  rowsPerPage: 10,
  sortBy: 'id',
  descending: true
}

export default {
  setup () {
    const vueRouter = useRouter()
    const vueStore = useStore()

    const letCargando = ref(false)
    const letCarrera = reactive({
      id: null,
      titulo: '',
      grado: '',
      planEstudio: []
    })
    const letEditar = ref(false)
    const letFilter = ref('')
    const letFilterMaterias = ref('')
    const letFormulario = ref(null)
    const letMateria = reactive({
      id: null,
      nombre: ''
    })
    const letMaterias = ref([])
    const letVer = ref(false)

    const dialogoCrear = ref(false)
    const dialogoQuitar = ref(false)

    asyncFunctionBuscarMaterias()

    if (vueStore.state.carrera.carrera !== null) {
      letEditar.value = false
      letVer.value = false
      Object.assign(letCarrera, vueStore.state.carrera.carrera)
      if (vueStore.state.carrera.accion === 'ver') {
        letVer.value = true
      } else if (vueStore.state.carrera.accion === 'editar') {
        letEditar.value = true
      }
    } else {
      vueRouter.push({ name: 'Carreras' })
    }

    function functionAccionCrear () {
      dialogoCrear.value = true
    }

    function functionAccionEditarCarrera () {
      letVer.value = false
      letEditar.value = true
    }

    function functionAccionEliminar (props) {
      Object.assign(letMateria, props.row)
      dialogoQuitar.value = true
    }

    function functionOcultar () {
      dialogoQuitar.value = false
      dialogoCrear.value = false
    }

    async function asyncFunctionAgregarMateriaACarrera (props) {
      try {
        letCargando.value = true
        const objeto = {
          idCarrera: letCarrera.id,
          idMateria: props.row.id
        }
        await vueStore.dispatch('agregarMateriaACarrera', objeto).then((result) => {
          if (result.status === 200) {
            servicioAlertas.alertaExito('La materia fue agregada correctamente del plan de estudios.')
            Object.assign(letCarrera, result.data)
          }
          if (result.status === 202) {
            servicioAlertas.alertaAdvertencia(result.headers.estado)
            console.warn('Advertencia', result.headers.estado)
          }
          letCargando.value = false
          functionOcultar()
        })
      } catch (err) {
        letCargando.value = false
        console.error('Error front', 'assign -> add error -> ' + err.message)
        console.error('Error api', err.headers.estado)
        servicioAlertas.alertaError(err.headers.estado)
      }
    }

    async function asyncFunctionBorrarMateriaDeCarrera () {
      try {
        letCargando.value = true
        const objeto = {
          idCarrera: letCarrera.id,
          idMateria: letMateria.id
        }
        await vueStore.dispatch('delMateriaDeCarrera', objeto).then((result) => {
          if (result.status === 200) {
            servicioAlertas.alertaExito('La materia fue eliminada correctamente del plan de estudios.')
            Object.assign(letCarrera, result.data)
          }
          if (result.status === 202) {
            servicioAlertas.alertaAdvertencia(result.headers.estado)
            console.warn('Advertencia', result.headers.estado)
          }
          letCargando.value = false
          functionOcultar()
        })
      } catch (err) {
        letCargando.value = false
        console.error('Error front', 'assign -> delete error -> ' + err.message)
        console.error('Error api', err.headers.estado)
        servicioAlertas.alertaError(err.headers.estado)
      }
    }

    async function asyncFunctionBuscarMaterias () {
      try {
        letCargando.value = true
        const datos = await vueStore.dispatch('getMaterias')
        if (datos.status === 200) {
          letCargando.value = false
          letMaterias.value = await datos.data
        } else if (datos.status === 202) {
          const mensaje = 'Error al cargar materias ' + datos.headers.estado
          servicioAlertas.infoAdvertencia(mensaje)
          console.warn(mensaje)
        } else {
          const mensaje = 'Error al cargar materias ' + datos.status
          servicioAlertas.infoError(mensaje)
          console.error(mensaje)
        }
        functionOcultar()
        letCargando.value = false
      } catch (err) {
        letCargando.value = false
        functionOcultar()
        const error = 'Hubo un error al intentar cargar las materias ' + err
        servicioAlertas.infoError(error)
        console.error(error)
      }
    }

    async function asyncFunctionGuardarCarrera () {
      if (letFormulario.value.validate()) {
        try {
          letCargando.value = true
          const objeto = {
            id: letCarrera.id,
            titulo: letCarrera.titulo,
            grado: letCarrera.grado
          }
          await vueStore.dispatch('saveCarrera', objeto).then((result) => {
            if (result.status === 201) {
              servicioAlertas.alertaExito('Carrera guardada correctamente')
              vueRouter.push({ name: 'Carreras' })
            } else if (result.status === 202) {
              servicioAlertas.alertaAdvertencia(result.headers.estado)
              console.warn('Advertencia', result.headers.estado)
            }
            letCargando.value = false
            functionOcultar()
          })
        } catch (err) {
          letCargando.value = false
          functionOcultar()
          console.error('Error front', 'save error -> ' + err.message)
          console.error('Error api', err.headers.estado)
          servicioAlertas.alertaError(err.headers.estado)
        }
      } else {
        servicioAlertas.alertaError('Formulario NO validado')
      }
    }

    return {
      asyncFunctionAgregarMateriaACarrera,
      asyncFunctionBorrarMateriaDeCarrera,
      asyncFunctionGuardarCarrera,

      constColumnas,
      constGrados,
      constPaginacion,

      dialogoCrear,
      dialogoQuitar,

      functionAccionCrear,
      functionAccionEditarCarrera,
      functionAccionEliminar,

      letCargando,
      letCarrera,
      letEditar,
      letFilter,
      letFilterMaterias,
      letFormulario,
      letMateria,
      letMaterias,
      letVer,

      rules: {
        required: (v) => !!v || 'Debés completar el campo',
        min: (v) => v.length >= 3 || 'Al menos 3 carácteres',
        max: (v) => v.length <= 50 || 'Máximo 50 carácteres'
      }
    }
  }
}
</script>
