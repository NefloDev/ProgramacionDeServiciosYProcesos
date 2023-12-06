import org.eclipse.paho.client.mqttv3.*;
import redis.clients.jedis.Jedis;
import java.util.UUID;

public class Radar implements Runnable {
    private MqttClient client;
    private int speed;
    private String licensePlate;
    private final String redisUrl;
    public Radar(String mqttUrl, String redisUrl) {
        try {
            this.redisUrl = redisUrl;
            this.client = new MqttClient(mqttUrl, UUID.randomUUID().toString());
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
            if(licensePlate != null){
                if (speed > 80){
                    String msg = String.format("EXCESS:%d:%s", speed, licensePlate);
                    MqttMessage message = new MqttMessage(msg.getBytes());
                    client.publish("car/excess", message);
                }else{
                    try(Jedis jedis = new Jedis(redisUrl, 6379)){
                        jedis.set("VEHICLES", licensePlate);
                    }
                }
                licensePlate = null;
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
                String[] data = mqttMessage.toString().split(":");
                licensePlate = data[0];
                speed = Integer.parseInt(data[1]);
            }
            @Override
            public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
            }
        });
        client.subscribe("car/data");
    }
}
