package Lesson6;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static final int PORT = 9100;
    public static final String CLIENT_NAME = "Клиент";

    public static void main(String[] args) {

        try (ServerSocket serVsock = new ServerSocket(PORT)) {
            System.out.println("*** СЕРВЕР ЗАПУЩЕН ***");
            Socket socket = serVsock.accept();
            // Обработка отправки сообщения
            BufferedReader buffOut = new BufferedReader(new InputStreamReader(System.in));
            OutputStream socketOut = socket.getOutputStream();
            PrintWriter pwOut = new PrintWriter(socketOut, true);

            // Обработка приема сообщения
            InputStream socketIn = socket.getInputStream();
            BufferedReader buffIn = new BufferedReader(new InputStreamReader(socketIn));

            chatToClient(buffOut, pwOut, buffIn);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void chatToClient(BufferedReader buffOut, PrintWriter pwOut, BufferedReader buffIn) {

        String receiveMessage, sendMessage;
        while (true) {
            try {
                if ((receiveMessage = buffIn.readLine()) != null) {
                    System.out.println(CLIENT_NAME + ": " + receiveMessage);
                }
                if (receiveMessage.equals("QQQ")) {
                    System.out.printf("*** СЕАНС СВЯЗИ С %s ОКОНЧЕН ***%n", CLIENT_NAME);
                    break;
                }
                sendMessage = buffOut.readLine();
                pwOut.println(sendMessage);
                pwOut.flush();
            } catch (IOException e) {
                break;
            }
        }
    }
}