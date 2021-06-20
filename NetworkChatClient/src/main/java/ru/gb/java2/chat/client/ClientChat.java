package ru.gb.java2.chat.client;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import ru.gb.java2.chat.client.controllers.AuthController;

import java.util.ArrayList;
import java.util.List;


public class ClientChat extends Application {

    public static final List<String> USERS_TEST_DATA = List.of(
            "username1",
            "username2",
            "username3");

    private static final String NETWORK_ERROR_TITLE = "Сетевая ошибка";
    private static final String AUTH_ERROR_TITLE = "Аутентификация";
    private static final String NETWORK_ERROR_CONNECTION_TYPE = "Невозможно установить сетевое соединение";

    private Stage primaryStage;
    private Stage authStage;

    // Содздание поля для юзернейма, ассоциированного с чатом
    private String chatUser;
    public void setChatUser(String username) {
        chatUser = username;
    }
    public String getChatUser() {
        return chatUser;
    }
    //*****

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;

        ViewController chatController = createChatDialog(primaryStage);
        connectToServer(chatController);

        createAuthDialog(primaryStage);
        chatController.initMessageHandler();
    }

    private void createAuthDialog(Stage primaryStage) throws java.io.IOException {
        FXMLLoader authLoader = new FXMLLoader();
        authLoader.setLocation(ClientChat.class.getResource("authDialog.fxml"));
        AnchorPane authDialogPanel = authLoader.load();

        authStage = new Stage();
        authStage.initOwner(primaryStage);
        authStage.initModality(Modality.WINDOW_MODAL);
        authStage.setScene(new Scene(authDialogPanel));

        AuthController authController = authLoader.getController();
        authController.setClientChat(this);
        authController.initMessageHandler();
        authStage.showAndWait();
    }

    private ViewController createChatDialog(Stage primaryStage) throws java.io.IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ClientChat.class.getResource("chat.fxml"));

        AnchorPane root = loader.load();

        setStageForSecondScreen(primaryStage);

        this.primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();

        ViewController viewController = loader.getController();
        return viewController;
    }

    private void connectToServer(ViewController viewController) {
        Network network = Network.getInstance();
        boolean result = network.connect();
        if (!result) {
            String errMsg = "Не удалось установить соединение с сервером!";
            showNetworkErrorDialog(NETWORK_ERROR_CONNECTION_TYPE, errMsg);
            return;
        }

        viewController.setApplication(this);

        primaryStage.setOnCloseRequest(windowEvent -> network.close());
    }

    private void showErrorDialog(String title, String type, String details) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(type);
        alert.setContentText(details);
        alert.showAndWait();
    }

    public void showNetworkErrorDialog(String type, String details) {
        showErrorDialog(NETWORK_ERROR_TITLE, type, details);
    }

    public void showAuthErrorDialog(String type, String details) {
        showErrorDialog(AUTH_ERROR_TITLE, type, details);
    }

    private void setStageForSecondScreen(Stage primaryStage) {
        Screen secondScreen = getSecondScreen();
        Rectangle2D bounds = secondScreen.getBounds();
        primaryStage.setX(bounds.getMinX() + (bounds.getWidth() - 300) / 2);
        primaryStage.setY(bounds.getMinY() + (bounds.getHeight() - 200) / 2);
    }

    private Screen getSecondScreen() {
        for (Screen screen : Screen.getScreens()) {
            if (!screen.equals(Screen.getPrimary())) {
                return screen;
            }
        }
        return Screen.getPrimary();
    }

    public Stage getAuthStage() {
        return authStage;
    }

    public static void main(String[] args) {
        launch(args);
    }

    public Stage getChatStage() {
        return primaryStage;
    }

}