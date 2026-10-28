package logica;

import java.util.Calendar;
import java.util.Random;

public class Fichero extends Elemento {
	
	private double tamannoKB;

	public Fichero(String extension, String nombre) {
		super(Calendar.getInstance().getTime(), extension, nombre);
		Random rand = new Random();
		this.tamannoKB = rand.nextDouble();
	}
	
	public Fichero(String extension, String nombre, double tamanno) {
		super(Calendar.getInstance().getTime(), extension, nombre);
		this.tamannoKB = tamanno;
	}

	@Override
	public double tamannoTotal() {
		return tamannoKB;
	}
	
	@Override
	public Fichero clone() {
	  Fichero fich = new Fichero(this.getExtension(), this.getNombre());
	  fich.tamannoKB = this.tamannoTotal();
	  fich.fecha = this.getFecha();
      return fich;
	}
}
