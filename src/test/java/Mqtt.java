import org.eclipse.paho.client.mqttv3.MqttException;
import org.example.util.MqttUtil;

import java.util.concurrent.TimeUnit;

public class Mqtt {
    public static void main(String[] args) {
        try {
            // MQTT 代理地址和客户端 ID
            String broker = "tcp://localhost:1883";
            String clientId = "JavaApp";

            // 初始化 MqttUtil
            MqttUtil mqttUtil = new MqttUtil(broker, clientId);

            // 连接到 MQTT 代理
            mqttUtil.connect();
            // 发布消息
            mqttUtil.publish("sensors/temperature", "22.5", 2);

            // 订阅消息
            mqttUtil.subscribe("sensors/temperature");

            // 休眠一段时间来接收消息
            TimeUnit.SECONDS.sleep(10);

            // 断开连接
            mqttUtil.disconnect();
        } catch (MqttException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
