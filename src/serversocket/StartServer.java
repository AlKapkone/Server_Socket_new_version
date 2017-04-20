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
    
    private PrintWriter respons;

    private void waitClient() throws IOException {
        client = servSocket.accept();
        respons = new PrintWriter(client.getOutputStream());
        System.out.println("Connection from: " + client.getInetAddress());

    }
    private final ChoiceCommand choiceComand = new ChoiceCommand();

    private static final int SHORT_COMAND_LENGTH = 3;

    public void clientExsecute() {
        try (Scanner in = new Scanner(client.getInputStream())) {
            String command = null;
            while (true) {
                String request = in.nextLine();

                if (request.length() == SHORT_COMAND_LENGTH) {
                    command = request;
                    sendRespons("Ok");
                    if (request.equals("Exi")) {
                        System.out.println("Client closed");
                        return;
                    }
                } else {
                    String res = choiceComand.choice(command, request);
                    sendRespons(res);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            respons.close();
        }
    }

    private void sendRespons(String message) {
        respons.write(message + "\n");
        respons.flush();
    }
}
