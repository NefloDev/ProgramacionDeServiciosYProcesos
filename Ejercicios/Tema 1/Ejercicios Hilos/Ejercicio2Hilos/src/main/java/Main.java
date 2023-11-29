
import java.util.List;

public class Main {

    public static void main(String[] args) {
        //Liebre 60km/h (8)
        //Caballo 70km/h (9)
        //Tortuga 1km/h (1)
        //Perro 72km/h (10)
        //Carrera 1000m (1000 repeticiones)

        int raceLength = 1000;

        List<Corredor> animales = List.of(
                new Corredor("\uD83D\uDC15", raceLength, 10, 10),
                new Corredor("\uD83D\uDC0E", raceLength,  9, 10),
                new Corredor("\uD83D\uDC07", raceLength,  8, 10),
                new Corredor("\uD83D\uDC22", raceLength,  1, 10)
        );

        animales.forEach(Thread::start);

        while (!Corredor.raceFinished){
            System.out.println("\n".repeat(10));
            System.out.println("-".repeat(raceLength) + "|");
            animales.forEach(Corredor::printPosition);
            System.out.println("-".repeat(raceLength) + "|");
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        animales.forEach(corredor -> {
            try {
                corredor.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        System.out.print("Ganador/ganadores: ");
        animales.stream().filter(c -> c.getPosition() == raceLength).map(Corredor::getIcon).forEach(System.out::print);
    }
}
