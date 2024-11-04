package ch.heig.dai.lab.protocoldesign;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    String serverAddress;
    int serverPort;

    public Client(String serverAddress, int serverPort) {
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
    }

    public static void main(String[] args) {
        String serverAddress = "1.2.3.4";
        int serverPort = 12345;

        Client client = new Client(serverAddress, serverPort);
        client.run();
    }

    private void run() {
        try (Socket socket = new Socket(serverAddress, serverPort);
             PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             Scanner scanner = new Scanner(System.in)) {

            // Display the welcome message and supported operations from the server
            String welcomeMessage = reader.readLine();
            if (welcomeMessage != null) {
                System.out.println("Server: " + welcomeMessage);
            } else {
                System.out.println("No response from server. Exiting.");
                return;
            }

            while (true) {
                System.out.print("Client: ");
                String command = scanner.nextLine().trim();

                if (command.equalsIgnoreCase("exit")) {
                    writer.println("EXIT");
                    System.out.println("Disconnecting...");
                    break;
                }

                if (!checkCommand(command)) {
                    System.out.println("Unrecognized or incorrect command. Please try again.");
                    continue;
                }

                writer.println(command);

                String response = reader.readLine();
                if (response != null) {
                    System.out.println("Server: " + response);
                } else {
                    System.out.println("Connection closed by server.");
                    break;
                }
            }
        } catch (IOException e) {
            System.out.println("Error connecting to server: " + e.getMessage());
        }
    }

    private boolean checkCommand(String command) {
        String[] tokens = command.split(" ");
        if (tokens.length < 2) {
            return false;
        }

        String operation = tokens[0].toUpperCase();
        int operandCount = tokens.length - 1;

        switch (operation) {
            case "ADD":
            case "SUB":
            case "MUL":
            case "DIV":
            case "POW":
            case "LOG_N":
                return checkOperands(tokens, operandCount >= 2);
            case "SQRT":
            case "FACT":
                return checkOperands(tokens, operandCount >= 1);
            default:
                return false;
        }
    }

    private boolean checkOperands(String[] tokens, boolean correctOperandCount) {
        if (!correctOperandCount) {
            System.out.println("Incorrect number of operands for operation " + tokens[0].toUpperCase());
            return false;
        }

        for (int i = 1; i < tokens.length; i++) {
            try {
                Double.parseDouble(tokens[i]);
            } catch (NumberFormatException e) {
                System.out.println("Invalid operand: " + tokens[i]);
                return false;
            }
        }
        return true;
    }
}