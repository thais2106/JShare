package br.univel.tela;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import br.dagostini.jshare.comum.pojos.Arquivo;
import br.dagostini.jshare.comun.Cliente;
import br.dagostini.jshare.comun.IServer;

public class TelaConexaoServidor extends JFrame implements Runnable, IServer {

	private JPanel contentPane;
	private JTextField txtIP;
	private JTextField txtPorta;
	Remote servidor;
	private Registry registry;
	private JButton btnDesconectar;
	private JButton btnConectar;
	private JTextArea textArea;
	private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy H:mm:ss:SSS");

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
		
		btnConectar = new JButton("Conectar");
		btnConectar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				iniciarServico();
			}
		});
		btnConectar.setIcon(new ImageIcon("src/br/univel/img/connect.png"));
		menuBar.add(btnConectar);
		
		btnDesconectar = new JButton("Desconectar");
		btnDesconectar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pararServico();
			}
		});
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
		txtIP.setText("127.0.0.1");
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
		
		textArea = new JTextArea();
		textArea.setBackground(Color.GRAY);
		scrollPane.setViewportView(textArea);
	}
	

	protected void pararServico() {
		imprimir("Parando servi�o...");

		try {
			UnicastRemoteObject.unexportObject(this, true);
			UnicastRemoteObject.unexportObject(registry, true);

			txtIP.setEnabled(true);
			txtPorta.setEnabled(true);
			btnConectar.setEnabled(true);

			btnDesconectar.setEnabled(false);

			imprimir("Servi�o encerrado.");

		} catch (RemoteException e) {
			e.printStackTrace();
		}

		
	}

	protected void iniciarServico() {
		
		String ip = txtIP.getText().trim();
		if (!ip.matches("[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}")) {
			JOptionPane.showMessageDialog(this, "Digite um endere�o de IP v�lido!");
			return;
		}
		
		
		String porta = txtPorta.getText().trim();

		if (!porta.matches("[0-9]+") || porta.length() > 5) {
			JOptionPane.showMessageDialog(this, "A porta deve ser um valor num�rico de no m�ximo 5 d�gitos!");
			return;
		}
		
		int intPorta = Integer.parseInt(porta);
		if (intPorta < 1024 || intPorta > 65535) {
			JOptionPane.showMessageDialog(this, "A porta deve estar entre 1024 e 65535");
			return;
		}

		try {

			servidor = (IServer) UnicastRemoteObject.exportObject((IServer) this, 0);
			registry = LocateRegistry.createRegistry(intPorta);
			registry.rebind(IServer.NOME_SERVICO, servidor);

			imprimir("Servi�o iniciado.");

			txtIP.setEnabled(false);
			txtPorta.setEnabled(false);
			btnConectar.setEnabled(false);

			btnDesconectar.setEnabled(true);

		} catch (RemoteException e) {
			JOptionPane.showMessageDialog(this, "Erro ao criar registro, verifique se a porta j� est� sendo usada.");
			e.printStackTrace();
		}

		
	}

	private void imprimir(String string) {
		textArea.append(sdf.format(new Date()));
		textArea.append(" -> ");
		textArea.append(string);
		textArea.append("\n");
	}

	@Override
	public void registrarCliente(Cliente c) throws RemoteException {
		
		imprimir(c.getNome() + " registrado como cliente.");
		
	}

	@Override
	public void publicarListaArquivos(Cliente c, List<Arquivo> lista)
			throws RemoteException {
		
		//Adicionando CLIENTE e a LISTA DE ARQUIVOS no servidor
		HashMap<Cliente, List<Arquivo>> arquivosServidor = new HashMap<>();
		arquivosServidor.put(c, lista);
		
		for (int i = 0; i < lista.size(); i++) {
			System.out.println(lista.get(i).getNome() + lista.get(i).getTamanho());
		}
		
		imprimir(c.getNome() + " publicou lista de arquivos.");
		
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
		imprimir(c.getNome()+" saiu.");
		
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

}
