import java.util.Random;

public class Main {

    public static void main(String[] args) {
        String url = "54.160.92.6";
        String mqttUrl = String.format("tcp://%s:1883", url);
        CarSimulator simulator = new CarSimulator(mqttUrl);
        Radar radar = new Radar(mqttUrl, url);
        PoliceStation policeStation = new PoliceStation(mqttUrl, url);

        Thread simulatorThread = new Thread(simulator);
        Thread radarThread = new Thread(radar);
        Thread policeStationThread = new Thread(policeStation);

        simulatorThread.start();
        radarThread.start();
        policeStationThread.start();

    }

}
