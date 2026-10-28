package util;

import java.util.Date;

import javax.swing.table.DefaultTableModel;

import logica.Carpeta;
import logica.Elemento;

public class BuscarCarpetasTableModel extends DefaultTableModel{

	private static final long serialVersionUID = 1L;

	public BuscarCarpetasTableModel(){
		String[] columnNames = {"Tipo", "Nombre", "Extensión", "Fecha", "Tamaño"};
		this.setColumnIdentifiers(columnNames);
	}
	
	public BuscarCarpetasTableModel(Elemento [] elementos){
		String[] columnNames = {"Tipo", "Nombre", "Extensión", "Fecha","Tamaño"};
		this.setColumnIdentifiers(columnNames);
		String tipo;
		for (int i = 0; i < elementos.length; i++) {
			if (elementos[i] instanceof Carpeta)
			  tipo = "(C)";
			else
			  tipo = "(F)";	
			Object[] newRow = new Object[]{tipo, elementos[i].getNombre(), elementos[i].getExtension(), elementos[i].getFecha().toString(),elementos[i].tamannoTotal()};
			addRow(newRow);
		}
	}
	
	public void actualizarTabla(Object [] elementos){
		
		String[] columnNames = {
				"Tipo", "Nombre", "Extensión", "Fecha","Tamaño"};
		this.setColumnIdentifiers(columnNames);
		String tipo;
		for (int i = 0; i < elementos.length; i++) {
			if (elementos[i] instanceof Carpeta)
			  tipo = "(C)";
			else
			  tipo = "(F)";	
			Object[] newRow = new Object[]{tipo, ((Elemento) elementos[i]).getNombre(), ((Elemento) elementos[i]).getExtension(), ((Elemento) elementos[i]).getFecha().toString(),((Elemento) elementos[i]).tamannoTotal()};
			addRow(newRow);
		}
	}

	public void adicionar(String Nombre ,String Extension, Date Fecha){
		Object[] newRow = new Object[]{Nombre, Extension, Fecha.toString()};
		addRow(newRow);
	}
	
	public void eliminar(int pos){
		removeRow(pos);
	}

	@SuppressWarnings("unchecked")
	public void modificar(int pos, String Nombre ,String Extension, Date Fecha){
		setValueAt(Nombre, pos, 0);
		setValueAt(Extension, pos, 1);
		setValueAt(Fecha.toString(), pos, 2);			
	}
	
	@Override
	   public boolean isCellEditable(int row, int column) {       
	       return false; // or a condition at your choice with row and column
	   }
	
	public String obtainNombre(int row){
		return (String) getValueAt(row, 0);
	}
	
	public String obtainExtension(int row){
		return (String) getValueAt(row, 1);
	}
}
