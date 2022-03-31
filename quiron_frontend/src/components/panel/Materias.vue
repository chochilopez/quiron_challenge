<template>
  <div class="row q-pa-md fuente3">
    <div class="col">
      <q-table
        :showing="!cargando"
        bordered
        title="Materias"
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
            label="Nueva materia"
            no-caps
            @click="crear" />
          <q-btn
            class="q-ml-md bg-c-5 text-black"
            icon-right="archive"
            label="Exportar"
            no-caps
            @click="exportTable" />
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
  </div>

  <q-dialog v-model="dialogoVer" persistent transition-show="scale" transition-hide="scale">
    <q-card class="text-white">
      <q-bar class="bg-c-5 text-black">
        <div>Ver materia</div>
        <q-space />
        <q-btn dense flat icon="close" v-close-popup>
          <q-tooltip class="bg-c-2 text-white">Cerrar</q-tooltip>
        </q-btn>
      </q-bar>
      <q-card-section>
        <q-list dense bordered padding class="rounded-borders">

          <q-item clickable v-ripple class="q-ma-md">
            <q-item-section>
              <q-item-label class="text-c-2">{{ materia.id }}</q-item-label>
              <q-item-label caption>Id</q-item-label>
            </q-item-section>
          </q-item>

          <q-item clickable v-ripple class="q-ma-md">
            <q-item-section>
              <q-item-label class="bg-c-2">{{ materia.nombre === null || materia.nombre === '' ? 'SIN DATOS' : materia.nombre }}</q-item-label>
              <q-item-label caption>Materia</q-item-label>
            </q-item-section>
          </q-item>

        </q-list>
      </q-card-section>
    </q-card>
  </q-dialog>

</template>

<script>
import { ref, reactive } from 'vue'
import { servicioAlertas } from 'app/_helpers/alerta'
import { useMateriaStore } from 'stores/materia'

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
    const storeMateria = useMateriaStore()
    const cargando = ref(false)
    const materias = ref([])
    const dialogoVer = ref(false)
    const materia = reactive({
      id: null,
      nombre: ''
    })

    getCarreras()

    function crear () {
      servicioAlertas.alertaAdvertencia('Opcion no habilitada')
    }

    function ver (props) {
      Object.assign(materia, props.row)
      dialogoVer.value = true
    }

    function editar (props) {
      servicioAlertas.alertaAdvertencia('Opcion no habilitada')
    }

    function eliminar (props) {
      servicioAlertas.alertaAdvertencia('Opcion no habilitada')
    }

    function limpiar () {
      materia.id = null
      materia.nombre = ''
    }

    function ocultar () {
      dialogoVer.value = false
    }

    function cancelar () {
      ocultar()
      limpiar()
    }

    async function getCarreras () {
      try {
        cargando.value = true
        const datos = await storeMateria.getMaterias()
        if (datos.status === 200) {
          cargando.value = false
          materias.value = await datos.data
        } else if (datos.status === 202) {
          const mensaje = 'Error al cargar materias ' + datos.headers.estado
          servicioAlertas.infoAdvertencia(mensaje)
          console.warn(mensaje)
        } else {
          const mensaje = 'Error al cargar materias ' + datos.status
          servicioAlertas.infoError(mensaje)
          console.error(mensaje)
        }
        ocultar()
        cargando.value = false
      } catch (err) {
        cargando.value = false
        ocultar()
        const error = 'Hubo un error al intentar cargar las materias ' + err
        servicioAlertas.infoError(error)
        console.error(error)
      }
    }

    return {
      crear,
      ver,
      editar,
      eliminar,
      cancelar,

      cargando,

      materia,
      materias,

      dialogoVer,

      paginacion,
      columnas,

      filter: ref(''),
      exportTable () {
        let csvContent = 'data:text/csv;charset=utf-8,'
        csvContent += [
          Object.keys(materias.value[0]).join(';'),
          ...materias.value.map(item => Object.values(item).join(';'))
        ]
          .join('\n')
          .replace(/(^\[)|(\]$)/gm, '')

        const data = encodeURI(csvContent)
        const link = document.createElement('a')
        link.setAttribute('href', data)
        link.setAttribute('download', 'materias.csv')
        link.click()
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
