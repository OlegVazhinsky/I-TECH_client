import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

/**
 * Class for establishing TCP connection as a server
 * @author vazhionsky_ot
 * @version 1.0
 */
public class TCPServer {

    private ServerSocket serverSocket;
    private Socket clientSocket;

    private PrintWriter out;
    private BufferedReader in;

    private final int PORT;

    /**
     * Class constructor
     * @param port - port for connection
     */
    public TCPServer(int port) {
        this.PORT = port;
    }

    /**
     * Method for connection
     * @throws IOException - is thrown ifconnection is lost
     */
    public void connect() throws IOException {
        serverSocket = new ServerSocket(PORT);
        clientSocket = serverSocket.accept();
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }

    /**
     * Method for disconnection
     */
    public void disconnect() {
        if (clientSocket.isConnected()) {
            try {
                out.close();
                in.close();
                clientSocket.close();
                serverSocket.close();
            } catch (IOException e) {
                System.out.println("EXCEPTION - IOException found while trying to close connection.");
            }
        }
    }

    /**
     * Method for writing to the socket
     * @param input - text that should be wrytten to socket
     */
    public void writeToSocket(String input) {
        if (clientSocket.isConnected()) {
            out.println(input);
        }
    }

    /**
     * Method for reading from socket
     * @return text from the socket
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
     * Method for getting client port
     * @return client port
     */
    public String getClient () {
        return "" + clientSocket.getPort();
    }

}
