/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.ttyh.game;

import java.net.URL;

/**
 *
 * @author ulltor
 */
public class Starter {
    
    public static void startSMP (String username, String sID) {
        
        try
        {
            URL urls [] = {};

            Loader cl = new Loader (urls);
            
            cl.run("net.minecraft.client.Minecraft", new String[] {username, sID});
        }
        catch (Exception ex)
        {
            System.out.println ("Failed.");
            ex.printStackTrace ();
        }
    }
    
}
