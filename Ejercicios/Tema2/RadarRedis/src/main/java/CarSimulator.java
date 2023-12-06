import org.eclipse.paho.client.mqttv3.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.UUID;

public class CarSimulator implements Runnable {
    private final MqttClient client;
    private final Random rand = new Random();
    public CarSimulator(String url) {
        try {
            this.client = new MqttClient(url, UUID.randomUUID().toString());
            MqttConnectOptions options = new MqttConnectOptions();
            options.setAutomaticReconnect(true);
            options.setCleanSession(true);
            options.setConnectionTimeout(10);
            client.connect();
            initCallbacks();
        } catch (MqttException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        try {
            Thread.sleep(1000);
            int speed = rand.nextInt(60, 141);
            String licensePlate = generateLicensePlate();

            if(client.isConnected()) {
                String topic = "car/data";
                byte[] payload = (licensePlate + ":" + speed).getBytes();
                MqttMessage msg = new MqttMessage(payload);
                msg.setQos(0);
                msg.setRetained(true);
                client.publish(topic, msg);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void initCallbacks() throws MqttException {
        client.setCallback(new MqttCallback() {
            @Override
            public void connectionLost(Throwable throwable) {}
            @Override
            public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
                String[] fine = mqttMessage.toString().split(":");
                System.out.printf("(%s): %s - %.2f€%n",
                        LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM HH:mm")),
                        fine[1], Double.parseDouble(fine[2]));
            }
            @Override
            public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
            }
        });
        client.subscribe("car/ticket");
    }

    private String generateLicensePlate(){
        Random rand = new Random();
        StringBuilder licensePlate = new StringBuilder();
        licensePlate.append(String.format("%04d", rand.nextInt(10000)));
        for (int i = 0; i < 3; i++) {
            licensePlate.append((char)rand.nextInt('A', 'Z'+1));
        }
        return licensePlate.toString();
    }
}
