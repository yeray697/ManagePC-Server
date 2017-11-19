package com.ncatz.yeray697.managepc.socket;

import java.awt.Color;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;


import com.ncatz.yeray697.managepc.Logger;
import com.ncatz.yeray697.managepc.config.Config;
import com.ncatz.yeray697.managepc.control.ClientConnectedThread;
import com.ncatz.yeray697.managepc.utils.UtilsConfig;
import com.ncatz.yeray697.managepc.utils.UtilsSystem;

public class ServerManager extends Thread {
	private Config config;
	private IServerStatus serverStatus;
	private String serverIp;
    private ClientConnectedThread clientConnectedThread;
    private boolean open;
    protected ServerSocket serverSocket;
    protected Socket socket;
	private Status status;
    
    public ServerManager(IServerStatus mainUI) {
    	this.serverStatus = mainUI;
		printLog(Status.STARTING);
		config = UtilsConfig.readConfig();
    	try {
			serverIp = UtilsSystem.getMachineIP();
		} catch (IOException | InterruptedException e) {
			modifyStatus(Status.customErrorStatus(e.getMessage()));
		}
    	this.clientConnectedThread = null;
    }

    public void run() {
    	try
        {
        	Logger.println("Running server over port "+ config.getPort());
        	serverSocket = new ServerSocket(config.getPort());
        	open = true;
            listenClients();
        }
        catch (Exception e)
        {
    		printErrorLog(e.getMessage());
        }
    }
    public void startServer() {
        start();
    }
    
    public void closeServer() throws IOException {
		printInfoLog("Closing server");
    	open = false;
    	if (clientConnectedThread != null) {
    		clientConnectedThread.closingSocket();
        	clientConnectedThread = null;
    	}
    	serverSocket.close();
		printInfoLog("Closed server");
    }
    
    public void listenClients() {

		while (open) {
			if (clientConnectedThread == null || clientConnectedThread.isValidPass() == 0 || !clientConnectedThread.isAlive()) {
				if (clientConnectedThread == null) {
					printLog(Status.LISTENING);
				} else if (clientConnectedThread.isValidPass() == 0) {
					printInfoLog("Contraseña incorrecta. Esperando cliente...");
				} else if (!clientConnectedThread.isAlive()) {
					printInfoLog("Cliente desconectado. Esperando cliente...");
				}
				try {
		        	socket = serverSocket.accept();
		        	Logger.println("Client accepted: " + socket.getInetAddress() + ":"+socket.getPort());
		        	clientConnectedThread = new ClientConnectedThread(socket, config.getPass(), serverStatus);
		            clientConnectedThread.start();
		    		printLog(Status.CONNECTED);
		        } catch (IOException e) {
		        	if (open)
		        		printErrorLog("I/O error: " + e.getMessage());
		        }
			}
		}
    }

    public String getIp() {
		return serverIp;
	}
    
    public String getPort() {
		return String.valueOf(config.getPort());
	}
    
    public String getPassword() {
		return config.getPass();
	}
    
    public String getStatusMessage() {
		return status.text;
	}
    
    public Color getStatusColor() {
		return status.color;
	}
    
    public void modifyStatus(Status newStatus) {
    	status = newStatus;
    	serverStatus.onStatusChanged(newStatus);
    }
    
    @Override
    public String toString() {
        	return serverIp + ";" + 
        			config.getPort() + ";" +
        			config.getPass() + ";";
    }

    private void printErrorLog(String message) {
		printLog(Status.customErrorStatus(message));
    }
    
    private void printInfoLog(String message) {
		printLog(Status.customInfoStatus(message));
    }
    
    private void printSuccessfulLog(String message) {
		printLog(Status.customSuccessfulStatus(message));
    }
    
    private void printLog(Status status) {
    	Logger.println(status.text);
		modifyStatus(status);
	}
}