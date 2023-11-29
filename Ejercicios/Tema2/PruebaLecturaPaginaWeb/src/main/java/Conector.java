import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
public class Conector {

    public static void main(String[] args) {
        System.out.println("Iniciando...");
        String destino = "www.google.com";
        int puertoDestino = 80;
        Socket socket = new Socket();
        InetSocketAddress address = new InetSocketAddress(destino, puertoDestino);

        try{
            socket.connect(address);

            System.out.println("Conectado a " + destino);

            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));

            writer.println("GET /index.html HTTP/1.0\r\n\r\n");
            writer.flush();

            String linea;
            FileWriter fWriter = new FileWriter("resultado.txt");
            while((linea=reader.readLine())!=null){
                fWriter.write(linea + "\n");
            }
            fWriter.close();
            reader.close();
            writer.close();
            socket.close();

        } catch (IOException e) {
            System.err.println("Error al realizar la conexión o al leer los datos");
        }

    }

}
