
public class Main {

    public static void main(String[] args) {
        MeteoClient client = new MeteoClient();
        Thread t = new Thread(client);
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
