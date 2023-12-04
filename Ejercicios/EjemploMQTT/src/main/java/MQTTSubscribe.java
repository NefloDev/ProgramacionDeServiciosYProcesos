import org.eclipse.paho.client.mqttv3.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

public class MQTTSubscribe {

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new java.io.InputStreamReader(System.in));

        System.out.println("Enter topic: ");
        String topic = reader.readLine();

        String publisherId = UUID.randomUUID().toString();
        IMqttClient publisher;
        try{
            publisher = new MqttClient("tcp://54.160.92.6:1883", publisherId);
            MqttConnectOptions options = new MqttConnectOptions();
            options.setAutomaticReconnect(true);
            options.setCleanSession(true);
            options.setConnectionTimeout(10);
            publisher.connect(options);

            publisher.setCallback(new MqttCallback() {
                @Override
                public void connectionLost(Throwable cause) {
                    System.out.println("Connection to Solace broker lost!" + cause.getMessage());
                }

                @Override
                public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
                    System.out.println("\nReceived a Message!" +
                            "\n\tTime:    " + LocalDateTime.now() +
                            "\n\tTopic:   " + s +
                            "\n\tMessage: " + new String(mqttMessage.getPayload()) +
                            "\n\tQoS:     " + mqttMessage.getQos() + "\n");
                }

                @Override
                public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
                }
            });

            publisher.subscribe(topic, 0);

        }catch (MqttException e){
            throw new RuntimeException(e);
        }
    }

}
