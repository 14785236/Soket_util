package org.example.util;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class MqttUtil {
    private MqttClient client;
    private String brokerUrl;
    private String clientId;

    /**
     * 构造函数，用于初始化 MQTT 客户端。
     *
     * @param brokerUrl MQTT 代理 URL。
     * @param clientId  客户端 ID。
     * @throws MqttException 初始化失败时抛出异常。
     */
    public MqttUtil(String brokerUrl, String clientId) throws MqttException {
        this.brokerUrl = brokerUrl;
        this.clientId = clientId;
        this.client = new MqttClient(brokerUrl, clientId);
    }

    /**
     * 连接到 MQTT 代理。
     *
     * @throws MqttException 连接失败时抛出异常。
     */
    public void connect() throws MqttException {
        if (!client.isConnected()) {
            client.connect();
            System.out.println("连接到MQTT代理: " + brokerUrl);
        }
    }

    /**
     * 断开与 MQTT 代理的连接。
     *
     * @throws MqttException 断开连接失败时抛出异常。
     */
    public void disconnect() throws MqttException {
        if (client.isConnected()) {
            client.disconnect();
            System.out.println("已断开连接");
        }
    }

    /**
     * 发布消息到指定的主题。
     *
     * @param topic   主题名称。
     * @param content 消息内容。
     * @param qos     服务质量 (QoS)。
     * @throws MqttException 发布失败时抛出异常。
     */
    public void publish(String topic, String content, int qos) throws MqttException {
        if (client.isConnected()) {
            MqttMessage message = new MqttMessage(content.getBytes());
            message.setQos(qos);
            client.publish(topic, message);
            System.out.println("消息已发布到主题: " + topic);
        } else {
            System.out.println("客户端未连接");
        }
    }

    /**
     * 订阅指定的主题。
     *
     * @param topic 主题名称。
     * @throws MqttException 订阅失败时抛出异常。
     */
    public void subscribe(String topic) throws MqttException {
        if (client.isConnected()) {
            client.subscribe(topic, (t, message) -> {
                System.out.println("主题: " + t);
                System.out.println("消息: " + new String(message.getPayload()));
            });
            System.out.println("已订阅主题: " + topic);
        } else {
            System.out.println("客户端未连接");
        }
    }
}
