import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class Ejercicio1 {
    public static void main(String[] args) {
        do {
            BufferedReader rd = new BufferedReader(new InputStreamReader(System.in));
            try {
                String input = rd.readLine();
                CompletableFuture<URL> url = CompletableFuture.supplyAsync(() ->{
                    try{
                        return URI.create(input).toURL();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
                CompletableFuture<String> page = CompletableFuture.supplyAsync(() -> {
                    try(BufferedReader reader = new BufferedReader(new InputStreamReader(url.get().openStream()))){
                        String line;
                        StringBuilder text = new StringBuilder();
                        while((line = reader.readLine()) !=  null){
                            text.append(line);
                        }
                        return text.toString();
                    } catch (ExecutionException | InterruptedException | IOException e) {
                        throw new RuntimeException(e);
                    }
                });
                System.out.println(page.get());
            } catch (ExecutionException | InterruptedException | IOException e) {
                throw new RuntimeException(e);
            }
        }while(true);
    }
}
