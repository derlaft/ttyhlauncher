/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.ttyh.masterserver;

/**
 *
 * @author ulltor
 */
public class BadResponseException extends Exception {
    
    public BadResponseException() {
        
        super("Server gave back something reeealy unexpected O_O");
        
    }
    
    public BadResponseException(String msg) {
        super(msg);
    }
    
}
