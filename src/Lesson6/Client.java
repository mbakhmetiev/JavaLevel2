package Lesson6;

import java.io.*;
import java.net.Socket;

public class Client {
    public static final int PORT = 9100;

    public static void main(String[] args) {
        try (Socket sock = new Socket("localhost", PORT)) {
            System.out.println("*** СЕАНС СВЯЗИ С СЕРВЕРОМ ЗАПУЩЕН ***");
            // Обработка отправки сообщения
            BufferedReader buffOut = new BufferedReader(new InputStreamReader(System.in));
            OutputStream socketOut = sock.getOutputStream();
            PrintWriter pwOut = new PrintWriter(socketOut, true);

            // Обработка приема сообщения
            InputStream socketIn = sock.getInputStream();
            BufferedReader buffIn = new BufferedReader(new InputStreamReader(socketIn));

            chatToServerRx(buffIn);
            chatToServerTx(buffOut, pwOut);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void chatToServerTx(BufferedReader buffOut, PrintWriter pwOut) {
        String sendMessage;
        while (true) {
            try {
                sendMessage = buffOut.readLine();
                pwOut.println(sendMessage);
                pwOut.flush();
            } catch (IOException e) {
                break;
            }
        }
    }

    private static void chatToServerRx(BufferedReader buffIn) {
        Thread thread = new Thread(() -> {
            String receiveMessage;
            while (true) {
                try {
                    if ((receiveMessage = buffIn.readLine()) != null) {
                        System.out.println("Сервер: " + receiveMessage);
                    }
                    if (receiveMessage.equals("QQQ")) {
                        System.out.println("*** СЕАНС СВЯЗИ С СЕРВЕРОМ ОКОНЧЕН ***");
                        break;
                    }
                } catch (IOException e) {
                    break;
                }
            }
        });
        thread.start();
    }
}
