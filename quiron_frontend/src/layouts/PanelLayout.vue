<template>
  <q-layout view="hHh lpR fFf">

    <q-header class="bg-c-1 text-white fuente3">
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
      <q-img src="../assets/images/baner.jpeg" height="125px" fit="contain" />
    </q-footer>

    <q-drawer show-if-above v-model="leftDrawerOpen" side="left" class="bg-primary text-white fuente6">
      <q-list bordered padding class="text-primary">

        <q-tree :nodes="menuTree" node-key="label" selected-color="warning" v-model:selected="selected" accordion  >
          <template v-slot:default-header="prop">
            <q-item class="rounded-borders" clickable v-ripple :active="link === prop.node.body" @click="link = prop.node.body" active-class="my-menu-link" exact :to="{ name: prop.node.label }" >
              <q-item-section avatar>
                <q-icon :name="prop.node.icon" />
              </q-item-section>
              <q-item-section class="white-text">{{ prop.node.label }}</q-item-section>
            </q-item>
          </template>
        </q-tree>

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
    const selected = ref('Food')
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
      },
      selected,
      menuTree: [
        {
          label: 'Dashboard',
          icon: 'mdi-view-dashboard',
          body: 'dashboard',
          header: 'generic'
        },
        {
          label: 'Carreras',
          icon: 'mdi-school',
          header: 'generic',
          body: 'carreras'
        },
        {
          label: 'Estudiantes',
          icon: 'mdi-account-group',
          header: 'generic',
          body: 'estudiantes'
        },
        {
          label: 'Leads',
          icon: 'mdi-account-convert',
          header: 'generic',
          body: 'leads'
        },
        {
          label: 'Materias',
          icon: 'mdi-book-open-page-variant',
          header: 'generic',
          body: 'materias'
        }
      ]
    }
  }
}
</script>

<style>
.my-menu-link {
  color: white !important;
  background-color: #6254fd;
}
.q-item {
  width: 100%;
}
a {
  color: white !important;
  text-decoration: none;
}
</style>
