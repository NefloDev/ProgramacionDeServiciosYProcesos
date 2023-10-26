public class ShowRaceThread extends Thread{

    private Race race;

    public ShowRaceThread(Race race){
        this.race = race;
    }

    @Override
    public void run() {
        while (!race.isFinished()){
            try {
                race.showRace();
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
