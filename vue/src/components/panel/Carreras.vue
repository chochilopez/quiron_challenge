<template>
  <div class="row q-pa-md fuente3">
    <div class="col">
      <q-table
        :showing="!letCargando"
        bordered
        title="Carreras"
        :columns="constColumnas"
        rows-per-page-label="Registros por pagina"
        no-data-label="Sin datos para mostrar"
        :pagination="constPaginacion"
        :filter="letFilter"
        hide-no-data
        :rows="letCarreras"
        row-key="name"
      >
        <template v-slot:top-right>
          <q-input outlined dense debounce="300" v-model="letFilter" placeholder="Buscar" class="q-ma-md">
            <template v-slot:append>
              <q-icon name="search" />
            </template>
          </q-input>
          <q-btn
            class="q-ma-md bg-c-4 text-black"
            icon-right="add_box"
            label="Nueva carrera"
            no-caps
            @click="functionAccionCrear" />
          <q-btn
            class="q-ma-md bg-c-5 text-black"
            icon-right="archive"
            label="Exportar"
            no-caps
            @click="exportTable" />
          <q-btn
            class="q-ma-md bg-info text-black"
            icon-right="mdi-magnify"
            label="Buscar por id"
            no-caps
            @click="functionAccionBuscarPorId" />
        </template>
        <template v-slot:body-cell-materias="props">
          <q-td :props="props">
            {{ props.row.planEstudio.length }}
          </q-td>
        </template>
        <template v-slot:body-cell-actions="props">
          <q-td :props="props">
            <q-btn @click="functionAccionVer(props)" dense round flat class="text-c-1" icon="visibility">
              <q-tooltip anchor="top middle" self="bottom middle" :offset="[3, 3]" class="bg-c-1">
                Ver
              </q-tooltip>
            </q-btn>
            <q-btn @click="functionAccionEditar(props)" dense round flat class="text-c-4" icon="edit">
              <q-tooltip anchor="top middle" self="bottom middle" :offset="[3, 3]" class="bg-c-1">
                Editar
              </q-tooltip>
            </q-btn>
            <q-btn @click="functionAccionEliminar(props)" dense round flat class="text-c-6" icon="delete">
              <q-tooltip anchor="top middle" self="bottom middle" :offset="[3, 3]" class="bg-c-1">
                Eliminar
              </q-tooltip>
            </q-btn>
          </q-td>
        </template>
      </q-table>
      <q-inner-loading :showing="letCargando">
        <q-spinner-puff class="text-c-1" size="6em" />
      </q-inner-loading>
    </div>
  </div>

  <q-dialog v-model="dialogoCrear" persistent transition-show="scale" transition-hide="scale">
    <q-card class="text-black" style="width: 400px">
      <q-bar class="bg-c-4">
        <div>Nueva carrera</div>
        <q-space />
        <q-btn dense flat icon="close" v-close-popup>
          <q-tooltip class="bg-c-2 text-white">Cerrar</q-tooltip>
        </q-btn>
      </q-bar>
      <q-card-section>
        <q-form  @submit.prevent="asyncFunctionGuardarCarrera()" ref="letFormulario">
          <div class="row q-pa-sm">
            <div class="col-12">
              <q-input
                v-model="letCarrera.titulo"
                type="input"
                label="Título"
                hint="Título de la carrera"
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
          </div>
          <div class="row q-pa-sm q-mb-sm">
            <div class="col-12">
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
                map-options
              >
                <template v-slot:before>
                  <q-icon name="mdi-swap-vertical" />
                </template>
              </q-select>
            </div>
          </div>
          <div class="row justify-around">
            <q-btn type="submit" class="text-white bg-negative" icon="cancel" v-close-popup>
              <span class="q-pl-sm">
                Cancelar
              </span>
            </q-btn>
            <q-btn type="submit" class="text-white bg-primary" icon="save" :loading="letCargando">
              <template v-slot:loading>
                <q-spinner-facebook />
              </template>
              <span class="q-pl-sm">
                Guardar
              </span>
            </q-btn>
          </div>
        </q-form>
      </q-card-section>
    </q-card>
  </q-dialog>

  <q-dialog v-model="dialogoEliminar" persistent transition-show="scale" transition-hide="scale">
    <q-card class="text-white">
      <q-bar class="bg-c-6">
        <div>Eliminar carrera</div>
        <q-space />
        <q-btn dense flat icon="close" v-close-popup>
          <q-tooltip class="bg-c-1 text-white">Cerrar</q-tooltip>
        </q-btn>
      </q-bar>
      <q-card-section class="q-pa-md">
        <q-list dense bordered padding class="rounded-borders">

          <q-item clickable v-ripple class="q-ma-md">
            <q-item-section>
              <q-item-label class="text-black">{{ letCarrera.id }}</q-item-label>
              <q-item-label caption>Id</q-item-label>
            </q-item-section>
          </q-item>

          <q-item clickable v-ripple class="q-ma-md">
            <q-item-section>
              <q-item-label class="text-black">{{ letCarrera.titulo === null || letCarrera.titulo === '' ? 'SIN DATOS' : letCarrera.titulo }}</q-item-label>
              <q-item-label caption>Titulo</q-item-label>
            </q-item-section>
          </q-item>

          <q-item clickable v-ripple class="q-ma-md">
            <q-item-section>
              <q-item-label class="text-black">{{ letCarrera.grado === null || letCarrera.grado === '' ? 'SIN DATOS' : letCarrera.grado }}</q-item-label>
              <q-item-label caption>Grado Académico</q-item-label>
            </q-item-section>
          </q-item>

        </q-list>
        <div class="row justify-end">
          <q-btn v-close-popup class="q-ma-md bg-c-7 text-white" icon-right="mdi-trash-can"  @click="asyncFunctionBorrarCarrera" label="Eliminar" no-caps />
        </div>
      </q-card-section>
    </q-card>
  </q-dialog>

  <q-dialog v-model="dialogoBuscar" persistent transition-show="scale" transition-hide="scale">
    <q-card class="text-black">
      <q-bar class="bg-c-4">
        <div>Buscar carrera por id</div>
        <q-space />
        <q-btn dense flat icon="close" v-close-popup>
          <q-tooltip class="bg-c-1">Cerrar</q-tooltip>
        </q-btn>
      </q-bar>
      <q-form  @submit.prevent="asyncFunctionBuscarCarreraPorId()" ref="letFormularioId">
        <div class="row justify-around">
          <div class="col-6 q-pa-md">
            <q-input
              v-model="letCarrera.id"
              label="Id"
              type="number"
              outlined
              :rules="[rules.required, rules.minNum, rules.maxNum]"
              clearable>
              <template v-slot:before>
                <q-icon name="mdi-counter" />
              </template>
            </q-input>
          </div>
          <div class="col-6 q-pa-md">
            <q-btn type="submit" class="q-ma-md bg-c-7 text-white" icon-right="mdi-magnify" label="Buscar por id" no-caps />
          </div>
        </div>
      </q-form>
    </q-card>
  </q-dialog>

