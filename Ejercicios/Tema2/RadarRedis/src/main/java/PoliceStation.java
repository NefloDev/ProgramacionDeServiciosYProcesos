import org.eclipse.paho.client.mqttv3.*;
import redis.clients.jedis.Jedis;
import java.util.UUID;

public class PoliceStation implements Runnable {
    private final MqttClient client;
    private final Jedis jedis;

    public PoliceStation(String mqttUrl, String redisUrl) {
        try {
            this.client = new MqttClient(mqttUrl, UUID.randomUUID().toString());
            MqttConnectOptions options = new MqttConnectOptions();
            options.setAutomaticReconnect(true);
            options.setCleanSession(true);
            options.setConnectionTimeout(10);
            client.connect();
            initCallbacks();
            jedis = new Jedis(redisUrl, 6379);
        } catch (MqttException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        try {
            Thread.sleep(1000);
            System.out.printf("Total vehicles: %d\n", jedis.get("VEHICLES").length());
            System.out.printf("Total tickets: %.2f\n", (double)jedis.get("FINEDVEHICLES").length()/jedis.get("VEHICLES").length());
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
                if(data[0].equals("EXCESS")) {
                    if(data[2] != null){
                        int percentage = (10000/Integer.parseInt(data[1]))-100;
                        int fine;
                        if (percentage >= 10 && percentage < 20) {
                            fine = 100;
                        }else if (percentage >= 20 && percentage < 30) {
                            fine = 200;
                        }else {
                            fine = 300;
                        }
                        String msg = String.format("TICKET:%s:%d", data[2], fine);
                        MqttMessage message = new MqttMessage(msg.getBytes());
                        client.publish("car/ticket", message);
                        jedis.set("FINEDVEHICLES", data[2]);
                    }
                }
            }
            @Override
            public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
            }
        });
        client.subscribe("car/excess");
    }
}
