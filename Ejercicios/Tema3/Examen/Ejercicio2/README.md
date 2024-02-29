# EXAMEN TEMA 3 - EJERCICIO 2
## Requisitos
- Amplía la aplicación anterior para que en el momento de que el usuario introduzca una URL vacía, se proceda a 
descargar todas las URLs cada una con un nombre de fichero (el nombre aleatorio único creado) y al terminar este proceso  
se comprimirán todos los archivos en un único archivo .ZIP.
- Esto lo deberás hacer usando Futuros de Java, de modo que la descarga de todos los archivos es la primera parte del 
futuro y cuando este proceso termina se ejecuta la compresión de todos ellos en un archivo .zip.

## Funcionamiento
El ejercicio consta de una clase [Main](./src/main/java/Main.java) que se encarga de pedir al usuario en un bucle infinito
urls que almacenará en una lista observable de la clase [DownloaderAndZipper](./src/main/java/DownloaderAndZipper.java)
que tendrá un listener que mostrará un mensaje cuando la url introducida esté vacía y un mensaje distinto cuando se introduzca
una url correctamente.
La clase [DownloaderAndZipper](./src/main/java/DownloaderAndZipper.java) tiene un método además para descargar todas las 
urls listadas hasta el momento de introducir una url vacía, que primero cargará un objeto URL por cada link, luego vaciará 
la lista (para poder volver a hacer una nueva carga desde 0) y escribe los html descargados en txt en la carpeta [urls](./urls) 
que luego es comprimida en el zip [compressed.zip](./compressed.zip)

## Autor
[Alejandro Nebot Flores](https://github.com/NefloDev)