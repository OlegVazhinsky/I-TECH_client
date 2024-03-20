package main;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.UnknownHostException;

/**
 * Class which describes IT8906A_1200_240 functions, commands and attitude
 * @author vazhionsky_ot
 * @version 1.0
 */
public class IT8906A_1200_240 {

    /**
     * instances for TCPconnection
     */
    private TCPConnection connection;

    /**
     * Class constructor
     */
    public IT8906A_1200_240 () {

    }

    /**
     * Method for setting up connection
     * @param connection - TCPconnection instance
     */
    public void setConnection(TCPConnection connection) {
        this.connection = connection;
    }

    /**
     * Method that sets load current in A
     * @param current - current value to be set in A
     */
    public void setCurrent(String current) {
        setFunctionCC();
        connection.writeToSocket("CURR " + current);
    }

    /**
     * Method that returns measured current value in A
     * @return - measured current value in A
     */
    public String getCurrent() {
        connection.writeToSocket("MEAS:CURR?");
        return connection.readFromSocket();
    }

    /**
     * Method that returns set current value in A
     * @return - set current value in A
     */
    public String getSetupCurrent() {
        connection.writeToSocket("SOUR:CURR?");
        return connection.readFromSocket();
    }

    /**
     * Method that returns protection current value in A
     * @return - protection current value in A
     */
    public String getProtectionCurrent() {
        connection.writeToSocket("CURR:PROT?");
        return connection.readFromSocket();
    }

    /**
     * Method that sets load protection current in A
     * @param current - current value to be set as protection level in A
     */
    public void setProtectionCurrent(String current) {
        connection.writeToSocket("CURR:PROT:STAT 1");
        connection.writeToSocket("CURR:PROT " + current);
    }

    /**
     * Method that sets load voltage in V
     * @param voltage - voltage value to be set in V
     */
    public void setVoltage(String voltage) {
        setFunctionCV();
        connection.writeToSocket("VOLT " + voltage);
    }

    /**
     * Method that returns measured voltage value in V
     * @return - measured voltage value in V
     */
    public String getVoltage() {
        connection.writeToSocket("MEAS:VOLT?");
        return connection.readFromSocket();
    }

    /**
     * Method that returns set voltage value in V
     * @return - set voltage value in V
     */
    public String getSetupVoltage() {
        connection.writeToSocket("SOUR:VOLT?");
        return connection.readFromSocket();
    }

    /**
     * Method that sets load resistance in Ohm
     * @param resistance - resistance value to be set in Ohm
     */
    public void setResistance(String resistance) {
        setFunctionCR();
        connection.writeToSocket("RES " + resistance);
    }

    /**
     * Method that returns set resistance value in Ohm
     * @return - set resistance value in Ohm
     */
    public String getSetupResistance() {
        connection.writeToSocket("SOUR:RES?");
        return connection.readFromSocket();
    }

    /**
     * Method that returns measured power value in W
     * @return - measured power value in W
     */
    public String getPower() {
        connection.writeToSocket("MEAS:POW?");
        return connection.readFromSocket();
    }

    /**
     * Method that returns protection power value in W
     * @return - protection power value in W
     */
    public String getProtectionPower() {
        connection.writeToSocket("POW:PROT?");
        return connection.readFromSocket();
    }

    /**
     * Method that sets load protection current in A
     * @param power - power value to be set as protection level in W
     */
    public void setProtectionPower(String power) {
        connection.writeToSocket("POW:PROT " + power);
    }

    /**
     * Method for turning load On
     */
    public void setDeviceOn() {
        connection.writeToSocket("INP 1");
    }

    /**
     * Method for turning load Off
     */
    public void setDeviceOff() {
        connection.writeToSocket("INP 0");
    }

    /**
     * Method that returns if the load is On
     * @return - true if load is on, false otherwise
     */
    public boolean isDeviceOn() {
        connection.writeToSocket("INP?");
        return connection.readFromSocket().equals("1");
    }

    /**
     * Method that returns protection error.
     * @return - the code of protection error. If there is no error returns 0.
     *           Error 8194 - Overcurrent protection
     *           Error 8200 - Overpower protection
     *           Error 8208 - Overtemperature protection
     */
    public String getProtectionError () {
        int answer;
        connection.writeToSocket("STAT:QUES:COND?");
        try {
            answer = Integer.parseInt(connection.readFromSocket());
            if (answer == 8194) {
                return "Error 8194 - Overcurrent protection";
            } else if (answer == 8200) {
                return "Error 8200 - Overpower protection";
            } else if (answer == 8208) {
                return "Error 8208 - Overtemperature protection";
            } else return "0";
        } catch (NumberFormatException e) {
            return "0";
        }

    }

    /**
     * Method that resets protection error. Should be sent if error has occurred
     */
    public void resetProtection() {
        connection.writeToSocket("PROT:CLE");
    }

    /**
     * Method that returns if load is connected
     * @return - true if load is connected, false otherwise
     */
    public boolean isDeviceConnected() {
        return connection.isConnected();
    }

    /**
     * Method that connects client to the load
     * @throws IOException - happens if connection is failed
     */
    public void connect() throws IOException {
        connection.connect();
        connection.writeToSocket("SYST:RWL");
    }

    /**
     * Method that disconnects client from the load
     */
    public void disconnect() {
        connection.disconnect();
    }

    /**
     * Method that use load as current stabiliser
     */
    public void setFunctionCC() {
        connection.writeToSocket("FUNC CURR");
    }

    /**
     * Method that use load as voltage stabiliser
     */
    public void setFunctionCV() {
        connection.writeToSocket("FUNC VOLT");
    }

    /**
     * Method that use load as resistance stabiliser
     */
    public void setFunctionCR() {
        connection.writeToSocket("FUNC RES");
    }

    /**
     * Method for getting client ip-address
     * @return - ip-address
     * @throws UnknownHostException - thrown if something went wrong with the host
     */
    public String getPC_IP() throws UnknownHostException {
        return Inet4Address.getLocalHost().getHostAddress();
    }

}