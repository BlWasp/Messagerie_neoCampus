package server;

import java.net.*;

public class TestServer {
    public static void main(String argv[]) throws Exception {
        System.out.println("Le serveur fonctionne ");
        ServerSocket serverSocket = new ServerSocket(5555);
        while(true) {
            Socket sock = serverSocket.accept();
            Server server = new Server(sock);
            Thread serverThread = new Thread(server);
            serverThread.start();
        }
    }
}
