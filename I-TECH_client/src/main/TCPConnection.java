package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketTimeoutException;

/**
 * Class for establishing TCP connection between client and server
 * @author vazhionsky_ot
 * @version 1.0
 */
public class TCPConnection {

    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    private final int PORT;
    private final String HOST;
    private final int TIMEOUT;

    /**
     * Class constructor
     * @param host - host name. Example - "127.0.0.1"
     * @param port - port number. Example - "8800"
     * @param timeout - timeout parameter in ms. The exception will be thrown after "timeout" ms if nothing is read. Example - "1000"
     */
    public TCPConnection(String host, int port, int timeout) {
            this.PORT = port;
            this.HOST = host;
            this.TIMEOUT = timeout;
    }

    /**
     * Method that trys to connect to server
     * @throws IOException - exception appears if something go wrong with connection
     */
    public void connect() throws IOException {
            socket = new Socket(HOST, PORT);
            socket.setSoTimeout(TIMEOUT);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    /**
     * Method that diconnects client from server
     */
    public void disconnect() {
        if (socket.isConnected()) {
            try {
                out.close();
                in.close();
                socket.close();
            } catch (IOException e) {
                System.out.println("EXCEPTION - IOException found while trying to close connection.");
            }
        }
    }

    /**
     * Method that write message to server
     * @param message - texst that will be send to server. Exaple - "HELP!"
     */
    public void writeToSocket(String message) {
        if (socket.isConnected()) {
            out.println(message);
        }
    }

    /**
     * Method that reads message from server
     * @return message from server. Example - "Help yourself."
     */
    public String readFromSocket() {
        String answer = "NO DATA";
        try {
            answer = in.readLine();
        } catch (SocketTimeoutException e) {
            System.out.println("EXCEPTION - SocketTimeoutException found while trying to read from socket.");
        } catch (IOException e) {
            System.out.println("EXCEPTION - IOException found while trying to read from socket.");
        }
        return answer;
    }

    /**
     * Method that shows if the connection is still established
     * @return "true" if connection is established, "false" otherwise
     */
    public boolean isConnected() {
        return socket.isConnected();
    }

}
