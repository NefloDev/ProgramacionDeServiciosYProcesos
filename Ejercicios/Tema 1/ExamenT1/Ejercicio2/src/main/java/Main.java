import Entities.GardenManager;
import Entities.Gardener;
import Entities.Citizen;

import java.util.ArrayList;
import java.util.List;

public class Main {

    //I made this program to be able to increment or decrease the amount of gardeners, gardens and days easily so it
    //can be adapted to different situations
    public static void main(String[] args) {
        final int NUMBER_OF_GARDENS = 10;
        final int NUMBER_OF_DAYS = 30;
        GardenManager manager = new GardenManager(NUMBER_OF_GARDENS);

        List<Gardener> gardeners = List.of(
                new Gardener(1, NUMBER_OF_DAYS, manager),
                new Gardener(2, NUMBER_OF_DAYS, manager),
                new Gardener(3, NUMBER_OF_DAYS, manager)
        );

        List<Citizen> citizens = List.of(
                new Citizen(NUMBER_OF_DAYS, manager),
                new Citizen(NUMBER_OF_DAYS, manager),
                new Citizen(NUMBER_OF_DAYS, manager),
                new Citizen(NUMBER_OF_DAYS, manager),
                new Citizen(NUMBER_OF_DAYS, manager)
        );

        ArrayList<Thread> threads = new ArrayList<>();
        gardeners.forEach(g -> threads.add(new Thread(g)));
        citizens.forEach(c -> threads.add(new Thread(c)));

        threads.forEach(Thread::start);

        threads.forEach(gardener -> {
            try {
                gardener.join();
            } catch (InterruptedException e) {
                System.err.println("Error joining thread");
            }
        });

        System.out.println("From the gardeners: ");

        gardeners.forEach(g -> System.out.println("Gardener " + g.getId() + " worked on " + g.getWorked() + " gardens"));

        System.out.println("\nFrom the citizens: ");

        citizens.forEach(c -> {
            if (!c.getComplaints().isEmpty()){
                c.getComplaints().forEach(System.out::println);
            }
        });

    }

}
