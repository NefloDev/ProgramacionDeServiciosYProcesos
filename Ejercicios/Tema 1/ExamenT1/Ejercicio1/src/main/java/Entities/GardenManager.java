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
    private boolean[] gardens;

    /**
     * Constructor of the class (by default it will set all the gardens to unoccupied)
     * @param numberOfGardens The amount of gardens that the garden manager will manage
     */
    public GardenManager(int numberOfGardens) {
        gardens = new boolean[numberOfGardens];
    }

    /**
     * This method will set the garden to occupied if it is unoccupied and return true
     * @param garden The garden that the gardener wants to work on
     * @return True if the garden was unoccupied and false if it was occupied
     */
    public synchronized boolean workOnGarden(int garden){
        if(gardens[garden]){
            return false;
        }else{
            gardens[garden] = true;
        }
        return true;
    }

    /**
     * This method will set the garden to unoccupied
     * @param garden The garden that the gardener has finished working on
     */
    public synchronized void stopWorkingOnGarden(int garden){
        gardens[garden] = false;
    }

    /**
     * This method will return the amount of gardens that the garden manager manages
     * @return The amount of gardens that the garden manager manages
     */
    public int getAmountOfGardens(){
        return gardens.length;
    }

}
