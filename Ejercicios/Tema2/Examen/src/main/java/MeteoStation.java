import org.eclipse.paho.client.mqttv3.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.UUID;

public class MeteoStation implements Runnable {
    private static final String NAME = "ALEJANDRO";
    private String id;
    private String topic;
    private MqttClient client;

    public MeteoStation() {
        this.id = UUID.randomUUID().toString().substring(0, 8);
        topic = NAME + "/METEO/" + id + "/MEASUREMENTS";
    }

    @Override
    public void run() {
        try {
            client = new MqttClient("tcp://test.mosquitto.org:1883", id);
            MqttConnectOptions options = new MqttConnectOptions();
            options.setAutomaticReconnect(true);
            options.setCleanSession(true);
            options.setConnectionTimeout(10);
            client.connect(options);
            initializeCallbacks();

            Random rand = new Random();
            while (true) {
                if(client.isConnected()){
                    LocalDateTime dateTime = LocalDateTime.now();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
                    int temp = rand.nextInt(-10, 41);
                    String message = String.format("(%s) : %dºC", formatter.format(dateTime), temp);
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

    private void initializeCallbacks(){
        client.setCallback(new MqttCallback() {
            @Override
            public void connectionLost(Throwable throwable) {
                System.err.println("Connection lost in MeteoStation " + id + ": " + throwable.getMessage());
            }

            @Override
            public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
                System.out.println("Message arrived in MeteoStation " + id + ": " + mqttMessage.toString());
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
                try {
                    System.out.println("Delivery complete in MeteoStation " + id + ": " + iMqttDeliveryToken.getMessage().toString());
                } catch (MqttException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}
