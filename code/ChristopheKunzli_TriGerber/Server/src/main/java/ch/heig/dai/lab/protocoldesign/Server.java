package ch.heig.dai.lab.protocoldesign;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Server {
    final int SERVER_PORT = 1234;

    public static void main(String[] args) {
        // Create a new server and run it
        Server server = new Server();
        server.run();
    }

    private void run() {
        try (ServerSocket serverSocket = new ServerSocket(1234)) {
            while (true) {
                try (Socket clientSocket = serverSocket.accept();
                     var in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream(), StandardCharsets.UTF_8));
                     var out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream(), StandardCharsets.UTF_8))
                ) {

                    System.out.println("Client connected");
                    out.write("Hello from server\n");
                    out.flush();

                    String line;
                    while ((line = in.readLine()) != null) {
                        System.out.println("Received: " + line);

                        out.write("Your input: " + line + "\n");
                        out.flush();
                    }

                } catch (IOException e) {
                    System.out.println("Server: socket ex.: " + e);
                }
            }
        } catch (IOException e) {
            System.out.println("Server: server socket ex.: " + e);
        }
    }
}
