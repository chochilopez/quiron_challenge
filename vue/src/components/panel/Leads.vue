<template>
  <div class="row q-pa-md fuente3">
    <div class="col">
      <q-table
        :showing="!letCargando"
        bordered
        title="Leads"
        :columns="constColumnas"
        rows-per-page-label="Registros por pagina"
        no-data-label="Sin datos para mostrar"
        :pagination="constPaginacion"
        :filter="letFilter"
        hide-no-data
        :rows="letLeads"
        row-key="name"
      >
        <template v-slot:top-right>
          <q-input outlined dense debounce="300" v-model="letFilter" placeholder="Buscar" class="q-ma-md">
            <template v-slot:append>
              <q-icon name="search" />
            </template>
          </q-input>
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
          <q-btn
            class="q-ma-md bg-info text-black"
            icon-right="mdi-plus-box"
            label="Agregar Lead"
            no-caps
            @click="functionAccionAgregarLead" />
        </template>
        <template v-slot:body-cell-ingresoLead="props">
          <q-td :props="props">
            {{ functionFormatoFecha(props.row.ingresoLead) }}
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

  <q-dialog v-model="dialogoEliminar" persistent transition-show="scale" transition-hide="scale">
    <q-card class="text-white">
      <q-bar class="bg-c-6">
        <div>Eliminar lead</div>
        <q-space />
        <q-btn dense flat icon="close" v-close-popup>
          <q-tooltip class="bg-c-1 text-white">Cerrar</q-tooltip>
        </q-btn>
      </q-bar>
      <q-card-section class="q-pa-md">
        <q-list dense bordered padding class="rounded-borders">

          <q-item clickable v-ripple class="q-ma-md">
            <q-item-section>
              <q-item-label class="text-black">{{ letLead.id }}</q-item-label>
              <q-item-label caption>Id</q-item-label>
            </q-item-section>
          </q-item>

          <q-item clickable v-ripple class="q-ma-md">
            <q-item-section>
              <q-item-label class="text-black">{{ letLead.nombre === null || letLead.nombre === '' ? 'SIN DATOS' : letLead.nombre }}</q-item-label>
              <q-item-label caption>Nombre</q-item-label>
            </q-item-section>
          </q-item>

          <q-item clickable v-ripple class="q-ma-md">
            <q-item-section>
              <q-item-label class="text-black">{{ letLead.apellido === null || letLead.apellido === '' ? 'SIN DATOS' : letLead.apellido }}</q-item-label>
              <q-item-label caption>Apellido</q-item-label>
            </q-item-section>
          </q-item>

        </q-list>
        <div class="row justify-end">
          <q-btn v-close-popup class="q-ma-md bg-c-7 text-white" icon-right="mdi-trash-can"  @click="asyncFunctionBorrarLead" label="Eliminar" no-caps />
        </div>
      </q-card-section>
    </q-card>
  </q-dialog>

  <q-dialog v-model="dialogoBuscar" persistent transition-show="scale" transition-hide="scale">
    <q-card class="text-black">
      <q-bar class="bg-c-4">
        <div>Buscar lead por id</div>
        <q-space />
        <q-btn dense flat icon="close" v-close-popup>
          <q-tooltip class="bg-c-1">Cerrar</q-tooltip>
        </q-btn>
      </q-bar>
      <q-form  @submit.prevent="asyncFunctionBuscarLeadPorIdYEstado()" ref="letFormularioId">
        <div class="row justify-around">
          <div class="col-6 q-pa-md">
            <q-input
              v-model="letLead.id"
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
import Helper from 'app/_helpers/helper'

