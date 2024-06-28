import org.eclipse.californium.elements.exception.ConnectorException;
import org.example.util.CoAPUtils;

import java.io.IOException;

public class CoAPTest {
    public static void main(String[] args) throws ConnectorException, IOException {
        // 启动 CoAP 服务器
        CoAPUtils.startCoapServer(5683);
        // 创建 CoAP 客户端工具实例
        String uri = "coap://localhost:5683/example";
        CoAPUtils coapClient = new CoAPUtils(uri);

        // 发送 GET 请求
        coapClient.sendGet();

        // 发送 POST 请求
        String payload = "Hello, CoAP";
        coapClient.sendPost(payload);
    }
}
