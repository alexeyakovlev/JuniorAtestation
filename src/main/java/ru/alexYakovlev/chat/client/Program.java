package ru.alexYakovlev.chat.client;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Program {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();
        Socket socket = new Socket("localhost", 8080);
        ChatClient chatClient = new ChatClient(socket, name);
        InetAddress inetAddress = socket.getInetAddress();
        System.out.println("InetAddress: " + inetAddress);
        String remoteIp = inetAddress.getHostAddress();
        System.out.println("Remote IP: " + remoteIp);
        System.out.println("LocalPort:" + socket.getLocalPort());
        chatClient.listenFromMessage();
        chatClient.sendMessage();
    }
}
