# EXAMEN TEMA 3 - EJERCICIO 1
## Requisitos
- Crea una aplicación que lea direcciones URL por parte del usuario y las almacena en un listado junto con una cadena 
(aleatoria y única) de 20 caracteres.
- Habrá una clase DownloaderAndZipper que escuchará ese listado y mostrará el siguiente texto por consola cada vez que
 se añada un elemento al listado.
  - **"{url} encolado como {código de 20 caracteres}"**
- En caso de que la URL esté vacía se mostrará el texto:
  - **"Se va a proceder a descargar y comprimir los ficheros"**
- Implementar el patrón Observer-listener para la implementación.

## Funcionamiento
El ejercicio consta de una clase [Main](./src/main/java/Main.java) que se encarga de pedir al usuario en un bucle infinito
urls que almacenará en una lista observable de la clase [DownloaderAndZipper](./src/main/java/DownloaderAndZipper.java) 
que tendrá un listener que mostrará un mensaje cuando la url introducida esté vacía y un mensaje distinto cuando se introduzca
una url correctamente.

## Autor
[Alejandro Nebot Flores](https://github.com/NefloDev)