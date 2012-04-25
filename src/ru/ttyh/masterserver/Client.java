/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.ttyh.masterserver;

import java.net.*;
import java.io.*;


/**
 *
 * @author ulltor
 */
public class Client {

    private String server;
    private String version;
    private Boolean loggedIn;
    private String username;
    private String sID;

    public Client(String server, String version) {

        this.server = server;
        this.version = version;

    }

    private URL getURL(String username, String password) {

        URL url = null;

        try {

            String eusername = URLEncoder.encode(username, "UTF-8");
            String epassword = URLEncoder.encode(password, "UTF-8");
            String eversion = URLEncoder.encode(this.version, "UTF-8");

            url = new URL(this.server + "?username=" + eusername + "&password="
                    + epassword + "&version=" + eversion);

        } catch (Exception ex) {

            ex.printStackTrace();

        } finally {

            return url;

        }

    }

    public void login(String username, String password)
            throws NoConnectionException, BadLoginException,
            BadRequestException, UsernameBusyException,
            OldVersionException, BadResponseException {

        URL ms = getURL(username, password);
        
        String response = "";

        try {

            URLConnection msc = ms.openConnection();

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                    msc.getInputStream()));
            String inputLine;

            response = in.readLine();
            
            in.close();

        } catch (IOException ex) {

            throw new NoConnectionException();

        } finally {
            
            handleResponse(response);
            
        }


    }
    
    private void handleResponse(String response)
            throws BadLoginException, BadRequestException,
            UsernameBusyException, OldVersionException, BadResponseException {
        
        if (response.equalsIgnoreCase("bad login")) {
            
            throw new BadLoginException();
            
        }
        
        if (response.equalsIgnoreCase("bad request")) {
            
            throw new BadRequestException();
            
        }
        
        if (response.equalsIgnoreCase("username busy")) {
            
            throw new UsernameBusyException();
            
        }
        
        if (response.equalsIgnoreCase("old version")) {
            
            throw new OldVersionException();
            
        }
        
        try {
            
            String[] split = response.split(":");
            this.username = split[2];
            this.sID = split[3];
            this.loggedIn = true;
            
        } catch (Exception ex) {
            
            throw new BadResponseException();
            
        }
        
    }
    
    public String getUsername() throws NotLoggedInException {
        
        if (!loggedIn)
            throw new NotLoggedInException();
        
        return username;        
        
    }
    
    public String getSID() throws NotLoggedInException {
        
        if (!loggedIn)
            throw new NotLoggedInException();
        
        return sID;        
        
    }
    
}
