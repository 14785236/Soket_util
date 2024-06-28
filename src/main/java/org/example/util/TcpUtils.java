package org.example.util;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.function.Consumer;

public class TcpUtils {
    private Socket socket;
    private BufferedReader reader;
    private BufferedWriter writer;
    /**
     * 连接到指定服务器。
     *
     * @param host 服务器主机名或 IP 地址
     * @param port 服务器端口号
     * @throws IOException 如果无法连接到服务器
     */
    public void connect(String host, int port) throws IOException {
        try {
            socket = new Socket(host, port);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            System.out.println("已连接到 " + host + ":" + port);
        } catch (IOException e) {
            throw new IOException("连接失败: " + e.getMessage());
        }
    }

    /**
     * 发送数据到服务器。
     *
     * @param data 要发送的数据
     * @throws IOException 如果发送数据失败
     */
    public void send(String data) throws IOException {
        if (socket == null || socket.isClosed()) {
            throw new SocketException("未连接到服务器");
        }
        try {
            writer.write(data);
            writer.newLine();
            writer.flush();
            System.out.println("发送数据: " + data);
        } catch (IOException e) {
            throw new IOException("发送数据失败: " + e.getMessage());
        }
    }

    /**
     * 接收来自服务器的数据。
     *
     * @return 接收到的数据
     * @throws IOException 如果接收数据失败
     */
    public String receive() throws IOException {
        if (socket == null || socket.isClosed()) {
            throw new SocketException("未连接到服务器");
        }
        try {
            String data = reader.readLine();
            System.out.println("接收到数据: " + data);
            return data;
        } catch (IOException e) {
            throw new IOException("接收数据失败: " + e.getMessage());
        }
    }

    /**
     * 断开与服务器的连接。
     */
    public void disconnect() {
        try {
            if (reader != null) {
                reader.close();
            }
            if (writer != null) {
                writer.close();
            }
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
            System.out.println("已断开连接");
        } catch (IOException e) {
            System.err.println("断开连接时发生错误: " + e.getMessage());
        }
    }

    /**
     * 发送数据并接收响应。
     *
     * @param data 数据
     * @return 响应
     * @throws IOException 发送或接收失败
     */
    public String sendAndReceive(String data) throws IOException {
        send(data);
        return receive();
    }

    /**
     * 异步接收数据
     *
     * @param onReceive 回调函数，用于处理接收到的数据
     * @throws IOException 如果接收数据失败
     */
    public void asyncReceive(Consumer<String> onReceive) throws IOException {
        if (socket == null || socket.isClosed()) {
            throw new SocketException("未连接到服务器");
        }

        new Thread(() -> {
            try {
                while (true) {
                    String data = reader.readLine();
                    if (data == null) break;
                    onReceive.accept(data);
                }
            } catch (IOException e) {
                System.err.println("异步接收数据失败: " + e.getMessage());
            }
        }).start();
    }
}
