package performance.monitoring.tracker;

import com.fazecast.jSerialComm.SerialPort;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class SerialPortManager {
    private SerialPort serialPort;
    private BufferedReader serialReader;
    private String portName;
    private SerialDataListener dataListener;

    public SerialPortManager(String portName, SerialDataListener dataListener) {
        this.portName = portName;
        this.dataListener = dataListener;
        initializeSerialPort();
    }

    private void initializeSerialPort() {
        serialPort = SerialPort.getCommPort(portName);
        serialPort.setBaudRate(9600);
        serialPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 2000, 0);
        if (serialPort.openPort()) {
            System.out.println("Serial port opened successfully.");
            serialReader = new BufferedReader(new InputStreamReader(serialPort.getInputStream()));
            startDataListener();
        } else {
            System.out.println("Failed to open serial port.");
        }
    }

    private void startDataListener() {
        Thread serialThread = new Thread(() -> {
            try {
                String line;
                while ((line = serialReader.readLine()) != null) {
                    dataListener.onSerialDataReceived(line);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        serialThread.setDaemon(true);
        serialThread.start();
    }

    public void closeSerialPort() {
        if (serialPort != null && serialPort.isOpen()) {
            serialPort.closePort();
        }
    }

    public interface SerialDataListener {
        void onSerialDataReceived(String data);
    }
}
