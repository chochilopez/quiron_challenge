<template>
  <div class="row fuente9">
    <div class="col-12">
      <q-form ref="letFormulario" @submit.prevent="asyncFunctionGuardarEstudiante">
        <div class="row q-pa-sm">
          <div class="col-12 col-sm-6 q-pa-sm">
            <q-input
              v-model="letEstudiante.id"
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
              v-model="letEstudiante.ingresoLead"
              label="Ingreso Lead"
              outlined
              counter
              maxlength="50"
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
              v-model="letEstudiante.nombre"
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
              v-model="letEstudiante.apellido"
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
              v-model="letEstudiante.email"
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
              v-model="letEstudiante.direccion"
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
              v-model="letEstudiante.telefono"
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
              v-model="letEstudiante.ingresoEstudiante"
              label="ingresoEstudiante"
              outlined
              counter
              maxlength="50"
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
            <q-btn class="text-white bg-c-1 q-ma-sm" icon-right="save" :loading="letCargando" v-if="letEditar" label="Guardar" no-caps type="submit" />
            <q-btn class="text-white bg-c-1 q-ma-sm" icon-right="edit" :loading="letCargando" v-if="letVer" label="Editar" no-caps @click="functionAccionEditar" />
            <q-btn class="text-black bg-c-5 q-ma-sm" icon-right="mdi-chair-rolling" :loading="letCargando" v-if="letEditar" label="Inscribir en carrera" no-caps @click="functionAccionInscribirEstudiante" />
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
          :active="letLink === estudianteCarrera.id"
          v-for="estudianteCarrera in letEstudianteCarreras"
          :key="estudianteCarrera"
          :name="estudianteCarrera.id"
          :label="estudianteCarrera.carrera.titulo"
          @click="asyncFunctionBuscarEstudianteMateriasPorEstudianteCarrera(estudianteCarrera), letLink === estudianteCarrera.id"
        >
          <q-item-section >
            <q-item-label>{{ estudianteCarrera.carrera.titulo }}</q-item-label>
            <q-item-label caption>{{ estudianteCarrera.carrera.grado }}</q-item-label>
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

  <q-dialog v-model="dialogoInscribirEstudiante" persistent transition-show="scale" transition-hide="scale">
    <q-card class="text-black">
      <q-bar class="bg-c-4">
        <div>Inscribir estudiante a carrera</div>
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
            <q-btn @click="asyncFunctionInscribirEstudianteACarrera(props)" dense round flat class="text-c-1" icon="mdi-account-plus">
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
import { useEstudianteCarreraStore } from 'stores/estudiante_carrera'
import { useCarreraStore } from 'stores/carrera'
import { useEstudianteStore } from 'stores/estudiante'
import { useEstudianteMateriaStore } from 'stores/estudiante_materia'
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
    const storeEstudiante = useEstudianteStore()
    const storeEstudianteCarrera = useEstudianteCarreraStore()
    const storeCarrera = useCarreraStore()
    const storeEstudianteMateria = useEstudianteMateriaStore()

    const letCargando = ref(false)
    const letCarreras = ref([])
    const letEditar = ref(false)
    const letEstadoCarrera = ref(false)
    const letEstudiante = reactive({
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
    const dialogoInscribirEstudiante = ref(false)
    const dialogoQuitar = ref(false)

    if (storeEstudiante.estudiante !== null) {
      asyncFunctionBuscarEstudianteCarreraPorIdEstudiante(storeEstudiante.estudiante.id)
      letEditar.value = false
      letVer.value = false
      Object.assign(letEstudiante, storeEstudiante.estudiante)
      letEstudiante.ingresoEstudiante = helper.getFormatoFecha(letEstudiante.ingresoEstudiante)
      letEstudiante.ingresoLead = helper.getFormatoFecha(letEstudiante.ingresoLead)
      if (storeEstudiante.accionEstudiante === 'ver') {
        letVer.value = true
      } else if (storeEstudiante.accionEstudiante === 'editar') {
        letEditar.value = true
      }
    } else {
      vueRouter.push({ name: 'Estudiantes' })
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

    function functionAccionInscribirEstudiante () {
      asyncFunctionBuscarCarreras()
      dialogoInscribirEstudiante.value = true
    }

    function functionOcultar () {
      dialogoQuitar.value = false
      dialogoCrear.value = false
    }

    async function asyncFunctionAprobarMateria (props) {
      try {
        letCargando.value = true
        const result = await storeEstudianteMateria.aprobarEstudiante(props.row)
        if (result.status === 200) {
          servicioAlertas.alertaExito('La materia fue aprobada correctamente.')
          asyncFunctionBuscarEstudianteMateriasPorEstudianteCarrera(props.row)
        }
        if (result.status === 202) {
          servicioAlertas.alertaAdvertencia(result.headers.estado)
          console.warn('Advertencia', result.headers.estado)
        }
        letCargando.value = false
        functionOcultar()
      } catch (err) {
        letCargando.value = false
        const mensaje = 'Hubo un error al aprobar la materia: ' + err.message
        console.error(mensaje)
        servicioAlertas.alertaError(mensaje)
      }
    }

    async function asyncFunctionBuscarCarreras () {
      try {
        letCargando.value = true
        const datos = await storeCarrera.getCarreras()
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

    async function asyncFunctionBuscarEstudianteCarreraPorIdEstudiante (id) {
      try {
        letCargando.value = true
        const datos = await storeEstudianteCarrera.getEstudianteCarreraPorIdEstudiante(id)
        if (datos.status === 200) {
          letCargando.value = false
          letEstudianteCarreras.value = await datos.data
        } else if (datos.status === 202) {
          const mensaje = 'Error al cargar las carreras del estudiante ' + datos.headers.estado
          servicioAlertas.infoAdvertencia(mensaje)
          console.warn(mensaje)
        } else {
          const mensaje = 'Error al cargar las carreras del estudiante  ' + datos.status
          servicioAlertas.infoError(mensaje)
          console.error(mensaje)
        }
        functionOcultar()
        letCargando.value = false
      } catch (err) {
        letCargando.value = false
        functionOcultar()
        const error = 'Hubo un error al intentar cargar las carreras del estudiante  ' + err
        servicioAlertas.infoError(error)
        console.error(error)
      }
    }

    async function asyncFunctionBuscarEstudianteMateriasPorEstudianteCarrera (objeto) {
      try {
        letCargando.value = true
        const datos = await storeEstudianteMateria.getEstudianteMateriaPorIdEstudianteIdCarrera(objeto)
        if (datos.status === 200) {
          letCargando.value = false
          letEstudianteMaterias.value = await datos.data
        } else if (datos.status === 202) {
          const mensaje = 'Error al cargar las materias del estudiante ' + datos.headers.estado
          servicioAlertas.infoAdvertencia(mensaje)
          console.warn(mensaje)
        } else {
          const mensaje = 'Error al cargar las materias del estudiante  ' + datos.status
          servicioAlertas.infoError(mensaje)
          console.error(mensaje)
        }
        functionOcultar()
        letCargando.value = false
      } catch (err) {
        letCargando.value = false
        functionOcultar()
        const error = 'Hubo un error al intentar cargar las materias del estudiante  ' + err
        servicioAlertas.infoError(error)
        console.error(error)
      }
    }

    async function asyncFunctionCursarMateria (props) {
      try {
        letCargando.value = true
        const result = await storeEstudianteMateria.inscribirEstudiante(props.row)
        if (result.status === 200) {
          servicioAlertas.alertaExito('La materia fue inscripta correctamente.')
          asyncFunctionBuscarEstudianteMateriasPorEstudianteCarrera(props.row)
        }
        if (result.status === 202) {
          servicioAlertas.alertaAdvertencia(result.headers.estado)
          console.warn('Advertencia', result.headers.estado)
        }
        letCargando.value = false
        functionOcultar()
      } catch (err) {
        letCargando.value = false
        const mensaje = 'Hubo un error al inscribirse a la materia: ' + err.message
        console.error(mensaje)
        servicioAlertas.alertaError(mensaje)
      }
    }

    async function asyncFunctionGuardarEstudiante () {
      if (letFormulario.value.validate()) {
        try {
          letCargando.value = true
          const objeto = {
            id: letEstudiante.id,
            nombre: letEstudiante.nombre,
            apellido: letEstudiante.apellido,
            email: letEstudiante.email,
            direccion: letEstudiante.direccion,
            telefono: letEstudiante.telefono
          }
          const result = await storeEstudiante.saveEstudiante(objeto)
          if (result.status === 201) {
            servicioAlertas.alertaExito('Estudiante guardado correctamente')
            vueRouter.push({ name: 'Estudiantes' })
          } else if (result.status === 202) {
            servicioAlertas.alertaAdvertencia(result.headers.estado)
            console.warn('Advertencia', result.headers.estado)
          }
          letCargando.value = false
          functionOcultar()
        } catch (err) {
          letCargando.value = false
          const mensaje = 'Hubo un error al guardar el estudiante: ' + err.message
          console.error(mensaje)
          servicioAlertas.alertaError(mensaje)
        }
      }
    }

    async function asyncFunctionInscribirEstudianteACarrera (props) {
      try {
        letCargando.value = true
        dialogoInscribirEstudiante.value = false
        const objeto = {
          idEstudiante: letEstudiante.id,
          idCarrera: props.row.id
        }
        const result = await storeEstudianteCarrera.enrollEstudianteCarrera(objeto)
        if (result.status === 200) {
          asyncFunctionBuscarEstudianteCarreraPorIdEstudiante(objeto.idEstudiante)
          servicioAlertas.alertaExito('Se inscribio correctamente el estudiante a la carrera.')
        } else if (result.status === 202) {
          servicioAlertas.alertaAdvertencia(result.headers.estado)
          console.warn('Advertencia', result.headers.estado)
        }
        letCargando.value = false
        functionOcultar()
      } catch (err) {
        letCargando.value = false
        const mensaje = 'Hubo un error al inscribir el estudiante a la carrera: ' + err.message
        console.error(mensaje)
        servicioAlertas.alertaError(mensaje)
      }
    }

    async function asyncFunctionLibreMateria (props) {
      try {
        letCargando.value = true
        const result = await storeEstudianteMateria.liberarEstudiante(props.row)
        if (result.status === 200) {
          servicioAlertas.alertaExito('El estudiante quedo libre correctamente.')
          asyncFunctionBuscarEstudianteMateriasPorEstudianteCarrera(props.row)
        }
        if (result.status === 202) {
          servicioAlertas.alertaAdvertencia(result.headers.estado)
          console.warn('Advertencia', result.headers.estado)
        }
        letCargando.value = false
        functionOcultar()
      } catch (err) {
        letCargando.value = false
        const mensaje = 'Hubo un error al liberar el estudiante de la carrera: ' + err.message
        console.error(mensaje)
        servicioAlertas.alertaError(mensaje)
      }
    }

    return {
      asyncFunctionAprobarMateria,
      asyncFunctionBuscarCarreras,
      asyncFunctionBuscarEstudianteCarreraPorIdEstudiante,
      asyncFunctionBuscarEstudianteMateriasPorEstudianteCarrera,
      asyncFunctionCursarMateria,
      asyncFunctionGuardarEstudiante,
      asyncFunctionInscribirEstudianteACarrera,
      asyncFunctionLibreMateria,

      constColumnas,
      constColumnasCarreras,
      constPaginacion,

      dialogoCrear,
      dialogoInscribirEstudiante,
      dialogoQuitar,

      functionAccionCrear,
      functionAccionEditar,
      functionAccionEliminar,
      functionAccionInscribirEstudiante,

      letCargando,
      letCarreras,
      letEditar,
      letEstadoCarrera,
      letEstudiante,
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
