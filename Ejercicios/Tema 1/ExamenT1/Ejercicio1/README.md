# README

## Descripción del Proyecto

Este proyecto simula cómo 3 jardineros trabajan en 10 jardines de una localidad. Los jardineros escogen un jardín, lo trabajan y pasan a trabajar en otro jardín. En cualquier momento dado sólo habrá un jardinero por jardín. Trabajar en un jardín cuesta un día y la simulación dura 30 días. Cada día de la simulación equivale a 1 segundo real.

## Funcionamiento del Programa

El programa tiene 10 jardines y 3 jardineros. Los jardineros empiezan a trabajar de la siguiente manera:

1. Buscan un jardín libre de manera aleatoria. No debe estar siendo trabajado en este momento por otro jardinero.
2. Empiezan a trabajarlo (durante un día / segundo)
3. Terminan liberando el jardín. Y a partir de aquí se repite el proceso hasta que termina la simulación.

El programa principal espera a que hayan terminado todos los hilos.

## Información que Muestra el Programa

El programa irá mostrando la siguiente información:

- Cada hilo mostrará el siguiente mensaje cuando se empiece a trabajar en un jardín: “El jardinero 3 ha empezado a trabajar en el jardín 9”. Siendo 3 el identificador único del hilo y 9 el identificador numérico que asignamos a mano a cada jardín.
- Cada hilo mostrará el fin de los trabajos de cada jardinero. Por ejemplo: “El jardinero 3 ha terminado de trabajar en el jardín 9”. Siendo 3 el identificador único del hilo y 9 el identificador numérico que asignamos a mano a cada jardín.
- El programa principal esperará que finalice la simulación y mostrará cuántos jardines ha trabajado cada jardinero.

## Cómo Ejecutar el Programa

Para ejecutar el programa, necesitarás tener instalado Java en tu sistema. Luego puedes compilar y ejecutar el programa usando los siguientes comandos en la terminal:

```bash
javac Main.java
java Main
```

Esto iniciará la simulación y podrás ver la salida en la terminal.

## Contribuciones

Las contribuciones son bienvenidas. Por favor, abre un problema primero para discutir lo que te gustaría cambiar. Asegúrate de actualizar las pruebas según corresponda.

## Licencia

[MIT](https://choosealicense.com/licenses/mit/)