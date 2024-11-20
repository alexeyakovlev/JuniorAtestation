package ru.alexYakovlev.chat.client;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class ChatClient {
    private Socket socket;
    private String name;
    private BufferedReader in;
    private BufferedWriter out;

    public ChatClient(Socket socket, String name) {
        this.socket = socket;
        this.name = name;
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (IOException e) {
            closeEverything(socket, in, out);
        }
    }

    public void listenFromMessage() {
        new Thread(new Runnable() {
            public void run() {
                String message;
                while (socket.isConnected()) {
                    try {
                        message = in.readLine();
                        System.out.println(message);
                    } catch (IOException e) {
                        closeEverything(socket, in, out);
                    }
                }
            }
        }).start();
    }

    public void sendMessage() {
        try{
            out.write(name);
            out.newLine();
            out.flush();

            Scanner scanner = new Scanner(System.in);
            while (socket.isConnected()) {
                String message = scanner.nextLine();
                out.write(name + ": " + message);
                out.newLine();
                out.flush();
            }
        } catch (IOException e) {
            closeEverything(socket, in, out);
        }
    }

    private void closeEverything(Socket socket, BufferedReader in, BufferedWriter out) {
        try {
            if (in != null) {
                in.close();
            }
            if (out!= null) {
                out.close();
            }
            if (socket!= null) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
