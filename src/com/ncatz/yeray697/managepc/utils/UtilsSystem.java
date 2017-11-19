package com.ncatz.yeray697.managepc.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class UtilsSystem {
	private static String OS;
	private static String OS_FOLDER;
	
	private static String getOSName()
	{
		if(OS == null) {
			OS = System.getProperty("os.name"); 
		}
		return OS;
	}

	public static boolean isWindows()
	{
		return getOSName().startsWith("Windows");
	}

	public static boolean isLinux()
	{
		return getOSName().startsWith("Linux");
	}

	public static boolean isMac()
   	{
	   return getOSName().startsWith("Mac");
   	}

	private static String getOSFolder() {
		if(OS_FOLDER == null) {
			if (isWindows()) {
				OS_FOLDER = "windows";
			} else if (isLinux()) {
				OS_FOLDER = "linux";
			} else if (isMac()) {
				OS_FOLDER = "mac";
			} else {
				OS_FOLDER = "";
			}
		}
		return OS_FOLDER;
	}
	
	public static String getLibFolder() {
		return "lib" + File.separator + getOSFolder() + File.separator;
	}

	public static String getMachineIP() throws IOException, InterruptedException {
		String command = "";
		if (isLinux()) {
			command = getLibFolder()+"GetIP.sh";
		}

        String ip = runCommandWithOutput(command);
        
        return ip;
	}
	
	public static void runCommand(String command) throws IOException, InterruptedException {
    	runCommandWithOutput(command);
    }
    
    public static String runCommandWithOutput(String command) throws IOException, InterruptedException{
    	ProcessBuilder ps = new ProcessBuilder(command);

    	//From the DOC:  Initially, this property is false, meaning that the 
    	//standard output and error output of a subprocess are sent to two 
    	//separate streams
    	ps.redirectErrorStream(true);

    	Process pr = ps.start();  

    	BufferedReader in = new BufferedReader(new InputStreamReader(pr.getInputStream()));
    	String output = "";
    	String line;
    	while ((line = in.readLine()) != null) {
    		output += line;
    	}
    	pr.waitFor();

    	in.close();
    	
    	return output;
	}

}
