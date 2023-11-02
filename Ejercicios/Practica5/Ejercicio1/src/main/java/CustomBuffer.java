public class CustomBuffer {

    private int[] list;
    private int writePointer;
    private int readPointer;
    private int filled;
    private int finishedProducers;

    public CustomBuffer(){
        list = new int[4];
        writePointer = 0;
        readPointer = 0;
        filled = 0;
        finishedProducers = 0;
    }

    public synchronized void put(int num){
        if (isFull()){
            try{
                System.out.println("Producer waiting...");
                wait();
            }catch (InterruptedException e) {
                System.err.println("Producer interrupted");
            }
        }

        list[writePointer] = num;
        writePointer = (writePointer+1) % list.length;
        filled++;
        notifyAll();
    }

    public synchronized int get(int id){
        if (isEmpty()){
            try{
                System.out.println("Consumer " + id + " waiting...");
                wait();
            }catch (InterruptedException e) {
                System.err.println("Consumer interrupted");
            }
        }
        int num = list[readPointer];
        readPointer = (readPointer+1) % list.length;
        filled--;
        notifyAll();

        return num;
    }

    private boolean isEmpty(){
        return filled == 0;
    }

    private boolean isFull(){
        return filled == list.length;
    }

    public synchronized void finishProducer(){
        finishedProducers++;
        if (isFinished()) notifyAll();//De esta forma evito que se queden consumidores en el estado de wait cuando
                                        //todos los productores hayan terminado
    }

    public synchronized boolean isFinished(){
        return finishedProducers == 3;
    }

}
