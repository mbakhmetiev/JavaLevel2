package ru.gb.java2.chat.server.chat;

import ru.gb.java2.chat.server.chat.auth.AuthService;
import ru.gb.java2.chat.server.chat.auth.User;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class MyServer {

    // Изменил arraylist Clienthandler на map с тем, чтобы прикрепить к ClientHandler юзера
    private final Map<ClientHandler, User> clients = new HashMap<>();
    private AuthService authService;

    public void start(int port) {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server has been started");
            authService = new AuthService();
            while (true) {
                waitAndProcessNewClientConnection(serverSocket);
            }
        } catch (IOException e) {
            System.err.println("Failed to bind port " + port);
            e.printStackTrace();
        }
    }

    private void waitAndProcessNewClientConnection(ServerSocket serverSocket) throws IOException {
        System.out.println("Waiting for new client connection...");
        Socket clientSocket = serverSocket.accept();
        System.out.println("Client has been connected");
        ClientHandler clientHandler = new ClientHandler(this, clientSocket);
        clientHandler.handle();
    }

    public void broadcastMessage(String[] message, ClientHandler sender) throws IOException {
        for (Map.Entry<ClientHandler, User> entry : clients.entrySet()) {
            if (entry.getKey() != sender) {
                // Посылать если: 1) Адресовано мне; 2) Адресовано всем как: null или когда
                // в сообщении 'src==dst', как 'to all', н-р username2:username2
                if (message[0].equals(entry.getValue().getUsername()) || message[0].equals("null") || message[0].equals(message[1])) {
                entry.getKey().sendMessage(message[1] + ": " + message[2]);
                }
            }
        }
    }

    public void subscribe(ClientHandler clientHandler, User user) {
        clients.put(clientHandler, user);
    }

    public void unsubscribe(ClientHandler clientHandler) {
        clients.remove(clientHandler);
    }

    public AuthService getAuthService() {
        return authService;
    }

    public Map<ClientHandler, User> getClients() {
        return clients;
    }
}