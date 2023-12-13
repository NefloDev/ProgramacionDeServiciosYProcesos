import redis.clients.jedis.Jedis;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) {
        //Constants declaration
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

        //Redis connection
        try(Jedis jedis = new Jedis("34.228.162.124", 6379)){
            thread.start();
            //Print help message with the available commands
            System.out.println(HELP_MESSAGE);
            do{
                //Read the command and split it in two parts (command and argument)
                System.out.print("$ ");
                command = sc.readLine().split(" ", 2);
                //Check if the command is not exit
                if(!command[0].equalsIgnoreCase("exit")){
                    //Check the value of the command
                    switch (command[0]){
                        case "shorten":
                            //When arrived here, if the input only has the command, it will be invalid because
                            //it needs an argument to work
                            if(command.length >= 2) {
                                //Push the URL to the list of URLs to shorten in Redis
                                jedis.rpush(URLS, command[1]);
                            }else{
                                System.err.println(INVALIDCOMMAND + "Missing URL");
                            }
                            break;
                        case "url":
                            //Get the original URL from the shortened URL
                            System.out.println(jedis.hget(SHORTENED_URLS, command[1]));
                            break;
                        case "help":
                            //Print the help message
                            System.out.println("\n" + HELP_MESSAGE);
                            break;
                        default:
                            //If the command is not valid, print an error message
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
