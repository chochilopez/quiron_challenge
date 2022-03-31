# EdMachina Challenge

Pequeña aplicacion que permite la carga de leads y convertirlos a estudiantes.
Spring Boot 2.6.5
Vue 3
Quasar 2
PostgreSQL

## Instalación

Descargar zip del projecto y ejecutar (docker compose) en la carpeta raiz

### `$ docker-compose build`
### `$ docker-compose up -d`
### `$ docker-compose ps` 
Para localizar el contenedor donde se ejecuta vue
### `$ docker exec -ti nombreDelContenedor sh` 
Una vez localizado el nombre del contenedor, reemplazar por nombreDelContenedor
### `$ quasar dev` 
Para correr la aplicacion web, ingresar manualmente desde el navegador a localhost:8080

## Accessos
  ### Apis Spring 
    localhost:9088

  ### Documentacion endpoints
    localhost:9088/api-quiron.html

  ### Apliacion
    localhost:8080

## Organizacion

  ### Backend - Spring Boot
    El proyecto esta dividido de la siguiente manera:

  #### Controller
      Endpoints divididos por modelo.

  #### helper
      Clases de ayuda, mocker.

  #### model
      Modelos de entidades: Carrera, Estudiante, EstudianteCarrera (pivot), 
      EstudianteMateria (pivot), Materia.

  #### repository
      Acceso a datos, un repositorio por clase con sus respectivos metodos.

  #### service e implementation
      Interfaces o contratos de servicio, luego, implmentaciones de los mismos.

      Asimismo, gran parte del proyecto esta documentada a traves de OpenApi y 
      Swagger ui, se accede a la misma

  ### Frontend - Quasar Framework (vue 3)

  #### src/assets
      Multimedia

  #### src/components
      componentes modulares de las paginas

  #### src/layout
      paginas de tipo marco

  #### src/pages
      paginas del sitio

  #### src/router
      VueRouter - rutas de la pagina y su gestion

  #### src/store
      Vuex - libreria de manejo de estados, manera de persistir informacion entre 
      sesiones o paginas
  

  ## Problemas
    Si bien al ejecutar el docker no fallan los contenedores, hay un error en el store 
    (almacenamiento de estados) que no permite la conexion con la base de datos.