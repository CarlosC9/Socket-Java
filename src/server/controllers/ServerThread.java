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
        boolean endOfConnection = false;

        while (!socket.isClosed() && !endOfConnection) {
            try {

                ObjectInputStream obs = new ObjectInputStream(this.socket.getInputStream());
                Message message = (Message) obs.readObject();
                
                if (message.isEndConnection()) {
                    endOfConnection = true;
                } else {
                    this.sendMessageAllUsers(message);
                }

            } catch (IOException ex) {
                Logger.getLogger(chatController.class.getName()).log(Level.SEVERE, null, ex);
                endOfConnection = true;
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(chatController.class.getName()).log(Level.SEVERE, null, ex);
                endOfConnection = true;
            }

        }

        this.sockets.remove(this.socket);

    }

    private void sendMessageAllUsers(Message message) throws IOException {
        System.out.println("The user " + message.getId() + " said " + message.getMessage());

        for (Socket s : this.sockets) {
            ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
            oos.writeObject(message);
            oos.flush();
        }
    }

}
