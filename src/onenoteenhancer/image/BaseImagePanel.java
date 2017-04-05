/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package onenoteenhancer.image;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

/**
 *
 * @author Davide
 */
public class BaseImagePanel extends JPanel{
    
    private BufferedImage image;
    private boolean resize;

    public BaseImagePanel(BufferedImage image, boolean resize) {
        super(true);
        this.image = image;
        this.resize = resize;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        

    }
    
    
    
}
