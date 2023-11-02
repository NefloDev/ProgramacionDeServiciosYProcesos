import java.util.Random;

public class Producer extends Thread{

    private int id;
    private CustomBuffer buffer;

    public Producer(int id, CustomBuffer buffer){
        this.id = id;
        this.buffer = buffer;
    }

    @Override
    public void run() {
        Random r = new Random();
        for (int i = 1; i <= 30; i++) {
//            try {
//                Thread.sleep(r.nextInt(0, 2001));
//            } catch (InterruptedException e) {
//                System.err.println("Producer interrupted");
//            }
            buffer.put(i);
            System.out.println("Producer " + id + " produced: " + i);
        }
        buffer.finishProducer();
        System.out.println("Producer " + id + " finished");
    }
}
