import java.util.List;

public class Main {
    public static void main(String[] args) {
        Table table = new Table(5);

        List<Thread> threads = List.of(
                new Thread(new Philosopher(table, 0, 1, 0)),
                new Thread(new Philosopher(table, 1, 2, 1)),
                new Thread(new Philosopher(table, 2, 3, 2)),
                new Thread(new Philosopher(table, 3, 4, 3)),
                new Thread(new Philosopher(table, 4, 0, 4))
        );

        threads.forEach(Thread::start);

    }
}