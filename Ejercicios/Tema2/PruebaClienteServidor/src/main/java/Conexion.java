import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Conexion {

    protected final int puerto = 32114;
    protected final String ip = "212.166.220.212";
    protected String mensajeServidor;
    protected ServerSocket ss;
    protected Socket cs;
    protected DataOutputStream salidaServidor, salidaCliente;

    public Conexion(String tipo) throws IOException {
        if (tipo.equalsIgnoreCase("servidor")) {
            ss = new ServerSocket(puerto);
        } else {
            cs = new Socket(ip, puerto);
        }
    }

}
