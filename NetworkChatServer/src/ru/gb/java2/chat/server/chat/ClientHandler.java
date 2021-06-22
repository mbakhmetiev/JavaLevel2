package ru.gb.java2.chat.server.chat;

import ru.gb.java2.chat.server.chat.auth.User;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;
import java.util.Map;

public class ClientHandler {

    private static final String AUTH_OK_COMMAND = "/authOk";
    private static final String AUTH_COMMAND = "/auth";
    public static final String ERR_01 = "Err01 - USER ALREADY REGISTERED";
    public static final String ERR_02 = "Err02 - INVALID CREDENTIALS";

    private MyServer server;
    private final Socket clientSocket;
    private DataInputStream inputStream;
    private DataOutputStream outputStream;
    private User user;

    public ClientHandler(MyServer server, Socket clientSocket) {
        this.server = server;
        this.clientSocket = clientSocket;
    }

    public void handle() throws IOException {
        inputStream = new DataInputStream(clientSocket.getInputStream());
        outputStream = new DataOutputStream(clientSocket.getOutputStream());

        new Thread(() -> {
            try {
                authentication();
                readMessages();
            } catch (IOException e) {
                System.err.println("Failed to process message from client");
            } finally {
                try {
                    closeConnection();
                } catch (IOException e) {
                    System.err.println("Failed to close connection");
                }
            }
        }).start();
    }

    private void authentication() throws IOException {
        while (true) {
            String message = inputStream.readUTF();
            if (message.startsWith(AUTH_COMMAND)) {
                String[] parts = message.split(" ");
                String login = parts[1];
                String password = parts[2];

                //String username = server.getAuthService().getUsernameByLoginAndPassword(login, password);
                // Метод getUsernameByLoginAndPassword изменен, чтобы возвращать user
                User user = server.getAuthService().getUserByLoginAndPassword(login, password);

                for (Map.Entry<ClientHandler, User> entry : server.getClients().entrySet()) {
                    if(entry.getValue().getLogin().equals(login)) {
                        sendMessage(String.format("%s:%s", ERR_01, "Пользователь уже зарегестрирован на сервере"));
                        return;
                    }
                }
                if (user == null) {
                    sendMessage(String.format("%s:%s", ERR_02, "Некорректные логин и пароль!"));
                } else {
                    sendMessage(String.format("%s:%s",AUTH_OK_COMMAND, user.getUsername()));

                    // подписываемся на сервер, передавая и юзера
                    server.subscribe(this, user);
                }
                return;
            }
        }
    }

    private void closeConnection() throws IOException {
        server.unsubscribe(this);
        clientSocket.close();
    }

    private void readMessages() throws IOException {
        while (true) {
            String[] message = inputStream.readUTF().trim().split(":");

            // Мессдж парсим на элементы 'dst:src:message'
            System.out.println("message:" + Arrays.toString(message));
            if (message[2].startsWith("/end")) {
                return;
            } else {
                processMessage(message);
            }
        }
    }

    private void processMessage(String[] message) throws IOException {
        server.broadcastMessage(message, this);
    }

    public void sendMessage(String message) throws IOException {
        outputStream.writeUTF(message);
    }
}