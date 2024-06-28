import org.example.util.UDPUtils;

public class UDPTest {
    public static void main(String[] args) {
        try {
            // 创建 UDP 工具实例
            UDPUtils udpUtils = new UDPUtils();

            // 目标 IP 地址
            String ipAddress = "127.0.0.1"; // 本地回环地址用于测试
            // 目标端口
            int port = 9876; // UDP 端口

            // 发送消息
            String messageToSend = "Hello, UDP!";
            udpUtils.send(messageToSend, ipAddress, port);

            // 接收消息（需要在另一个线程或另一个实例中发送消息，才能接收到消息）
            int bufferSize = 1024; // 接收缓冲区大小
            String receivedMessage = udpUtils.receive(bufferSize);

            // 打印接收到的消息
            System.out.println("接收到的消息: " + receivedMessage);

            // 关闭 UDP 套接字
            udpUtils.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
