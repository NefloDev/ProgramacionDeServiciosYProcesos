package Entities;

import static java.lang.System.currentTimeMillis;

/**
 * <h1>Garden</h1>
 * This class represents a garden
 *
 * @author Alejandro Nebot Flores
 * @version 1.0
 * @since 11-09-2023
 */
public class Garden {

    /**
     * A boolean that represents if the garden is occupied or not
     */
    private boolean occupied;
    /**
     * A long that represents the time that has passed since the garden was worked on
     */
    private long lastWorked;

    /**
     * Constructor of the class
     */
    public Garden(){
        occupied = false;
        lastWorked = 0;
    }

    /**
     * This method will set the occupied variable to the value of the parameter
     */
    public synchronized void work(){
        this.occupied = true;
        this.lastWorked = currentTimeMillis();
    }

    /**
     * This method will return the time that has passed since the garden was worked on
     * @return The time that has passed since the garden was worked on
     */
    public synchronized long getTime(){
        return currentTimeMillis() - this.lastWorked;
    }

    /**
     * This method will set the occupied variable to false
     */

    public synchronized void free(){
        this.occupied = false;
    }

    /**
     * This method will return the value of the occupied variable
     * @return The value of the occupied variable
     */
    public synchronized boolean getOccupied(){
        return occupied;
    }

    /**
     * This method will return true if the garden has not been worked on for 2 or more seconds
     * @return True if the garden has not been worked on for 2 or more seconds
     */
    public synchronized boolean isBadCondition(){
        return (currentTimeMillis() - this.lastWorked) >= 2000;
    }

}
