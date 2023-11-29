import java.util.concurrent.atomic.AtomicInteger;

public class Contador{

    AtomicInteger cont = new AtomicInteger();

    public synchronized void subeContador(){
        cont.incrementAndGet();
    }

    public void muestraContador(){
        System.out.println("Contador: " + cont);
    }

}
