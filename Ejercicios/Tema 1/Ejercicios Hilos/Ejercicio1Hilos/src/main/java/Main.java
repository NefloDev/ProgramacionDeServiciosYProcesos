import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        Contador c = new Contador();

        ArrayList<Thread> list = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            list.add(new Thread(() -> {
                for (int j = 0; j < 500; j++) {
                    c.subeContador();
                }
            }));
        }

        list.forEach(Thread::start);
        list.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        c.muestraContador();
    }
}
