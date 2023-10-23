public class Consumer extends Thread{

    private final Buffer buffer;

    public Consumer(Buffer buffer){
        this.buffer = buffer;
    }

    public void run(){
        int value;
        for (int i = 0; i < 10; i++) {
            value = buffer.get();
            System.out.println("Consumer got: " + value);
            try{
                sleep((long) (Math.random()*100));
            }catch (InterruptedException e){
                System.err.println("Consumer interrupted");
            }
        }
    }

}
