import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class Servidor extends Conexion{
    public Servidor() throws IOException {super("servidor");}

    public void startServer(){
        try{
            System.out.println("Esperando conexión");
            cs = ss.accept();
            System.out.println("Conexión establecida");
            salidaCliente = new DataOutputStream(cs.getOutputStream());
            salidaCliente.writeUTF("Petición aceptada");
            BufferedReader reader = new BufferedReader(new InputStreamReader(cs.getInputStream()));
            while((mensajeServidor = reader.readLine()) != null){
                System.out.println(mensajeServidor);
            }
            System.out.println("Fin de la conexión");
            ss.close();
        }catch (IOException e){
            System.err.println("Error: " + e.getMessage());
        }
    }
}
