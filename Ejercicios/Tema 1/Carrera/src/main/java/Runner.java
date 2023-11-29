import java.util.Random;

public class Runner {

    private char symbol;
    private int baseSpeed;
    private int turboProbability;
    private int crashProbability;
    private int position;

    public Runner(char symbol, int baseSpeed, int turboProbability, int crashProbability) {
        this.symbol = symbol;
        this.baseSpeed = baseSpeed;
        this.turboProbability = turboProbability;
        this.crashProbability = crashProbability;
        position = 0;
    }

    public char getSymbol() {
        return symbol;
    }

    public int getBaseSpeed() {
        return baseSpeed;
    }

    public int getTurboProbability() {
        return turboProbability;
    }

    public int getCrashProbability() {
        return crashProbability;
    }

    public int getPosition() {
        return position;
    }

    public synchronized boolean advance(int raceEnd){
        Random r = new Random();
        int boost = r.nextInt(1,10);
        if(position < raceEnd){
            position+=boost<=turboProbability?baseSpeed*2:baseSpeed;
        }

        if (position > raceEnd){
            position = raceEnd;
        }

        return position < raceEnd;
    }

    public synchronized boolean crash(int raceEnd){
        Random r = new Random();
        int crash = r.nextInt(1,10);

        if (position < raceEnd){
            position-=crash<=crashProbability?0:baseSpeed;
        }

        if (position< 0){
            position = 0;
        }

        return position < raceEnd;
    }

}
