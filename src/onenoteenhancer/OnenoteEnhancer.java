/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package onenoteenhancer;

import java.awt.AWTException;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author Davide
 */
public class OnenoteEnhancer {

    /**
     * @param args the command line arguments
     */
    
    static ImageViewer imgView = null;
    
    public static void main(String[] args) throws IOException, AWTException {
        // TODO code application logic here
        
        TrayIcon ti = new TrayIcon(ImageIO.read(new File("OnenoteEnhancerICO.png")), "OneNoteEnhancer");
        ti.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    
                    if (e.getButton() == MouseEvent.BUTTON1)
                    {
                        GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[0];

                        JFullScreenSelector sel = new JFullScreenSelector(new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize())));
                        sel.addImageTakenListener((img)->{
                            if (imgView == null)
                            {
                                imgView = new ImageViewer(img);
                                
                            }
                            imgView.setImage(img);
                            imgView.setVisible(true);
                        });
                    } else if (imgView!=null){
                        imgView.setVisible(false);
                    }
                     
                    
                } catch (HeadlessException ex) {
                    Logger.getLogger(OnenoteEnhancer.class.getName()).log(Level.SEVERE, null, ex);
                } catch (AWTException ex) {
                    Logger.getLogger(OnenoteEnhancer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
                
            }
        });
        SystemTray.getSystemTray().add(ti);
    }
    
}
