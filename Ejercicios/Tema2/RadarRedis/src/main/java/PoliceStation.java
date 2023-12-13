import org.eclipse.paho.client.mqttv3.*;
import redis.clients.jedis.Jedis;

import java.util.UUID;

public class PoliceStation implements Runnable {
    private final MqttClient client;
    private final Jedis jedis;
    public static final String VEHICLES = "ALEJANDRO:VEHICLES";
    public static final String FINEDVEHICLES = "ALEJANDRO:FINEDVEHICLES";

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
            jedis.del(FINEDVEHICLES, VEHICLES);
        } catch (MqttException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        do {
            try {
                long vehiclesLength = jedis.llen(VEHICLES);
                long finedVehiclesLength = jedis.llen(FINEDVEHICLES);
                double percentage = (double)finedVehiclesLength/vehiclesLength*100;
                System.out.printf("Total vehicles: %d\n", vehiclesLength);
                System.out.printf("Total tickets: %.2f%%(%d fined vehicles)\n", finedVehiclesLength == 0 ? 0 : percentage, finedVehiclesLength);
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
                        jedis.rpush(FINEDVEHICLES, data[2]);
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
