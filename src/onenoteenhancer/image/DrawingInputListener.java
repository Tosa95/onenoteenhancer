/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package onenoteenhancer.image;

/**
 *
 * @author Davide
 */
public interface DrawingInputListener {
    public void moved (int x, int y, double force, DrawMode dt);
    public void dragged (int x, int y, double force, DrawMode dt);
    public void clicked (int x, int y, double force, DrawMode dt);
    public void pressed (int x, int y, double  force, DrawMode dt);
    public void released (int x, int y, double force, DrawMode dt);
}
