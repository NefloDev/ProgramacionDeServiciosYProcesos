import java.util.Arrays;

public class Table {
    boolean[] freeCutlery;

    public Table(int cutleryAmount) {
        freeCutlery = new boolean[cutleryAmount];
        Arrays.fill(freeCutlery, true);
    }

    public synchronized boolean tryToGetCutlery(int pos1, int pos2) {
        boolean hasCutlery = (freeCutlery[pos1]) && (freeCutlery[pos2]);

        Arrays.fill(freeCutlery, pos1, pos2, !hasCutlery);

        return hasCutlery;
    }

    public void releaseCutlery(int pos1, int pos2) {
        Arrays.fill(freeCutlery, pos1, pos2, true);
    }
}