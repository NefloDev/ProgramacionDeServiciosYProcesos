package Entities;

import java.util.Random;

/**
 * <h1>Gardener</h1>
 * This class represents a gardener that works on a garden
 *
 * @author Alejandro Nebot Flores
 * @version 1.0
 * @since 11-09-2023
 */
public class Gardener implements Runnable {
    /**
     * A GardenManager that manages the availability of the gardens
     */
    private GardenManager manager;
    /**
     * An integer that represents the id of the gardener
     */
    private int id;
    /**
     * An integer that represents the amount of days the gardener will work
     */
    private int days;
    /**
     * An integer that represents the amount of gardens the gardener has worked on
     */
    private int worked;

    /**
     * Constructor of the class
     * @param id The id of the gardener
     * @param days The amount of days the gardener will work
     * @param manager The garden manager
     */
    public Gardener(int id, int days, GardenManager manager) {
        this.id = id;
        this.days = days;
        this.manager = manager;
        worked = 0;
    }

    /**
     * This method is executed when the thread is started
     * It will make the gardener work on the garden that hasn't been worked on the longest
     * and show a message when it starts and when it finishes
     * If the garden that it wants to work on is occupied, it will try again until it finds an unoccupied one
     */
    @Override
    public void run() {
        int garden;
        boolean working;

        for (int i = 0; i < days; i++) {
            garden = manager.getGardenWorkedLongestTimeAgo();

            do {
                working = manager.workOnGarden(garden);

                if(working){
                    System.out.println("Gardener " + id + " is working on garden " + (garden + 1));

                    try {
                        Thread.sleep(1000);
                        manager.stopWorkingOnGarden(garden);
                        System.out.println("Gardener " + id + " stopped working on garden " + (garden + ));
                        worked++;

                    } catch (InterruptedException e) {
                        System.err.println("Gardener " + id + " was interrupted while working on garden " + (garden + 1));
                    }
                }
            }while (!working);
        }
    }

    /**
     * This method returns the amount of gardens the gardener has worked on
     * @return The amount of gardens the gardener has worked on
     */
    public int getWorked(){
        return worked;
    }

    /**
     * This method returns the id of the gardener
     * @return The id of the gardener
     */
    public int getId(){
        return id;
    }
}
