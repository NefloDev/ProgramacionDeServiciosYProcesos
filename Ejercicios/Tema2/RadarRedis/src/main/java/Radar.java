import org.eclipse.paho.client.mqttv3.*;
import redis.clients.jedis.Jedis;
import java.util.UUID;

public class Radar implements Runnable {
    //Class attributes
    private MqttClient client;
    private int speed;
    private String licensePlate;
    private final String redisUrl;
    public static final String VEHICLES = "ALEJANDRO:VEHICLES";
    public Radar(String mqttUrl, String redisUrl) {
        try {
            this.redisUrl = redisUrl;
            //Creating mqtt client from url using a random id
            this.client = new MqttClient(mqttUrl, UUID.randomUUID().toString());
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
        //Creating jedis client from url
        try(Jedis jedis = new Jedis(redisUrl, 6379)){
            do {
                try {
                    if(licensePlate != null){
                        //Checking if the speed is higher than 80
                        if (speed > 80){
                            //Setting up the excess message
                            String msg = String.format("EXCESS:%d:%s", speed, licensePlate);
                            MqttMessage message = new MqttMessage(msg.getBytes());
                            //Publishing the message to the topic "car/excess"
                            client.publish("car/excess", message);
                        }
                        //Adding the license plate to the vehicle list in Redis
                        jedis.rpush(VEHICLES, licensePlate);
                        licensePlate = null;
                    }
                    Thread.sleep(1000);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }while (true);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    private void initCallbacks() throws MqttException {
        client.setCallback(new MqttCallback() {
            @Override
            public void connectionLost(Throwable throwable) {}
            @Override
            public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
                //Splitting message by ":" to get the data
                String[] data = mqttMessage.toString().split(":");
                //Setting the data to the class attributes
                licensePlate = data[0];
                speed = Integer.parseInt(data[1]);
            }
            @Override
            public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
            }
        });
        //Subscribing to the topic "car/data"
        client.subscribe("car/data");
    }
}
