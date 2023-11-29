package cliente;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

public class Peticion implements Runnable {

    private InetSocketAddress direccion;
    private static int i = 1;

    public Peticion(InetSocketAddress direccion) {
        this.direccion = direccion;
    }
    @Override
    public void run() {
        try {
            Socket socket;
            BufferedReader bfr;
            PrintWriter pw;
            while (true){
                socket = new Socket();
                socket.connect(direccion);
                bfr = getFlujo(socket.getInputStream());
                pw = new PrintWriter(socket.getOutputStream());
                pw.write("+" + "\n");
                pw.write(i*4 + "\n");
                pw.write(i*8 + "\n");
                pw.flush();
                String linea = bfr.readLine();
                System.out.println(linea);
                i++;
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    public BufferedReader getFlujo(InputStream is){
        return new BufferedReader(new InputStreamReader(is));
    }
}
