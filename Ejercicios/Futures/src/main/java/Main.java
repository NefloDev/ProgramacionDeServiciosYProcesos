import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class Main {

    public static String fastMethod(){
        return "F2";
    }
    public static String slowMethod(){
        try{
            Thread.sleep(1);
        }catch (InterruptedException ignored){}
        return "S2";
    }
    public static void main(String[] args) {
        System.out.println("Future with slow method:");
        System.out.println("1");
        CompletableFuture.supplyAsync(() -> slowMethod())
                .thenAccept(System.out::println)
                .whenComplete((nothing, error) -> System.out.println("S3"));
        System.out.println("4");
        System.out.println("Future with fast method:");
        System.out.println("1");
        CompletableFuture.supplyAsync(() -> fastMethod())
                .thenAccept(System.out::println)
                .whenComplete((nothing, error) -> System.out.println("F3"));
        System.out.println("4");
        while (true);
    }

}
