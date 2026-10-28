package logica;

import java.util.Date;

public abstract class Elemento implements EClonable {
	
	protected Date fecha;
	protected String extension;
	protected String nombre;
	
	public Elemento(Date fecha, String extension, String nombre) {
		super();
		this.fecha = fecha;
		this.extension = extension;
		this.nombre = nombre;
	}
	
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public String getExtension() {
		return extension;
	}
	public void setExtension(String extension) {
		this.extension = extension;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public abstract double tamannoTotal();
	
	public abstract Elemento clone();
	
}
