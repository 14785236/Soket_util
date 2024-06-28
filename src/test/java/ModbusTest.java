import org.example.util.ModbusUtils;

public class ModbusTest {
    public static void main(String[] args) {
        String ipAddress = "192.168.1.100"; // 替换为你的 Modbus 设备 IP
        int port = 502; // Modbus TCP 默认端口
        int unitId = 1; // Modbus 单元标识符
        int registerRef = 0; // 寄存器地址
        int coilRef = 0; // 线圈地址
        int valueToWrite = 12345; // 要写入的寄存器值
        boolean stateToWrite = true; // 要写入的线圈状态

        ModbusUtils modbusUtils = new ModbusUtils(ipAddress, port);

        // 连接 Modbus 设备
        modbusUtils.connect();

        // 读取保持寄存器的值
        int[] registerValues = modbusUtils.readHoldingRegisters(unitId, registerRef, 10);
        System.out.println("保持寄存器的值：");
        for (int value : registerValues) {
            System.out.println(value);
        }

        // 写入保持寄存器的值
        modbusUtils.writeHoldingRegister(unitId, registerRef, valueToWrite);
        System.out.println("已写入保持寄存器，值为 " + valueToWrite);

        // 读取线圈的状态
        boolean[] coilStates = modbusUtils.readCoils(unitId, coilRef, 10);
        System.out.println("线圈状态：");
        for (boolean state : coilStates) {
            System.out.println(state);
        }

        // 写入线圈的状态
        modbusUtils.writeCoil(unitId, coilRef, stateToWrite);
        System.out.println("已写入线圈状态，状态为 " + stateToWrite);

        // 断开 Modbus 设备连接
        modbusUtils.disconnect();
    }
}
