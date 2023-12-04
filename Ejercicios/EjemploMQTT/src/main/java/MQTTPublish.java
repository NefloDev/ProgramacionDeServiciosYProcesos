import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MQTTPublish {

    public static void main(String[] args) {
        String publisherId = UUID.randomUUID().toString();
        IMqttClient publisher;
        try{
            publisher = new MqttClient("tcp://54.160.92.6:1883", publisherId);

            MqttConnectOptions options = new MqttConnectOptions();
            options.setAutomaticReconnect(true);
            options.setCleanSession(true);
            options.setConnectionTimeout(10);

            publisher.connect(options);

            BufferedReader reader = new BufferedReader(new java.io.InputStreamReader(System.in));
            System.out.println("Enter message: ");
            String message = reader.readLine();
            System.out.println("Enter topic: ");
            String topic = reader.readLine();

            try{
                ExecutorService executorService = Executors.newFixedThreadPool(5);
                Future<Boolean> result = executorService.submit(new MQTTPublisher(publisher, topic, message));
                if(result.get()) {
                    System.out.println("MQTT message sent");
                }else{
                    System.out.println("MQTT message not sent");
                }
            }catch (InterruptedException | ExecutionException e){
                e.printStackTrace();
            }

            publisher.disconnect();
            publisher.close();

        }catch (MqttException | IOException e){
            throw new RuntimeException(e);
        }
    }

}
