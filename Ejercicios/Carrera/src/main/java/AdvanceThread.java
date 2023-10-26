public class AdvanceThread extends Thread{

    private Runner runner;
    private int endRace;

    public AdvanceThread(Runner runner, int endRace){
        this.runner = runner;
        this.endRace = endRace;
    }

    @Override
    public void run() {
        while(runner.advance(endRace)){
            try{
                Thread.sleep(1000);
            }catch (InterruptedException e){
                throw new RuntimeException(e);
            }
        }
        if(Race.getWinner() == null){
            Race.setWinner(runner);
        }
    }
}
