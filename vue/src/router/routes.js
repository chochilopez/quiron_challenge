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
        component: () => import('pages/panel/Estudiante.vue')
      },
      {
        path: '/dashboard/carrera',
        name: 'Carrera',
        component: () => import('pages/panel/Carrera.vue')
      },
      {
        path: '/dashboard/lead',
        name: 'Lead',
        component: () => import('pages/panel/Lead.vue')
      },
      {
        path: '/dashboard/materias',
        name: 'Materia',
        component: () => import('pages/panel/Materia.vue')
      }
    ]
  },
  {
    path: '/:catchAll(.*)*',
    component: () => import('pages/Error404.vue')
  }
]

export default routes
