import redis.clients.jedis.Jedis;

import java.util.Random;
import java.util.UUID;

public class Shortener implements Runnable{
    @Override
    public void run() {
        //Constants declaration
        String SHORTENED_URLS = "ALEJANDRO:SHORTENED_URLS";
        String URLS = "ALEJANDRO:URLS_TO_SHORTEN";
        String customDomain = "short.net/";

        //Redis connection
        try(Jedis jedis = new Jedis("34.228.162.124", 6379)){
            while (true){
                //Get the first URL from the list of URLs to shorten
                String url = jedis.lpop(URLS);
                if(url != null){
                    //Generate a random code and add it to the shortened URL
                    String randomCode = randomString();
                    String shortened = customDomain + randomCode;
                    //Add the shortened URL to the hash of shortened URLs with the original URL as value
                    jedis.hset(SHORTENED_URLS, shortened, url);
                    //Showing the shortened URL because the user doesn't know the random code
                    System.out.println(shortened);
                }
            }
        }catch (Exception e){
            System.out.println("Error connecting to Redis");
        }
    }

    private String randomString(){
        StringBuilder sb = new StringBuilder();
        Random rand = new Random();
        //Using string builder append random characters between A and Z
        for (int i = 0; i < 8; i++) {
            sb.append((char)rand.nextInt('A', 'Z'+1));
        }
        return sb.toString();
    }
}
