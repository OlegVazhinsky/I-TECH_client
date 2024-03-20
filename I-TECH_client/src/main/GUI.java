package main;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.UnknownHostException;


/**
 * Class for graphical user interface
 * @author vazhinsky_ot
 * @version 1.0
 */
public class GUI {

    /**
     * instances for validator, date formatter and electronic load
     */
    private final Validation validation = new Validation();
    private final DateFormatter date = new DateFormatter();
    private final IT8906A_1200_240 load = new IT8906A_1200_240();

    /**
     * instance of thread which measures physical values
     */
    private Thread measurementThread;

    /**
     * GUI variables for objects
     */
    private JFrame mainFrame;

    private JRadioButton CVradioButton;
    private JRadioButton CCradioButton;
    private JRadioButton CRradioButton;
    private JButton onButton;
    private JButton offButton;
    private JButton setupButton;
    private JButton protectionButton;
    private JButton connectButton;
    private JButton disconnectButton;
    private JTextField measurementVoltage;
    private JTextField measurementCurrent;
    private JTextField measurementPower;
    private JTextField setupVoltage;
    private JTextField setupCurrent;
    private JTextField setupResistance;
    private JTextField protectionCurrent;
    private JTextField protectionPower;
    private JTextField ipAddressText;
    private JTextField portText;
    private JTextField timeoutText;
    private JTextArea infoText;
    private JPanel mainPanel;
    private JPanel measurementPanel;
    private JPanel setupPanel;
    private JPanel protectionPanel;
    private JPanel connectionPanel;
    private JPanel infoPanel;

    /**
     * GUI variables for front
     */
    private final Font LABEL_FONT = new Font("Consolas", Font.PLAIN, 16);
    private final Font TEXT_FONT = new Font("Consolas", Font.PLAIN, 20);
    private final Font BUTTON_FONT = new Font("Consolas", Font.PLAIN, 16);
    private final Font INFO_TEXT_FONT = new Font("Consolas", Font.PLAIN, 12);
    private final Dimension LABEL_SIZE = new Dimension(100,30);
    private final Dimension TEXT_SIZE = new Dimension(100,30);
    private final Dimension PANEL_SIZE = new Dimension(880,80);
    private final Dimension INFO_PANEL_SIZE = new Dimension(880,315);
    private GridLayout panelGridLayout;

    /**
     * Class constructor, creates all objects
     */
    public GUI() {
        setMainFrame();
        setRadioButtons();
        setButtons();
        setTextFields();
        setMeasurementPanel();
        setSetupPanel();
        setProtectionPanel();
        setConnectionPanel();
        setInfoPanel();
        setMainPanel();
    }

    /**
     * Method that makes mainFrame visible
     */
    public void start() {
        mainFrame.add(mainPanel);
        mainFrame.setVisible(true);
    }

    /**
     * Method that sets up main panel. Main panel contains all others panels
     */
    private void setMainFrame() {
        mainFrame = new JFrame("I-tech SCPI client");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(900, 700);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setResizable(false);
        panelGridLayout = new GridLayout(2,5,5,5);
    }

    /**
     * Method that sets up radiobuttons. Radiobuttons are used to choose the load working regime (CC, CV, CR)
     */
    private void setRadioButtons() {
        ButtonGroup radioButtonGroup = new ButtonGroup();
        CVradioButton = new JRadioButton("Voltage, V");
        CVradioButton.setFont(LABEL_FONT);
        CVradioButton.setPreferredSize(LABEL_SIZE);
        CVradioButton.setHorizontalAlignment(JLabel.CENTER);
        CVradioButton.setVerticalAlignment(JLabel.BOTTOM);
        CVradioButton.setEnabled(false);
        CCradioButton = new JRadioButton("Current, A", true);
        CCradioButton.setFont(LABEL_FONT);
        CCradioButton.setPreferredSize(LABEL_SIZE);
        CCradioButton.setHorizontalAlignment(JLabel.CENTER);
        CCradioButton.setVerticalAlignment(JLabel.BOTTOM);
        CCradioButton.setEnabled(false);
        CRradioButton = new JRadioButton("Resistance, Ohm");
        CRradioButton.setFont(LABEL_FONT);
        CRradioButton.setPreferredSize(LABEL_SIZE);
        CRradioButton.setHorizontalAlignment(JLabel.CENTER);
        CRradioButton.setVerticalAlignment(JLabel.BOTTOM);
        CRradioButton.setEnabled(false);
        radioButtonGroup.add(CVradioButton);
        radioButtonGroup.add(CCradioButton);
        radioButtonGroup.add(CRradioButton);

    }

