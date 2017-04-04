/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package onenoteenhancer;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 *
 * @author Davide
 */
public class SingleArgumentEventManager<T> {
    private List<Consumer<T>> listeners = new ArrayList<>();
    
    public void addListener (Consumer<T> lst)
    {
        listeners.add(lst);
    }
    
    public void removeListener (Consumer<T> lst)
    {
        listeners.remove(lst);
    }
    
    public void fire (T arg)
    {
        for (Consumer<T> lst:listeners)
            lst.accept(arg);
    }
}