</template>

<script>
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'
import { ref, reactive } from 'vue'
import { servicioAlertas } from 'app/_helpers/alerta'

const constColumnas = [
  {
    name: 'id',
    label: 'Id Carrera',
    field: 'id',
    sortable: true,
    sortOrder: 'ad',
    align: 'center'
  },
  {
    name: 'titulo',
    label: 'Título',
    field: 'titulo',
    sortable: true,
    sortOrder: 'ad',
    align: 'left'
  },
  {
    name: 'grado',
    label: 'Grado Académico',
    field: 'grado',
    sortable: true,
    sortOrder: 'ad',
    align: 'left'
  },
  {
    name: 'materias',
    label: 'Cantidad materias',
    field: '',
    sortable: true,
    sortOrder: 'ad',
    align: 'center'
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

    const dialogoBuscar = ref(false)
    const dialogoCrear = ref(false)
    const dialogoEliminar = ref(false)

    const letCargando = ref(false)
    const letCarrera = reactive({
      id: null,
      titulo: '',
      grado: '',
      planEstudio: []
    })
    const letCarreras = ref([])
    const letFilter = ref('')
    const letFormulario = ref(null)
    const letFormularioId = ref(null)

    asyncFunctionBuscarCarreras()

    function functionAccionBuscarPorId () {
      dialogoBuscar.value = true
    }

    function functionAccionCancelar () {
      functionOcultar()
      functionLimpiar()
    }

    function functionAccionCrear () {
      functionLimpiar()
      dialogoCrear.value = true
    }

    function functionAccionEditar (props) {
      vueStore.commit('setAccionCarrera', 'editar')
      vueStore.commit('setCarrera', props.row)
      vueRouter.push({ name: 'Carrera' })
    }

    function functionAccionEliminar (props) {
      Object.assign(letCarrera, props.row)
      dialogoEliminar.value = true
    }

    function functionLimpiar () {
      letCarrera.id = null
      letCarrera.titulo = ''
      letCarrera.grado = ''
      letCarrera.planEstudio = []
    }

    function functionOcultar () {
      dialogoCrear.value = false
      dialogoEliminar.value = false
    }

    function functionAccionVer (props) {
      vueStore.commit('setAccionCarrera', 'ver')
      vueStore.commit('setCarrera', props.row)
      vueRouter.push({ name: 'Carrera' })
    }

    async function asyncFunctionBorrarCarrera () {
      try {
        letCargando.value = true
        const result = await vueStore.dispatch('delCarrera', letCarrera.id)
        if (result.status === 200) {
          servicioAlertas.alertaExito('Carrera eliminada correctamente')
          const arreglo = letCarreras.value.filter(function (carreraEliminada) {
            return carreraEliminada.id !== letCarrera.id
          })
          letCarreras.value = [...arreglo]
        } else if (result.status === 202) {
          const mensaje = 'Error al eliminar ' + result.headers.estado
          servicioAlertas.infoAdvertencia(mensaje)
          console.warn(mensaje)
        } else {
          const mensaje = 'Error al eliminar ' + result.status
          servicioAlertas.infoError(mensaje)
          console.error(mensaje)
        }
        letCargando.value = false
        functionOcultar()
      } catch (err) {
        letCargando.value = false
        functionOcultar()
        servicioAlertas.infoAdvertencia('No se puede eliminar la entidad ya que existen estudiantes que dependen de ella.')
      }
    }

    async function asyncFunctionBuscarCarreraPorId () {
      try {
        letCargando.value = true
        const datos = await vueStore.dispatch('getCarreraPorId', letCarrera.id)
        if (datos.status === 200) {
          letCargando.value = false
          vueStore.commit('setCarrera', await datos.data)
          vueStore.commit('setAccionCarrera', 'ver')
          servicioAlertas.alertaExito('Se encontró la Carrera.')
          vueRouter.push({ name: 'Carrera' })
        } else if (datos.status === 202) {
          const mensaje = 'No se encontro la identidad ' + datos.headers.estado
          servicioAlertas.infoAdvertencia(mensaje)
          console.warn(mensaje)
        } else {
          const mensaje = 'Error al cargar carreras ' + datos.status
          servicioAlertas.infoError(mensaje)
          console.error(mensaje)
        }
        functionOcultar()
        letCargando.value = false
      } catch (err) {
        letCargando.value = false
        functionOcultar()
        const error = 'Hubo un error al intentar buscar la carrera ' + err
        servicioAlertas.infoError(error)
        console.error(error)
      }
    }

    async function asyncFunctionBuscarCarreras () {
      try {
        letCargando.value = true
        const datos = await vueStore.dispatch('getCarreras')
        if (datos.status === 200) {
          letCargando.value = false
          letCarreras.value = await datos.data
        } else if (datos.status === 202) {
          const mensaje = 'Error al cargar carreras ' + datos.headers.estado
          servicioAlertas.infoAdvertencia(mensaje)
          console.warn(mensaje)
        } else {
          const mensaje = 'Error al cargar carreras ' + datos.status
          servicioAlertas.infoError(mensaje)
          console.error(mensaje)
        }
        functionOcultar()
        letCargando.value = false
      } catch (err) {
        letCargando.value = false
        functionOcultar()
        const error = 'Hubo un error al intentar cargar las carreras ' + err
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
          const result = await vueStore.dispatch('saveCarrera', objeto)
          if (result.status === 201) {
            servicioAlertas.alertaExito('Carrera guardada correctamente')
            asyncFunctionBuscarCarreras()
          } else if (result.status === 202) {
            const mensaje = 'Error al guardar ' + result.headers.estado
            servicioAlertas.infoAdvertencia(mensaje)
            console.warn(mensaje)
          } else {
            const mensaje = 'Error al guardar ' + result.status
            servicioAlertas.infoError(mensaje)
            console.error(mensaje)
          }
          letCargando.value = false
          functionOcultar()
        } catch (err) {
          const mensaje = 'Error al guardar ' + err
          servicioAlertas.infoError(mensaje)
          console.error(mensaje)
          letCargando.value = false
          functionOcultar()
        }
      } else {
        servicioAlertas.alertaError('Formulario no validado')
      }
    }

    return {
      asyncFunctionBorrarCarrera,
      asyncFunctionBuscarCarreraPorId,
      asyncFunctionGuardarCarrera,

      functionAccionBuscarPorId,
      functionAccionCancelar,
      functionAccionCrear,
      functionAccionEditar,
      functionAccionEliminar,
      functionAccionVer,

      constColumnas,
      constGrados,
      constPaginacion,

      letCargando,
      letCarrera,
      letCarreras,
      letFilter,
      letFormulario,
      letFormularioId,

      dialogoBuscar,
      dialogoCrear,
      dialogoEliminar,

      exportTable () {
        let csvContent = 'data:text/csv;charset=utf-8,'
        csvContent += [
          Object.keys(letCarreras.value[0]).join(';'),
          ...letCarreras.value.map(item => Object.values(item).join(';'))
        ]
          .join('\n')
          .replace(/(^\[)|(\]$)/gm, '')

        const data = encodeURI(csvContent)
        const link = document.createElement('a')
        link.setAttribute('href', data)
        link.setAttribute('download', 'carreras.csv')
        link.click()
      },

      rules: {
        required: (v) => !!v || 'Debés completar el campo',
        min: (v) => v.length >= 3 || 'Al menos 3 carácteres',
        max: (v) => v.length <= 50 || 'Máximo 50 carácteres',
        minNum: (v) => v.length >= 1 || 'Al menos 1 carácteres',
        maxNum: (v) => v.length <= 7 || 'Cómo máximo 7 carácteres'
      }
    }
  }
}
</script>

<style scoped>
a {
  color: blue !important;
}
</style>