    /**
     * Method that sets up buttons.
     * Buttons are used to control the load
     * onButton - turns the load ON
     * offButton - turns the load OFF
     * setupButton - sets the preset parameters
     * protectionButton - sets the protection parameters
     * connectButton - connects the client to the load
     * disconnectButton - disconnects the clients from the load
     */
    private void setButtons() {
        onButton = new JButton("ON");
        onButton.setFont(BUTTON_FONT);
        onButton.addActionListener(new onButtonListener());
        onButton.setEnabled(false);
        offButton = new JButton("OFF");
        offButton.setFont(BUTTON_FONT);
        offButton.addActionListener(new offButtonListener());
        offButton.setEnabled(false);
        setupButton = new JButton("SETUP");
        setupButton.setFont(BUTTON_FONT);
        setupButton.addActionListener(new setupButtonListener());
        setupButton.setEnabled(false);
        protectionButton = new JButton("SETUP");
        protectionButton.setFont(BUTTON_FONT);
        protectionButton.addActionListener(new protectionButtonListener());
        protectionButton.setEnabled(false);
        connectButton = new JButton("CONNECT");
        connectButton.setFont(BUTTON_FONT);
        connectButton.addActionListener(new connectButtonListener());
        disconnectButton = new JButton("DISCONNECT");
        disconnectButton.setFont(BUTTON_FONT);
        disconnectButton.addActionListener(new disconnectButtonListener());
        disconnectButton.setEnabled(false);

    }

    /**
     * Method that makes some of the GUI objects visible or not.
     * @param flag - sets GUI objects visible if true, invisible - otherwise.
     *             Objects: onButton, offButton, setupButton, protectionButton, disconnectButton,
     *             CVradioButton, CCradioButton, CRradioButton, setupCurrent, setupVoltage, setupResistance,
     *             protectionCurrent, protectionPower.
     */
    private void setMenuEnabled(Boolean flag) {
        onButton.setEnabled(flag);
        offButton.setEnabled(flag);
        setupButton.setEnabled(flag);
        protectionButton.setEnabled(flag);
        disconnectButton.setEnabled(flag);
        CVradioButton.setEnabled(flag);
        CCradioButton.setEnabled(flag);
        CRradioButton.setEnabled(flag);
        setupCurrent.setEditable(flag);
        setupVoltage.setEditable(flag);
        setupResistance.setEditable(flag);
        protectionCurrent.setEditable(flag);
        protectionPower.setEditable(flag);
    }

