package server.controllers;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;


public class ServerController {
    
    private final int PORT = 9999;
    
    public ServerController() {
        
        try {
            ArrayList<Socket> sockets = new ArrayList<>();
            ServerSocket serverSocket = new ServerSocket(PORT);
            while (true) {
                Socket socket = serverSocket.accept();
                sockets.add(socket);
                ServerThread clientThread = new ServerThread(socket,sockets);
                clientThread.start();
            }
            
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
        
    }
    
}
