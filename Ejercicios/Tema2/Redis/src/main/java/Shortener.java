import redis.clients.jedis.Jedis;

import java.util.UUID;

public class Shortener {
    public Shortener(){

    }

    private void shorten(){
        String SHORTENED_URLS = "ALEJANDRO:SHORTENED_URLS";
        String URLS = "ALEJANDRO:URLS_TO_SHORTEN";

        try(Jedis jedis = new Jedis("54.92.203.211", 6379)){
            while(true){
                String url = jedis.lpop(URLS);
                if(url != null){
                    //Create random 8 character string
                    String shortened = UUID.randomUUID().toString().substring(0, 8);
                    jedis.hset(SHORTENED_URLS, shortened, url);
                }
                Thread.sleep(500);
            }
        }catch (Exception e){
            System.out.println("Error connecting to Redis");
        }
    }

}
