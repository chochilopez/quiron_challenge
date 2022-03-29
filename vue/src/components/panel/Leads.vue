<template>
  <div class="row q-pa-md fuente3">
    <div class="col">
      <q-table
        :showing="!cargando"
        bordered
        title="Leads"
        :columns="columnas"
        rows-per-page-label="Registros por pagina"
        no-data-label="Sin datos para mostrar"
        :pagination="paginacion"
        :filter="filter"
        hide-no-data
        :rows="leads"
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
            label="Nueva lead"
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
        <div>Ver lead</div>
        <q-space />
        <q-btn dense flat icon="close" v-close-popup>
          <q-tooltip class="bg-c-2 text-white">Cerrar</q-tooltip>
        </q-btn>
      </q-bar>
      <q-card-section>
        <q-list dense bordered padding class="rounded-borders">

          <q-item clickable v-ripple class="q-ma-md">
            <q-item-section>
              <q-item-label class="text-c-2">{{ lead.id }}</q-item-label>
              <q-item-label caption>Id</q-item-label>
            </q-item-section>
          </q-item>

          <q-item clickable v-ripple class="q-ma-md">
            <q-item-section>
              <q-item-label class="text-c-2">{{ lead.nombre === null || lead.nombre === '' ? 'SIN DATOS' : lead.nombre }}</q-item-label>
              <q-item-label caption>Nombre</q-item-label>
            </q-item-section>
          </q-item>

          <q-item clickable v-ripple class="q-ma-md">
            <q-item-section>
              <q-item-label class="text-c-2">{{ lead.apellido === null || lead.apellido === '' ? 'SIN DATOS' : lead.apellido }}</q-item-label>
              <q-item-label caption>Apellido</q-item-label>
            </q-item-section>
          </q-item>

          <q-item clickable v-ripple class="q-ma-md">
            <q-item-section>
              <q-item-label class="text-c-2">{{ lead.email === null || lead.email === '' ? 'SIN DATOS' : lead.email }}</q-item-label>
              <q-item-label caption>Email</q-item-label>
            </q-item-section>
          </q-item>

          <q-item clickable v-ripple class="q-ma-md">
            <q-item-section>
              <q-item-label class="text-c-2">{{ lead.direccion === null || lead.direccion === '' ? 'SIN DATOS' : lead.direccion }}</q-item-label>
              <q-item-label caption>Direccion</q-item-label>
            </q-item-section>
          </q-item>

          <q-item clickable v-ripple class="q-ma-md">
            <q-item-section>
              <q-item-label class="text-c-2">{{ lead.telefono === null || lead.telefono === '' ? 'SIN DATOS' : lead.telefono }}</q-item-label>
              <q-item-label caption>Teléfono</q-item-label>
            </q-item-section>
          </q-item>

          <q-item clickable v-ripple class="q-ma-md">
            <q-item-section>
              <q-item-label class="text-c-2">{{ lead.status === null || lead.status === '' ? 'SIN DATOS' : lead.status }}</q-item-label>
              <q-item-label caption>Estado</q-item-label>
            </q-item-section>
          </q-item>

          <q-item clickable v-ripple class="q-ma-md">
            <q-item-section>
              <q-item-label class="text-c-2">{{ lead.ingresoLead === null || lead.ingresoLead === '' ? 'SIN DATOS' : lead.ingresoLead }}</q-item-label>
              <q-item-label caption>Ingreso Lead</q-item-label>
            </q-item-section>
          </q-item>

        </q-list>
      </q-card-section>
    </q-card>
  </q-dialog>

</template>

<script>
import { useStore } from 'vuex'
import { ref, reactive } from 'vue'
import { servicioAlertas } from 'app/_helpers/alerta'

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
    name: 'apellido',
    label: 'Apellido',
    field: 'apellido',
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
    name: 'telefono',
    label: 'Teléfono',
    field: 'telefono',
    sortable: true,
    sortOrder: 'ad',
    align: 'left'
  },
  {
    name: 'ingresoLead',
    label: 'Ingreso Lead',
    field: 'ingresoLead',
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
    const cargando = ref(false)
    const leads = ref([])
    const dialogoVer = ref(false)
    const lead = reactive({
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

    getLeads()

    function crear () {
      servicioAlertas.alertaAdvertencia('Opcion no habilitada')
    }

    function ver (props) {
      Object.assign(lead, props.row)
      dialogoVer.value = true
    }

    function editar (props) {
      servicioAlertas.alertaAdvertencia('Opcion no habilitada')
    }

    function eliminar (props) {
      servicioAlertas.alertaAdvertencia('Opcion no habilitada')
    }

    function limpiar () {
      lead.id = null
      lead.nombre = ''
      lead.apellido = ''
      lead.email = ''
      lead.direccion = ''
      lead.telefono = ''
      lead.status = ''
      lead.ingresoLead = ''
      lead.ingresoEstudiante = ''
    }

    function ocultar () {
      dialogoVer.value = false
    }

    function cancelar () {
      ocultar()
      limpiar()
    }

    async function getLeads () {
      try {
        cargando.value = true
        const datos = await store.dispatch('getLeads')
        if (datos.status === 200) {
          cargando.value = false
          leads.value = await datos.data
        } else if (datos.status === 202) {
          const mensaje = 'Error al cargar leads ' + datos.headers.estado
          servicioAlertas.infoAdvertencia(mensaje)
          console.warn(mensaje)
        } else {
          const mensaje = 'Error al cargar leads ' + datos.status
          servicioAlertas.infoError(mensaje)
          console.error(mensaje)
        }
        ocultar()
        cargando.value = false
      } catch (err) {
        cargando.value = false
        ocultar()
        const error = 'Hubo un error al intentar cargar las leads ' + err
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

      lead,
      leads,

      dialogoVer,

      paginacion,
      columnas,

      filter: ref(''),
      exportTable () {
        let csvContent = 'data:text/csv;charset=utf-8,'
        csvContent += [
          Object.keys(leads.value[0]).join(';'),
          ...leads.value.map(item => Object.values(item).join(';'))
        ]
          .join('\n')
          .replace(/(^\[)|(\]$)/gm, '')

        const data = encodeURI(csvContent)
        const link = document.createElement('a')
        link.setAttribute('href', data)
        link.setAttribute('download', 'leads.csv')
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
