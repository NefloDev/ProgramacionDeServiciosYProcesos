# EXAMEN TEMA 3 - EJERCICIO 3
## Requisitos
- Crea una API con Java y Spring para trabajar con números aleatorios. 
- Tendrá los siguientes Endpoints:
  - GET /random/numbers Devolverá un listado de 100 números aleatorios. 
  - GET /random/number/{d} Devuelve un número aleatorio que tendrá d dígitos. 
  - PUT /random/number Recibe un número aleatorio (JSON en el body) y devuelve un número aleatorio (JSON en el body) con el mismo número de dígitos que el número recibido. 
- El JSON para cada número aleatorio es:
```
{ 
random: 12345 
}
```

## Funcionamiento
El ejercicio consta de una clase [Ejercicio3Application](./src/main/java/org/example/ejercicio3/Ejercicio3Application.java) 
que se encarga de lanzar el servicio REST de Spring. Otra clase [Number](./src/main/java/org/example/ejercicio3/Number.java) 
que es el modelo de valor numerico que se va a usar en el API REST con un valor llamado "random" de tipo Long. 
Otras dos clases llamadas [RandomNumberService](./src/main/java/org/example/ejercicio3/RandomNumberService.java) y 
[RandomNumberServiceImpl](./src/main/java/org/example/ejercicio3/RandomNumberServiceImpl.java) que se encargarán de crear 
e implementar los métodos CRUD del servicio REST. Y por último una clase [RandomNumberController](./src/main/java/org/example/ejercicio3/RandomNumberController.java) 
que gestiona las llamadas REST y devuelve respuestas acordes a los requisitos del ejercicio.

## Autor
[Alejandro Nebot Flores](https://github.com/NefloDev)