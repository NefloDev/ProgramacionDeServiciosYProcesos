import redis.clients.jedis.Jedis;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) {
        final String INVALIDCOMMAND = "Invalid command: ";
        final String URLS = "ALEJANDRO:URLS_TO_SHORTEN";
        final String SHORTENED_URLS = "ALEJANDRO:SHORTENED_URLS";
        String[] command;
        BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));

        try(Jedis jedis = new Jedis("54.92.203.211", 6379)){
            System.out.println(jedis.ping());
            do{
                System.out.print("$ ");
                command = sc.readLine().split(" ", 2);
                if(!command[0].equalsIgnoreCase("exit")){
                    if(command[0].equalsIgnoreCase("shorten")){
                        if(command.length >= 2) {
                            jedis.rpush(URLS, command[1]);
                        }else{
                            System.err.println(INVALIDCOMMAND + "Missing URL");
                        }
                    }else if(command[0].equalsIgnoreCase("url")){
                        System.out.println(jedis.hget(SHORTENED_URLS, command[1]));
                    }else if(command[0].equalsIgnoreCase("help")){
                        System.out.println("""
                                
                                Commands:
                                shorten <URL> - Shorten the given URL
                                url <shortened URL> - Return the original URL
                                exit - Exit the program
                                """);
                    }else{
                        System.err.println(INVALIDCOMMAND + "Command not found");
                    }
                }
            }while (!command[0].equalsIgnoreCase("exit"));
        }catch (IOException e){
            System.out.println("Error reading input");
        }
    }

}
