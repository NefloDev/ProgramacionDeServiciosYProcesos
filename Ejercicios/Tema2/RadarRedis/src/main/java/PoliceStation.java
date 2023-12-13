import org.eclipse.paho.client.mqttv3.*;
import redis.clients.jedis.Jedis;

import java.util.UUID;

public class PoliceStation implements Runnable {
    //Class attributes
    private final MqttClient client;
    private final Jedis jedis;
    public static final String VEHICLES = "ALEJANDRO:VEHICLES";
    public static final String FINEDVEHICLES = "ALEJANDRO:FINEDVEHICLES";

    public PoliceStation(String mqttUrl, String redisUrl) {
        try {
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
            //Creating jedis client from url
            jedis = new Jedis(redisUrl, 6379);
            //Deleting previous data (to show proper data in the first run)
            jedis.del(FINEDVEHICLES, VEHICLES);
        } catch (MqttException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        do {
            try {
                //Getting the length of the vehicles list and the fined vehicles list
                long vehiclesLength = jedis.llen(VEHICLES);
                long finedVehiclesLength = jedis.llen(FINEDVEHICLES);
                //Calculating the percentage of fined vehicles
                double percentage = (double)finedVehiclesLength/vehiclesLength*100;
                //Printing the data
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
                //Splitting message by ":" to get the data
                String[] data = mqttMessage.toString().split(":");
                //If the message is "EXCESS" and the data is not null
                if(data[0].equals("EXCESS") && data[2]!=null) {
                    //Calculating the percentage of speed excess
                    int percentage = (10000/Integer.parseInt(data[1]))-100;
                    int fine;
                    //Calculating the fine depending on the percentage of excess
                    if (percentage >= 10 && percentage < 20) {
                        fine = 100;
                    }else if (percentage >= 20 && percentage < 30) {
                        fine = 200;
                    }else {
                        fine = 300;
                    }
                    //Setting up the message to send to the ticket topic
                    String msg = String.format("TICKET:%s:%d", data[2], fine);
                    MqttMessage message = new MqttMessage(msg.getBytes());
                    //Publishing the message
                    client.publish("car/ticket", message);
                    //Adding the vehicle to the fined vehicles list in redis
                    jedis.rpush(FINEDVEHICLES, data[2]);
                }
            }
            @Override
            public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
            }
        });
        //Subscribing to the excess topic
        client.subscribe("car/excess");
    }
}
