import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        try {
            Servidor servidor = new Servidor();
            servidor.startServer();
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

}
