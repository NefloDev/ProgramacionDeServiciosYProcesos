import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        CustomBuffer buffer = new CustomBuffer();

        List<Producer> producers = List.of(
                new Producer(1, buffer),
                new Producer(2, buffer),
                new Producer(3, buffer)
        );

        List<Consumer> consumers = List.of(
                new Consumer(1, buffer),
                new Consumer(2, buffer)
        );

        producers.forEach(Thread::start);
        consumers.forEach(Thread::start);

        producers.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                System.err.println("Error");
            }
        });
        consumers.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                System.err.println("Error");
            }
        });

    }

}
