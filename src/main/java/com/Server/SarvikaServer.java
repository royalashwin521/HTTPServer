package com.Server;

import com.ClientRequestHandler.RequestHandler;
import com.ServerProperties.ServerProperties;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SarvikaServer {

    public static void main(String[] args) throws IOException {
        SarvikaServer sarvikaServer = new SarvikaServer();

        ServerProperties serverProperties = new ServerProperties();
        if(!(serverProperties.getServerProperties("config.properties"))){
            System.exit(1);
        }
        sarvikaServer.startServer(serverProperties.getPort());
    }

    public void startServer(int port) throws IOException {
        ServerSocket serverSocket = new ServerSocket(port);
        try {
            while (!(serverSocket.isClosed())) {
                System.out.println("Looking for Client");
                Socket socket = serverSocket.accept();
                System.out.println("Client Connected");
                RequestHandler gp = new RequestHandler(socket);
                Thread t1 = new Thread(gp);
                t1.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

