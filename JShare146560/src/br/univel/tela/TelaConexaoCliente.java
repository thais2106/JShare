package br.univel.tela;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
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
import br.dagostini.jshare.comum.pojos.Diretorio;
import br.dagostini.jshare.comum.pojos.ListarDiretoriosArquivos;
import br.dagostini.jshare.comun.Cliente;
import br.dagostini.jshare.comun.IServer;

public class TelaConexaoCliente extends JFrame implements IServer{

	private JPanel contentPane;
	private JTextField txtNome;
	private JTextField txtIPServidor;
	private JTextField txtPortaServidor;
	private JTable tableArquivos;
	private Registry registry;
	private IServer servidor;
	private Cliente cliente;
	private JButton btnConectar;
	private JButton btnDesconectar;
	private JButton btnPesquisar;
	private JButton btnDownload;
	private JTextField txtPesquisar;
	private JLabel lblPesquisar;
	private JLabel lblIpCliente;
	private JTextField txtIPCliente;
	private JLabel lblPortaCliente;
	private JTextField txtPortaCliente;

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
		gbl_panel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_panel.columnWeights = new double[]{0.0, 1.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		JLabel lblNome = new JLabel("Nome");
		GridBagConstraints gbc_lblNome = new GridBagConstraints();
		gbc_lblNome.anchor = GridBagConstraints.EAST;
		gbc_lblNome.insets = new Insets(0, 0, 5, 5);
		gbc_lblNome.gridx = 0;
		gbc_lblNome.gridy = 1;
		panel.add(lblNome, gbc_lblNome);
		
		txtNome = new JTextField();
		txtNome.setText("thais");
		GridBagConstraints gbc_txtNome = new GridBagConstraints();
		gbc_txtNome.gridwidth = 3;
		gbc_txtNome.insets = new Insets(0, 0, 5, 5);
		gbc_txtNome.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtNome.gridx = 1;
		gbc_txtNome.gridy = 1;
		panel.add(txtNome, gbc_txtNome);
		txtNome.setColumns(10);
		
		JLabel lblIp = new JLabel("IP Servidor");
		GridBagConstraints gbc_lblIp = new GridBagConstraints();
		gbc_lblIp.anchor = GridBagConstraints.EAST;
		gbc_lblIp.insets = new Insets(0, 0, 5, 5);
		gbc_lblIp.gridx = 0;
		gbc_lblIp.gridy = 2;
		panel.add(lblIp, gbc_lblIp);
		
		txtIPServidor = new JTextField();
		txtIPServidor.setText("127.0.0.1");
		GridBagConstraints gbc_txtIPServidor = new GridBagConstraints();
		gbc_txtIPServidor.insets = new Insets(0, 0, 5, 5);
		gbc_txtIPServidor.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtIPServidor.gridx = 1;
		gbc_txtIPServidor.gridy = 2;
		panel.add(txtIPServidor, gbc_txtIPServidor);
		txtIPServidor.setColumns(10);
		
		JLabel lblPorta = new JLabel("Porta Servidor");
		GridBagConstraints gbc_lblPorta = new GridBagConstraints();
		gbc_lblPorta.anchor = GridBagConstraints.EAST;
		gbc_lblPorta.insets = new Insets(0, 0, 5, 5);
		gbc_lblPorta.gridx = 2;
		gbc_lblPorta.gridy = 2;
		panel.add(lblPorta, gbc_lblPorta);
		
		txtPortaServidor = new JTextField();
		txtPortaServidor.setText("1818");
		GridBagConstraints gbc_txtPortaServidor = new GridBagConstraints();
		gbc_txtPortaServidor.insets = new Insets(0, 0, 5, 5);
		gbc_txtPortaServidor.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtPortaServidor.gridx = 3;
		gbc_txtPortaServidor.gridy = 2;
		panel.add(txtPortaServidor, gbc_txtPortaServidor);
		txtPortaServidor.setColumns(10);
		
		lblIpCliente = new JLabel("IP Cliente");
		GridBagConstraints gbc_lblIpCliente = new GridBagConstraints();
		gbc_lblIpCliente.anchor = GridBagConstraints.EAST;
		gbc_lblIpCliente.insets = new Insets(0, 0, 5, 5);
		gbc_lblIpCliente.gridx = 0;
		gbc_lblIpCliente.gridy = 3;
		panel.add(lblIpCliente, gbc_lblIpCliente);
		
		txtIPCliente = new JTextField();
		txtIPCliente.setColumns(10);
		GridBagConstraints gbc_txtIPCliente = new GridBagConstraints();
		gbc_txtIPCliente.insets = new Insets(0, 0, 5, 5);
		gbc_txtIPCliente.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtIPCliente.gridx = 1;
		gbc_txtIPCliente.gridy = 3;
		panel.add(txtIPCliente, gbc_txtIPCliente);
		
		lblPortaCliente = new JLabel("Porta Cliente");
		GridBagConstraints gbc_lblPortaCliente = new GridBagConstraints();
		gbc_lblPortaCliente.anchor = GridBagConstraints.EAST;
		gbc_lblPortaCliente.insets = new Insets(0, 0, 5, 5);
		gbc_lblPortaCliente.gridx = 2;
		gbc_lblPortaCliente.gridy = 3;
		panel.add(lblPortaCliente, gbc_lblPortaCliente);
		
		txtPortaCliente = new JTextField();
		txtPortaCliente.setColumns(10);
		GridBagConstraints gbc_txtPortaCliente = new GridBagConstraints();
		gbc_txtPortaCliente.insets = new Insets(0, 0, 5, 5);
		gbc_txtPortaCliente.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtPortaCliente.gridx = 3;
		gbc_txtPortaCliente.gridy = 3;
		panel.add(txtPortaCliente, gbc_txtPortaCliente);
		
		lblPesquisar = new JLabel("Pesquisar");
		GridBagConstraints gbc_lblPesquisar = new GridBagConstraints();
		gbc_lblPesquisar.insets = new Insets(0, 0, 5, 5);
		gbc_lblPesquisar.anchor = GridBagConstraints.EAST;
		gbc_lblPesquisar.gridx = 0;
		gbc_lblPesquisar.gridy = 4;
		panel.add(lblPesquisar, gbc_lblPesquisar);
		
		txtPesquisar = new JTextField();
		GridBagConstraints gbc_txtPesquisar = new GridBagConstraints();
		gbc_txtPesquisar.gridwidth = 2;
		gbc_txtPesquisar.insets = new Insets(0, 0, 5, 5);
		gbc_txtPesquisar.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtPesquisar.gridx = 1;
		gbc_txtPesquisar.gridy = 4;
		panel.add(txtPesquisar, gbc_txtPesquisar);
		txtPesquisar.setColumns(10);
		
		btnPesquisar = new JButton("Pesquisar");
		btnPesquisar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				consultarArquivo();
			}
		});
		btnPesquisar.setIcon(new ImageIcon("src/br/univel/img/search.png"));
		GridBagConstraints gbc_btnPesquisar = new GridBagConstraints();
		gbc_btnPesquisar.gridwidth = 2;
		gbc_btnPesquisar.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnPesquisar.insets = new Insets(0, 0, 5, 5);
		gbc_btnPesquisar.gridx = 3;
		gbc_btnPesquisar.gridy = 4;
		panel.add(btnPesquisar, gbc_btnPesquisar);
		btnPesquisar.setEnabled(false);
		
		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridheight = 2;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane.gridwidth = 8;
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 5;
		panel.add(scrollPane, gbc_scrollPane);
		
		tableArquivos = new JTable();
		scrollPane.setViewportView(tableArquivos);
		
		btnDownload = new JButton("Download");
		btnDownload.setIcon(new ImageIcon("src/br/univel/img/download.png"));
		GridBagConstraints gbc_btnDownload = new GridBagConstraints();
		gbc_btnDownload.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnDownload.insets = new Insets(0, 0, 0, 5);
		gbc_btnDownload.gridx = 3;
		gbc_btnDownload.gridy = 7;
		panel.add(btnDownload, gbc_btnDownload);
		btnDownload.setEnabled(false);
		
		
	}

	protected void consultarArquivo() {

		String pesquisa = txtPesquisar.getText();
		
		if (pesquisa.isEmpty()){
			JOptionPane.showMessageDialog(this, "Campo de pesquisa em branco!");
		}
		
	    //procura arquivo no servidor
		System.out.println("procura arquivo");
		
		try {
			servidor.procurarArquivo(pesquisa);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
		
		String ip = txtIPServidor.getText().trim();
		if (!ip.matches("[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}")) {
			JOptionPane.showMessageDialog(this, "Digite um endere�o de IP v�lido!");
			txtIPServidor.requestFocus();
			return;
		}
		
		String porta = txtPortaServidor.getText().trim();
		if (!porta.matches("[0-9]+") || porta.length() > 5) {
			JOptionPane.showMessageDialog(this, "A porta deve ser um valor num�rico de no m�ximo 5 d�gitos!");
			txtPortaServidor.requestFocus();
			return;
		}
		
		int intPorta = Integer.parseInt(porta);
		
		try {
			registry = LocateRegistry.getRegistry(ip, intPorta);
			servidor = (IServer) registry.lookup(NOME_SERVICO);
			cliente = new Cliente(nomeCliente, ip, intPorta);
			//cliente = (Cliente) UnicastRemoteObject.exportObject(this,0);
			
			servidor.registrarCliente(cliente);
			
			//publica lista de arquivos ao conectar
			servidor.publicarListaArquivos(cliente, criarListaCliente());

			btnDesconectar.setEnabled(true);
			btnPesquisar.setEnabled(true);
			btnDownload.setEnabled(true);
			txtNome.setEnabled(false);
			txtIPServidor.setEnabled(false);
			txtPortaServidor.setEnabled(false);
			btnConectar.setEnabled(false);
			
			
		} catch (RemoteException e) {
			// TODO: handle exception
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}


	private List<Arquivo> criarListaCliente() {
		
		//Cria lista de arquivos que estão na pasta JShare
		File dirStart = new File("C:/JShare/Uploads");

		List<Arquivo> listaArquivos = new ArrayList<>();
		List<Diretorio> listaDiretorios = new ArrayList<>();
		
		for (File file : dirStart.listFiles()) {
			if (file.isFile()) {
				System.out.println("entrou aqui");
				Arquivo arq = new Arquivo();
				arq.setNome(file.getName());
				System.out.println(file.getName());
				arq.setTamanho(file.length());
				listaArquivos.add(arq);
			} else {
				Diretorio dir = new Diretorio();
				dir.setNome(file.getName());
				listaDiretorios.add(dir);				
			}
		}
		
		
		return listaArquivos;
		
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
			//UnicastRemoteObject.unexportObject(this, true);
			servidor = null;
		}
		
		btnDesconectar.setEnabled(false);
		btnConectar.setEnabled(true);
		txtNome.setEnabled(true);
		txtIPServidor.setEnabled(true);
		txtPortaServidor.setEnabled(true);
	}
}
