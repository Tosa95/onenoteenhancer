/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package onenoteenhancer.image;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 *
 * @author Davide
 */
public class MouseDrawingInput extends DrawingInput{

    private static final double DEFAULT_FORCE = 1.0;
    
    public MouseDrawingInput(JPanel panel) {
        
        panel.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                fireDrawingInputEvent_clicked(e.getX(), e.getY(), DEFAULT_FORCE, DrawMode.DRAW);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                fireDrawingInputEvent_pressed(e.getX(), e.getY(), DEFAULT_FORCE, DrawMode.DRAW);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                fireDrawingInputEvent_released(e.getX(), e.getY(), DEFAULT_FORCE, DrawMode.DRAW);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                
            }

            @Override
            public void mouseExited(MouseEvent e) {
                
            }
        });
        
        panel.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                
                DrawMode dt = SwingUtilities.isLeftMouseButton(e)?DrawMode.DRAW:DrawMode.EREASE;
                
                fireDrawingInputEvent_dragged(e.getX(), e.getY(), DEFAULT_FORCE, dt);
                
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                
                fireDrawingInputEvent_moved(e.getX(), e.getY(), DEFAULT_FORCE, DrawMode.HOVER);
                
            }
        });
        
    }
    
    
    
}
