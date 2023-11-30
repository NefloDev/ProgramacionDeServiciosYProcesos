package aplication2;

import java.net.*;
import java.io.*;
import java.util.Base64;

public class ServerApp2 {

    public static void main(String args[]) {
        try {
            DatagramSocket socketUDP = new DatagramSocket(6789);
            byte[] buffer = new byte[1000];

            while (true) {
                DatagramPacket receivedDatagram = new DatagramPacket(buffer, buffer.length);
                socketUDP.receive(receivedDatagram);

                System.out.print("Datagrama recibido del host: " + receivedDatagram.getAddress());
                System.out.println(" desde el puerto remoto: " + receivedDatagram.getPort());
                String data = new String(receivedDatagram.getData(), "US-ASCII");
                data = data.replaceAll("#", "");
                data = new String(Base64.getMimeDecoder().decode(data.getBytes()));

                DatagramPacket responseDatagram = new DatagramPacket(data.getBytes(), data.getBytes().length, receivedDatagram.getAddress(), receivedDatagram.getPort());

                socketUDP.send(responseDatagram);
            }
        } catch (SocketException e) {
            System.out.println("Socket: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("IO: " + e.getMessage());
        }
    }
}
