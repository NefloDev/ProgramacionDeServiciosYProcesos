import java.util.ArrayList;
import java.util.List;

public class Race {

    private List<Runner> runners;
    private List<AdvanceThread> advanceThreads;
    private List<CrashThread> crashThreads;
    private int raceEnd;
    private ShowRaceThread showRaceThread;

    private static Runner winner;
    private boolean finished;

    public Race(List<Runner> runners, int raceEnd){
        this.runners = runners;
        this.raceEnd = raceEnd;
        advanceThreads = new ArrayList<>();
        crashThreads = new ArrayList<>();
        init();
    }

    private void init(){
        for (Runner runner : runners) {
            advanceThreads.add(new AdvanceThread(runner, raceEnd));
            crashThreads.add(new CrashThread(runner, raceEnd));
        }
        showRaceThread = new ShowRaceThread(this);
        Race.winner = null;
        finished = false;
    }

    public void startRace(){
        advanceThreads.forEach(Thread::start);
        crashThreads.forEach(Thread::start);
        showRaceThread.start();
    }

    public void endRace(){
        advanceThreads.forEach(advanceThread -> {
            try {
                advanceThread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        crashThreads.forEach(crashThread -> {
            try {
                crashThread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        finished = true;
        try{
            showRaceThread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Winner: \"" + winner.getSymbol() + "\"");
    }

    public static synchronized void setWinner(Runner winner){
        Race.winner = winner;
    }

    public static Runner getWinner(){
        return Race.winner;
    }

    public boolean isFinished(){
        return finished;
    }

    public void showRace(){
        StringBuilder sb = new StringBuilder();
        System.out.println("\n".repeat(20));
        System.out.println("-".repeat(raceEnd+3));
        for (Runner runner : runners) {
            sb.setLength(0);
            sb.append("|").append(" ".repeat(runner.getPosition())).append(runner.getSymbol());
            sb.append(" ".repeat(raceEnd - runner.getPosition())).append("|");
            System.out.println(sb);
        }
        System.out.println("-".repeat(raceEnd+3));
    }

}
