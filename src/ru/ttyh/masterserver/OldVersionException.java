/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.ttyh.masterserver;

/**
 *
 * @author ulltor
 */
public class OldVersionException extends Exception {
    
    public OldVersionException() {
        
        super("Launcher version is too old");
        
    }
    
    public OldVersionException(String msg) {
        super(msg);
    }
    
}
