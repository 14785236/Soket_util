package org.example.util;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPUtils {
    private DatagramSocket socket;
    /**
     * 构造函数，用于创建一个 UDP 工具实例。
     *
     * @throws Exception 如果创建套接字失败
     */
    public UDPUtils() throws Exception {
        this.socket = new DatagramSocket();
    }

    /**
     * 发送 UDP 数据报。
     *
     * @param message  发送的消息
     * @param ipAddress 目标 IP 地址
     * @param port    目标端口号
     * @throws Exception 如果发送失败
     */
    public void send(String message, String ipAddress, int port) throws Exception {
        InetAddress address = InetAddress.getByName(ipAddress);
        byte[] buffer = message.getBytes();
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, port);
        socket.send(packet);
        System.out.println("已发送消息到 " + ipAddress + ":" + port + " 内容: " + message);
    }

    /**
     * 接收 UDP 数据报。
     *
     * @param bufferSize 缓冲区大小
     * @return 接收到的消息
     * @throws Exception 如果接收失败
     */
    public String receive(int bufferSize) throws Exception {
        byte[] buffer = new byte[bufferSize];
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
        socket.receive(packet);
        String receivedMessage = new String(packet.getData(), 0, packet.getLength());
        System.out.println("接收到的消息: " + receivedMessage + " 来自: " + packet.getAddress().getHostAddress() + ":" + packet.getPort());
        return receivedMessage;
    }

    /**
     * 关闭 UDP 套接字。
     */
    public void close() {
        if (socket != null && !socket.isClosed()) {
            socket.close();
            System.out.println("已关闭 UDP 套接字");
        }
    }
}
