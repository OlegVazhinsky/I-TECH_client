import main.Validation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestValidation {

    Validation v = new Validation();

    @Test
    public void testIPaddressValidation() {
        Assertions.assertTrue(v.isIP4validate("192.168.1.1"));
        Assertions.assertFalse(v.isIP4validate("192.168.1"));
        Assertions.assertFalse(v.isIP4validate("192/168/1/1"));
        Assertions.assertTrue(v.isIP4validate("255.255.255.255"));
        Assertions.assertTrue(v.isIP4validate("0.0.0.0"));
        Assertions.assertFalse(v.isIP4validate("192.168.v.1"));
    }

    @Test
    public void testPortValidation() {
        Assertions.assertTrue(v.isPortValidate("0"));
        Assertions.assertTrue(v.isPortValidate("000"));
        Assertions.assertTrue(v.isPortValidate("8800"));
        Assertions.assertTrue(v.isPortValidate("65535"));
        Assertions.assertFalse(v.isPortValidate("65536"));
        Assertions.assertFalse(v.isPortValidate("0a"));
        Assertions.assertFalse(v.isPortValidate("655355"));
    }

    @Test
    public void testTimeoutValidation() {
        Assertions.assertTrue(v.isTimeoutValidate("1000"));
        Assertions.assertFalse(v.isTimeoutValidate("100"));
        Assertions.assertFalse(v.isTimeoutValidate("10000"));
        Assertions.assertFalse(v.isTimeoutValidate("99000"));
        Assertions.assertTrue(v.isTimeoutValidate("9999"));
        Assertions.assertFalse(v.isTimeoutValidate("10O0"));
    }

    @Test
    public void testCurrentValidation() {
        Assertions.assertTrue(v.isCurrentValidate("1"));
        Assertions.assertTrue(v.isCurrentValidate("0.1"));
        Assertions.assertTrue(v.isCurrentValidate("240"));
        Assertions.assertFalse(v.isCurrentValidate("240.1"));
        Assertions.assertFalse(v.isCurrentValidate("0"));
        Assertions.assertFalse(v.isCurrentValidate("-1.0"));
        Assertions.assertFalse(v.isCurrentValidate("a"));
    }

    @Test
    public void testVoltageValidation() {
        Assertions.assertTrue(v.isVoltageValidate("1"));
        Assertions.assertTrue(v.isVoltageValidate("0.1"));
        Assertions.assertTrue(v.isVoltageValidate("1200"));
        Assertions.assertFalse(v.isVoltageValidate("1200.1"));
        Assertions.assertFalse(v.isVoltageValidate("0"));
        Assertions.assertFalse(v.isVoltageValidate("-1.0"));
        Assertions.assertFalse(v.isVoltageValidate("a"));
    }

    @Test
    public void testPowerValidation() {
        Assertions.assertTrue(v.isPowerValidate("1"));
        Assertions.assertFalse(v.isPowerValidate("0.1"));
        Assertions.assertTrue(v.isPowerValidate("6000"));
        Assertions.assertFalse(v.isPowerValidate("6000.1"));
        Assertions.assertFalse(v.isPowerValidate("0"));
        Assertions.assertFalse(v.isPowerValidate("-1.0"));
        Assertions.assertFalse(v.isPowerValidate("a"));
    }

    @Test
    public void testResistanceValidation() {
        Assertions.assertTrue(v.isResistanceValidate("10"));
        Assertions.assertFalse(v.isResistanceValidate("0.1"));
        Assertions.assertTrue(v.isResistanceValidate("7500"));
        Assertions.assertFalse(v.isResistanceValidate("7500.1"));
        Assertions.assertFalse(v.isResistanceValidate("0"));
        Assertions.assertFalse(v.isResistanceValidate("-1.0"));
        Assertions.assertFalse(v.isResistanceValidate("a"));
    }

}