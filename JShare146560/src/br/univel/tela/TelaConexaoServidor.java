package br.univel.tela;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.CardLayout;
import java.awt.GridBagLayout;

import javax.swing.JLabel;

import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.JTextField;
import javax.swing.JTextArea;

import java.awt.Color;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JScrollPane;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JMenuItem;

public class TelaConexaoServidor extends JFrame {

	private JPanel contentPane;
	private JTextField txtIP;
	private JTextField txtPorta;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaConexaoServidor frame = new TelaConexaoServidor();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public TelaConexaoServidor() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JButton btnConectar = new JButton("Conectar");
		btnConectar.setIcon(new ImageIcon("src/br/univel/img/connect.png"));
		menuBar.add(btnConectar);
		
		JButton btnDesconectar = new JButton("Desconectar");
		btnDesconectar.setIcon(new ImageIcon("src/br/univel/img/disconnect.png"));
		menuBar.add(btnDesconectar);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{0, 0, 0};
		gbl_panel.rowHeights = new int[]{0, 0, 0, 0};
		gbl_panel.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 1.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		JLabel lblIp = new JLabel("IP");
		GridBagConstraints gbc_lblIp = new GridBagConstraints();
		gbc_lblIp.anchor = GridBagConstraints.EAST;
		gbc_lblIp.insets = new Insets(0, 0, 5, 5);
		gbc_lblIp.gridx = 0;
		gbc_lblIp.gridy = 0;
		panel.add(lblIp, gbc_lblIp);
		
		txtIP = new JTextField();
		GridBagConstraints gbc_txtIP = new GridBagConstraints();
		gbc_txtIP.insets = new Insets(0, 0, 5, 0);
		gbc_txtIP.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtIP.gridx = 1;
		gbc_txtIP.gridy = 0;
		panel.add(txtIP, gbc_txtIP);
		txtIP.setColumns(10);
		
		JLabel lblPorta = new JLabel("Porta");
		GridBagConstraints gbc_lblPorta = new GridBagConstraints();
		gbc_lblPorta.anchor = GridBagConstraints.EAST;
		gbc_lblPorta.insets = new Insets(0, 0, 5, 5);
		gbc_lblPorta.gridx = 0;
		gbc_lblPorta.gridy = 1;
		panel.add(lblPorta, gbc_lblPorta);
		
		txtPorta = new JTextField();
		txtPorta.setText("1818");
		GridBagConstraints gbc_txtPorta = new GridBagConstraints();
		gbc_txtPorta.insets = new Insets(0, 0, 5, 0);
		gbc_txtPorta.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtPorta.gridx = 1;
		gbc_txtPorta.gridy = 1;
		panel.add(txtPorta, gbc_txtPorta);
		txtPorta.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridwidth = 2;
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 2;
		panel.add(scrollPane, gbc_scrollPane);
		
		JTextArea textArea = new JTextArea();
		textArea.setBackground(Color.GRAY);
		scrollPane.setViewportView(textArea);
	}

}
