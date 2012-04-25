/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.ttyh.masterserver;

/**
 *
 * @author ulltor
 */
public class NotLoggedInException extends Exception {
    
    public NotLoggedInException() {
        
        super("You're currently not logged in.");
        
    }
    
    public NotLoggedInException(String msg) {
        super(msg);
    }
    
}
