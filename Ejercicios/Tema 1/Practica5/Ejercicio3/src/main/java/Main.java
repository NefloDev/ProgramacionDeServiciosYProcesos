import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        final int TRY_AMOUNT = 10;
        Shop shop = new Shop();

        List<Client> clients = new ArrayList<>();

        for (int i = 0; i < 200; i++) {
            clients.add(new Client(shop, TRY_AMOUNT, i));
        }

        clients.forEach(Client::start);

        clients.forEach(client -> {
            try {
                client.join();
            } catch (InterruptedException e) {
                System.err.println("Join error");
            }
        });

        System.out.println("Shop has " + shop.getPlayStationAmount() + " PS5 left");

    }
}