    /**
     * Method that presets all text or label fields in GUI
     */
    private void setTextFields() {
        measurementVoltage = new JTextField("-");
        measurementVoltage.setFont(TEXT_FONT);
        measurementVoltage.setPreferredSize(TEXT_SIZE);
        measurementVoltage.setEditable(false);
        measurementVoltage.setHorizontalAlignment(JTextField.CENTER);

        measurementCurrent = new JTextField("-");
        measurementCurrent.setFont(TEXT_FONT);
        measurementCurrent.setPreferredSize(TEXT_SIZE);
        measurementCurrent.setEditable(false);
        measurementCurrent.setHorizontalAlignment(JTextField.CENTER);
        measurementPower = new JTextField("-");

        measurementPower.setFont(TEXT_FONT);
        measurementPower.setPreferredSize(TEXT_SIZE);
        measurementPower.setEditable(false);
        measurementPower.setHorizontalAlignment(JTextField.CENTER);

        setupVoltage = new JTextField("-");
        setupVoltage.setFont(TEXT_FONT);
        setupVoltage.setPreferredSize(TEXT_SIZE);
        setupVoltage.setEditable(false);
        setupVoltage.setHorizontalAlignment(JTextField.CENTER);

        setupCurrent = new JTextField("-");
        setupCurrent.setFont(TEXT_FONT);
        setupCurrent.setPreferredSize(TEXT_SIZE);
        setupCurrent.setEditable(false);
        setupCurrent.setHorizontalAlignment(JTextField.CENTER);

        setupResistance = new JTextField("-");
        setupResistance.setFont(TEXT_FONT);
        setupResistance.setPreferredSize(TEXT_SIZE);
        setupResistance.setEditable(false);
        setupResistance.setHorizontalAlignment(JTextField.CENTER);

        protectionCurrent = new JTextField("-");
        protectionCurrent.setFont(TEXT_FONT);
        protectionCurrent.setPreferredSize(TEXT_SIZE);
        protectionCurrent.setEditable(false);
        protectionCurrent.setHorizontalAlignment(JTextField.CENTER);

        protectionPower = new JTextField("-");
        protectionPower.setFont(TEXT_FONT);
        protectionPower.setPreferredSize(TEXT_SIZE);
        protectionPower.setEditable(false);
        protectionPower.setHorizontalAlignment(JTextField.CENTER);

        ipAddressText = new JTextField("10.1.42.3");
        ipAddressText.setFont(TEXT_FONT);
        ipAddressText.setPreferredSize(TEXT_SIZE);
        ipAddressText.setEditable(true);
        ipAddressText.setHorizontalAlignment(JTextField.CENTER);

        portText = new JTextField("30000");
        portText.setFont(TEXT_FONT);
        portText.setPreferredSize(TEXT_SIZE);
        portText.setEditable(true);
        portText.setHorizontalAlignment(JTextField.CENTER);

        timeoutText = new JTextField("1000");
        timeoutText.setFont(TEXT_FONT);
        timeoutText.setPreferredSize(TEXT_SIZE);
        timeoutText.setEditable(true);
        timeoutText.setHorizontalAlignment(JTextField.CENTER);

        infoText = new JTextArea("");
        infoText.setFont(INFO_TEXT_FONT);
        infoText.setEditable(false);
        infoText.setAutoscrolls(true);
        infoText.setLineWrap(true);
        infoText.setWrapStyleWord(true);
    }

    /**
     * Method that presets measurement panel in GUI
     * Measurement panel contains text fields for voltage, current and power measurements
     */
    private void setMeasurementPanel() {
        JLabel voltLabel;
        JLabel ampereLabel;
        JLabel wattLabel;
        voltLabel = new JLabel("Voltage, V");
        voltLabel.setFont(LABEL_FONT);
        voltLabel.setPreferredSize(LABEL_SIZE);
        voltLabel.setHorizontalAlignment(JLabel.CENTER);
        voltLabel.setVerticalAlignment(JLabel.BOTTOM);
        ampereLabel = new JLabel("Current, A");
        ampereLabel.setFont(LABEL_FONT);
        ampereLabel.setPreferredSize(LABEL_SIZE);
        ampereLabel.setHorizontalAlignment(JLabel.CENTER);
        ampereLabel.setVerticalAlignment(JLabel.BOTTOM);
        wattLabel = new JLabel("Power, W");
        wattLabel.setFont(LABEL_FONT);
        wattLabel.setPreferredSize(LABEL_SIZE);
        wattLabel.setHorizontalAlignment(JLabel.CENTER);
        wattLabel.setVerticalAlignment(JLabel.BOTTOM);
        measurementPanel = new JPanel(panelGridLayout);
        measurementPanel.setBorder(new TitledBorder(new EtchedBorder(), "Measurement"));
        measurementPanel.setPreferredSize(PANEL_SIZE);
        measurementPanel.add(voltLabel);
        measurementPanel.add(ampereLabel);
        measurementPanel.add(wattLabel);
        measurementPanel.add(new JLabel());
        measurementPanel.add(new JLabel());
        measurementPanel.add(measurementVoltage);
        measurementPanel.add(measurementCurrent);
        measurementPanel.add(measurementPower);
        measurementPanel.add(onButton);
        measurementPanel.add(offButton);
    }

