<template>
  <div class="row fuente9">
    <div class="col-12">
      <q-form ref="letFormulario" @submit.prevent="asyncFunctionGuardarLead">
        <div class="row q-pa-sm">
          <div class="col-12 col-sm-6 q-pa-sm">
            <q-input
              v-model="letLead.id"
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
          <div class="col-12 col-sm-6 q-pa-sm">
            <q-input
              v-model="letLead.ingresoLead"
              label="Ingreso Lead"
              outlined
              counter
              clearable
              disable
            >
              <template v-slot:before>
                <q-icon name="mdi-account-convert-outline" />
              </template>
            </q-input>
          </div>
        </div>
        <div class="row q-pa-sm items-center items-baseline">
          <div class="col-12 col-sm-6 q-pa-sm">
            <q-input
              v-model="letLead.nombre"
              label="Nombre"
              outlined
              counter
              maxlength="50"
              clearable
              :disable="letVer"
              :rules="[rules.required, rules.min, rules.max]"
            >
              <template v-slot:before>
                <q-icon name="mdi-alpha-n-box" />
              </template>
            </q-input>
          </div>
          <div class="col-12 col-sm-6 q-pa-sm">
            <q-input
              v-model="letLead.apellido"
              label="Apellido"
              outlined
              counter
              maxlength="50"
              clearable
              :disable="letVer"
              :rules="[rules.required, rules.min, rules.max]"
            >
              <template v-slot:before>
                <q-icon name="mdi-alpha-a-box" />
              </template>
            </q-input>
          </div>
        </div>
        <div class="row q-pa-sm items-center items-baseline">
          <div class="col-12 col-sm-6 q-pa-sm">
            <q-input
              v-model="letLead.email"
              label="Email"
              outlined
              counter
              maxlength="50"
              clearable
              :disable="letVer"
              :rules="[rules.required, rules.min, rules.max]"
            >
              <template v-slot:before>
                <q-icon name="mdi-alpha-e-box" />
              </template>
            </q-input>
          </div>
          <div class="col-12 col-sm-6 q-pa-sm">
            <q-input
              v-model="letLead.direccion"
              label="Direccion"
              outlined
              counter
              maxlength="100"
              clearable
              :disable="letVer"
              :rules="[rules.required, rules.min, rules.maxTexto]"
            >
              <template v-slot:before>
                <q-icon name="mdi-alpha-d-box" />
              </template>
            </q-input>
          </div>
        </div>
        <div class="row q-pa-sm items-center items-baseline">
          <div class="col-12 col-sm-6 q-pa-sm">
            <q-input
              v-model="letLead.telefono"
              label="Telefono"
              outlined
              counter
              maxlength="50"
              clearable
              :disable="letVer"
              :rules="[rules.required, rules.min, rules.max]"
            >
              <template v-slot:before>
                <q-icon name="mdi-alpha-t-box" />
              </template>
            </q-input>
          </div>
          <div class="col-12 col-sm-6 q-pa-sm">
            <q-input
              v-model="letLead.ingresoLead"
              label="ingresoLead"
              outlined
              counter
              clearable
              disable
            >
              <template v-slot:before>
                <q-icon name="mdi-school" />
              </template>
            </q-input>
          </div>
        </div>
        <div class="row q-pa-sm justify-around">
          <div class="col-12 q-pa-md text-center">
            <q-btn class="text-white bg-c-1 q-ma-sm" icon-right="save" :loading="letCargando" v-if="letEditar || letCrear" label="Guardar" no-caps type="submit" />
            <q-btn class="text-white bg-c-1 q-ma-sm" icon-right="edit" :loading="letCargando" v-if="letVer" label="Editar" no-caps @click="functionAccionEditar" />
            <q-btn class="text-black bg-c-5 q-ma-sm" icon-right="mdi-chair-rolling" :loading="letCargando" v-if="letEditar" label="Inscribir en carrera" no-caps @click="functionAccionInscribirLead" />
          </div>
        </div>
      </q-form>
    </div>
  </div>
  <div class="row">
    <div class="col-12 col-md-4 q-pa-md">
      <q-list bordered separator>
        <q-item
          clickable
          v-ripple
          :active="letLink === leadCarrera.id"
          v-for="leadCarrera in letEstudianteCarreras"
          :key="leadCarrera"
          :name="leadCarrera.id"
          :label="leadCarrera.carrera.titulo"
          @click="asyncFunctionBuscarLeadMateriasPorLeadCarrera(leadCarrera), letLink === leadCarrera.id"
        >
          <q-item-section >
            <q-item-label>{{ leadCarrera.carrera.titulo }}</q-item-label>
            <q-item-label caption>{{ leadCarrera.carrera.grado }}</q-item-label>
          </q-item-section>
        </q-item>
      </q-list>
    </div>
    <div class="col-12 col-md-8 q-pa-md" v-if="letEstudianteMaterias.length > 0">
      <q-table
        :showing="!letCargando"
        bordered
        :columns="constColumnas"
        rows-per-page-label="Registros por pagina"
        no-data-label="Sin datos para mostrar"
        :pagination="constPaginacion"
        :filter="letFilter"
        hide-no-data
        :rows="letEstudianteMaterias"
        row-key="name"
      >
        <template v-slot:body-cell-nombre="props">
          <q-td :props="props">
            {{ props.row.materia.nombre}}
          </q-td>
        </template>
        <template v-slot:body-cell-actions="props">
          <q-td :props="props">
            <q-btn @click="asyncFunctionCursarMateria(props)" dense round flat class="text-c-1" icon="mdi-account-plus" v-if="props.row.estado === 'ESTADO_MATERIA_SIN_CURSAR'" >
              <q-tooltip anchor="top middle" self="bottom middle" :offset="[3, 3]" class="bg-c-1">
                Inscrbir
              </q-tooltip>
            </q-btn>
            <q-btn @click="asyncFunctionAprobarMateria(props)" dense round flat class="text-c-4" icon="mdi-account-check" v-if="props.row.estado === 'ESTADO_MATERIA_CURSANDO'" >
              <q-tooltip anchor="top middle" self="bottom middle" :offset="[3, 3]" class="bg-c-1">
                Aprobar
              </q-tooltip>
            </q-btn>
            <q-btn @click="asyncFunctionLibreMateria(props)" dense round flat class="text-c-6" icon="mdi-account-remove" v-if="props.row.estado === 'ESTADO_MATERIA_CURSANDO'" >
              <q-tooltip anchor="top middle" self="bottom middle" :offset="[3, 3]" class="bg-c-1">
                Libre
              </q-tooltip>
            </q-btn>
          </q-td>
        </template>
      </q-table>
    </div>
  </div>

  <q-dialog v-model="dialogoInscribirLead" persistent transition-show="scale" transition-hide="scale">
    <q-card class="text-black">
      <q-bar class="bg-c-4">
        <div>Inscribir lead a carrera</div>
        <q-space />
        <q-btn dense flat icon="close" v-close-popup>
          <q-tooltip class="bg-c-1">Cerrar</q-tooltip>
        </q-btn>
      </q-bar>
      <q-table
        :showing="!letCargando"
        bordered
        title="Carreras"
        :columns="constColumnasCarreras"
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
        </template>
        <template v-slot:body-cell-materias="props">
          <q-td :props="props">
            {{ props.row.planEstudio.length }}
          </q-td>
        </template>
        <template v-slot:body-cell-actions="props">
          <q-td :props="props">
            <q-btn @click="asyncFunctionInscribirLeadACarrera(props)" dense round flat class="text-c-1" icon="mdi-account-plus">
              <q-tooltip anchor="top middle" self="bottom middle" :offset="[3, 3]" class="bg-c-1">
                Inscribir
              </q-tooltip>
            </q-btn>
          </q-td>
        </template>
      </q-table>
    </q-card>
  </q-dialog>

