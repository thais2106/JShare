package br.univel.tela;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.border.EmptyBorder;

import br.dagostini.jshare.comum.pojos.Arquivo;
import br.dagostini.jshare.comun.Cliente;
import br.dagostini.jshare.comun.IServer;

public class TelaConexaoCliente extends JFrame implements IServer{

	private JPanel contentPane;
	private JTextField txtNome;
	private JTextField txtIP;
	private JTextField txtPorta;
	private JTable tableArquivos;
	private Registry registry;
	private IServer servidor;
	private Cliente cliente;
	private JButton btnConectar;
	private JButton btnDesconectar;
	private JButton btnPesquisar;
	private JButton btnDownload;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaConexaoCliente frame = new TelaConexaoCliente();
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
	public TelaConexaoCliente() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		btnConectar = new JButton("Conectar");
		btnConectar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				conectar();
			}
		});
		btnConectar.setIcon(new ImageIcon("src/br/univel/img/connect.png"));
		menuBar.add(btnConectar);
		
		
		btnDesconectar = new JButton("Desconectar");
		btnDesconectar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				desconectar();
			}
		});
		btnDesconectar.setIcon(new ImageIcon("src/br/univel/img/disconnect.png"));
		menuBar.add(btnDesconectar);
		btnDesconectar.setEnabled(false);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_panel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
		gbl_panel.columnWeights = new double[]{0.0, 1.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		JLabel lblNome = new JLabel("Nome");
		GridBagConstraints gbc_lblNome = new GridBagConstraints();
		gbc_lblNome.anchor = GridBagConstraints.EAST;
		gbc_lblNome.insets = new Insets(0, 0, 5, 5);
		gbc_lblNome.gridx = 0;
		gbc_lblNome.gridy = 1;
		panel.add(lblNome, gbc_lblNome);
		
		txtNome = new JTextField();
		GridBagConstraints gbc_txtNome = new GridBagConstraints();
		gbc_txtNome.gridwidth = 3;
		gbc_txtNome.insets = new Insets(0, 0, 5, 5);
		gbc_txtNome.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtNome.gridx = 1;
		gbc_txtNome.gridy = 1;
		panel.add(txtNome, gbc_txtNome);
		txtNome.setColumns(10);
		
		JLabel lblIp = new JLabel("IP");
		GridBagConstraints gbc_lblIp = new GridBagConstraints();
		gbc_lblIp.anchor = GridBagConstraints.EAST;
		gbc_lblIp.insets = new Insets(0, 0, 5, 5);
		gbc_lblIp.gridx = 0;
		gbc_lblIp.gridy = 2;
		panel.add(lblIp, gbc_lblIp);
		
		txtIP = new JTextField();
		GridBagConstraints gbc_txtIP = new GridBagConstraints();
		gbc_txtIP.gridwidth = 3;
		gbc_txtIP.insets = new Insets(0, 0, 5, 5);
		gbc_txtIP.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtIP.gridx = 1;
		gbc_txtIP.gridy = 2;
		panel.add(txtIP, gbc_txtIP);
		txtIP.setColumns(10);
		
		JLabel lblPorta = new JLabel("Porta");
		GridBagConstraints gbc_lblPorta = new GridBagConstraints();
		gbc_lblPorta.anchor = GridBagConstraints.EAST;
		gbc_lblPorta.insets = new Insets(0, 0, 5, 5);
		gbc_lblPorta.gridx = 0;
		gbc_lblPorta.gridy = 3;
		panel.add(lblPorta, gbc_lblPorta);
		
		txtPorta = new JTextField();
		txtPorta.setText("1818");
		GridBagConstraints gbc_txtPorta = new GridBagConstraints();
		gbc_txtPorta.gridwidth = 3;
		gbc_txtPorta.insets = new Insets(0, 0, 5, 5);
		gbc_txtPorta.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtPorta.gridx = 1;
		gbc_txtPorta.gridy = 3;
		panel.add(txtPorta, gbc_txtPorta);
		txtPorta.setColumns(10);
		
		btnPesquisar = new JButton("Pesquisar");
		btnPesquisar.setIcon(new ImageIcon("src/br/univel/img/search.png"));
		GridBagConstraints gbc_btnPesquisar = new GridBagConstraints();
		gbc_btnPesquisar.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnPesquisar.insets = new Insets(0, 0, 5, 5);
		gbc_btnPesquisar.gridx = 1;
		gbc_btnPesquisar.gridy = 5;
		panel.add(btnPesquisar, gbc_btnPesquisar);
		btnPesquisar.setEnabled(false);
		
		btnDownload = new JButton("Download");
		btnDownload.setIcon(new ImageIcon("src/br/univel/img/download.png"));
		GridBagConstraints gbc_btnDownload = new GridBagConstraints();
		gbc_btnDownload.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnDownload.insets = new Insets(0, 0, 5, 5);
		gbc_btnDownload.gridx = 3;
		gbc_btnDownload.gridy = 5;
		panel.add(btnDownload, gbc_btnDownload);
		btnDownload.setEnabled(false);
		
		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridwidth = 8;
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 6;
		panel.add(scrollPane, gbc_scrollPane);
		
		tableArquivos = new JTable();
		scrollPane.setViewportView(tableArquivos);
	}

	protected void desconectar() {
		try {
			desconectar(cliente);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	protected void conectar() {
		String nomeCliente = txtNome.getText();
		if (nomeCliente.isEmpty()){
			JOptionPane.showMessageDialog(this, "Digite um nome!");
			txtNome.requestFocus();
			return;
		}
		
		String ip = txtIP.getText().trim();
		if (!ip.matches("[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}")) {
			JOptionPane.showMessageDialog(this, "Digite um endere�o de IP v�lido!");
			txtIP.requestFocus();
			return;
		}
		
		String porta = txtPorta.getText().trim();
		if (!porta.matches("[0-9]+") || porta.length() > 5) {
			JOptionPane.showMessageDialog(this, "A porta deve ser um valor num�rico de no m�ximo 5 d�gitos!");
			txtPorta.requestFocus();
			return;
		}
		
		int intPorta = Integer.parseInt(porta);
		
		try {
			registry = LocateRegistry.getRegistry(ip, intPorta);
			servidor = (IServer) registry.lookup(NOME_SERVICO);
			cliente = new Cliente(nomeCliente, ip, intPorta);
			cliente = (Cliente) UnicastRemoteObject.exportObject(this,0);
			
			servidor.registrarCliente(cliente);

			//fazer publicar lista de arquivos ao conectar
			
			btnDesconectar.setEnabled(true);
			btnPesquisar.setEnabled(true);
			btnDownload.setEnabled(true);
			txtNome.setEnabled(false);
			txtIP.setEnabled(false);
			txtPorta.setEnabled(false);
			btnConectar.setEnabled(false);
			
			
		} catch (RemoteException e) {
			// TODO: handle exception
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void registrarCliente(Cliente c) throws RemoteException {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void publicarListaArquivos(Cliente c, List<Arquivo> lista)
			throws RemoteException {
		
		
	}

	@Override
	public Map<Cliente, List<Arquivo>> procurarArquivo(String nome)
			throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public byte[] baixarArquivo(Arquivo arq) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void desconectar(Cliente c) throws RemoteException {
		
		if (servidor != null){
			servidor.desconectar(c);
			UnicastRemoteObject.unexportObject(this, true);
			servidor = null;
		}
		
		btnDesconectar.setEnabled(false);
		btnConectar.setEnabled(true);
		txtNome.setEnabled(true);
		txtIP.setEnabled(true);
		txtPorta.setEnabled(true);
	}
}
