package aplication1;


import java.io.*;
import java.net.Socket;

public class ClientApp1 {
    public static void main(String[] args){
        try {
            Socket clientSocket = new Socket("localhost", 4444);
            BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter output = new PrintWriter(new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream())), true);

            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
            System.out.print(">> ");
            output.println(stdIn.readLine());
            String response = input.readLine();
            if(response != null){
                System.out.println("Hora: " + response);
            }else{
                System.out.println("El servidor no ha respondido");
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            output.close();
            input.close();
            stdIn.close();
            clientSocket.close();
        } catch (IOException e) {
            System.out.println("IOException: " + e.getMessage());
        }
    }
}
