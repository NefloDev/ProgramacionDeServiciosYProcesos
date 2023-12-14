import org.eclipse.paho.client.mqttv3.*;
import redis.clients.jedis.Jedis;

public class MeteoServer implements Runnable{
    private MqttClient client;
    private Jedis jedis;
    private boolean stop;
    public static final String NAME = "ALEJANDRO";
    private static final String IP = "184.73.34.167";
    private static final String LASTMEASUREMENT = "LASTMEASUREMENT";
    private static final String TEMP = "TEMPERATURES";
    public static final String ALERTS = "ALERTS";
    public MeteoServer(){
        try{
            client = new MqttClient("tcp://" + IP +":1883", "SERVER");
            jedis = new Jedis(IP,6379);
            MqttConnectOptions options = new MqttConnectOptions();
            options.setAutomaticReconnect(true);
            options.setCleanSession(true);
            options.setConnectionTimeout(10);
            client.connect(options);

            initializeCallbacks();
            stop = false;
        }catch(Exception e){
            System.err.println("Error in MeteoServer: " + e.getMessage());
        }
    }
    @Override
    public void run() {
        while (!stop){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void stop(){
        stop = true;
        System.out.println("MeteoServer stopped");
    }

    private void initializeCallbacks() throws MqttException{
        client.setCallback(new MqttCallback() {
            @Override
            public void connectionLost(Throwable throwable) {
                System.err.println("Connection lost in MeteoServer: " + throwable.getMessage());
            }

            @Override
            public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
                String[] message = mqttMessage.toString().split("/");
                String id = message[0];
                String date = message[1];
                String time = message[2];
                int temp = Integer.parseInt(message[3]);

                String lastList = NAME + ":" + LASTMEASUREMENT + ":" + id;
                String evoList = NAME + ":" + TEMP + ":" + id;
                String alertList = NAME + ":" + ALERTS;

                jedis.hset(lastList, "date", date + " " + time);
                jedis.hset(lastList, "temp", String.valueOf(temp));
                jedis.rpush(evoList, String.valueOf(temp));

                if (temp > 30 || temp < 0){
                    String alert = "Alerta por temperaturas extremas el " + date + " a las " + time + " en la estación " + id;
                    jedis.rpush(alertList, alert);
                }
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {}
        });
        for (int i = 1; i <= 10 ; i++) {
            String topic = NAME + "/METEO/" + i + "/MEASUREMENTS";
            client.subscribe(topic);
        }
    }
}
