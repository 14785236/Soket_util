package org.example.util;


import com.ghgande.j2mod.modbus.ModbusException;
import com.ghgande.j2mod.modbus.io.ModbusTCPTransaction;
import com.ghgande.j2mod.modbus.msg.*;
import com.ghgande.j2mod.modbus.net.TCPMasterConnection;
import com.ghgande.j2mod.modbus.procimg.SimpleRegister;

import java.net.InetAddress;

/**
 * Modbus 工具类，用于连接 Modbus 设备并执行读写操作。
 */
public class ModbusUtils {

    private TCPMasterConnection connection;
    private InetAddress address;
    private int port;

    /**
     * 构造方法，初始化 Modbus 工具类。
     *
     * @param ipAddress Modbus 设备的 IP 地址
     * @param port      Modbus 设备的端口号
     */
    public ModbusUtils(String ipAddress, int port) {
        try {
            this.address = InetAddress.getByName(ipAddress);
        } catch (Exception e) {
            throw new RuntimeException("初始化 IP 地址失败: " + e.getMessage());
        }
        this.port = port;
    }

    /**
     * 连接到 Modbus 设备。
     */
    public void connect() {
        try {
            connection = new TCPMasterConnection(address);
            connection.setPort(port);
            connection.connect();
            System.out.println("连接 Modbus 设备成功");
        } catch (Exception e) {
            throw new RuntimeException("连接 Modbus 设备失败: " + e.getMessage());
        }
    }

    /**
     * 断开与 Modbus 设备的连接。
     */
    public void disconnect() {
        if (connection != null && connection.isConnected()) {
            connection.close();
            System.out.println("断开 Modbus 设备连接");
        }
    }

    /**
     * 读取保持寄存器的值。
     *
     * @param unitId       Modbus 单元 ID
     * @param ref          寄存器参考地址
     * @param count        读取的寄存器数量
     * @return 读取到的寄存器值
     */
    public int[] readHoldingRegisters(int unitId, int ref, int count) {
        int[] values = new int[count];
        try {
            ReadMultipleRegistersRequest request = new ReadMultipleRegistersRequest(ref, count);
            request.setUnitID(unitId);
            ModbusTCPTransaction transaction = new ModbusTCPTransaction(connection);
            transaction.setRequest(request);
            transaction.execute();
            ReadMultipleRegistersResponse response = (ReadMultipleRegistersResponse) transaction.getResponse();
            for (int i = 0; i < count; i++) {
                values[i] = response.getRegisterValue(i);
            }
        } catch (ModbusException e) {
            throw new RuntimeException("读取保持寄存器失败: " + e.getMessage());
        }
        return values;
    }

    /**
     * 写入单个保持寄存器的值。
     *
     * @param unitId Modbus 单元 ID
     * @param ref    寄存器参考地址
     * @param value  要写入的值
     */
    public void writeHoldingRegister(int unitId, int ref, int value) {
        try {
            WriteSingleRegisterRequest request = new WriteSingleRegisterRequest(ref, new SimpleRegister(value));
            request.setUnitID(unitId);
            ModbusTCPTransaction transaction = new ModbusTCPTransaction(connection);
            transaction.setRequest(request);
            transaction.execute();
            WriteSingleRegisterResponse response = (WriteSingleRegisterResponse) transaction.getResponse();
            System.out.println("写入成功，寄存器地址 " + ref + " 的值为 " + response.getRegisterValue());
        } catch (ModbusException e) {
            throw new RuntimeException("写入保持寄存器失败: " + e.getMessage());
        }
    }

    /**
     * 读取线圈的状态。
     *
     * @param unitId Modbus 单元 ID
     * @param ref    线圈参考地址
     * @param count  读取的线圈数量
     * @return 读取到的线圈状态
     */
    public boolean[] readCoils(int unitId, int ref, int count) {
        boolean[] states = new boolean[count];
        try {
            ReadCoilsRequest request = new ReadCoilsRequest(ref, count);
            request.setUnitID(unitId);
            ModbusTCPTransaction transaction = new ModbusTCPTransaction(connection);
            transaction.setRequest(request);
            transaction.execute();
            ReadCoilsResponse response = (ReadCoilsResponse) transaction.getResponse();
            for (int i = 0; i < count; i++) {
                states[i] = response.getCoils().getBit(i);
            }
        } catch (ModbusException e) {
            throw new RuntimeException("读取线圈状态失败: " + e.getMessage());
        }
        return states;
    }

    /**
     * 写入单个线圈的状态。
     *
     * @param unitId Modbus 单元 ID
     * @param ref    线圈参考地址
     * @param state  要写入的状态
     */
    public void writeCoil(int unitId, int ref, boolean state) {
        try {
            WriteCoilRequest request = new WriteCoilRequest(ref, state);
            request.setUnitID(unitId);
            ModbusTCPTransaction transaction = new ModbusTCPTransaction(connection);
            transaction.setRequest(request);
            transaction.execute();
            WriteCoilResponse response = (WriteCoilResponse) transaction.getResponse();
            System.out.println("写入成功，线圈地址 " + ref + " 的状态为 " + response.getCoil());
        } catch (ModbusException e) {
            throw new RuntimeException("写入线圈状态失败: " + e.getMessage());
        }
    }
}