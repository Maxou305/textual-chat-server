package org.example;

import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class EchoMultiServer {
    private ServerSocket serverSocket;
    public static List<EchoClientHandler> clientList = new ArrayList<>();

    public void start(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        System.out.println("Server is up!");
        while (true) {
            EchoClientHandler handler = new EchoClientHandler(serverSocket.accept());
            clientList.add(handler);
            handler.start();
        }
    }

    public void stop() throws IOException {
        serverSocket.close();
    }

    public static void sendToAll(EchoClientHandler sender, String message) {
        for (EchoClientHandler client : clientList) {
            if (client != sender) client.out.println(message);
        }
    }
}
