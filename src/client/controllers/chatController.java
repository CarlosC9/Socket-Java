/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.controllers;

import java.util.UUID;
import client.views.*;
import client.views.ClientPanel;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JScrollBar;
import models.Message;

/**
 *
 * @author Carlos
 */
public class chatController {

    private ClientPanel viewChat;
    private ClientFrame frame;
    private String id;
    private Socket socket;
    private final String IP = "localhost";
    private final int PORT = 9999;

    public chatController() {
        
        this.frame = new ClientFrame();
        this.viewChat = this.frame.getPanel();
        this.createId();
        try {
            this.socket = new Socket(IP, PORT);
        } catch (IOException ex) {
            Logger.getLogger(chatController.class.getName()).log(Level.SEVERE, null, ex);
        }

        this.events();

        while (!socket.isClosed()) {
            try {
                ObjectInputStream ois = new ObjectInputStream(this.socket.getInputStream());
                Message message = (Message) ois.readObject();
                String chatText = this.viewChat.getChatTextArea().getText();
                if (!chatText.equalsIgnoreCase("")) {
                    chatText += "\n\n";
                }
                chatText += message.getId() + ":" + message.getMessage();
                this.viewChat.getChatTextArea().setText(chatText);
                JScrollBar verticalBar = this.viewChat.getVerticaljScrollPane1();
                verticalBar.setValue(verticalBar.getMaximum());
                System.out.println(verticalBar.getMaximum());
            } catch (IOException ex) {
                Logger.getLogger(chatController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(chatController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }

    private void createId() {
        this.id = UUID.randomUUID().toString().substring(0, 8);
        this.viewChat.getClientIdLabel().setText("You are: " + id);
    }

    private void events() {

        this.viewChat.getSendButton().addActionListener((ActionEvent evt) -> {
            String textMessage = this.viewChat.getSendTextField().getText();
            this.viewChat.getSendTextField().setText("");
            Message message = new Message(this.id, textMessage);
            try {
                ObjectOutputStream oos = new ObjectOutputStream(this.socket.getOutputStream());
                oos.writeObject(message);
                oos.flush();
            } catch (IOException ex) {
                Logger.getLogger(chatController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        this.frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                try {
                    
                    ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                    Message message = new Message();
                    message.thisMessageEndConnection();
                    oos.writeObject(message);
                    
                } catch (IOException ex) {
                    
                    Logger.getLogger(chatController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (NullPointerException ex) {
                    frame.dispose();
                }
            }
        });

    }

}
