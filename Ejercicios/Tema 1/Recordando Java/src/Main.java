
public class Main {
    public static void main(String[] args) {
        final long NANOSECONDSINSECOND = 1000000000L;
        double firstRecord;
        double lastRecord;
        double totalTime;
        double totalTimeInSeconds;
        boolean isPrime;

        int[] list = new int[10];
        Utilities.fillWithRandomNumbers(list);

        firstRecord = System.nanoTime();

        for (int num: list) {

            isPrime = Utilities.naive(num);

            System.out.println(num + (isPrime?" Is prime":" Is not prime"));
        }

        lastRecord = System.nanoTime();
        totalTime = (lastRecord - firstRecord);
        totalTimeInSeconds = totalTime/NANOSECONDSINSECOND;

        System.out.println("\nTime delayed: " + String.format("%.3f", totalTimeInSeconds) + "s");

    }
}