package aplication1;

import java.io.*;
import java.net.*;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class ServerApp1 {
    public static final int PORT = 4444;
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)){

            while(true){
                Socket clientSocket = serverSocket.accept();
                BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter output = new PrintWriter(new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream())), true);

                String s = input.readLine();
                if (s.equals("time")) {
                    output.println(LocalTime.now().getHour() + ":" + LocalDateTime.now().getMinute());
                }
            }

        } catch (IOException e) {
            System.out.println("IOException: " + e.getMessage());
        }
    }
}
