import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        //Exercise 1
        List<MeteoStation> stations = new ArrayList<>();
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            stations.add(new MeteoStation());
            threads.add(new Thread(stations.get(i)));
        }
        threads.forEach(Thread::start);

    }

}
