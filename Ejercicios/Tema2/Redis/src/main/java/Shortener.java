import redis.clients.jedis.Jedis;

import java.util.UUID;

public class Shortener implements Runnable{
    @Override
    public void run() {
        String SHORTENED_URLS = "ALEJANDRO:SHORTENED_URLS";
        String URLS = "ALEJANDRO:URLS_TO_SHORTEN";
        String customDomain = "short.net/";

        try(Jedis jedis = new Jedis("34.228.162.124", 6379)){
            while (true){
                String url = jedis.lpop(URLS);
                if(url != null){
                    String randomCode = randomString();
                    String shortened = customDomain + randomCode;
                    jedis.hset(SHORTENED_URLS, shortened, url);
                    //La imprimo por pantalla porque sino no sabes cual es al ser aleatoria
                    System.out.println(shortened);
                }
            }
        }catch (Exception e){
            System.out.println("Error connecting to Redis");
        }
    }

    private String randomString(){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            sb.append((char) (Math.random() * 26 + 'A'));
        }
        return sb.toString();
    }
}
