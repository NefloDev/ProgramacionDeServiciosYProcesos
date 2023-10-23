import java.util.Random;

public class Corredor extends Thread{

    private String icon;
    private int raceLength;
    private int position = 0;
    private int slipProbability;
    public volatile static boolean raceFinished;

    public Corredor(String icon, int raceLength, int priority, int slipProbability){
        this.icon = icon;
        this.raceLength = raceLength;
        setPriority(priority);
        this.slipProbability = slipProbability;
    }

    @Override
    public void run() {
        Random random = new Random();
        position = 0;
        raceFinished = false;

        while(position < raceLength && !raceFinished){

            if(random.nextInt(0,101) < slipProbability){
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }else{
                position += 1;

                try {
                    sleep(300);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }

            if(!Corredor.raceFinished){
                Corredor.raceFinished = position == raceLength;
            }
        }
    }

    public String getIcon(){
        return icon;
    }

    public int getPosition(){
        return position;
    }

    public synchronized void printPosition(){
        System.out.println(" ".repeat(position) + icon);
    }
}
