//<editor-fold defaultstate="collapsed" desc="BSD license">
/*
 * Copyright (c) 2012, NewClass
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 * 
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 * 3. The name of the author may not be used to endorse or promote products
 *    derived from this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 * NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 * THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
//</editor-fold>

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

            url = new URL(this.server + "?user=" + eusername + "&password="
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
