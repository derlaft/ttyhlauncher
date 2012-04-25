/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.ttyh.masterserver;

/**
 *
 * @author ulltor
 */
public class BadRequestException extends Exception {
    
    public BadRequestException() {
        
        super("Server tells me that the request was bad. Probably true.");
        
    }
    
    public BadRequestException(String msg) {
        super(msg);
    }
    
}
