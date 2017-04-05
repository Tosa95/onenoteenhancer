/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package onenoteenhancer;

import onenoteenhancer.image.DeprecatedImagePanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.HeadlessException;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;

/**
 *
 * @author Davide
 */
public class ImageViewer extends JFrame{

    BufferedImage img;
    
    private int getTaskbarH ()
    {
        Dimension scrnSize = Toolkit.getDefaultToolkit().getScreenSize();
        Rectangle winSize = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();

        return scrnSize.height - winSize.height;
    }
    
    private int getTaskbarW ()
    {
        Dimension scrnSize = Toolkit.getDefaultToolkit().getScreenSize();
        Rectangle winSize = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();

        return scrnSize.width - winSize.width;
    }
    
    private int getScreenH()
    {
        return (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
    }
    
    private int getScreenW()
    {
        return (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
    }
    
    public ImageViewer(BufferedImage img) throws HeadlessException {
    
        
        this.setPreferredSize(new Dimension(img.getWidth(), img.getHeight()));
        this.setAlwaysOnTop(true);
        this.setUndecorated(true);
        initComponent();
        setImage(img);
    }
    
    DeprecatedImagePanel imgP;
    
    
    
    public void setImage(BufferedImage img)
    {
        this.img = img;
        
        
        imgP.setImage(img);
        this.setPreferredSize(new Dimension(img.getWidth(), img.getHeight()));
        
        int y = getScreenH() - (img.getHeight() + getTaskbarH());
        int x = getScreenW() - (img.getWidth() + getTaskbarW());
        
        this.setBounds(x, y, img.getWidth(), img.getHeight());
        
        pack();
        repaint();
    }
            
    
    private void initComponent()
    {
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
            setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
            getContentPane().setLayout(new java.awt.GridLayout(1, 1));


            imgP = new DeprecatedImagePanel(img, false, true, true);
            
            this.add(imgP);
            
            
            pack();
    }

    @Override
    public void paintComponents(Graphics g) {
        super.paintComponents(g); 
        
    }
    
    
}
