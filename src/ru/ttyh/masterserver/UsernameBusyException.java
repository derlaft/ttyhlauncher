/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.ttyh.masterserver;

/**
 *
 * @author ulltor
 */
public class UsernameBusyException extends Exception {
    
    public UsernameBusyException() {
        
        super("Sorry, but this username is already taken :(");
        
    }
    
    public UsernameBusyException(String msg) {
        super(msg);
    }
    
}
