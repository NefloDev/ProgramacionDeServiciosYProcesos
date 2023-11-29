package cliente;

import java.io.DataOutputStream;
import java.io.IOException;

public class Cliente extends Conexion{
    public Cliente() throws IOException {super("cliente");}

    public void startClient(){
        try{
            salidaServidor = new DataOutputStream(cs.getOutputStream());
            salidaServidor.writeUTF("Hola Mundo!".trim());
            cs.close();
        }catch (IOException e){
            System.err.println("Error: " + e.getMessage());
        }
    }
}
