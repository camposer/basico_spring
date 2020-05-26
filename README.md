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
