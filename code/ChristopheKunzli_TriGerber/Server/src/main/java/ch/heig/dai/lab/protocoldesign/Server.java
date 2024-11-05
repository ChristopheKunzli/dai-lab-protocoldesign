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
            System.out.println("Server started on port " + SERVER_PORT);
            while (true) {
                try (Socket clientSocket = serverSocket.accept();
                     var in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream(), StandardCharsets.UTF_8));
                     var out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream(), StandardCharsets.UTF_8))) {

                    //Send welcome message
                    System.out.println("CONNECTED");
                    out.write("Welcome to the calculator server. Supported operations are: ");
                    for (String operation : Calculator.supportedOperations) {
                        out.write(operation + " ");
                    }
                    out.write("\n");
                    out.flush();

                    //Read input
                    String line;
                    while ((line = in.readLine()) != null && !line.equalsIgnoreCase("EXIT")) {
                        System.out.println("Received: " + line);
                        System.out.print("Response: ");

                        //Parse input
                        String operationWord = line.substring(0, line.indexOf(" ")).toUpperCase();

                        String[] operands = line.substring(line.indexOf(" ") + 1).split(" ");
                        double[] values = Calculator.parseValues(operands);

                        try {
                            double result = switch (operationWord) {
                                case "ADD" -> Calculator.add(values);
                                case "SUB" -> Calculator.sub(values);
                                case "MUL" -> Calculator.mul(values);
                                case "DIV" -> Calculator.div(values);
                                case "POW" -> Calculator.pow(values);
                                case "SQRT" -> Calculator.sqrt(values);
                                case "FACT" -> Calculator.fact(values);
                                case "LOG_N" -> Calculator.log(values);
                                default -> 0;
                            };
                            System.out.println(result);
                            out.write("Result: " + result + "\n");
                        } catch (IllegalArgumentException e) {
                            System.out.println(e.getMessage());
                            out.write(e.getMessage() + "\n");
                        }
                        out.flush();
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
