<template>
  <q-layout view="hHh lpR fFf">

    <q-header class="bg-c-1 text-white fuente1">
      <q-toolbar>
        <q-btn dense flat round icon="menu" @click="toggleLeftDrawer" />
        <q-toolbar-title>
          <q-avatar square class="q-mb-xs">
            <img src="../assets/images/icono-purpura.png">
          </q-avatar>
          <span class="q-ml-sm">Ed Machina</span>
        </q-toolbar-title>
        <q-space />
        <q-btn-dropdown
          :label="nombreUsuario"
          class="q-ml-xs bg-c-4 text-black"
          no-caps
          dropdown-icon="change_history">
          <q-item clickable v-close-popup @click="cerrar">
          <q-item-section>
            <q-item-label>Cerrar sesión</q-item-label>
          </q-item-section>
          </q-item>
        </q-btn-dropdown>
      </q-toolbar>
    </q-header>

    <q-footer class="bg-c-1">
      <q-img src="../assets/images/baner.jpeg" height="200px" fit="contain" />
    </q-footer>

    <q-drawer show-if-above v-model="leftDrawerOpen" side="left" class="bg-primary text-white fuente4">
      <q-list bordered padding class="text-primary">

        <q-item clickable v-ripple :active="link === 'dashboard'" @click="link = 'dashboard'" active-class="my-menu-link" exact :to="{ name: 'Dashboard' }" >
          <q-item-section avatar>
            <q-icon name="dashboard" />
          </q-item-section>
          <q-item-section class="white-text">Panel Principal</q-item-section>
        </q-item>

        <q-item clickable v-ripple :active="link === 'carrera'" @click="link = 'carrera'" active-class="my-menu-link" exact :to="{ name: 'Carrera' }" >
          <q-item-section avatar>
            <q-icon name="mdi-school" />
          </q-item-section>
          <q-item-section>Carrera</q-item-section>
        </q-item>

        <q-item clickable v-ripple :active="link === 'estudiante'" @click="link = 'estudiante'" active-class="my-menu-link" exact :to="{ name: 'Estudiante' }" >
          <q-item-section avatar>
            <q-icon name="mdi-account-group" />
          </q-item-section>
          <q-item-section>Estudiante</q-item-section>
        </q-item>

        <q-item clickable v-ripple :active="link === 'lead'" @click="link = 'lead'" active-class="my-menu-link" exact :to="{ name: 'Lead' }" >
          <q-item-section avatar>
            <q-icon name="mdi-account-convert" />
          </q-item-section>
          <q-item-section>Lead</q-item-section>
        </q-item>

        <q-item clickable v-ripple :active="link === 'materia'" @click="link = 'materia'" active-class="my-menu-link" exact :to="{ name: 'Materia' }" >
          <q-item-section avatar>
            <q-icon name="mdi-book-open-page-variant" />
          </q-item-section>
          <q-item-section>Materia</q-item-section>
        </q-item>

      </q-list>
    </q-drawer>

    <q-page-container>
      <router-view />
    </q-page-container>
  </q-layout>
</template>

<script>
import { ref } from 'vue'
import { servicioAlertas } from 'app/_helpers/alerta'

export default {
  setup () {
    const layout = ref([])
    const leftDrawerOpen = ref(false)
    const link = ref('dashboard')
    const nombreUsuario = 'invitado@invitado'
    const cerrar = () => {
      servicioAlertas.alertaExito('De estar logueado, te hubieses deslogueado exitosamente!')
    }

    return {
      link,
      leftDrawerOpen,
      nombreUsuario,
      cerrar,
      layout,
      toggleLeftDrawer () {
        leftDrawerOpen.value = !leftDrawerOpen.value
      }
    }
  }
}
</script>

<style>
.my-menu-link {
  color: white !important;
  background-color: #6254fd;
}
a {
  color: white !important;
  text-decoration: none;
}
</style>
