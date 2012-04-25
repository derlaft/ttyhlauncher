/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
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
