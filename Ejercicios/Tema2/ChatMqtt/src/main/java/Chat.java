import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Chat {
    public static void main(String[] args) {
        Client chatClient = new Client("tcp://test.mosquitto.org:1883",
                "chat", "david", "");
        chatClient.connect();

        String command = "";
        BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));
        do {
            try{
                System.out.print("Enter command (help to command guide): ");
                String input = sc.readLine();
                String[] fullCommand = input.split(" ", 3);
                command = fullCommand[0];

                switch (command) {
                    case "send" -> {
                        chatClient.sendMessage(fullCommand[1], fullCommand[2]);
                        System.out.printf("\nSending %s to %s\r\n", fullCommand[2], fullCommand[1]);
                    }
                    case "chat" -> System.out.println(chatClient.getMessages(fullCommand[1]));
                    case "help" -> System.out.println("""
                            
                            send <topic> <message> - send a message to a topic
                            chat <topic> - get all messages from a topic
                            exit - exit the program
                            help - show this help message
                            
                            """);
                }
            }catch (Exception e) {
                System.err.println(e.getMessage());
                sc = new BufferedReader(new InputStreamReader(System.in));//reset scanner
            }
        }while (!command.equals("exit"));


        chatClient.close();
    }
}
