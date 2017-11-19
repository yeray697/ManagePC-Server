package com.ncatz.yeray697.managepc.config;

import com.ncatz.yeray697.managepc.utils.Utils;

public class Config {
	public static final String PATH_FILE = "app.config";
	public static final int DEFAULT_PORT = 5312;
	
	private int port;
	private String pass;
	
	public Config(int port, String pass) {
		this.port = port;
		this.pass = pass;
	}
	
	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}
	
	public String getPass() {
		return pass;
	}
	
	public void setPass(String pass) {
		this.pass = pass;
	}
	
	@Override
	public String toString() {
		return port + ";" + pass + ";";
	}
	
	public static Config getDefaultConfig() {
		return new Config(DEFAULT_PORT, Utils.generatePassword());
	}
}
