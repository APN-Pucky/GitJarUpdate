package de.neuwirthinformatik.Alexander.GitJarUpdate;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

public class Info {

	public static enum OS {LINUX,WINDOWS};
	 
	public static OS os = detectOS();
	public static String VERSION = detectVersions();

	private static OS detectOS() {
		//detect os
		String OpS = System.getProperty("os.name").toLowerCase();
		
		if (OpS.indexOf("win") >= 0) {
			return OS.WINDOWS;
			//System.out.println("This is Windows");
		} else if (OpS.indexOf("mac") >= 0) {
			return OS.LINUX;
			//System.out.println("This is Mac");
		} else if (OpS.indexOf("nix") >= 0 || OpS.indexOf("nux") >= 0 || OpS.indexOf("aix") > 0 ) {
			return OS.LINUX;
			//System.out.println("This is Unix or Linux");
		} else if (OpS.indexOf("sunos") >= 0) {
			return OS.LINUX;
			//System.out.println("This is Solaris");
		} else {
			return OS.WINDOWS;
			//System.out.println("Your OS is not support!! => Falling back to Windows");
		}
	}
	public static String detectVersions(Object o) {
		return Info.detectVersions(o.getClass());
	}
	public static String detectVersions(Class c) {
		String VERSION = getManifestInfo(c);
		if(VERSION==null)VERSION="DirtyDebug";
		return VERSION;
	}

	public static String detectVersions() {
		String VERSION = getManifestInfo();
		if(VERSION==null)VERSION="DirtyDebug";
		return VERSION;
	}

	public static String getManifestInfo(Object o) {
	    return getManifestInfo(o.getClass()); 
	}

	public static String getManifestInfo(Class c) {
	    Enumeration resEnum;
	    try {
	        resEnum = c.getClassLoader().getResources(JarFile.MANIFEST_NAME);
	        return getManifestInfo(resEnum);
	    } catch (IOException e1) {
	        // Silently ignore wrong manifests on classpath?
	    }
	    return null; 
	}

	public static String getManifestInfo() {
	    Enumeration resEnum;
        try {
			resEnum = Thread.currentThread().getContextClassLoader().getResources(JarFile.MANIFEST_NAME);
			return getManifestInfo(resEnum);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return null;
	}
	public static String getManifestInfo(Enumeration resEnum) {
	    while (resEnum.hasMoreElements()) {
		    try {
		        URL url = (URL)resEnum.nextElement();
		        InputStream is = url.openStream();
		        if (is != null) {
		            Manifest manifest = new Manifest(is);
		            Attributes mainAttribs = manifest.getMainAttributes();
		            String version = mainAttribs.getValue("Implementation-Version");
		            boolean devlive = mainAttribs.getValue("Implementation-Title").equals("Apache Commons Math");
		            if(devlive) return "9999";
		            if(version != null) {
		                return version;
		            }
		        }
		    }
		    catch (Exception e) {
		        // Silently ignore wrong manifests on classpath?
		    }
		}
	    return null; 
	}
}
