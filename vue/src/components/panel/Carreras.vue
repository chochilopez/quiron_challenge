<template>
  <div class="row q-pa-md fuente3">
    <div class="col">
      <q-table
        :showing="!cargando"
        bordered
        title="Carreras"
        :columns="columnas"
        rows-per-page-label="Registros por pagina"
        no-data-label="Sin datos para mostrar"
        :pagination="paginacion"
        :filter="filter"
        hide-no-data
        :rows="carreras"
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
            label="Nueva carrera"
            no-caps
            @click="crear" />
          <q-btn
            class="q-ml-md bg-c-5 text-black"
            icon-right="archive"
            label="Exportar"
            no-caps
            @click="exportTable" />
        </template>
        <template v-slot:body-cell-materias="props">
          <q-td :props="props">
            {{ props.row.planEstudio.length }}
          </q-td>
        </template>
        <template v-slot:body-cell-imagen="icons">
          <q-td :props="icons">
            <q-icon :name="icons.row.icono" size="md"></q-icon>
          </q-td>
        </template>
        <template v-slot:body-cell-actions="props">
          <q-td :props="props">
            <q-btn @click="ver(props)" dense round flat class="text-c-1" icon="visibility">
              <q-tooltip anchor="top middle" self="bottom middle" :offset="[3, 3]" class="bg-c-1">
                Ver
              </q-tooltip>
            </q-btn>
            <q-btn @click="editar(props)" dense round flat class="text-c-4" icon="edit">
              <q-tooltip anchor="top middle" self="bottom middle" :offset="[3, 3]" class="bg-c-1">
                Editar
              </q-tooltip>
            </q-btn>
            <q-btn @click="eliminar(props)" dense round flat class="text-c-6" icon="delete">
              <q-tooltip anchor="top middle" self="bottom middle" :offset="[3, 3]" class="bg-c-1">
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
  </div>

  <q-dialog v-model="dialogoCrear" persistent transition-show="scale" transition-hide="scale">
    <q-card class="text-white text-black" style="width: 400px">
      <q-bar class="bg-c-4">
        <div>Nueva carrera</div>
        <q-space />
        <q-btn dense flat icon="close" v-close-popup>
          <q-tooltip class="bg-c-2 text-white">Cerrar</q-tooltip>
        </q-btn>
      </q-bar>
      <q-card-section>
        <q-form  @submit="guardar()" ref="formulario">
          <div class="row q-pa-sm">
            <div class="col-12">
              <q-input
                v-model="carrera.titulo"
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
                v-model="carrera.grado"
                :options="estados"
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
            <q-btn type="submit" class="text-white bg-primary" icon="save" :loading="cargando">
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
      <q-card-section class="q-pt-none">
        <q-list dense bordered padding class="rounded-borders">

          <q-item clickable v-ripple class="q-ma-md">
            <q-item-section>
              <q-item-label class="text-black">{{ carrera.id }}</q-item-label>
              <q-item-label caption>Id</q-item-label>
            </q-item-section>
          </q-item>

          <q-item clickable v-ripple class="q-ma-md">
            <q-item-section>
              <q-item-label class="text-black">{{ carrera.titulo === null || carrera.titulo === '' ? 'SIN DATOS' : carrera.titulo }}</q-item-label>
              <q-item-label caption>Titulo</q-item-label>
            </q-item-section>
          </q-item>

          <q-item clickable v-ripple class="q-ma-md">
            <q-item-section>
              <q-item-label class="text-black">{{ carrera.grado === null || carrera.grado === '' ? 'SIN DATOS' : carrera.grado }}</q-item-label>
              <q-item-label caption>Grado Académico</q-item-label>
            </q-item-section>
          </q-item>

        </q-list>
        <div class="row justify-end">
          <q-btn v-close-popup stack class="text-white bg-c-1 q-ma-md" icon="mdi-trash-can" @click="borrar" :loading="cargando">
            <template v-slot:loading>
              <q-spinner-facebook />
            </template>
            BORRAR
          </q-btn>
        </div>
      </q-card-section>
    </q-card>
  </q-dialog>

</template>

<script>
import { useStore } from 'vuex'
import { ref, reactive } from 'vue'
import { servicioAlertas } from 'app/_helpers/alerta'
import { useRouter } from 'vue-router'

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

