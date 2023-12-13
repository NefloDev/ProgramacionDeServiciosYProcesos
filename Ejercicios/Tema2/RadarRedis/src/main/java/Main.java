import java.util.Random;

public class Main {

    public static void main(String[] args) {
        //Constants declarations
        String url = "34.228.162.124";
        String mqttUrl = String.format("tcp://%s:1883", url);

        //Runnable objects declarations
        CarSimulator simulator = new CarSimulator(mqttUrl);
        Radar radar = new Radar(mqttUrl, url);
        PoliceStation policeStation = new PoliceStation(mqttUrl, url);

        //Thread for each runnable object declaration
        Thread simulatorThread = new Thread(simulator);
        Thread radarThread = new Thread(radar);
        Thread policeStationThread = new Thread(policeStation);

        //Start threads
        simulatorThread.start();
        radarThread.start();
        policeStationThread.start();

    }

}
