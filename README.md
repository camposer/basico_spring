Para probar la api puede ejecutar:

```
$ curl -v -X POST -H "Content-Type: application/json" -d '{ "id": 1, "nombre": "rodo", "apellido": "rodo" }' http://localhost:8080/personas && echo
```