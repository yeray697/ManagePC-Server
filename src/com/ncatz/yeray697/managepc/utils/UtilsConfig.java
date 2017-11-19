package com.ncatz.yeray697.managepc.utils;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.ncatz.yeray697.managepc.config.Config;

public class UtilsConfig {
	
	public static boolean writeConfig(Config config) {
    	boolean result = false;
        try {
        	File file= new File (Config.PATH_FILE);
        	FileWriter fw;
        	if (!file.exists()) {
         	   file.createNewFile();
        	}
     	   	fw = new FileWriter(file, false);
            fw.write(config.toString());
            fw.close();
            result = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
	}
    
    public static Config readConfig() {

    	Config config = null;
    	int port = Config.DEFAULT_PORT;
    	String pass;
    	try {
    		String text;
            FileReader reader = new FileReader(Config.PATH_FILE);
            int character;
            text = "";
            while ((character = reader.read()) != -1) {
            	text += ((char) character);
            }
            reader.close();
            String[] splitted = text.split(";");
            port = Integer.parseInt(splitted[0]);
            pass = splitted[1];
            config = new Config(port, pass);
        } catch (Exception e) {
        }
    	if (config == null) {
    		config = Config.getDefaultConfig();
    		
    		if (!writeConfig(config)) {
    			config = null;
    		}
    	}
    	return config;
	}
}
