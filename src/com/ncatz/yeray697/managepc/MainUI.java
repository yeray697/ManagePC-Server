package com.ncatz.yeray697.managepc;
import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.GridBagLayout;
import java.io.IOException;

import com.google.zxing.WriterException;
import com.ncatz.yeray697.managepc.socket.ServerManager;
import com.ncatz.yeray697.managepc.socket.Status;
import com.ncatz.yeray697.managepc.socket.IServerStatus;
import com.ncatz.yeray697.managepc.utils.UtilsQR;

import javax.swing.JPanel;
import java.awt.Font;

import javax.swing.SwingConstants;
import java.awt.GridBagConstraints;
import java.awt.Insets;

public class MainUI implements IServerStatus{
	
	private ServerManager serverManager;
	private JFrame frame;
	private JLabel lblQR, lblIPAddress, lblPort, lblPassword, lblStatus;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainUI window = new MainUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * @throws IOException 
	 * @throws WriterException 
	 */
	public MainUI() throws WriterException, IOException {
		serverManager = new ServerManager(this);
		String qrPath = UtilsQR.generateQR(serverManager.toString());
		initialize();
		updateUI(serverManager.getIp(), serverManager.getPort(),
				serverManager.getPassword(),
				qrPath,
				serverManager.getStatusMessage(),
				serverManager.getStatusColor());
		serverManager.startServer();
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		int MAX_WIDTH = 324;
		int HEADER_HEIGHT = 100;
		int MARGIN_RIGHT_LEFT = 15;
		
		frame = new JFrame();
		frame.setBounds(100, 100, MAX_WIDTH, 416);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JPanel panelHeader = new JPanel();
		panelHeader.setBounds(0, 0, MAX_WIDTH, HEADER_HEIGHT);
		frame.getContentPane().add(panelHeader);
		panelHeader.setBackground(new Color(0,80,239));
		panelHeader.setLayout(new GridBagLayout());
		
		JLabel lblAppName = new JLabel("Manage PC");
		lblAppName.setForeground(new Color(240, 240, 240));
		lblAppName.setFont(new Font("SansSerif", Font.BOLD, 30));
		GridBagConstraints gbc_lblAppName = new GridBagConstraints();
		gbc_lblAppName.insets = new Insets(0, 0, 5, 0);
		gbc_lblAppName.gridx = 0;
		gbc_lblAppName.gridy = 0;
		panelHeader.add(lblAppName, gbc_lblAppName);
		
		
		JPanel panelBody = new JPanel();
		panelBody.setLayout(null);
		panelBody.setBackground(Color.WHITE);
		panelBody.setBounds(0, HEADER_HEIGHT, MAX_WIDTH, 290);
		
		JLabel lblIPAddressTitle= new JLabel("Dirección:");
		lblIPAddressTitle.setHorizontalAlignment(SwingConstants.RIGHT);
		lblIPAddressTitle.setBounds(57, 25, 100, 15);
		panelBody.add(lblIPAddressTitle);
		JLabel lblPortTitle = new JLabel("Puerto:");
		lblPortTitle.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPortTitle.setBounds(57, 55, 100, 15);
		panelBody.add(lblPortTitle);
		
		JLabel lblPasswordTitle = new JLabel("Contraseña:");
		lblPasswordTitle.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPasswordTitle.setBounds(57, 85, 100, 15);
		panelBody.add(lblPasswordTitle);

		
		lblQR = new JLabel("");
		lblQR.setBounds(99, 136, 126, 126);
		
		panelBody.add(lblQR);
		frame.getContentPane().add(panelBody);

		
		lblIPAddress = new JLabel("xxx.xxx.x.xxx");
		lblIPAddress.setHorizontalAlignment(SwingConstants.LEFT);
		lblIPAddress.setBounds(169, 25, 100, 15);
		panelBody.add(lblIPAddress);
		
		lblPort = new JLabel("x");
		lblPort.setHorizontalAlignment(SwingConstants.LEFT);
		lblPort.setBounds(169, 55, 100, 15);
		panelBody.add(lblPort);
		
		lblPassword = new JLabel("xxxxx");
		lblPassword.setHorizontalAlignment(SwingConstants.LEFT);
		lblPassword.setBounds(169, 85, 100, 15);
		panelBody.add(lblPassword);
		
		JLabel lblO = new JLabel("──────────────────── O ────────────────────");
		lblO.setHorizontalAlignment(SwingConstants.LEFT);
		lblO.setBounds(MARGIN_RIGHT_LEFT, 121, 312, 15);
		panelBody.add(lblO);
		
		lblStatus = new JLabel("Status:");
		lblStatus.setBounds(MARGIN_RIGHT_LEFT, 268, 306, 15);
		panelBody.add(lblStatus);

	}
	
	private void updateUI(String ip, String port, String pass, String qrPath, String status, Color color) {
		lblIPAddress.setText(ip);
		lblPort.setText(port);
		lblPassword.setText(pass);
		lblQR.setIcon(new ImageIcon(qrPath));
		updateStatus(status, color);
	}
	
	private void updateStatus(String status, Color color) {
		if (lblStatus != null) {
			lblStatus.setText(status);
			lblStatus.setForeground(color);
		}
	}

	@Override
	public void onStatusChanged(Status status) {
		updateStatus(status.text, status.color);	
	}

	@Override
	public void onUserDisconnected() {
		Status status = Status.customInfoStatus("Usuario desconectado");
		updateStatus(status.text, status.color);
	}

	@Override
	public void onUserConnected() {
		Status status = Status.customSuccessfulStatus("Usuario conectado");
		updateStatus(status.text, status.color);
	}

	@Override
	public void onError(String error) {
		Status status = Status.customErrorStatus(error);
		updateStatus(status.text, status.color);
	}
}
