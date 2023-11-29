public class CrashThread extends Thread{

    private Runner runner;
    private int endRace;

    public CrashThread(Runner runner, int endRace){
        this.runner = runner;
        this.endRace = endRace;
    }

    @Override
    public void run() {
        while(runner.crash(endRace)){
            try{
                Thread.sleep(1000);
            }catch (InterruptedException e){
                throw new RuntimeException(e);
            }
        }
    }

}
