import java.util.Stack;

public class Buffer {

    private final Stack<Integer> bufferStack;
    private boolean empty;

    public Buffer() {
        this.bufferStack = new Stack<>();
        empty = true;
    }

    public synchronized void put(int value){
        while(!empty){
            try{
                wait();
            }catch (InterruptedException e){
                System.err.println("Producer interrupted");
            }
        }
        bufferStack.push(value);
        empty = false;
        notifyAll();
    }

    public synchronized int get(){
        while(empty){
            try{
                wait();
            }catch (InterruptedException e){
                System.err.println("Consumer interrupted");
            }
        }
        empty = true;
        notifyAll();
        return bufferStack.pop();
    }

}
