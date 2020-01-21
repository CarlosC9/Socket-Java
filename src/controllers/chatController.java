/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import javax.swing.JPanel;
import views.ClientFrame;

/**
 *
 * @author Carlos
 */
public class chatController {
    
    private JPanel viewChat;
    
    public chatController() {
        
        ClientFrame clientFrame = new ClientFrame();
        this.viewChat = clientFrame.getPanel();
        
    }
    
}
