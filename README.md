# Proyecto simple Spring Boot - nómina

Para construir y ejecutar el proyecto:

```
$ ./mvnw package
$ java -jar target/nomina-xxxx.jar
```

Para probar la api puede ejecutar:

```
$ curl -v http://localhost:8080/personas && echo
$ curl -v -X POST -H "Content-Type: application/json" -d '{ "id": 1, "nombre": "rodo", "apellido": "rodo" }' http://localhost:8080/personas && echo
```

Para configurar acceso externo a la BD:
- Cambiar listen_addresses="*" en postgresql.conf
- Añadir host en pg_hba.conf

Nota: Utilizar ip asignada por el router

Para ejecutar iniciar el container:
```
$ ./build.sh
$ docker run -d -p 1234:8080 nomina:0.0.1
$ curl http://localhost:1234/personas
```

Para iniciar el proyecto utilizando docker compose:
```
$ ./build.sh
$ docker-compose up
```

