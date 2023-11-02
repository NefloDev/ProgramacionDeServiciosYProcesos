import java.util.Random;

public class Client extends Thread{
    private int tryAmount;
    private int id;
    private Shop shop;

    public Client(Shop shop, int tryAmount, int id) {
        this.shop = shop;
        this.tryAmount = tryAmount;
        this.id = id;
    }

    public void run() {
        Random r = new Random();
        int i = 1;
        boolean exit = false;
        boolean bought = false;

        while (i <= tryAmount && !exit){
            if(shop.tryToBuyPlayStation()){
                bought = shop.buyPlayStation(id);
                if (bought) {
                    System.out.println("Client " + id + " bought a PS5");
                    exit = true;
                } else {
                    System.out.println("Client " + id + " couldn't buy a PS5");
                }
            }

            try {
                Thread.sleep(r.nextInt(500));
            } catch (InterruptedException e) {
                System.err.println("Sleep error");
            }
            i++;
        }

        if (!bought) {
            System.out.println("Client " + id + " didn't buy a PS5");
        }
    }

}
