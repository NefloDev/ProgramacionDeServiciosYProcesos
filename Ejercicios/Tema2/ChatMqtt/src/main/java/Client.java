import org.eclipse.paho.client.mqttv3.*;

import java.io.*;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import java.util.stream.Collectors;

public class Client {
    MqttClient client;
    String username;
    String url;
    String path;
    public Client(String serverUrl, String baseURL, String username, String path) {
        this.username = username;
        this.url = baseURL;
        this.path = path;
        String publisherId = UUID.randomUUID().toString();
        try {
            this.client = new MqttClient(serverUrl, publisherId);
        } catch (MqttException e) {
            throw new RuntimeException(e);
        }
    }
    public void connect(){
        try{
            MqttConnectOptions options = new MqttConnectOptions();
            options.setAutomaticReconnect(true);
            options.setCleanSession(true);
            options.setConnectionTimeout(10);
            client.connect(options);
            initCallbacks();
        }catch (MqttException e) {
            throw new RuntimeException(e);
        }
    }
    public void close() {
        try{
            client.disconnect();
            client.close();
        }catch (MqttException e) {
            throw new RuntimeException(e);
        }
    }
    public void sendMessage(String username, String message){
        if (client.isConnected()) {
            String topic = getChatTopicSend(username);
            byte[] payload = message.getBytes();
            MqttMessage msg = new MqttMessage(payload);
            msg.setQos(0);
            msg.setRetained(true);
            try{
                client.publish(topic, msg);
            }catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
    public String getMessages(String username) throws IOException {
        Path filename = Path.of(path, "chat" + username);
        BufferedReader br = new BufferedReader(new FileReader(filename.toFile()));
        return br.lines().collect(Collectors.joining("\r\n"));
    }
    private void initCallbacks() throws MqttException {
        client.setCallback(new MqttCallback() {
            @Override
            public void connectionLost(Throwable throwable) {}
            @Override
            public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
                writeReceived(topic, mqttMessage);
            }
            @Override
            public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
                try {
                    String topic = iMqttDeliveryToken.getTopics()[0];
                    MqttMessage mqttMessage = iMqttDeliveryToken.getMessage();
                    writeSent(topic, mqttMessage);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
        String[] topics = new String[]{"/%s/todos".formatted(url), "/%s/+/%s".formatted(url, username)};
        client.subscribe(topics, new int[]{0,0});
    }
    private String getChatTopicSend(String otherUser){
        String topic;
        if (otherUser.equals("todos")){
            topic = String.format("/%s/%s", url, otherUser);
        } else {
            topic = String.format("/%s/%s/%s", url, username, otherUser);
        }
        return topic;
    }
    private void writeReceived(String topic, MqttMessage mqttMessage) throws IOException {
        String[] topicSplit = topic.split("/");

        writeMessage(Path.of(path, topicSplit[1] + topicSplit[2]), mqttMessage);
    }
    private void writeSent(String topic, MqttMessage mqttMessage) throws IOException {
        String[] topicSplit = topic.split("/");
        if (!topicSplit[2].equals("todos")) {
            writeMessage(Path.of(path, topicSplit[1] + topicSplit[3]), mqttMessage);
        }
    }
    private void writeMessage(Path filename, MqttMessage mqttMessage) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(filename.toFile(), true));
        bw.write("%s (%s): %s".formatted(username, LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM HH:mm")), mqttMessage.toString()));
        bw.newLine();
        bw.close();
    }
}
