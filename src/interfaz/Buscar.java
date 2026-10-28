package interfaz;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.Color;

import javax.swing.JScrollPane;
import javax.swing.JTable;

import logica.Carpeta;
import logica.Elemento;
import logica.Fichero;
import logica.Sistema;
import util.BuscarCarpetasTableModel;
import util.CarpetasTableModel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JTextPane;

public class Buscar extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private BuscarCarpetasTableModel tableModel;
	private Sistema sistema;
	private JButton btnBuscar;
	private JTextPane textPane;


	public Buscar(Sistema sistema1) {
		this.sistema=sistema1;
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 524, 350);
		contentPane = new JPanel();
		contentPane.setBackground(Color.LIGHT_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 55, 488, 244);
		contentPane.add(scrollPane);

		table = new JTable();
		tableModel=new BuscarCarpetasTableModel();
		table.setModel(tableModel);
		scrollPane.setViewportView(table);
		
		btnBuscar = new JButton("Buscar");
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String nombre = textPane.getText();
				if (!nombre.isEmpty()){
					actualizarTabla(sistema.buscarElementos(nombre));
				}
			}
		});
		btnBuscar.setBounds(409, 21, 89, 23);
		contentPane.add(btnBuscar);

		textPane = new JTextPane();
		textPane.setToolTipText("buscar....");
		textPane.setBounds(10, 24, 389, 20);
		contentPane.add(textPane);
		actualizarTabla(this.sistema.getRaiz());
	}

	public void actualizarTabla(ArrayList<Elemento> cap){
		tableModel=new BuscarCarpetasTableModel();
		table.setModel(tableModel);
		((BuscarCarpetasTableModel)table.getModel()).actualizarTabla(cap.toArray());
	}
	
	public void actualizarTabla(Carpeta cap){
		tableModel=new BuscarCarpetasTableModel();
		table.setModel(tableModel);
		((BuscarCarpetasTableModel)table.getModel()).actualizarTabla(cap.getContenido().toArray());
	}

}