</template>

<script>
import { useStore } from 'vuex'
import { reactive, ref } from 'vue'
import { servicioAlertas } from 'app/_helpers/alerta'
import { useRouter } from 'vue-router'
import helper from 'app/_helpers/helper'

const constColumnas = [
  {
    name: 'id',
    label: 'Id Materia',
    field: 'id',
    sortable: true,
    sortOrder: 'ad',
    align: 'left'
  },
  {
    name: 'nombre',
    label: 'Nombre',
    field: '',
    sortable: true,
    sortOrder: 'ad',
    align: 'left'
  },
  {
    name: 'estado',
    label: 'Estado',
    field: 'estado',
    sortable: true,
    sortOrder: 'ad',
    align: 'left'
  },
  {
    name: 'cursada',
    label: 'Veces cursada',
    field: 'cursada',
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

const constColumnasCarreras = [
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

    const letCargando = ref(false)
    const letCarreras = ref([])
    const letCrear = ref(false)
    const letEditar = ref(false)
    const letEstadoCarrera = ref(false)
    const letLead = reactive({
      id: null,
      nombre: '',
      apellido: '',
      email: '',
      direccion: '',
      telefono: '',
      status: '',
      ingresoLead: '',
      ingresoEstudiante: ''
    })
    const letEstudianteCarrera = reactive({
      id: null,
      estudiante: '',
      carrera: '',
      ingreso: '',
      egreso: ''
    })
    const letEstudianteCarreras = ref([])
    const letEstudianteMateria = reactive({
      id: null,
      estudiante: '',
      materia: '',
      carrera: '',
      estado: '',
      cursada: '',
      inicioCursado: '',
      finCursado: ''
    })
    const letEstudianteMaterias = ref([])
    const letFilter = ref('')
    const letFilterMaterias = ref('')
    const letFormulario = ref(null)
    const letLink = ref('')
    const letMateria = reactive({
      id: null,
      nombre: ''
    })
    const letVer = ref(false)

    const dialogoCrear = ref(false)
    const dialogoInscribirLead = ref(false)
    const dialogoQuitar = ref(false)

    if (vueStore.state.estudiante.lead !== null) {
      asyncFunctionBuscarLeadCarreraPorIdLead(vueStore.state.estudiante.lead.id)
      letEditar.value = false
      letVer.value = false
      letCrear.value = false
      Object.assign(letLead, vueStore.state.estudiante.lead)
      letLead.ingresoLead = helper.getFormatoFecha(letLead.ingresoLead)
      if (vueStore.state.estudiante.accionEstudiante === 'ver') {
        letVer.value = true
      } else if (vueStore.state.estudiante.accionEstudiante === 'editar') {
        letEditar.value = true
      }
    } else if (vueStore.state.estudiante.accionEstudiante === 'crear') {
      letCrear.value = true
    } else {
      vueRouter.push({ name: 'Leads' })
    }

    function functionAccionCrear () {
      dialogoCrear.value = true
    }

    function functionAccionEditar () {
      letVer.value = false
      letEditar.value = true
    }

    function functionAccionEliminar (props) {
      Object.assign(letMateria, props.row)
      dialogoQuitar.value = true
    }

    function functionAccionInscribirLead () {
      asyncFunctionBuscarCarreras()
      dialogoInscribirLead.value = true
    }

    function functionOcultar () {
      dialogoQuitar.value = false
      dialogoCrear.value = false
    }

    async function asyncFunctionAprobarMateria (props) {
      try {
        letCargando.value = true
        const result = await vueStore.dispatch('aprobarEstudiante', props.row)
        if (result.status === 200) {
          servicioAlertas.alertaExito('La materia fue aprobada correctamente.')
          asyncFunctionBuscarLeadMateriasPorLeadCarrera(props.row)
        }
        if (result.status === 202) {
          servicioAlertas.alertaAdvertencia(result.headers.estado)
          console.warn('Advertencia', result.headers.estado)
        }
        letCargando.value = false
        functionOcultar()
      } catch (err) {
        letCargando.value = false
        console.error('Error front', 'assign -> error -> ' + err.message)
        console.error('Error api', err.headers.estado)
        servicioAlertas.alertaError(err.headers.estado)
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

    async function asyncFunctionBuscarLeadCarreraPorIdLead (id) {
      try {
        letCargando.value = true
        const datos = await vueStore.dispatch('getEstudianteCarreraPorIdEstudiante', id)
        if (datos.status === 200) {
          letCargando.value = false
          letEstudianteCarreras.value = await datos.data
        } else if (datos.status === 202) {
          const mensaje = 'Error al cargar las carreras del lead ' + datos.headers.estado
          console.warn(mensaje)
        } else {
          const mensaje = 'Error al cargar las carreras del lead  ' + datos.status
          servicioAlertas.infoError(mensaje)
          console.error(mensaje)
        }
        functionOcultar()
        letCargando.value = false
      } catch (err) {
        letCargando.value = false
        functionOcultar()
        const error = 'Hubo un error al intentar cargar las carreras del lead  ' + err
        console.error(error)
      }
    }

    async function asyncFunctionBuscarLeadMateriasPorLeadCarrera (objeto) {
      try {
        letCargando.value = true
        const datos = await vueStore.dispatch('getEstudianteMateriaPorIdEstudianteIdCarrera', objeto)
        if (datos.status === 200) {
          letCargando.value = false
          letEstudianteMaterias.value = await datos.data
        } else if (datos.status === 202) {
          const mensaje = 'Error al cargar las materias del lead ' + datos.headers.estado
          console.warn(mensaje)
        } else {
          const mensaje = 'Error al cargar las materias del lead  ' + datos.status
          servicioAlertas.infoError(mensaje)
          console.error(mensaje)
        }
        functionOcultar()
        letCargando.value = false
      } catch (err) {
        letCargando.value = false
        functionOcultar()
        const error = 'Hubo un error al intentar cargar las materias del lead  ' + err
        servicioAlertas.infoError(error)
        console.error(error)
      }
    }

    async function asyncFunctionCursarMateria (props) {
      try {
        letCargando.value = true
        const result = await vueStore.dispatch('inscribirEstudiante', props.row)
        if (result.status === 200) {
          servicioAlertas.alertaExito('La materia fue inscripta correctamente.')
          asyncFunctionBuscarLeadMateriasPorLeadCarrera(props.row)
        }
        if (result.status === 202) {
          servicioAlertas.alertaAdvertencia(result.headers.estado)
          console.warn('Advertencia', result.headers.estado)
        }
        letCargando.value = false
        functionOcultar()
      } catch (err) {
        letCargando.value = false
        console.error('Error front', 'assign -> error -> ' + err.message)
        console.error('Error api', err.headers.estado)
        servicioAlertas.alertaError(err.headers.estado)
      }
    }

    async function asyncFunctionGuardarLead () {
      if (letFormulario.value.validate()) {
        try {
          letCargando.value = true
          const objeto = {
            id: letLead.id === null ? null : letLead.id,
            nombre: letLead.nombre,
            apellido: letLead.apellido,
            email: letLead.email,
            direccion: letLead.direccion,
            telefono: letLead.telefono,
            status: 'ESTADO_LEAD'
          }
          await vueStore.dispatch('saveEstudiante', objeto).then((result) => {
            if (result.status === 201) {
              servicioAlertas.alertaExito('Lead guardado correctamente')
              vueRouter.push({ name: 'Leads' })
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

    async function asyncFunctionInscribirLeadACarrera (props) {
      try {
        letCargando.value = true
        dialogoInscribirLead.value = false
        const objeto = {
          idEstudiante: letLead.id,
          idCarrera: props.row.id
        }
        await vueStore.dispatch('enrollEstudianteCarrera', objeto).then((result) => {
          if (result.status === 200) {
            asyncFunctionBuscarLeadCarreraPorIdLead(objeto.idEstudiante)
            servicioAlertas.alertaExito('Se inscribio correctamente el lead a la carrera.')
            vueRouter.push({ name: 'Estudiantes' })
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
    }

    async function asyncFunctionLibreMateria (props) {
      try {
        letCargando.value = true
        const result = await vueStore.dispatch('liberarEstudiante', props.row)
        if (result.status === 200) {
          servicioAlertas.alertaExito('El lead quedo libre correctamente.')
          asyncFunctionBuscarLeadMateriasPorLeadCarrera(props.row)
        }
        if (result.status === 202) {
          servicioAlertas.alertaAdvertencia(result.headers.estado)
          console.warn('Advertencia', result.headers.estado)
        }
        letCargando.value = false
        functionOcultar()
      } catch (err) {
        letCargando.value = false
        console.error('Error front', 'assign -> error -> ' + err.message)
        console.error('Error api', err.headers.estado)
        servicioAlertas.alertaError(err.headers.estado)
      }
    }

    return {
      asyncFunctionAprobarMateria,
      asyncFunctionBuscarCarreras,
      asyncFunctionBuscarLeadCarreraPorIdLead,
      asyncFunctionBuscarLeadMateriasPorLeadCarrera,
      asyncFunctionCursarMateria,
      asyncFunctionGuardarLead,
      asyncFunctionInscribirLeadACarrera,
      asyncFunctionLibreMateria,

      constColumnas,
      constColumnasCarreras,
      constPaginacion,

      dialogoCrear,
      dialogoInscribirLead,
      dialogoQuitar,

      functionAccionCrear,
      functionAccionEditar,
      functionAccionEliminar,
      functionAccionInscribirLead,

      letCargando,
      letCarreras,
      letCrear,
      letEditar,
      letEstadoCarrera,
      letLead,
      letEstudianteCarrera,
      letEstudianteCarreras,
      letEstudianteMateria,
      letEstudianteMaterias,
      letFilter,
      letFilterMaterias,
      letFormulario,
      letLink,
      letMateria,
      letVer,

      rules: {
        required: (v) => !!v || 'Debés completar el campo',
        min: (v) => v.length >= 3 || 'Al menos 3 carácteres',
        max: (v) => v.length <= 50 || 'Máximo 50 carácteres',
        maxTexto: (v) => v.length <= 100 || 'Máximo 100 carácteres'
      },

      tab: ref(''),
      splitterModel: ref(40)
    }
  }
}
</script>
