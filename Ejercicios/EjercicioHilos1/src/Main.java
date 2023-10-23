
public class Main {

    public static void main(String[] args) {
        Contador c = new Contador();

        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 500; i++) {
                c.subeContador();
            }
        });
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 500; i++) {
                c.subeContador();
            }
        });
        Thread t3 = new Thread(() -> {
            for (int i = 0; i < 500; i++) {
                c.subeContador();
            }
        });
        Thread t4 = new Thread(() -> {
            for (int i = 0; i < 500; i++) {
                c.subeContador();
            }
        });

        t1.start();
        t2.start();
        t3.start();
        t4.start();

        try {
            t1.join();
            t2.join();
            t3.join();
            t4.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        c.muestraContador();
    }
}
