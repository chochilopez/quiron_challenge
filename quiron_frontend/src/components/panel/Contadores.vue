<template>
  <q-card class="q-ma-md fuente8">
    <div class="row" >
      <div class="col">
        <div class="row justify-around" v-if="cantidadLeads == 0">
          <q-btn @click="poblarDB" class="bg-c-4 q-ma-md" :showing="!cargando">Poblar DB</q-btn>
          <q-inner-loading :showing="cargando">
            <q-spinner-puff class="text-c-3" size="6em" />
          </q-inner-loading>
        </div>
        <div class="row justify-around" v-if="cantidadLeads > 0">
          <div class="col-6 col-sm-3 col-md-2 q-pa-sm">
            <q-card class="my-card text-white">
              <q-card-actions align="center" class="bg-c-6">
                <span class="text-h6">Leads</span>
              </q-card-actions>

              <q-card-actions align="center" class="bg-c-6">
                <q-icon name="mdi-account-convert" size="md" />
                <span class="text-h4 q-ml-md">{{ cantidadLeads }}</span>
              </q-card-actions>

              <q-card-actions align="center" class="bg-c-6 justify-around" style="filter: opacity(0.9);">
                <q-btn flat round icon="fas fa-sign-in-alt" :to="{ name: 'Lead'}" />
              </q-card-actions>
            </q-card>
          </div>

          <div class="col-6 col-sm-3 col-md-2 q-pa-sm">
            <q-card class="my-card text-white">
              <q-card-actions align="center" class="bg-info">
                <span class="text-h6">Estudiantes</span>
              </q-card-actions>

              <q-card-actions align="center" class="bg-info">
                <q-icon name="mdi-account-group" size="md" />
                <span class="text-h4 q-ml-md">{{ cantidadEstudiantes }}</span>
              </q-card-actions>

              <q-card-actions align="center" class="bg-info justify-around" style="filter: opacity(0.9);">
                <q-btn flat round icon="fas fa-sign-in-alt" :to="{ name: 'Estudiante'}" />
              </q-card-actions>

            </q-card>
          </div>

          <div class="col-6 col-sm-3 col-md-2 q-pa-sm">
            <q-card class="my-card text-white">
              <q-card-actions align="center" class="bg-warning">
                <span class="text-h6">Carreras</span>
              </q-card-actions>

              <q-card-actions align="center" class="bg-warning">
                <q-icon name="mdi-school" size="md" />
                <span class="text-h4 q-ml-md">{{ cantidadCarreras }}</span>
              </q-card-actions>

              <q-card-actions align="center" class="bg-warning justify-around" style="filter: opacity(0.9);">
                <q-btn flat round icon="fas fa-sign-in-alt" :to="{ name: 'Carrera'}" />
              </q-card-actions>

            </q-card>
          </div>

          <div class="col-6 col-sm-3 col-md-2 q-pa-sm">
            <q-card class="my-card text-white">
              <q-card-actions align="center" class="bg-c-7">
                <span class="text-h6">Materias</span>
              </q-card-actions>

              <q-card-actions align="center" class="bg-c-7">
                <q-icon name="mdi-book-open-page-variant" size="md" />
                <span class="text-h4 q-ml-md">{{ cantidadMaterias }}</span>
              </q-card-actions>

              <q-card-actions align="center" class="bg-c-7 justify-around" style="filter: opacity(0.9);">
                <q-btn flat round icon="fas fa-sign-in-alt" :to="{ name: 'Materia'}" />
              </q-card-actions>
            </q-card>
          </div>

        </div>
      </div>
    </div>
  </q-card>
</template>

<script>
import { ref } from 'vue'
import { servicioAlertas } from 'app/_helpers/alerta'
import { useEstudianteStore } from 'stores/estudiante'
import { useCarreraStore } from 'stores/carrera'
import { useMateriaStore } from 'stores/materia'
import { useFakerStore } from 'stores/faker'

export default {
  setup () {
    const storeEstudiante = useEstudianteStore()
    const storeFaker = useFakerStore()
    const storeMateria = useMateriaStore()
    const storeCarrera = useCarreraStore()
    const cantidadLeads = ref(0)
    const cantidadEstudiantes = ref(0)
    const cantidadCarreras = ref(0)
    const cantidadMaterias = ref(0)
    const cargando = ref(false)

    getContadores()

    async function poblarDB () {
      try {
        cargando.value = true
        const poblarMaterias = storeFaker.poblarMaterias()
        await poblarMaterias
        const poblarCarreras = storeFaker.poblarCarreras()
        await poblarCarreras
        const poblarEstudiantes = storeFaker.poblarEstudiantes()
        await poblarEstudiantes
        const poblarInscripciones = storeFaker.poblarInscripciones()
        await poblarInscripciones
        getContadores()
        cargando.value = false
      } catch (err) {
        console.error('Error front', 'database-init -> post error -> ' + err.message)
        servicioAlertas.alertaError('Error al poblar los base de datos.')
        cargando.value = false
      }
    }

    async function getContadores () {
      try {
        let result = await storeEstudiante.getCantidadLeads()
        if (result.status === 202) {
          console.warn('Advertencia', result.headers.estado)
        } else if (result.status === 200) {
          console.info('Info', 'dashboard-counters -> leads -> fetched')
          cantidadLeads.value = result.data
        }
        result = await storeEstudiante.getCantidadEstudiantes()
        if (result.status === 202) {
          console.warn('Advertencia', result.headers.estado)
        } else if (result.status === 200) {
          console.info('Info', 'dashboard-counters -> students -> fetched')
          cantidadEstudiantes.value = result.data
        }
        result = await storeCarrera.getCantidadCarreras()
        if (result.status === 202) {
          console.warn('Advertencia', result.headers.estado)
        } else if (result.status === 200) {
          console.info('Info', 'dashboard-counters -> careers -> fetched')
          cantidadCarreras.value = result.data
        }
        result = await storeMateria.getCantidadMaterias()
        if (result.status === 202) {
          console.warn('Advertencia', result.headers.estado)
        } else if (result.status === 200) {
          console.info('Info', 'dashboard-counters -> assigns -> fetched')
          cantidadMaterias.value = result.data
        }
      } catch (err) {
        console.error('Error front', 'dashboard-counters -> counters -> fetch error -> ' + err.message)
        servicioAlertas.alertaError('Error al cargar los contadores')
      }
    }

    return {
      cantidadLeads,
      cantidadCarreras,
      cantidadMaterias,
      cantidadEstudiantes,
      cargando,
      poblarDB
    }
  }
}
</script>
