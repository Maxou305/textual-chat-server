package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class EchoClientHandler extends Thread {
    private final Socket clientSocket;
    PrintWriter out;
    private BufferedReader in;
    protected String clientName;

    public EchoClientHandler(Socket socket) {
        this.clientSocket = socket;
    }

    public void run() {
        try {
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            clientName = in.readLine();
            EchoMultiServer.sendToAll(this, clientName + " has joined the chat");

            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                if (".".equals(inputLine)) {
                    out.println("bye");
                    break;
                }

                System.out.println(clientName + " said: " + inputLine);
                EchoMultiServer.sendToAll(this, clientName + " said: " + inputLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) in.close();
                if (out != null) out.close();
                if (clientSocket != null) clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}