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
            <q-btn class="text-black bg-c-5 q-ma-sm" icon-right="mdi-chair-rolling" :loading="letCargando" v-if="letEditar" label="Inscribir en carrera" no-caps type="submit" />
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
    </div>
  </div>

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
    const letEditar = ref(false)
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
    const letMaterias = ref([])
    const letVer = ref(false)

    const dialogoCrear = ref(false)
    const dialogoQuitar = ref(false)

    asyncFunctionBuscarMaterias()

    if (vueStore.state.estudiante.estudiante !== null) {
      asyncFunctionBuscarEstudianteCarreraPorIdEstudiante(vueStore.state.estudiante.estudiante.id)
      letEditar.value = false
      letVer.value = false
      Object.assign(letEstudiante, vueStore.state.estudiante.estudiante)
      letEstudiante.ingresoEstudiante = helper.getFormatoFecha(letEstudiante.ingresoEstudiante)
      letEstudiante.ingresoLead = helper.getFormatoFecha(letEstudiante.ingresoLead)
      if (vueStore.state.estudiante.accion === 'ver') {
        letVer.value = true
      } else if (vueStore.state.estudiante.accion === 'editar') {
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

    function functionOcultar () {
      dialogoQuitar.value = false
      dialogoCrear.value = false
    }

    async function asyncFunctionAgregarMateriaAEstudiante (props) {
      try {
        letCargando.value = true
        const objeto = {
          idEstudiante: letEstudiante.id,
          idMateria: props.row.id
        }
        await vueStore.dispatch('agregarMateriaAEstudiante', objeto).then((result) => {
          if (result.status === 200) {
            servicioAlertas.alertaExito('La materia fue agregada correctamente del plan de estudios.')
            Object.assign(letEstudiante, result.data)
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

    async function asyncFunctionBorrarMateriaDeEstudiante () {
      try {
        letCargando.value = true
        const objeto = {
          idEstudiante: letEstudiante.id,
          idMateria: letMateria.id
        }
        await vueStore.dispatch('delMateriaDeEstudiante', objeto).then((result) => {
          if (result.status === 200) {
            servicioAlertas.alertaExito('La materia fue eliminada correctamente del plan de estudios.')
            Object.assign(letEstudiante, result.data)
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

    async function asyncFunctionBuscarEstudianteCarreraPorIdEstudiante (id) {
      try {
        letCargando.value = true
        const datos = await vueStore.dispatch('getEstudianteCarreraPorIdEstudiante', id)
        if (datos.status === 200) {
          letCargando.value = false
          letEstudianteCarreras.value = await datos.data
          console.log(letEstudianteCarreras)
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
        const datos = await vueStore.dispatch('getEstudianteMateriaPorIdEstudianteIdCarrera', objeto)
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
          await vueStore.dispatch('saveEstudiante', objeto).then((result) => {
            if (result.status === 201) {
              servicioAlertas.alertaExito('Estudiante guardado correctamente')
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
          console.error('Error front', 'panel-images -> image -> save error -> ' + err.message)
          console.error('Error api', err.headers.estado)
          servicioAlertas.alertaError(err.headers.estado)
        }
      } else {
        servicioAlertas.alertaError('Formulario NO validado')
      }
    }

    return {
      asyncFunctionAgregarMateriaAEstudiante,
      asyncFunctionBorrarMateriaDeEstudiante,
      asyncFunctionBuscarEstudianteCarreraPorIdEstudiante,
      asyncFunctionBuscarEstudianteMateriasPorEstudianteCarrera,
      asyncFunctionGuardarEstudiante,

      constColumnas,
      constPaginacion,

      dialogoCrear,
      dialogoQuitar,

      functionAccionCrear,
      functionAccionEditar,
      functionAccionEliminar,

      letCargando,
      letEditar,
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
      letMaterias,
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
