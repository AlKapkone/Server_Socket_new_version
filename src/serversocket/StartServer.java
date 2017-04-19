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
            waitClient();
            clientExsecute();

        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    private void startClient() throws IOException {
        servSocket = new ServerSocket(port);
        System.out.println("Server running...");
    }

    private void waitClient() throws IOException {
        client = servSocket.accept();
        System.out.println("Connection from: " + client.getInetAddress());
    }
    private final ChoiceComand choiceComand = new ChoiceComand();

    private static final int shortComandLength = 3;
    
    public void clientExsecute() {
        try (Scanner in = new Scanner(client.getInputStream());
                PrintWriter respons = new PrintWriter(client.getOutputStream())) {
            String command = null;
            while (true) {
                String request = in.nextLine();

//                System.out.println(request.length());
                if (request.length() == shortComandLength) {
                    command = request;
                    respons.write("ok\n");
                    respons.flush();
                } else {
                    String res = choiceComand.choice(command, request);
                    respons.write(res + "\n");
                    respons.flush();
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
