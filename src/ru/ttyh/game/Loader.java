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

package ru.ttyh.game;

/**
 *
 * @author ulltor
 */
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

public class Loader extends URLClassLoader {

    public Loader(URL[] urls) throws MalformedURLException {
        
        super(urls);
        loadAllJars();
        setNativesPath();
        
    }

    private static String getMCBinFolder() {

        String os = System.getProperty("os.name");

        if (os.startsWith("Windows")) {
            return System.getenv("APPDATA") + "\\.minecraft\\bin\\";
        } else {
            return System.getenv("HOME") + "/.minecraft/bin/";
        }

    }

    private URL getJarURL(String jarname) throws MalformedURLException {

        File jarfile = new File(getMCBinFolder() + jarname);
        return new URL("jar", "", "file:" + jarfile.getAbsolutePath() + "!/");

    }

    private void loadJar(String jar) throws MalformedURLException {

        addURL(getJarURL(jar));

    }

    private void loadAllJars() throws MalformedURLException {

        loadJar("minecraft.jar");
        loadJar("lwjgl.jar");
        loadJar("lwjgl_util.jar");
        loadJar("jinput.jar");

    }

    private void setNativesPath() {

        String nativef = getMCBinFolder() + "natives/";

        System.setProperty("org.lwjgl.librarypath", nativef);
        System.setProperty("net.java.games.input.librarypath", nativef);

    }

    public void run(String name, String[] args)
            throws ClassNotFoundException,
            NoSuchMethodException,
            InvocationTargetException {
        Class c = loadClass(name);
        Method m = c.getMethod("main", new Class[]{args.getClass()});
        m.setAccessible(true);
        int mods = m.getModifiers();
        if (m.getReturnType() != void.class || !Modifier.isStatic(mods)
                || !Modifier.isPublic(mods)) {
            throw new NoSuchMethodException("main");
        }
        try {
            m.invoke(null, new Object[]{args});
        } catch (IllegalAccessException e) {
            // This should not happen, as we have disabled access checks
        }
    }
}
