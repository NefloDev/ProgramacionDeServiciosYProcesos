import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        //###########Exercise 1
        List<MeteoStation> stations = new ArrayList<>();
        List<Thread> threads = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            stations.add(new MeteoStation(i));
            threads.add(new Thread(stations.get(i-1)));
        }
        threads.forEach(Thread::start);

        //###########Exercise 2
        MeteoServer server = new MeteoServer();
        Thread t = new Thread(server);
        t.start();
    }

}
