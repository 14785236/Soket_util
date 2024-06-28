package org.example.util;

import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.CoapResponse;
import org.eclipse.californium.core.CoapServer;
import org.eclipse.californium.core.coap.MediaTypeRegistry;
import org.eclipse.californium.core.server.resources.CoapExchange;
import org.eclipse.californium.elements.exception.ConnectorException;

import java.io.IOException;

public class CoAPUtils {
    private CoapClient client;

    /**
     * 构造函数，用于创建一个 CoAP 工具实例。
     *
     * @param uri CoAP 服务器 URI
     */
    public CoAPUtils(String uri) {
        this.client = new CoapClient(uri);
    }

    /**
     * 发送 GET 请求。
     *
     * @return 响应内容
     */
    public String sendGet() throws ConnectorException, IOException {
        CoapResponse response = client.get();
        if (response != null) {
            System.out.println("GET 请求响应: " + response.getResponseText());
            return response.getResponseText();
        } else {
            System.out.println("GET 请求失败");
            return null;
        }
    }

    /**
     * 发送 POST 请求。
     *
     * @param payload 请求内容
     * @return 响应内容
     */
    public String sendPost(String payload) throws ConnectorException, IOException {
        CoapResponse response = client.post(payload, MediaTypeRegistry.TEXT_PLAIN);
        if (response != null) {
            System.out.println("POST 请求响应: " + response.getResponseText());
            return response.getResponseText();
        } else {
            System.out.println("POST 请求失败");
            return null;
        }
    }
    /**
     * 启动一个简单的 CoAP 服务器。
     *
     * @param port 监听端口
     */
    public static void startCoapServer(int port) {
        CoapServer server = new CoapServer(port);
        server.add(new CoapResource("example") {
            @Override
            public void handleGET(CoapExchange exchange) {
                exchange.respond("Hello, CoAP!");
            }

            @Override
            public void handlePOST(CoapExchange exchange) {
                String payload = exchange.getRequestText();
                exchange.respond("Received payload: " + payload);
            }
        });
        server.start();
        System.out.println("CoAP 服务器已启动，端口: " + port);
    }
}
