import redis.clients.jedis.Jedis;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) {
        final String INVALIDCOMMAND = "Invalid command: ";
        final String URLS = "ALEJANDRO:URLS_TO_SHORTEN";
        final String SHORTENED_URLS = "ALEJANDRO:SHORTENED_URLS";
        final String HELP_MESSAGE = """
                                Commands:
                                shorten <URL> - Shorten the given URL
                                url <shortened URL> - Return the original URL
                                help - Shows this message
                                exit - Exit the program""";
        String[] command;
        BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));
        Thread thread = new Thread(new Shortener());

        try(Jedis jedis = new Jedis("34.228.162.124", 6379)){
            thread.start();
            System.out.println(HELP_MESSAGE);
            do{
                System.out.print("$ ");
                command = sc.readLine().split(" ", 2);
                if(!command[0].equalsIgnoreCase("exit")){

                    switch (command[0]){
                        case "shorten":
                            if(command.length >= 2) {
                                jedis.rpush(URLS, command[1]);
                            }else{
                                System.err.println(INVALIDCOMMAND + "Missing URL");
                            }
                            break;
                        case "url":
                            System.out.println(jedis.hget(SHORTENED_URLS, command[1]));
                            break;
                        case "help":
                            System.out.println("\n" + HELP_MESSAGE);
                            break;
                        default:
                            System.err.println(INVALIDCOMMAND + "Command not found");
                        break;
                    }
                }
            }while (!command[0].equalsIgnoreCase("exit"));
            thread.join();
        }catch (IOException e){
            System.out.println("Error reading input");
        }catch (InterruptedException e){
            System.out.println("Error joining thread");
        }
    }

}
