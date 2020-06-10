package com.ezfun.guess.pomversion;

import org.springframework.core.io.FileSystemResource;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;
import java.util.Optional;
import java.util.Properties;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @author SoySauce
 * @date 2020/3/25
 */
public class PomVersion {


    public static void getPomVersion(String prefix) {
        try {
            Enumeration<URL> dirs = Thread.currentThread().getContextClassLoader().getResources("");
            while (dirs.hasMoreElements()) {
                URL url = dirs.nextElement();
                System.out.println("url>>>>>>>" + url);
                Optional.ofNullable(url).ifPresent(u -> {
                            System.out.println("path >>>>>>>>>>>>" + url.getPath());
                            boolean flag = url.getPath().startsWith(prefix);
                            if (flag) {
                                String path = url.getPath().replaceFirst("file:", "").replaceFirst("!/BOOT-INF/classes!/", "");
                                JarFile jarFile = null;
                                try {
                                    jarFile = new JarFile(path);
                                    JarEntry entry = jarFile.getJarEntry("META-INF/MANIFEST.MF");
                                    InputStream inputStream = jarFile.getInputStream(entry);
                                    Properties config = new Properties();
                                    config.load(inputStream);
                                    System.out.println("pom version:" + config.getProperty("Implementation-Version"));
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                );
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args){
        getPomVersion("guess");

    }


    public void getVersion1() throws IOException {
        ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
        String path = systemClassLoader.getResource("").getPath().replace("/classes", "");
        FileSystemResource fileSystemResource = new FileSystemResource(path + "maven-archiver" + "/pom.properties");
        Properties config = new Properties();
        config.load(fileSystemResource.getInputStream());
        System.out.println("pom 版本:" + config.getProperty("version"));

        ////////////////////////////






      /*  FileSystemResource r = new FileSystemResource( "pom.properties");
        Properties config2 = new Properties();
        config2.load(r.getInputStream());
        System.out.println("pom 版本2:"+config2.getProperty("version"));

        ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
        String path = systemClassLoader.getResource("").getPath().replace("/classes", "");
        FileSystemResource fileSystemResource = new FileSystemResource(path + "maven-archiver" + "/pom.properties");
        Properties config = new Properties();
        config.load(fileSystemResource.getInputStream());
        System.out.println("pom 版本:"+config.getProperty("version"));*/
    }

    public void getVersion2() {
        try {
            String path = Thread.currentThread().getContextClassLoader().getResource("").getPath().replaceFirst("file:", "").replaceFirst("!/BOOT-INF/classes!/", "");
            JarFile file = new JarFile(path);
            Properties config = new Properties();
            config.load(file.getInputStream(file.getJarEntry("META-INF/MANIFEST.MF")));
            System.out.println("pom 版本:" + config.getProperty("Implementation-Version"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getVersion3() {


    }

    public void getVersion4() {
    }

    public void getVersion5() {
    }


}
