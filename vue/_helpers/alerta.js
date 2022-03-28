import { Notify, Loading } from 'quasar'

export const servicioAlertas = {
  alertaError,
  alertaAdvertencia,
  alertaExito,
  alertaPrimaria,
  loadingPantalla,
  infoAdvertencia,
  infoError,
  infoExito
}

function infoAdvertencia (mensaje) {
  Notify.create({
    progress: true,
    message: mensaje,
    position: 'bottom',
    color: 'warning',
    textColor: 'black',
    icon: 'warning',
    timeout: 10000,
    closeBtn: 'CERRAR',
    multiLine: true
  })
}

function infoExito (mensaje) {
  Notify.create({
    progress: true,
    message: mensaje,
    position: 'bottom',
    color: 'positive',
    textColor: 'black',
    icon: 'check_circle',
    timeout: 10000,
    closeBtn: 'CERRAR',
    multiLine: true
  })
}

function infoError (mensaje) {
  Notify.create({
    progress: true,
    message: mensaje,
    position: 'bottom',
    color: 'negative',
    textColor: 'black',
    icon: 'error',
    timeout: 10000,
    closeBtn: 'CERRAR',
    multiLine: true
  })
}

function alertaError (mensaje) {
  Notify.create({
    progress: true,
    message: mensaje,
    position: 'top-right',
    color: 'negative',
    icon: 'error',
    timeout: 5000,
    multiLine: true
  })
}

function alertaAdvertencia (mensaje) {
  Notify.create({
    progress: true,
    message: mensaje,
    position: 'top-right',
    color: 'warning',
    icon: 'warning',
    timeout: 5000,
    multiLine: true
  })
}

function alertaExito (mensaje) {
  Notify.create({
    progress: true,
    message: mensaje,
    position: 'top-right',
    color: 'positive',
    icon: 'check_circle',
    timeout: 5000,
    multiLine: true
  })
}

function alertaPrimaria (mensaje) {
  Notify.create({
    progress: true,
    message: mensaje,
    position: 'top-right',
    color: 'primary',
    icon: 'check_circle',
    timeout: 5000,
    multiLine: true
  })
}

function loadingPantalla (mensaje) {
  Loading.show({
    message: mensaje,
    delay: 400 // ms
  })
}
