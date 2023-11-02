import java.util.Random;

public class Consumer extends Thread{

    private int id;
    private CustomBuffer buffer;

    public Consumer(int id, CustomBuffer buffer){
        this.id = id;
        this.buffer = buffer;
    }

    @Override
    public void run() {
        Random r = new Random();
        int num;
        while(!buffer.isFinished()) {
//            try {
//                Thread.sleep(r.nextInt(0, 2001));
//            } catch (InterruptedException e) {
//                System.err.println("Consumer interrupted");
//            }
            num = buffer.get(id);
            System.out.println("Consumer " + id + " consumed: " + num);
        }
        System.out.println("Consumer " + id + " finished");
    }
}