package cliente;

import java.io.IOException;
public class MainCliente {

    public static void main(String[] args) {
        try {
            Cliente c = new Cliente();
            c.startClient();
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

}