export default {
  setup () {
    const router = useRouter()
    const store = useStore()
    const cargando = ref(false)
    const estados = ref([
      { id: 1, nombre: 'TITULO_PREGRADO' },
      { id: 2, nombre: 'TITULO_GRADO' },
      { id: 3, nombre: 'TITULO_POSTGRADO' }
    ])
    const carreras = ref([])
    const dialogoCrear = ref(false)
    const dialogoEliminar = ref(false)
    const formulario = ref(null)
    const carrera = reactive({
      id: null,
      titulo: '',
      grado: '',
      planEstudio: []
    })

    getCarreras()

    function crear () {
      limpiar()
      dialogoCrear.value = true
    }

    function ver (props) {
      store.commit('setCarrera', props.row)
      router.push({ name: 'Carrera' })
    }

    function editar (props) {
      store.commit('setCarrera', props.row)
      router.push({ name: 'Carrera' })
    }

    function eliminar (props) {
      Object.assign(carrera, props.row)
      dialogoEliminar.value = true
    }

    function limpiar () {
      carrera.id = null
      carrera.titulo = ''
      carrera.grado = ''
      carrera.planEstudio = []
    }

    function ocultar () {
      dialogoCrear.value = false
      dialogoEliminar.value = false
    }

    function cancelar () {
      ocultar()
      limpiar()
    }

    async function guardar () {
      if (formulario.value.validate()) {
        try {
          cargando.value = true
          const objeto = {
            id: carrera.id,
            titulo: carrera.titulo,
            grado: carrera.grado,
            planEstudio: carrera.planEstudio
          }
          await store.dispatch('saveCarrera', objeto).then((result) => {
            if (result.status === 201) {
              servicioAlertas.alertaExito('Carrera guardada correctamente')
              getCarreras().then(() => {
                carreras.value = [...store.state.carrera.carreras]
              })
            } else if (result.status === 202) {
              const mensaje = 'Error al guardar ' + result.headers.estado
              servicioAlertas.infoAdvertencia(mensaje)
              console.warn(mensaje)
            } else {
              const mensaje = 'Error al guardar ' + result.status
              servicioAlertas.infoError(mensaje)
              console.error(mensaje)
            }
          })
          cargando.value = false
          ocultar()
        } catch (err) {
          const mensaje = 'Error al guardar ' + err
          servicioAlertas.infoError(mensaje)
          console.error(mensaje)
          cargando.value = false
          ocultar()
        }
      } else {
        servicioAlertas.alertaError('formulario NO validado')
      }
    }

    async function borrar () {
      try {
        cargando.value = true
        await store.dispatch('delCarrera', carrera.id).then((result) => {
          if (result.status === 200) {
            servicioAlertas.alertaExito('Carrera eliminada correctamente')
            const arreglo = carreras.value.filter(function (carreraEliminada) {
              return carreraEliminada.id !== carrera.id
            })
            carreras.value = [...arreglo]
          } else if (result.status === 202) {
            const mensaje = 'Error al eliminar ' + result.headers.estado
            servicioAlertas.infoAdvertencia(mensaje)
            console.warn(mensaje)
          } else {
            const mensaje = 'Error al eliminar ' + result.status
            servicioAlertas.infoError(mensaje)
            console.error(mensaje)
          }
        })
        cargando.value = false
        ocultar()
      } catch (err) {
        cargando.value = false
        ocultar()
        servicioAlertas.infoAdvertencia('No se puede eliminar la entidad ya que existen estudiantes que dependen de ella.')
      }
    }

    async function getCarreras () {
      try {
        cargando.value = true
        const datos = await store.dispatch('getCarreras')
        if (datos.status === 200) {
          cargando.value = false
          carreras.value = await datos.data
        } else if (datos.status === 202) {
          const mensaje = 'Error al cargar carreras ' + datos.headers.estado
          servicioAlertas.infoAdvertencia(mensaje)
          console.warn(mensaje)
        } else {
          const mensaje = 'Error al cargar carreras ' + datos.status
          servicioAlertas.infoError(mensaje)
          console.error(mensaje)
        }
        ocultar()
        cargando.value = false
      } catch (err) {
        cargando.value = false
        ocultar()
        const error = 'Hubo un error al intentar cargar las carreras ' + err
        servicioAlertas.infoError(error)
        console.error(error)
      }
    }

    return {
      crear,
      ver,
      editar,
      eliminar,
      guardar,
      cancelar,
      borrar,

      estados,

      formulario,
      cargando,

      carrera,
      carreras,

      dialogoCrear,
      dialogoEliminar,

      paginacion,
      columnas,

      filter: ref(''),
      exportTable () {
        let csvContent = 'data:text/csv;charset=utf-8,'
        csvContent += [
          Object.keys(carreras.value[0]).join(';'),
          ...carreras.value.map(item => Object.values(item).join(';'))
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
        required: (value) => !!value || 'Debés completar el campo',
        min: (v) => v.length >= 8 || 'Al menos 8 carácteres',
        max: (v) => v.length <= 50 || 'Cómo máximo 50 carácteres'
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
