package cliente;

import java.net.InetSocketAddress;
import java.util.ArrayList;

public class ClienteCalculo {

    public static void main(String[] args){
        InetSocketAddress direccion=new
                InetSocketAddress("10.2.1.124", 9876);
        ArrayList<Thread> hilos=new ArrayList<>();
        for(int i=0;i<100;i++){
            Thread hilo=new Thread(new Peticion(direccion));
            hilos.add(hilo);
            hilo.start();
        }
    }

}