    /**
     * Method that presets setup panel in GUI
     * Setup panel contains text fields for voltage, current and power setups and buttons
     */
    private void setSetupPanel() {
        setupPanel = new JPanel(panelGridLayout);
        setupPanel.setBorder(new TitledBorder(new EtchedBorder(), "Setup"));
        setupPanel.setPreferredSize(PANEL_SIZE);
        setupPanel.add(CVradioButton);
        setupPanel.add(CCradioButton);
        setupPanel.add(CRradioButton);
        setupPanel.add(new JLabel());
        setupPanel.add(new JLabel());
        setupPanel.add(setupVoltage);
        setupPanel.add(setupCurrent);
        setupPanel.add(setupResistance);
        setupPanel.add(setupButton);
        setupPanel.add(new JLabel());
    }

    /**
     * Method that presets protection panel in GUI
     * Protection panel contains text fields for voltage, current and power protection settings and buttons
     */
    private void setProtectionPanel() {
        JLabel ampereLabel;
        JLabel wattLabel;
        ampereLabel = new JLabel("Current, A");
        ampereLabel.setFont(LABEL_FONT);
        ampereLabel.setPreferredSize(LABEL_SIZE);
        ampereLabel.setHorizontalAlignment(JLabel.CENTER);
        ampereLabel.setVerticalAlignment(JLabel.BOTTOM);
        wattLabel = new JLabel("Power, W");
        wattLabel.setFont(LABEL_FONT);
        wattLabel.setPreferredSize(LABEL_SIZE);
        wattLabel.setHorizontalAlignment(JLabel.CENTER);
        wattLabel.setVerticalAlignment(JLabel.BOTTOM);
        protectionPanel = new JPanel(panelGridLayout);
        protectionPanel.setBorder(new TitledBorder(new EtchedBorder(), "Protection"));
        protectionPanel.setPreferredSize(PANEL_SIZE);
        protectionPanel.add(ampereLabel);
        protectionPanel.add(wattLabel);
        protectionPanel.add(new JLabel());
        protectionPanel.add(new JLabel());
        protectionPanel.add(new JLabel());
        protectionPanel.add(protectionCurrent);
        protectionPanel.add(protectionPower);
        protectionPanel.add(new JLabel());
        protectionPanel.add(protectionButton);
        protectionPanel.add(new JLabel());
    }

    /**
     * Method that presets connection panel in GUI
     * Connection panel contains text fields and buttons for connection preset
     */
    private void setConnectionPanel() {
        JLabel ipLabel;
        JLabel portLabel;
        JLabel timeoutLabel;
        ipLabel = new JLabel("IP-address");
        ipLabel.setFont(LABEL_FONT);
        ipLabel.setPreferredSize(LABEL_SIZE);
        ipLabel.setHorizontalAlignment(JLabel.CENTER);
        ipLabel.setVerticalAlignment(JLabel.BOTTOM);
        portLabel = new JLabel("Port");
        portLabel.setFont(LABEL_FONT);
        portLabel.setPreferredSize(LABEL_SIZE);
        portLabel.setHorizontalAlignment(JLabel.CENTER);
        portLabel.setVerticalAlignment(JLabel.BOTTOM);
        timeoutLabel = new JLabel("Timeout");
        timeoutLabel.setFont(LABEL_FONT);
        timeoutLabel.setPreferredSize(LABEL_SIZE);
        timeoutLabel.setHorizontalAlignment(JLabel.CENTER);
        timeoutLabel.setVerticalAlignment(JLabel.BOTTOM);
        connectionPanel = new JPanel(panelGridLayout);
        connectionPanel.setBorder(new TitledBorder(new EtchedBorder(), "Connection"));
        connectionPanel.setPreferredSize(PANEL_SIZE);
        connectionPanel.add(ipLabel);
        connectionPanel.add(portLabel);
        connectionPanel.add(timeoutLabel);
        connectionPanel.add(new JLabel());
        connectionPanel.add(new JLabel());
        connectionPanel.add(ipAddressText);
        connectionPanel.add(portText);
        connectionPanel.add(timeoutText);
        connectionPanel.add(connectButton);
        connectionPanel.add(disconnectButton);
    }

