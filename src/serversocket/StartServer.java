package serversocket;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.Scanner;

public class StartServer {

    private final int port;

    StartServer(int port) {
        this.port = port;
    }

    private Socket client;
    private ServerSocket servSocket;

    public void startServ() throws SQLException, ClassNotFoundException {
        try {

            startClient();
            while (true) {
                waitClient();
                clientExsecute();
            }

        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    private void startClient() throws IOException {
        servSocket = new ServerSocket(port);
        System.out.println("Server running...");
    }
    
    private PrintWriter response;

    private void waitClient() throws IOException {
        client = servSocket.accept();
        response = new PrintWriter(client.getOutputStream());
        System.out.println("Connection from: " + client.getInetAddress());

    }
    private final ChoiceCommand choiceCommand = new ChoiceCommand();

    private static final int SHORT_COMMAND_LENGTH = 3;

    public void clientExsecute() {
        try (Scanner in = new Scanner(client.getInputStream())) {
            String command = null;
            while (true) {
                String request = in.nextLine();

                if (request.length() == SHORT_COMMAND_LENGTH) {
                    command = request;
                    sendRespons("Ok");
                    if (request.equals("Exi")) {
                        System.out.println("Client closed");
                        return;
                    }
                } else {
                    String res = choiceCommand.choice(command, request);
                    sendRespons(res);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            response.close();
        }
    }

    private void sendRespons(String message) {
        response.write(message + "\n");
        response.flush();
    }
}
