package com.ncatz.yeray697.managepc.control;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

import com.ncatz.yeray697.managepc.Logger;

public class ClientConnectedThread extends Thread {
    protected Socket socket;

    private volatile boolean isServerRunning;
    private boolean isClientRunning;

    public ClientConnectedThread(Socket clientSocket) {
        this.socket = clientSocket;
        this.isServerRunning = true;
        this.isClientRunning = true;
    }

    public void run() {
        readMessage();
    }
    
    public void readMessage() {

        InputStream inp = null;
        BufferedReader brinp = null;
        //Declaring streams
        try {
            inp = socket.getInputStream();
            brinp = new BufferedReader(new InputStreamReader(inp));
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

                int order = ParseClientResponse.parseClientResponse(line);
                switch (order) {
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
}