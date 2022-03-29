const routes = [
  {
    path: '/',
    component: () => import('layouts/PanelLayout.vue'),
    children: [
      {
        path: '',
        name: 'Dashboard',
        component: () => import('pages/panel/Dashboard.vue')
      },
      {
        path: '/dashboard/estudiante',
        name: 'Estudiante',
        component: () => import('src/pages/panel/Estudiante.vue')
      },
      {
        path: '/dashboard/estudiantes',
        name: 'Estudiantes',
        component: () => import('src/pages/panel/Estudiantes.vue')
      },
      {
        path: '/dashboard/carrera',
        name: 'Carrera',
        component: () => import('src/pages/panel/Carrera.vue')
      },
      {
        path: '/dashboard/carreras',
        name: 'Carreras',
        component: () => import('src/pages/panel/Carreras.vue')
      },
      {
        path: '/dashboard/lead',
        name: 'Lead',
        component: () => import('src/pages/panel/Lead.vue')
      },
      {
        path: '/dashboard/leads',
        name: 'Leads',
        component: () => import('src/pages/panel/Leads.vue')
      },
      {
        path: '/dashboard/materia',
        name: 'Materia',
        component: () => import('src/pages/panel/Materia.vue')
      },
      {
        path: '/dashboard/materias',
        name: 'Materias',
        component: () => import('src/pages/panel/Materias.vue')
      }
    ]
  },
  {
    path: '/:catchAll(.*)*',
    component: () => import('pages/Error404.vue')
  }
]

export default routes
