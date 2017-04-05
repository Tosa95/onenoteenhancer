/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package onenoteenhancer;

import onenoteenhancer.image.DeprecatedImagePanel;
import java.awt.AWTException;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.HeadlessException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Davide
 */
public class JFullScreenSelector extends JFrame{

    BufferedImage img;
    GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[0];
    
    public JFullScreenSelector(BufferedImage img) throws HeadlessException, AWTException {
        
        this.img = img;
        
        setUndecorated(true);
        
        InitComponents();
        
        
        
        device.setFullScreenWindow(this);
        
    }
    
    
    DeprecatedImagePanel imgP;
    private void InitComponents () throws AWTException
    {
            setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
            setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
            getContentPane().setLayout(new java.awt.GridLayout(1, 1));


            imgP = new DeprecatedImagePanel(img, true, false, false);
            
            add(imgP);
            
            imgP.addImageTakenListener((img)->{
                closeWindow();
                dispose();
            });
            
            
            pack();
    }
    
    private void closeWindow ()
    {
        device.setFullScreenWindow(null);
    }
    
    public void addImageTakenListener (Consumer<BufferedImage> list)
    {
       
        imgP.addImageTakenListener(list);
    }
    
    public void removeImageTakenListener (Consumer<BufferedImage> list)
    {
        imgP.removeImageTakenListener(list);
    }
}


