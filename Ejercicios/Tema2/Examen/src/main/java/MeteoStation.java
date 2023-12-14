import org.eclipse.paho.client.mqttv3.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class MeteoStation implements Runnable {
    private static final String NAME = "ALEJANDRO";
    private String id;
    private String topic;
    private MqttClient client;
    private static final String IP = "184.73.34.167";
    private boolean stop;

    public MeteoStation(int id) {
        this.id = String.valueOf(id);
        topic = NAME + "/METEO/" + this.id + "/MEASUREMENTS";
        stop = false;
    }

    @Override
    public void run() {
        try {
            client = new MqttClient("tcp://" + IP + ":1883", id);
            MqttConnectOptions options = new MqttConnectOptions();
            options.setAutomaticReconnect(true);
            options.setCleanSession(true);
            options.setConnectionTimeout(10);
            client.connect(options);
            initializeCallbacks();

            Random rand = new Random();
            while (!stop) {
                if(client.isConnected()){
                    LocalDateTime dateTime = LocalDateTime.now();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy/HH:mm:ss");
                    int temp = rand.nextInt(-10, 41);
                    String message = String.format("%s/%s/%d", id, formatter.format(dateTime), temp);
                    MqttMessage msg = new MqttMessage(message.getBytes());
                    msg.setQos(0);
                    msg.setRetained(false);
                    client.publish(topic, msg);
                    Thread.sleep(5000);
                }
            }
        } catch (Exception e) {
            System.err.println("Error in MeteoStation " + id + ": " + e.getMessage());
        }
    }

    public void stop(){
        stop = true;
        System.out.printf("MeteoStation %s stopped\n", id);
    }

    private void initializeCallbacks(){
        client.setCallback(new MqttCallback() {
            @Override
            public void connectionLost(Throwable throwable) {
                System.err.println("Connection lost in MeteoStation " + id + ": " + throwable.getMessage());
            }

            @Override
            public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {}

            @Override
            public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {}
        });
    }
}
