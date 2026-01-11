package frc.utils.swerve;

import edu.wpi.first.wpilibj.SerialPort;

/**
 * Minimal NavX2 gyro workaround until Studica releases WPILib 2026 support.
 * Uses USB serial communication which doesn't require the native Studica library.
 */
public class NavX2Gyro {
    
    private final SerialPort serial;
    private volatile double yaw = 0.0;
    private volatile double yawOffset = 0.0;
    private volatile boolean connected = false;
    
    private final Thread readThread;
    private volatile boolean running = true;
    
    public NavX2Gyro() {
        this(SerialPort.Port.kUSB);
    }
    
    public NavX2Gyro(SerialPort.Port port) {
        serial = new SerialPort(57600, port);
        serial.setReadBufferSize(256);
        serial.setTimeout(1.0);
        serial.enableTermination('\n');
        
        readThread = new Thread(this::readLoop, "NavX2Reader");
        readThread.setDaemon(true);
        readThread.start();
    }
    
    private void readLoop() {
        StringBuilder buffer = new StringBuilder();
        
        while (running) {
            try {
                String data = serial.readString();
                if (data != null && !data.isEmpty()) {
                    connected = true;
                    buffer.append(data);
                    
                    int idx;
                    while ((idx = buffer.indexOf("\n")) >= 0) {
                        String line = buffer.substring(0, idx).trim();
                        buffer.delete(0, idx + 1);
                        parseLine(line);
                    }
                }
                Thread.sleep(5);
            } catch (Exception e) {
                // Ignore
            }
        }
    }
    
    private void parseLine(String line) {
        try {
            if (line.startsWith("!y")) {
                String[] parts = line.substring(2).split(",");
                if (parts.length >= 1) {
                    yaw = Double.parseDouble(parts[0]);
                }
            }
        } catch (NumberFormatException e) {
            // Ignore
        }
    }
    
    public double getYaw() {
        return yaw - yawOffset;
    }
    
    public double getAngle() {
        return getYaw();
    }
    
    public void zeroYaw() {
        yawOffset = yaw;
    }
    
    public boolean isConnected() {
        return connected;
    }
    
    public void close() {
        running = false;
        try {
            readThread.join(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        serial.close();
    }
}
