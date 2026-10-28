package interfaz;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.Color;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import logica.Carpeta;
import logica.Elemento;
import logica.Fichero;
import logica.Sistema;
import util.CarpetasTableModel;
import util.DatosElementoDialog;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
//import java.util.ArrayList;

public class Principal extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private CarpetasTableModel tableModel;
	private Sistema sistema;
	private Carpeta actual;
	private JMenuBar menuBar;
	private String nombre = "";
	private String extension = "";
	
	private Elemento portapapeles = null; // elemento que se va a copiar o mover
	private Carpeta carpetaOrigen;         //  carpeta origen de la cual se va a mover/copiar
	
	private JMenuItem mntmMover;  
	private JMenuItem mntmPegar;  
	private JMenuItem mntmAtrs;   
	private JMenuItem mntmCopiar; 
	private JMenuItem mntmBorrar; 
	private JMenuItem mntmBuscar;  
	private JMenuItem mntmRenombrar; 


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Principal frame = new Principal();
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
	public Principal() {
		sistema = new Sistema("Disco local C:", "cap", 1);
		llenarDatos();
		actual=sistema.getRaiz();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 524, 350);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		menuBar = new JMenuBar();
		menuBar.setBackground(Color.LIGHT_GRAY);
		menuBar.setBounds(0, 0, 528, 24);
		contentPane.add(menuBar);

		JMenu mnArchivo = new JMenu("Archivo");
		mnArchivo.setBackground(Color.LIGHT_GRAY);
		menuBar.add(mnArchivo);

		JMenuItem mntmNuevaCarpeta = new JMenuItem("Nueva Carpeta");
		mntmNuevaCarpeta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				DatosElementoDialog ren = new DatosElementoDialog("Nueva Carpeta");
				nombre = ren.getNombre();
				extension = ren.getExtension();
				try{
					if (!nombre.isEmpty()) {
					  Elemento elemento = new Carpeta(nombre, extension);
					  sistema.agregarElemento(actual, elemento);
					  actualizarTabla(actual);
					  nombre = "";
					  extension = "";
    				}
				}
				catch(NullPointerException ex) {
					System.out.println("No se obtuvieron los datos");
				}
			}
		});
		mnArchivo.add(mntmNuevaCarpeta);

		JMenuItem mntmNuevoFichero = new JMenuItem("Nuevo Fichero");
		mntmNuevoFichero.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				DatosElementoDialog ren = new DatosElementoDialog("Nuevo Fichero");
				nombre = ren.getNombre();
				extension = ren.getExtension();
				try {
					if (!nombre.isEmpty() && !extension.isEmpty()) {
					   Elemento elemento = new Fichero(extension, nombre, 0);
					   sistema.agregarElemento(actual, elemento);
					   actualizarTabla(actual);
					   nombre = "";
					   extension = "";
				    }
				}
				catch(NullPointerException ex) {
					System.out.println("No se obtuvieron los datos");
				}
			}
		});
		mnArchivo.add(mntmNuevoFichero);

		JMenu mnEdicin = new JMenu("Edici\u00F3n");
		menuBar.add(mnEdicin);

		mntmAtrs = new JMenuItem("Atr\u00E1s");
		mntmAtrs.setEnabled(false);
		mntmAtrs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Carpeta actual1 = sistema.buscarPadre(actual);

				if (actual1 != null) {
				  actual = actual1;
				  actualizarTabla(actual);
				}

				if (actual.equals(sistema.getRaiz()))   
				  mntmAtrs.setEnabled(false);	
	
			}
		});
		mnEdicin.add(mntmAtrs);

		mntmBuscar = new JMenuItem("Buscar");
		mnEdicin.add(mntmBuscar);
		
		mntmRenombrar = new JMenuItem("Renombrar");
		mnEdicin.add(mntmRenombrar);

		
		mntmBorrar = new JMenuItem("Borrar");
		mnEdicin.add(mntmBorrar);
		
		mntmCopiar = new JMenuItem("Copiar");
		mnEdicin.add(mntmCopiar);
		
		mntmMover = new JMenuItem("Mover");
		mntmMover.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int pos = table.getSelectedRow();
				if (pos != -1) {
					portapapeles = actual.getContenido().get(pos);
					carpetaOrigen = sistema.buscarPadre(portapapeles);
				}
			}
		});
		mnEdicin.add(mntmMover);
		
		mntmPegar = new JMenuItem("Pegar");
		mntmPegar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			  boolean accion = false;	
			  if (portapapeles != null)	{
				  accion = sistema.mover(carpetaOrigen, actual, portapapeles);
                  if (accion) 
				     actualizarTabla(actual);
			  }	
			}
		});
		mnEdicin.add(mntmPegar);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 35, 488, 264);
		contentPane.add(scrollPane);

		table = new JTable();
		tableModel=new CarpetasTableModel();
		table.setModel(tableModel);
		table.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent e) {
				if (e.getClickCount() == 2) 
					entrarCarpeta();
		
			}
		});
		scrollPane.setViewportView(table);
		actualizarTabla(actual);
	}
	

	public void llenarDatos(){
		Carpeta cap1 = new Carpeta("Universidad", "folder");
		Carpeta cap2 = new Carpeta("Películas", "folder");
		Carpeta cap3 = new Carpeta("Imágenes", "folder");
		Carpeta cap4 = new Carpeta("Música", "folder");

		sistema.getRaiz().getContenido().add(cap1);
		sistema.getRaiz().getContenido().add(cap2);		
		sistema.getRaiz().getContenido().add(cap3);		
		sistema.getRaiz().getContenido().add(cap4);	
		
		Carpeta cap5 = new Carpeta("ClasesDPOO", "folder");
		Fichero fich10 = new Fichero("doc", "Orientaciones Generales", 100.55);
		
		cap1.getContenido().add(cap5);
		cap1.getContenido().add(fich10);
		
		Carpeta cap6 = new Carpeta("El Rey León", "folder");
		Carpeta cap7 = new Carpeta("Cumpleaños", "folder");

		cap2.getContenido().add(cap6);
		cap3.getContenido().add(cap7);

		
		Carpeta cap8 = new Carpeta("Queen", "folder");
		Fichero fich11 = new Fichero("mp3", "BSB: I Want It That Way", 5005.55);

		cap4.getContenido().add(cap8);
		cap4.getContenido().add(fich11);
		

		Fichero fich = new Fichero("pdf", "Deitel 9na ed.",2048);
		Fichero fich1 = new Fichero("doc", "Lab6",41.5);

		cap5.getContenido().add(fich);
		cap5.getContenido().add(fich1);

		Fichero fich2 = new Fichero("avi", "El Rey León", 3000.58);
		cap6.getContenido().add(fich2);

		Fichero fich3  =new Fichero("png", "2200701HP0", 400.54);
		Fichero fich4 = new Fichero("png", "2200701HP1", 401.78);
		Fichero fich5 = new Fichero("png", "2200701HP2", 415.54);
		Fichero fich6 = new Fichero("png", "2200701HP3", 420.88);
		cap7.getContenido().add(fich3);
		cap7.getContenido().add(fich4);
		cap7.getContenido().add(fich5);
		cap7.getContenido().add(fich6);

		Fichero fich7 = new Fichero("mp3", "Bohemian Rhapsody ", 5400.54);
		Fichero fich8 = new Fichero("mp3", "Radio Ga Ga", 4401.78);
		Fichero fich9 = new Fichero("mp3", "Killer Queen", 2415.54);
		cap8.getContenido().add(fich7);
		cap8.getContenido().add(fich8);
		cap8.getContenido().add(fich9);
	}

	public void actualizarTabla(Carpeta cap){
		tableModel = new CarpetasTableModel();
		table.setModel(tableModel);
		((CarpetasTableModel)table.getModel()).actualizarTabla(cap.getContenido().toArray());
	}

	public void entrarCarpeta() {
		int pos = table.getSelectedRow();
		if (actual.getContenido().get(pos) instanceof Carpeta) {
  			 actualizarTabla(((Carpeta)actual.getContenido().get(pos)));
		     actual = ((Carpeta) actual.getContenido().get(pos));
		     mntmAtrs.setEnabled(true);
		}
	}
}
