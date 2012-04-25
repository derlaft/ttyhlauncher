/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.ttyh.masterserver;

/**
 *
 * @author ulltor
 */
public class NoConnectionException extends Exception {
    
    public NoConnectionException() {
        
        super("Sory, no connection to the Master Server.");
        
    }
    
    public NoConnectionException(String msg) {
        super(msg);
    }
    
}
