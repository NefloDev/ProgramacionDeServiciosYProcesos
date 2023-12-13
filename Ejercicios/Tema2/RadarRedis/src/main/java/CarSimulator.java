import org.eclipse.paho.client.mqttv3.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.UUID;

public class CarSimulator implements Runnable {
    //Class attributes
    private final MqttClient client;
    private final Random rand = new Random();
    public CarSimulator(String url) {
        try {
            //Creating mqtt client from url using a random id
            this.client = new MqttClient(url, UUID.randomUUID().toString());
            //Setting up mqttconnection options
            MqttConnectOptions options = new MqttConnectOptions();
            options.setAutomaticReconnect(true);
            options.setCleanSession(true);
            options.setConnectionTimeout(10);
            //Connecting to the broker
            client.connect();
            //Initializing callbacks
            initCallbacks();
        } catch (MqttException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        do {
            try {
                //Generating a random speed between 60 and 140
                int speed = rand.nextInt(60, 141);
                //Generating a random license plate
                String licensePlate = generateLicensePlate();

                if(client.isConnected()) {
                    String topic = "car/data";
                    //Setting the payload as licensePlate:speed to an array of bytes
                    byte[] payload = (licensePlate + ":" + speed).getBytes();
                    //Creating a new mqtt message with the payload
                    MqttMessage msg = new MqttMessage(payload);
                    //Setting the message options
                    msg.setQos(0);
                    msg.setRetained(true);
                    //Publishing the message to the topic "car/data"
                    client.publish(topic, msg);
                }
                Thread.sleep(1000);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }while (true);
    }

    private void initCallbacks() throws MqttException {
        client.setCallback(new MqttCallback() {
            @Override
            public void connectionLost(Throwable throwable) {}
            @Override
            public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
                //Splitting message by ":" to get the data
                String[] fine = mqttMessage.toString().split(":");
                //Printing the fine
                System.out.printf("(%s): %s - %.2f€%n",
                        LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM HH:mm")),
                        fine[1], Double.parseDouble(fine[2]));
            }
            @Override
            public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
            }
        });
        //Subscribing to the topic "car/ticket"
        client.subscribe("car/ticket");
    }

    private String generateLicensePlate(){
        Random rand = new Random();
        StringBuilder licensePlate = new StringBuilder();
        //Generating a random license plate appending a random number between 0 and 9999 and 3 random letters
        licensePlate.append(String.format("%04d", rand.nextInt(10000)));
        for (int i = 0; i < 3; i++) {
            licensePlate.append((char)rand.nextInt('A', 'Z'+1));
        }
        return licensePlate.toString();
    }
}
