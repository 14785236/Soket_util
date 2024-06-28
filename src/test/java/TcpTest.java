import org.example.util.TcpUtils;

public class TcpTest {
    public static void main(String[] args) {
        String serverHost = "127.0.0.1"; // 替换为你的服务器 IP
        int serverPort = 1200; // 替换为你的服务器端口
        TcpUtils tcpUtils = new TcpUtils();
        try {
            // 连接到服务器
            tcpUtils.connect(serverHost, serverPort);

            // 发送数据
            tcpUtils.send("Hello, Server!");

            // 接收响应
            String response = tcpUtils.receive();
            System.out.println("收到响应: " + response);

            // 使用异步接收
            tcpUtils.asyncReceive(data -> {
                System.out.println("异步收到数据: " + data);
            });

            // 断开连接
            tcpUtils.disconnect();
        } catch (Exception e) {
            System.err.println("错误: " + e.getMessage());
        }
    }
}
