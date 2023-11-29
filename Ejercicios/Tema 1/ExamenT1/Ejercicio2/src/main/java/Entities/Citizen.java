package Entities;

import java.util.ArrayList;
import java.util.Random;

/**
 * <h1>Citizen</h1>
 * This class represents a citizen that visits a garden
 *
 * @author Alejandro Nebot Flores
 * @version 1.0
 * @since 11-09-2023
 */
public class Citizen implements Runnable{

    /**
     * An integer that represents the amount of days the citizen will visit the gardens
     */
    private int days;
    /**
     * A GardenManager that manages the availability of the gardens
     */
    private GardenManager manager;
    /**
     * An ArrayList of Strings that represents the complaints of the citizen
     */
    private ArrayList<String> complaints;

    /**
     * Constructor of the class
     * @param days The amount of days the citizen will visit the gardens
     * @param manager The garden manager
     */
    public Citizen(int days, GardenManager manager){
        this.days = days;
        this.manager = manager;
        complaints = new ArrayList<>();
    }

    /**
     * This method is executed when the thread is started
     */
    @Override
    public void run() {
        Random r = new Random();
        int garden;
        for (int i = 0; i < days; i+=2) {
            try{
                garden = r.nextInt(manager.getAmountOfGardens());
                if(manager.visitGarden(garden)){
                    complaints.add("Garden " + (garden + 1) + " is on bad conditions");
                }
                Thread.sleep(2000);
            }catch (InterruptedException e){
                System.err.println("Citizen was interrupted");
            }
        }
    }

    /**
     * This method will return the complaints of the citizen
     * @return The complaints of the citizen
     */

    public ArrayList<String> getComplaints(){
        return complaints;
    }
}
