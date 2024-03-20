import java.io.IOException;
import java.util.Random;

/**
 * Class for listening socket. This  thread emulates electronic load
 */
class listenerThread implements Runnable {

    private final Thread thread;
    private final TCPServer server;

    public listenerThread (TCPServer server) {
        this.server = server;
        this.thread = new Thread(this);
        this.thread.start();
    }

    @Override
    public void run() {

        String input;
        Random random = new Random();
        double scale = Math.pow(10, 3);

        // Preset parameters
        double measuredCurrent;
        double measuredVoltage;
        double calculatedPower;
        double protectionCurrent = 240.0;
        double protectionVoltage = 1200.0;
        double protectionPower = 6000.0;
        double setupCurrent = 1.0;
        double setupVoltage = 1.0;
        double setupResistance = 1.0;
        int loadOn = 0;

        while (true) {
            input = server.readFromSocket();

            // Emulating measured values
            measuredCurrent = Math.ceil(((setupCurrent + random.nextDouble() / 10) * scale)) / scale;
            measuredVoltage = Math.ceil(((setupVoltage + random.nextDouble() / 10) * scale)) / scale;
            calculatedPower = Math.ceil(measuredCurrent * measuredVoltage * scale) / scale;

            // Turn load on
            if (input.equals("INP 1")) {
                loadOn = 1;
            }
            // Turn load off
            if (input.equals("INP 0")) {
                loadOn = 0;
            }
            // Return load output status
            if (input.equals("INP?")) {
                server.writeToSocket(String.valueOf(loadOn));
            }
            // Return measured current
            if (input.equals("MEAS:CURR?")) {
                server.writeToSocket(String.valueOf(measuredCurrent));
            }
            // Return measured voltage
            if (input.equals("MEAS:VOLT?")) {
                server.writeToSocket(String.valueOf(measuredVoltage));
            }
            // Return measured power
            if (input.equals("MEAS:POW?")) {
                server.writeToSocket(String.valueOf(calculatedPower));
            }
            // Return current protection
            if (input.equals("CURR:PROT?")) {
                server.writeToSocket(String.valueOf(protectionCurrent));
            }
            // Return voltage protection
            if (input.equals("VOLT:PROT?")) {
                server.writeToSocket(String.valueOf(protectionVoltage));
            }
            // Return power protection
            if (input.equals("POW:PROT?")) {
                server.writeToSocket(String.valueOf(protectionPower));
            }
            // Return preset current
            if (input.equals("SOUR:CURR?")) {
                server.writeToSocket(String.valueOf(setupCurrent));
            }
            // Return preset voltage
            if (input.equals("SOUR:VOLT?")) {
                server.writeToSocket(String.valueOf(setupVoltage));
            }
            // Return preset resistance
            if (input.equals("SOUR:RES?")) {
                server.writeToSocket(String.valueOf(setupResistance));
            }
            // Set current
            if (input.contains("CURR ")) {
                setupCurrent = Double.parseDouble(input.substring(5));
            }
            // Set voltage
            if (input.contains("VOLT ")) {
                setupVoltage = Double.parseDouble(input.substring(5));
            }
            // Set resistance
            if (input.contains("RES ")) {
                setupResistance = Double.parseDouble(input.substring(4));
            }
            // Set power protection
            if (input.contains("POW:PROT ")) {
                protectionPower = Double.parseDouble(input.substring(9));
            }
            // Set voltage protection
            if (input.contains("VOLT:PROT ")) {
                protectionVoltage = Double.parseDouble(input.substring(10));
            }
            // Set current protection
            if (input.contains("CURR:PROT ")) {
                protectionCurrent = Double.parseDouble(input.substring(10));
            }
            // Return protection error code
            if (input.equals("STAT:QUES:COND?")) {
                if (measuredCurrent > protectionCurrent) server.writeToSocket("8194");
                else if (calculatedPower > protectionPower) server.writeToSocket("8200");
                else server.writeToSocket("0");
            }
        }
    }
}

/**
 * Class for emulating electronic load
 */
public class IT8906_1200_240_emulator {

    public static void main(String[] args) {

        TCPServer server = new TCPServer(4000);

        try {
            server.connect();
            System.out.println("Client accepted. Port - " + server.getClient());
            new listenerThread(server);
        } catch (IOException e) {
            System.out.println("EXCEPTION: " + e.getMessage());
            System.exit(500);
        }

    }
}
