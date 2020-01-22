package server.controllers;

import client.controllers.chatController;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.Message;

public class ServerThread extends Thread {
    
    private Socket socket;
    private ArrayList<Socket> sockets;

    public ServerThread(Socket socket, ArrayList<Socket> sockets) {
        this.socket = socket;
        this.sockets = sockets;
    }

    @Override
    public void run() {
        
        while (this.socket.isConnected()) {
            try {
                
                ObjectInputStream obs = new ObjectInputStream(this.socket.getInputStream());
                Message message = (Message) obs.readObject();
                System.out.println("The user " + message.getId() + " said " + message.getMessage());

                for (Socket s : this.sockets) {
                    ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
                    oos.writeObject(message);
                    oos.flush();
                }
            } catch (IOException ex) {
                Logger.getLogger(chatController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(chatController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        
    }
   
}
