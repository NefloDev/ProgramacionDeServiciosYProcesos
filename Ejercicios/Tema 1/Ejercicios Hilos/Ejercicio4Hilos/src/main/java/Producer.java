public class Producer extends Thread{

    private final Buffer buffer;

    public Producer(Buffer buffer){
        this.buffer = buffer;
    }

    @Override
    public void run(){
        for (int i = 0; i < 10; i++) {
            buffer.put(i);
            System.out.println("Producer put: " + i);
            try{
                sleep((long) (Math.random()*100));
            }catch (InterruptedException e){
                System.err.println("Producer interrupted");
            }
        }
    }

}