    /**
     * Method that presets info panel in GUI
     * Info panel contains text area for system messages
     */
    private void setInfoPanel() {
        JScrollPane infoScrollPane;
        infoPanel = new JPanel(new BorderLayout());
        infoPanel.setBorder(new TitledBorder(new EtchedBorder(), "Info"));
        infoPanel.setLayout(new BorderLayout(0, 0));
        infoPanel.setPreferredSize(INFO_PANEL_SIZE);
        infoScrollPane = new JScrollPane(infoText);
        infoScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        infoPanel.add(infoScrollPane);
    }

    /**
     * Method that sets main panel in GUI
     * It adds all other panels
     */
    private void setMainPanel() {
        mainPanel = new JPanel();
        mainPanel.add(measurementPanel, BorderLayout.NORTH);
        mainPanel.add(setupPanel, BorderLayout.NORTH);
        mainPanel.add(protectionPanel, BorderLayout.NORTH);
        mainPanel.add(connectionPanel, BorderLayout.NORTH);
        mainPanel.add(infoPanel, BorderLayout.NORTH);
    }

    /**
     * Method for measurement update. This runnable method goes into measurement Thread
     */
    public Runnable measurementUpdate = new Runnable() {
        @Override
        public void run() {
            while (!measurementThread.isInterrupted()) {
                if(load.isDeviceConnected()) {
                    String error = load.getProtectionError();
                    if (error.equals("0")) {
                        try {
                            Thread.sleep(100);
                            measurementVoltage.setText(load.getVoltage());
                            Thread.sleep(100);
                            measurementCurrent.setText(load.getCurrent());
                            Thread.sleep(100);
                            measurementPower.setText(load.getPower());
                        } catch (InterruptedException e) {
                            measurementThread.interrupt();
                        }
                    } else {
                        load.setDeviceOff();
                        load.resetProtection();
                        addInfoText(error);
                        setupButton.setEnabled(true);
                        protectionButton.setEnabled(true);
                        onButton.setEnabled(true);
                        measurementThread.interrupt();
                    }
                } else {
                    addInfoText("Connection is not established or lost. Try to connect");
                }
            }
            measurementVoltage.setText("-");
            measurementCurrent.setText("-");
            measurementPower.setText("-");
        }
    };

