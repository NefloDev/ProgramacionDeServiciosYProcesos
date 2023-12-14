import redis.clients.jedis.Jedis;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MeteoClient implements Runnable{
    private static final String IP = "184.73.34.167";
    private static final String HELP = """
                                    Commands:
                                    LAST ID -> Last measurement by ID
                                    MAXTEMP ID -> Max temperature by ID
                                    MAXTEMP ALL -> Max temperature by all stations
                                    ALERTS -> Alerts
                                    HELP -> Shows this message
                                    EXIT -> Exits the program
                                    """;
    private Jedis jedis;
    private List<MeteoStation> stations;
    private MeteoServer server;
    private Thread serverThread;
    List<Thread> threads;
    public MeteoClient(){
        try {
            System.out.println("Starting service...");
            jedis = new Jedis(IP, 6379);

            //###########Exercise 1
            stations = new ArrayList<>();
            threads = new ArrayList<>();
            for (int i = 1; i <= 10; i++) {
                stations.add(new MeteoStation(i));
                threads.add(new Thread(stations.get(i-1)));
            }
            threads.forEach(Thread::start);

            //###########Exercise 2
            server = new MeteoServer();
            serverThread = new Thread(server);
            serverThread.start();

        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void run() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        boolean exit = false;
        //Eliminamos todas las listas para comenzar una nueva sesión vacía
        for (int i = 1; i <= 10; i++) {
            jedis.hdel("ALEJANDRO:LASTMEASUREMENT:"+i, "date");
            jedis.hdel("ALEJANDRO:LASTMEASUREMENT:"+i, "temp");
            jedis.del("ALEJANDRO:TEMPERATURES:"+i);
        }
        jedis.del("ALEJANDRO:ALERTS");
        System.out.println("Welcome to MeteoClient");
        System.out.println(HELP);
        do{
            try{
                System.out.print("$ ");
                String input = br.readLine();
                switch (input){
                    case "EXIT": exit = true; break;
                    case "LAST ID":
                        System.out.print("ID: ");
                        input = br.readLine();

                        if(!input.isEmpty()){
                            String last = "ALEJANDRO:LASTMEASUREMENT:" + input;

                            String date = jedis.hget(last, "date");
                            String temp = jedis.hget(last, "temp");

                            System.out.println("Last measurement by " + input + ": " + temp + "ºC at " + date);
                        }
                        break;
                    case "MAXTEMP ID":
                        System.out.print("ID: ");
                        input = br.readLine();

                        String evoList = "ALEJANDRO:TEMPERATURES:" + input;
                        List<String> temps = jedis.lrange(evoList, 0, jedis.llen(evoList));

                        int max = temps.stream().map(Integer::parseInt).max(Integer::compareTo).get();

                        System.out.println("Max temperature by " + input + ": " + max + "ºC");
                        break;
                    case "MAXTEMP ALL":
                        ArrayList<String> allTempList = new ArrayList<>();
                        for (int i = 1; i <= 10; i++) {
                            String evo = "ALEJANDRO:TEMPERATURES:" + i;
                            allTempList.addAll(jedis.lrange(evo, 0, jedis.llen(evo)));
                        }
                        int maxAll = allTempList.stream().map(Integer::parseInt).max(Integer::compareTo).get();
                        System.out.println("Max temperature by all stations: " + maxAll + "ºC");
                        break;
                    case "ALERTS":
                        System.out.println("Alerts:");

                        for (int i = 0; i < jedis.llen("ALEJANDRO:ALERTS"); i++) {
                            System.out.println(jedis.lpop("ALEJANDRO:ALERTS"));
                        }
                        break;
                        case "HELP":
                            System.out.println(HELP);
                    default:
                        System.out.println("Command not available");
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }while (!exit);
        server.stop();
        stations.forEach(MeteoStation::stop);
        try {
            serverThread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        threads.forEach(thread -> {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }
}
