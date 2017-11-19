package com.ncatz.yeray697.managepc.control;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

import com.ncatz.yeray697.managepc.Logger;
import com.ncatz.yeray697.managepc.socket.IServerStatus;
import com.ncatz.yeray697.managepc.socket.ParseClientResponse;
import com.ncatz.yeray697.managepc.socket.Status;

public class ClientConnectedThread extends Thread {
	
	protected Socket socket;

    private volatile boolean isServerRunning;
    private boolean isClientRunning;
    private int isValidPass;
    private String password;
    private IServerStatus serverStatus;

    public ClientConnectedThread(Socket clientSocket, String password, IServerStatus serverStatus) {
        this.socket = clientSocket;
        this.isServerRunning = true;
        this.isValidPass = -1;
        this.password = password;
        this.serverStatus = serverStatus;
    }

    public void run() {
        this.isClientRunning = true;
        readMessage();
    }
    
    public void readMessage() {

        InputStream inp = null;
        BufferedReader brinp = null;
        OutputStream outp = null;
        BufferedWriter bwoutp = null;
        //Declaring streams
        try {
            inp = socket.getInputStream();
            brinp = new BufferedReader(new InputStreamReader(inp));
            outp = socket.getOutputStream();
            bwoutp = new BufferedWriter(new OutputStreamWriter(outp));
        } catch (IOException e) {
            return;
        }
        
        //Listen messages from client
    	char[] line;
    	Logger.println("Reading messages from " + socket.getInetAddress() + ":" + socket.getPort());

        while (isServerRunning && isClientRunning) {
            try {
            	line = new char[50000];
            	brinp.read(line);
            	
                int order = ParseClientResponse.parseClientResponse(line,password);
                switch (order) {
                case ParseClientResponse.VALID_PASS:
                	isValidPass = 1;
                	bwoutp.append('1');
                	bwoutp.flush();
                	break;
                case ParseClientResponse.INVALID_PASS:
                	isValidPass = 0;
                	bwoutp.append('0');
                	bwoutp.flush();
                	serverStatus.onStatusChanged(Status.customInfoStatus("Contraseña incorrecta"));
                	isClientRunning = false;
                    socket.close();
                	break;
				default:
					Logger.print("["+socket.getInetAddress() + ":" + socket.getPort()+"]: " + new String(line)+ "\n\r");
					break;
				case ParseClientResponse.ORDER_CLOSE:
					Logger.print("["+socket.getInetAddress() + ":" + socket.getPort()+"]: Closing connection.\n\r");
					isClientRunning = false;
                    socket.close();
					break;
				}
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }
        Logger.print("["+socket.getInetAddress() + ":" + socket.getPort()+"]: Clossed connection.\n\r");
    }
    
	public void closingSocket() {
		isServerRunning = false;
	}
	
	public int isValidPass() {
		return isValidPass;
	}
}