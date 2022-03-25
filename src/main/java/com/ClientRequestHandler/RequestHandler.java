package com.ClientRequestHandler;

import com.ResourcesHandling.ResourceHandler;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class RequestHandler implements Runnable {

    private final Socket socket;

    public RequestHandler(Socket clientSocket) {
        this.socket = clientSocket;
    }

    public void requestHandler() throws IOException {

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            StringBuilder requestBuilder = new StringBuilder();
            String line;
            while (!(line = bufferedReader.readLine()).isBlank()) {
                requestBuilder.append(line + "\r\n");
            }
            String request = requestBuilder.toString();
            System.out.println(request);

            String[] requestsLines = request.split("\r\n");
            String[] requestLine = requestsLines[0].split(" ");
            String method = requestLine[0];
            String path = requestLine[1];
            String version = requestLine[2];
            String host = requestsLines[1].split(" ")[1];

            List<String> headers = new ArrayList<>();
            for (int h = 2; h < requestsLines.length; h++) {
                String header = requestsLines[h];
                headers.add(header);
            }
             System.out.println();
            String accessLog = String.format("Client %s, method %s, path %s, version %s, host %s, headers %s",
                    socket.toString(), method, path, version, host, headers.toString());
            System.out.println(accessLog);

        System.out.println(path);

        OutputStream clientOutput = socket.getOutputStream();

        requestedResource(clientOutput, path);

        System.out.println("this is work");
        clientOutput.write("\r\n\r\n".getBytes());
        clientOutput.flush();
        socket.close();
    }

    public void requestedResource(OutputStream clientOut, String filePath){
        ResourceHandler resourceHandler = new ResourceHandler();
        try {
            resourceHandler.getMeThisResource(clientOut,filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            requestHandler();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
