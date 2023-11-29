import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServidorContinuo {

    public static void escuchar() throws IOException {
        System.out.println("Arrancado el servidor");
        ServerSocket socketEscucha;
        try {
            socketEscucha=new ServerSocket(9876);
            while (true){
                Socket conexion=socketEscucha.accept();
                System.out.println("Conexion recibida!");

                Peticion p=new Peticion(conexion);
                Thread hilo=new Thread (p);
                hilo.start();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

    public static void main(String[] args) throws IOException{
        escuchar();
    }

}
