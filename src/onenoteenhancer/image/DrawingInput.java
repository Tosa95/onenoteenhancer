/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package onenoteenhancer.image;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Davide
 */
public abstract class DrawingInput {
    private List<DrawingInputListener> listeners = new ArrayList<>();
    
    public void addDrawingInputListener (DrawingInputListener lst)
    {
        listeners.add(lst);
    }
    
    public void removeDrawingInputListener (DrawingInputListener lst)
    {
        listeners.remove(lst);
    }
    
    private void fire(FireDelegate dlg)
    {
        for (DrawingInputListener l : listeners)
        {
            dlg.fire(l);
        }
    }
    
    public void fireDrawingInputEvent_pressed (int x, int y, double force, DrawMode dt)
    {
        fire((l)->l.pressed(x, y, force, dt));
    }
    
    public void fireDrawingInputEvent_moved (int x, int y, double force, DrawMode dt)
    {
        fire((l)->l.moved(x, y, force, dt));
    }
    
    public void fireDrawingInputEvent_dragged (int x, int y, double force, DrawMode dt)
    {
        fire((l)->l.dragged(x, y, force, dt));
    }
    
    public void fireDrawingInputEvent_clicked (int x, int y, double force, DrawMode dt)
    {
        fire((l)->l.clicked(x, y, force, dt));
    }
    
    public void fireDrawingInputEvent_released (int x, int y, double force, DrawMode dt)
    {
        fire((l)->l.released(x, y, force, dt));
    }
    
    private interface FireDelegate
    {
        public void fire(DrawingInputListener lst);
    }
}
