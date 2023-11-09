package Entities;

/**
 * <h1>GardenManager</h1>
 * This class represents a garden manager that manages the gardens
 *
 * @author Alejandro Nebot Flores
 * @version 1.0
 * @since 11-09-2023
 */
public class GardenManager {

    /**
     * An array of booleans that represents the availability of the gardens
     */
    private Garden[] gardens;

    /**
     * Constructor of the class
     * @param numberOfGardens The amount of gardens that the garden manager will manage
     */
    public GardenManager(int numberOfGardens) {
        gardens = new Garden[numberOfGardens];
        for (int i = 0; i < gardens.length; i++) {
            gardens[i] = new Garden();
        }
    }

    /**
     * This method will set the garden to occupied if it is unoccupied and return true
     * @param garden The garden that the gardener wants to work on
     * @return True if the garden was unoccupied and false if it was occupied
     */
    public synchronized boolean workOnGarden(int garden){
        if(!gardens[garden].getOccupied()){
            gardens[garden].work();
            return true;
        }
        return false;
    }

    /**
     * This method will return the garden that was worked on the longest time ago
     * @return The garden that was worked on the longest time ago
     */
    public synchronized int getGardenWorkedLongestTimeAgo(){
        int garden = 0;
        long time = 0;
        for (int i = 0; i < gardens.length; i++) {
            if(gardens[i].getTime() > time){
                time = gardens[i].getTime();
                garden = i;
            }
        }
        return garden;
    }

    /**
     * This method will set the garden to unoccupied
     * @param garden The garden that the gardener has finished working on
     */
    public synchronized void stopWorkingOnGarden(int garden){
        gardens[garden].free();
    }

    /**
     * This method will return true if the garden is in bad condition
     * @param garden The garden that the citizen wants to visit
     * @return True if the garden is in bad condition
     */
    public synchronized boolean visitGarden(int garden){
        return gardens[garden].isBadCondition();
    }

    /**
     * This method will return the amount of gardens that the garden manager manages
     * @return The amount of gardens that the garden manager manages
     */
    public int getAmountOfGardens(){
        return gardens.length;
    }

}
