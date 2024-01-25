import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        List<CompletableFuture<String>> futures = List.of(
                CompletableFuture.supplyAsync(() -> {
                    try{
                        Thread.sleep(1000);
                    }catch (InterruptedException ignored){}
                    return "Hello";
                }),
                CompletableFuture.supplyAsync(() -> "Beautiful"),
                CompletableFuture.supplyAsync(() -> "World"));
        System.out.println(futures.stream()
                        .map(CompletableFuture::join)
                        .collect(Collectors.joining(" ")));
    }

}
