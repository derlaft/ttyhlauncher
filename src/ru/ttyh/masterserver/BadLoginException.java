/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.ttyh.masterserver;

/**
 *
 * @author ulltor
 */
public class BadLoginException extends Exception {
    
    public BadLoginException() {
        
        super("Wrong login/password.");
        
    }
    
    public BadLoginException(String msg) {
        super(msg);
    }
    
}
