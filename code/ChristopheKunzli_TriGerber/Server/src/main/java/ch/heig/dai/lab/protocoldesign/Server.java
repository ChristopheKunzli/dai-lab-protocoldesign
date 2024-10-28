package ch.heig.dai.lab.protocoldesign;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    final int SERVER_PORT = 1234;

    private ServerSocket serverSocket;

    public static void main(String[] args) {
        // Create a new server and run it
        Server server = new Server();
        try {
            server.serverSocket = new ServerSocket(server.SERVER_PORT);
        } catch (Exception e) {
            e.printStackTrace();
        }
        server.run();
    }

    private void run() {
        try (Socket clientSocket = serverSocket.accept()) {
            var in = new BufferedInputStream(clientSocket.getInputStream());
            var out = new BufferedOutputStream(clientSocket.getOutputStream());

            System.out.println("Client connected");
            out.write("Hello from server\n".getBytes());
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}