    /**
     * onButton listener. Turns the load on. Starts new measurement thread
     */
    private class onButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(load.isDeviceConnected()) {
                setupButton.setEnabled(false);
                protectionButton.setEnabled(false);
                if (!load.isDeviceOn()) {
                    load.setDeviceOn();
                    onButton.setEnabled(false);
                    measurementThread = new Thread(measurementUpdate);
                    measurementThread.start();
                    addInfoText("Load is on");
                } else {
                    addInfoText("Load is already on");
                }
            } else {
                addInfoText("Connection is not established or lost. Try to connect");
            }
        }
    }

    /**
     * offButton listener. Turns the load off. Interrupts measurement thread
     */
    private class offButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (load.isDeviceConnected()) {
                onButton.setEnabled(true);
                if (load.isDeviceOn()) {
                    measurementThread.interrupt();
                    load.setDeviceOff();
                    addInfoText("Load is off");
                } else {
                    load.setDeviceOff();
                    addInfoText("Load is already off");
                }
                setupButton.setEnabled(true);
                protectionButton.setEnabled(true);
                measurementVoltage.setText("-");
                measurementCurrent.setText("-");
                measurementPower.setText("-");
            } else {
                addInfoText("Connection is not established or lost. Try to connect");
            }
        }
    }

    /**
     * setupButton listener. Sets preset for the load
     */
    private class setupButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (CVradioButton.isSelected()) {
                if (validation.isVoltageValidate(setupVoltage.getText())) {
                    addInfoText("Setup voltage value validation success");
                    load.setVoltage(setupVoltage.getText());
                    addInfoText("Setup voltage" + setupVoltage.getText() + " V");
                } else {
                    addInfoText("Setup voltage value validation failed");
                }
            }
            if (CCradioButton.isSelected()) {
                if (validation.isCurrentValidate(setupCurrent.getText())) {
                    addInfoText("Setup current value validation success");
                    load.setCurrent(setupCurrent.getText());
                    addInfoText("Setup current " + setupCurrent.getText() + " A");
                }else {
                    addInfoText("Setup current value validation failed");
                }
            }
            if (CRradioButton.isSelected()) {
                if (validation.isResistanceValidate(setupResistance.getText())) {
                    addInfoText("Setup resistance value validation success");
                    load.setResistance(setupResistance.getText());
                    addInfoText("Setup resistance " + setupResistance.getText() + " Ohm");
                }
                addInfoText("Setup resistance value validation failed");
            }
        }
    }

    /**
     * protectionButton listener. Sets protection for the load
     */
    private class protectionButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(load.isDeviceConnected()) {
                if (validation.isCurrentValidate(protectionCurrent.getText())) {
                    addInfoText("Protection current value validation success");
                    load.setProtectionCurrent(protectionCurrent.getText());
                    addInfoText("Protection current " + protectionCurrent.getText() + " A");
                } else {
                    addInfoText("Protection current value validation failed");
                }
                if (validation.isPowerValidate(protectionPower.getText())) {
                    addInfoText("Protection power value validation success");
                    load.setProtectionPower(protectionPower.getText());
                    addInfoText("Protection power " + protectionPower.getText() + " W");
                } else {
                    addInfoText("Protection power value validation failed");
                }
            } else {
                addInfoText("Connection is not established or lost. Try to connect");
            }
        }
    }

    /**
     * connectButton listener. Connects client to the load
     */
    private class connectButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            addInfoText("--------------------------------");
            if (validation.isIP4validate(ipAddressText.getText())) {
                addInfoText("IP4 validation success");
                if (validation.isPortValidate(portText.getText())) {
                    addInfoText("Port validation success");
                    if (validation.isTimeoutValidate(timeoutText.getText())) {
                        addInfoText("Timeout validation success");
                        addInfoText("Trying to connect to " + ipAddressText.getText());
                        load.setConnection(new TCPConnection(ipAddressText.getText(), Integer.parseInt(portText.getText()), Integer.parseInt(timeoutText.getText())));
                        try {
                            load.connect();
                            connectButton.setEnabled(false);
                            setMenuEnabled(true);
                            addInfoText("Connection successfully established");
                            setupVoltage.setText(load.getSetupVoltage());
                            setupCurrent.setText(load.getSetupCurrent());
                            setupResistance.setText(load.getSetupResistance());

                            protectionCurrent.setText(load.getProtectionCurrent());
                            protectionPower.setText(load.getProtectionPower());

                            load.setFunctionCC();

                        } catch (IOException ex) {
                            try {
                                addInfoText("Failed to connect to " + ipAddressText.getText() + ". Make sure your PC is located in the same LAN" +
                                        " as I-Tech device. Also make sure that I-Tech device is on and connected to LAN." +
                                        " Your current IP is " + load.getPC_IP());
                            } catch (UnknownHostException exc) {
                                addInfoText("Getting IP of current PC failed. Check your network or LAN device");
                            }
                        }
                    }
                    else {
                        addInfoText("Timeout validation failed. Check timeout and try again");
                    }
                }
                else {
                    addInfoText("Port validation failed. Check port and try again");
                }
            }
            else {
                addInfoText("IP4 validation failed. Check IP4 and try again");
            }
        }
    }

    /**
     * disconnectButton listener. Disconnects client from the load
     */
    private class disconnectButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            if (load.isDeviceConnected()) {
                if (load.isDeviceOn()) {
                    if (!measurementThread.isInterrupted()) {
                        measurementThread.interrupt();
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
                load.setDeviceOff();
                load.disconnect();
                setMenuEnabled(false);
                connectButton.setEnabled(true);
                addInfoText("Connection successfully closed");
            } else {
                addInfoText("Connection is already closed");
            }
        }
    }

    /**
     * Method for adding text to infoText area
     * @param text - text which goes in the info
     */
    public void addInfoText (String text) {
        infoText.append(date.getDateAndTime() + text + ".\n");
    }
}