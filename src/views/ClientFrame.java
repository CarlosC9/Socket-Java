/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import java.awt.Dimension;
import javax.swing.JFrame;

/**
 *
 * @author Carlos
 */
public class ClientFrame extends JFrame {
    
    private ClientPanel panel;
    
    public ClientFrame() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setMinimumSize(new Dimension(300,300));
        this.setLocationRelativeTo(null);
        this.panel = new ClientPanel();
        this.add(this.panel);
        this.setVisible(true);
    }

    public ClientPanel getPanel() {
        return panel;
    }
    
    
}
