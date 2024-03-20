package main;

import java.util.regex.Pattern;

/**
 * Class for validating ip-address, port, timeout, voltage, current, resistance and power values
 * @author vazhinsky_ot
 * @version 1.0
 */
public class Validation {

    /**
     * variable for pattern ip-address
     */
    private final String regExpIp4 = "(\\d{1,2}|([01])\\d{2}|2[0-4]\\d|25[0-5])";
    private final String ip4String = regExpIp4 + "\\." + regExpIp4 + "\\." + regExpIp4 + "\\." + regExpIp4;
    private final Pattern ip4pattern = Pattern.compile(ip4String);

    /**
     * variable for pattern port
     */
    private final String portString = "\\d{1,5}";
    private final Pattern portPattern = Pattern.compile(portString);

    /**
     * variable for pattern timeout
     */
    private final String timeoutString = "\\d{4}";
    private final Pattern timeoutPattern = Pattern.compile(timeoutString);

    /**
     * variable for pattern voltage
     */
    private final String voltageString = "[0-9]{1,4}(\\.[0-9]*)?";
    private final Pattern voltagePattern = Pattern.compile(voltageString);

    /**
     * variable for pattern current
     */
    private final String currentString = "[0-9]{1,3}(\\.[0-9]*)?";
    private final Pattern currentPattern = Pattern.compile(currentString);

    /**
     * variable for pattern resistance
     */
    private final String resistanceString = "[0-9]{1,4}(\\.[0-9]*)?";
    private final Pattern resistancePattern = Pattern.compile(resistanceString);

    /**
     * variable for pattern power
     */
    private final String powerString = "[0-9]{1,5}(\\.[0-9]*)?";
    private final Pattern powerPattern = Pattern.compile(powerString);

    /**
     * Class constructor
     */
    public Validation() {

    }

    /**
     * Method that validates ip-address value
     * @param ipAddress - ip-address format. Example - "127.0.0.1"
     * @return true if ip-address value validates, false otherwise
     */
    public boolean isIP4validate(String ipAddress) {
        return ip4pattern.matcher(ipAddress).matches();
    }

    /**
     * Method that validates port value
     * @param port - port format. Should be less or equal 65535. Example - "8800"
     * @return true if port value validates, false otherwise
     */
    public boolean isPortValidate(String port) {
        if (portPattern.matcher(port).matches()) {
            return (Integer.parseInt(port) <= 65535);
        }
        return false;
    }

    /**
     * Method that validates timeout value
     * @param timeout - timeout format in ms. Should be more or equal 1000 and less or equal 9999. Example - "1000"
     * @return true if timeout value validates, false otherwise
     */
    public boolean isTimeoutValidate(String timeout) {
        return timeoutPattern.matcher(timeout).matches();
    }

    /**
     * Method that validates voltage value
     * @param voltage - voltage format in V. Should be more or equal 0.1 and less or equal 1200. Example - "27"
     * @return true if voltage value validates, false otherwise
     */
    public boolean isVoltageValidate (String voltage) {
        if (voltagePattern.matcher(voltage).matches()) {
            return (Double.parseDouble(voltage) <= 1200 && Double.parseDouble(voltage) >= 0.1);
        }
        return false;
    }

    /**
     * Method that validates current value
     * @param current - current format in A. Should be more or equal 0.01 and less or equal 240. Example - "10"
     * @return true if current value validates, false otherwise
     */
    public boolean isCurrentValidate (String current) {
        if (currentPattern.matcher(current).matches()) {
            return (Double.parseDouble(current) <= 240 && Double.parseDouble(current) >= 0.01);
        }
        return false;
    }

    /**
     * Method that validates resistance value
     * @param resistance - resistance format in Ohm. Should be more or equal 10 and less or equal 7500. Example - "75"
     * @return true if resistance value validates, false otherwise
     */
    public boolean isResistanceValidate (String resistance) {
        if (resistancePattern.matcher(resistance).matches()) {
            return (Double.parseDouble(resistance) <= 7500 && Double.parseDouble(resistance) >= 10);
        }
        return false;
    }

    /**
     * Method that validates power value
     * @param power - power format in W. Should be more or equal 1 and less or equal 6000. Example - "100"
     * @return true if power value validates, false otherwise
     */
    public boolean isPowerValidate (String power) {
        if (powerPattern.matcher(power).matches()) {
            return (Double.parseDouble(power) <= 6000 && Double.parseDouble(power) >= 1);
        }
        return false;
    }
}
