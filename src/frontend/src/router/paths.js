/**
 * Define all of your application routes here
 * for more information on routes, see the
 * official documentation https://router.vuejs.org/en/
 */
export default [
  {
    path: '/dashboard',
    // Regarder dans /src/views
    view: 'Dashboard'
  },
  {
    path: '/nouvelle-seance',
    name: 'Nouvelle Séance',
    view: 'NewSession'
  },
  {
    path: '/mes-seances',
    name: 'Mes séances',
    view: 'SessionsList'
  },
  {
    path: '/mon-compte',
    name: 'Mon compte',
    view: 'Account'
  },
  {
    path: '/erreur',
    name: 'Erreur',
    view: 'Error'
  },
  {
    path: '/video',
    name: 'Démarrer une vidéo',
    view: 'Video'
  },
  {
    path: '/seance-termine',
    name: 'Séance terminée',
    view: 'FinishedSession'
  },

  {
    path: '/statistiques-seance/:id',
    name: 'Statistiques de séance : Conférence Pierre et Marie Currie',
    view: 'SessionStatistics'
  }
]
