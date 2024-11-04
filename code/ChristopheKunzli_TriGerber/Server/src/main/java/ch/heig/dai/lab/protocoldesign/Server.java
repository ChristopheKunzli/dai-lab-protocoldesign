package ch.heig.dai.lab.protocoldesign;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Server {
    final int SERVER_PORT = 1234;
    final Calculator calculator = new Calculator();

    public static void main(String[] args) {
        // Create a new server and run it
        Server server = new Server();
        server.run();
    }

    private void run() {
        try (ServerSocket serverSocket = new ServerSocket(1234)) {
            System.out.println("Server started on port " + SERVER_PORT);
            while (true) {
                try (Socket clientSocket = serverSocket.accept();
                     var in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream(), StandardCharsets.UTF_8));
                     var out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream(), StandardCharsets.UTF_8))) {

                    System.out.println("CONNECTED");
                    out.write("Welcome to the calculator server. Supported operations are: ADD, SUB, MUL, DIV, POW, SQRT, FACT, LOG_N\n");
                    out.flush();

                    String line;
                    while ((line = in.readLine()) != null) {
                        System.out.println("Received: " + line);

                        if (line.equalsIgnoreCase("EXIT")) {
                            break;
                        }
                    }

                    System.out.println("DISCONNECTED");
                    out.write("Goodbye!\n");
                    out.flush();
                } catch (IOException e) {
                    System.out.println("Error managing input: " + e);
                }
            }
        } catch (Exception e) {
            System.out.println("Error : " + e);
        }
    }
}
