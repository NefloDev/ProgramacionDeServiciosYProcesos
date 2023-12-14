# PSP 2nd Exam

This exam consists of a program with 3 classes that register random temperatures every 5 seconds.
# How does it work

The [MeteoStation.java](https://github.com/NefloArt/ProgramacionDeServiciosYProcesos/blob/main/Ejercicios/Tema2/Examen/src/main/java/MeteoStation.java) class is a Runnable that every 5 seconds sends an Mqtt message to its own topic a String containing the date, hour and temperature registered.
The [MeteoServer.java](https://github.com/NefloArt/ProgramacionDeServiciosYProcesos/blob/main/Ejercicios/Tema2/Examen/src/main/java/MeteoServer.java) class is another Runnable that everytime it gets a message from the Station, it sets the data of a Redis hash containing the temperature and the date, and adds data to a list of temperatures and a list of Warnings if the temperature registered was above 30 or below 0.
The [MeteoClient.java](https://github.com/NefloArt/ProgramacionDeServiciosYProcesos/blob/main/Ejercicios/Tema2/Examen/src/main/java/MeteoClient.java) class is another Runnable that manages the command input of the user, and gets the data desired in the command entered. It also has a command that stops a Station thread by its id.
## Available Commands
LAST ID -> Last measurement by ID  
MAXTEMP ID -> Max temperature by ID  
MAXTEMP ALL -> Max temperature by all stations  
ALERTS -> Alerts  
HELP -> Shows this message  
STOP ID -> Stops station with ID  
EXIT -> Exits the program

## Exercise 1 execution proof

![proof_ex1](https://github.com/NefloArt/ProgramacionDeServiciosYProcesos/blob/main/Ejercicios/Tema2/Examen/imgs/Exercise1_proof.jpg)
## Exercise 2 execution proof

![proof_ex2](https://github.com/NefloArt/ProgramacionDeServiciosYProcesos/blob/main/Ejercicios/Tema2/Examen/imgs/Exercise2_proof.jpg)
## Exercise 3 execution proof

![proof_ex3_1](https://github.com/NefloArt/ProgramacionDeServiciosYProcesos/blob/main/Ejercicios/Tema2/Examen/imgs/Exercise3_proof1.jpg)
![proof_ex3_2](https://github.com/NefloArt/ProgramacionDeServiciosYProcesos/blob/main/Ejercicios/Tema2/Examen/imgs/Exercise3_proof2.jpg)
## Exercise 4 execution proof

![proof_ex4](https://github.com/NefloArt/ProgramacionDeServiciosYProcesos/blob/main/Ejercicios/Tema2/Examen/imgs/Exercise4_proof.jpg)

## Author
[NefloArt](https://github.com/NefloArt)
## Teacher
[nafarrin](https://github.com/nafarrin)
