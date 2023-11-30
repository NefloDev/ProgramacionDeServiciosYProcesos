package aplication2;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Base64;

public class ClientApp2 {

    public static void main(String[] args) {
        try {
            DatagramSocket socketUDP = new DatagramSocket();
            BufferedReader reader = new BufferedReader(new java.io.InputStreamReader(System.in));

            System.out.print(">> ");
            String input = reader.readLine();
            String encodedString = Base64.getEncoder().encodeToString(input.getBytes());
            input = "#" + encodedString + "#";
            byte[] encoded = input.getBytes();

            InetAddress serverHost = InetAddress.getByName("localhost");
            int serverPort = 6789;

            DatagramPacket sendDatagram = new DatagramPacket(encoded, encoded.length, serverHost, serverPort);

            socketUDP.send(sendDatagram);

            byte[] buffer = new byte[1000];
            DatagramPacket answerDatagram = new DatagramPacket(buffer, buffer.length);
            socketUDP.receive(answerDatagram);

            String received = new String(answerDatagram.getData());
            System.out.print("Respuesta: ");
            System.out.println(received);

            socketUDP.close();
        } catch (SocketException e) {
            System.out.println("Socket: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("IO: " + e.getMessage());
        }
    }

}
