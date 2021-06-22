package ru.gb.java2.chat.server;

import ru.gb.java2.chat.server.chat.MyServer;

public class ServerApp {

    private static final int DEFAULT_PORT = 8189;

    public static void main(String[] args) {

        new MyServer().start(DEFAULT_PORT);

    }
}