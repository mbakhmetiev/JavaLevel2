package ru.gb.java2.chat.client.controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import ru.gb.java2.chat.client.ClientChat;
import ru.gb.java2.chat.client.Network;

import java.io.IOException;
import java.util.function.Consumer;

public class AuthController {
    private static final String INVALID_CREDENTIALS = "Некорректный ввод данных";
    private static final String CREDENTIALS_REQUIRED = "Логин и пароль должны быть указаны!";
    private static final String USER_ALREADY_EXISTS = "Повторная регистрация невозможна!";

    private static final String AUTH_COMMAND = "/auth";
    private static final String AUTH_OK_COMMAND = "/authOk";

    @FXML
    private TextField loginField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button authButton;

    private ClientChat clientChat;

    @FXML
    public void executeAuth(ActionEvent actionEvent) {
        String login = loginField.getText();
        String password = passwordField.getText();
        if (login == null || login.isBlank() || password == null || password.isBlank()) {
            clientChat.showAuthErrorDialog(INVALID_CREDENTIALS, CREDENTIALS_REQUIRED);
            return;
        }

        String authCommandMessage = String.format("%s %s %s", AUTH_COMMAND, login, password);
        try {
            Network.getInstance().sendMessage(authCommandMessage);
        } catch (IOException e) {
            clientChat.showNetworkErrorDialog("Ошибка передачи данных по сети", "Не удалось отправить сообщение!");
            e.printStackTrace();
        }
    }

    public void setClientChat(ClientChat clientChat) {
        this.clientChat = clientChat;
    }

    public void initMessageHandler() {
        Network.getInstance().waitMessages(new Consumer<String>() {
            @Override
            public void accept(String message) {
                if (message.startsWith(AUTH_OK_COMMAND)) {
                    String[] parts = message.split(":");
                    String username = parts[1];
                    Thread.currentThread().interrupt();
                    Platform.runLater(() -> {
                        clientChat.getChatStage().setTitle(username);
                        // Здесь передаем в чат юзернейм
                        clientChat.setChatUser(username);
                        clientChat.getAuthStage().close();
                    });
                // Обработка входящих ошибок
                // Некорректные логин/пароль
                } if (message.startsWith("Err02")){
                    String [] errParts = message.split(":");
                    Platform.runLater(() -> {
                        clientChat.showAuthErrorDialog(INVALID_CREDENTIALS, errParts[1]);
                    });
                // Повторная регистрация
                } if (message.startsWith("Err01")){
                    String[] errParts = message.split(":");
                    Platform.runLater(() -> {
                        clientChat.showAuthErrorDialog(USER_ALREADY_EXISTS, errParts[1]);
                    });
                }
            }
        });
    }
}