const constColumnas = [
  {
    name: 'id',
    label: 'Id Lead',
    field: 'id',
    sortable: true,
    sortOrder: 'ad',
    align: 'center'
  },
  {
    name: 'apellido',
    label: 'Apellido',
    field: 'apellido',
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
    name: 'email',
    label: 'Email',
    field: 'email',
    sortable: true,
    sortOrder: 'ad',
    align: 'left'
  },
  {
    name: 'ingresoLead',
    label: 'Ingreso Lead',
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
    const letFilter = ref('')
    const letFormulario = ref(null)
    const letFormularioId = ref(null)
    const letLead = reactive({
      id: null,
      nombre: '',
      apellido: '',
      email: '',
      direccion: '',
      telefono: '',
      status: '',
      ingresoLead: ''
    })
    const letLeads = ref([])

    asyncFunctionBuscarLeads()

    function functionAccionAgregarLead () {
      vueStore.commit('setLead', null)
      vueStore.commit('setAccionEstudiante', 'crear')
      vueRouter.push({ name: 'Lead' })
    }

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
      vueStore.commit('setAccionEstudiante', 'editar')
      vueStore.commit('setLead', props.row)
      vueRouter.push({ name: 'Lead' })
    }

    function functionAccionEliminar (props) {
      Object.assign(letLead, props.row)
      dialogoEliminar.value = true
    }

    function functionAccionVer (props) {
      vueStore.commit('setAccionEstudiante', 'ver')
      vueStore.commit('setLead', props.row)
      vueRouter.push({ name: 'Lead' })
    }

    function functionFormatoFecha (fecha) {
      return Helper.getFormatoFecha(fecha)
    }

    function functionLimpiar () {
      letLead.id = null
      letLead.nombre = ''
      letLead.apellido = ''
      letLead.email = ''
      letLead.direccion = ''
      letLead.telefono = ''
      letLead.status = ''
      letLead.ingresoLead = ''
    }

    function functionOcultar () {
      dialogoCrear.value = false
      dialogoEliminar.value = false
    }

    async function asyncFunctionBorrarLead () {
      try {
        letCargando.value = true
        const result = await vueStore.dispatch('delEstudiante', letLead.id)
        if (result.status === 200) {
          servicioAlertas.alertaExito('Lead eliminado correctamente')
          const arreglo = letLeads.value.filter(function (estudianteEliminado) {
            return estudianteEliminado.id !== letLead.id
          })
          letLeads.value = [...arreglo]
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
        servicioAlertas.infoAdvertencia('No se puede eliminar la entidad.')
      }
    }

    async function asyncFunctionBuscarLeadPorIdYEstado () {
      try {
        letCargando.value = true
        const objeto = {
          id: letLead.id,
          status: 'ESTADO_LEAD'
        }
        const datos = await vueStore.dispatch('getEstudiantePorIdEstado', objeto)
        if (datos.status === 200) {
          letCargando.value = false
          vueStore.commit('setLead', await datos.data)
          vueStore.commit('setAccionEstudiante', 'ver')
          servicioAlertas.alertaExito('Se encontró la entidad Lead.')
          vueRouter.push({ name: 'Lead' })
        } else if (datos.status === 202) {
          const mensaje = 'No se encontro la identidad ' + datos.headers.estado
          servicioAlertas.infoAdvertencia(mensaje)
          console.warn(mensaje)
        } else {
          const mensaje = 'Error al cargar el lead ' + datos.status
          servicioAlertas.infoError(mensaje)
          console.error(mensaje)
        }
        functionOcultar()
        letCargando.value = false
      } catch (err) {
        letCargando.value = false
        functionOcultar()
        const error = 'Hubo un error al intentar buscar el lead ' + err
        servicioAlertas.infoError(error)
        console.error(error)
      }
    }

    async function asyncFunctionBuscarLeads () {
      try {
        letCargando.value = true
        const datos = await vueStore.dispatch('getLeads')
        if (datos.status === 200) {
          letCargando.value = false
          letLeads.value = await datos.data
        } else if (datos.status === 202) {
          const mensaje = 'Error al cargar leads ' + datos.headers.estado
          servicioAlertas.infoAdvertencia(mensaje)
          console.warn(mensaje)
        } else {
          const mensaje = 'Error al cargar leads ' + datos.status
          servicioAlertas.infoError(mensaje)
          console.error(mensaje)
        }
        functionOcultar()
        letCargando.value = false
      } catch (err) {
        letCargando.value = false
        functionOcultar()
        const error = 'Hubo un error al intentar cargar los leads ' + err
        servicioAlertas.infoError(error)
        console.error(error)
      }
    }

    async function asyncFunctionGuardarLead () {
      if (letFormulario.value.validate()) {
        try {
          letCargando.value = true
          const objeto = {
            id: letLead.id,
            titulo: letLead.titulo,
            grado: letLead.grado
          }
          const result = await vueStore.dispatch('saveEstudiante', objeto)
          if (result.status === 201) {
            servicioAlertas.alertaExito('Lead guardada correctamente')
            asyncFunctionBuscarLeads()
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
      asyncFunctionBorrarLead,
      asyncFunctionBuscarLeadPorIdYEstado,
      asyncFunctionGuardarLead,

      functionAccionAgregarLead,
      functionAccionBuscarPorId,
      functionAccionCancelar,
      functionAccionCrear,
      functionAccionEditar,
      functionAccionEliminar,
      functionAccionVer,
      functionFormatoFecha,

      constColumnas,
      constPaginacion,

      letCargando,
      letLead,
      letLeads,
      letFilter,
      letFormulario,
      letFormularioId,

      dialogoBuscar,
      dialogoCrear,
      dialogoEliminar,

      exportTable () {
        let csvContent = 'data:text/csv;charset=utf-8,'
        csvContent += [
          Object.keys(letLeads.value[0]).join(';'),
          ...letLeads.value.map(item => Object.values(item).join(';'))
        ]
          .join('\n')
          .replace(/(^\[)|(\]$)/gm, '')

        const data = encodeURI(csvContent)
        const link = document.createElement('a')
        link.setAttribute('href', data)
        link.setAttribute('download', 'leads.csv')
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
