import java.util.Random;

public class Philosopher implements Runnable {
    Table table;
    int leftCutlery, rightCutlery;
    int id;

    public Philosopher(Table table, int leftCutlery, int rightFork, int id) {
        this.table = table;
        this.leftCutlery = leftCutlery;
        this.rightCutlery = rightFork;
        this.id = id;
    }

    public void run() {
        while (true) {
            if (table.tryToGetCutlery(leftCutlery, rightCutlery)) {
                doSomething("Philosopher " + id + " is eating...");
                table.releaseCutlery(leftCutlery, rightCutlery);
                doSomething("Philosopher " + id + " is thinking...");
            }
        }
    }

    private void doSomething(String message) {
        System.out.println(message);
        try {
            Random r = new Random();
            Thread.sleep(r.nextInt(5000));
        } catch (InterruptedException ex) {
            System.out.println("Wait failed");
        }
    }
}