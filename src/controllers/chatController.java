/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.util.UUID;
import views.ClientFrame;
import views.ClientPanel;
import java.awt.event.ActionEvent;
import models.Message;

/**
 *
 * @author Carlos
 */
public class chatController {
    
    private ClientPanel viewChat;
    private String id;
    
    public chatController() {
        this.viewChat = new ClientFrame().getPanel();
        this.createId();
        this.events();
    }
    
    private void createId() {
        this.id = UUID.randomUUID().toString().substring(0, 8);
        this.viewChat.getClientIdLabel().setText("You are: " + id);
    }

    private void events() {
        this.viewChat.getSendButton().addActionListener( (ActionEvent evt) -> {
            String textMessage = this.viewChat.getSendTextField().getText();
            this.viewChat.getSendTextField().setText("");
            Message message = new Message(this.id, textMessage);
            System.out.println(textMessage);
        });
    }
    
    
    
}
