package logica;

import java.util.ArrayList;

public class Sistema {

	private Carpeta raiz;
	private double capacidad;     // en GB

	public Sistema(String nombre, String extension, double capacidad) {
		super(); 
		this.raiz = new Carpeta(nombre, extension);
		setCapacidad(capacidad);
	}

	public Carpeta getRaiz() {
		return raiz;
	}

	public void setRaiz(Carpeta raiz) {
		this.raiz = raiz;
	}

	public double getCapacidad() {
	  return capacidad;	
	}
	
	public void setCapacidad(double capacidad) {
	  if (capacidad >= 0.5)
		this.capacidad = capacidad;
	  else
		this.capacidad = 1;  
	}
	
	public double capacidadDisponible() {
	  return capacidad * 1024 * 1024 - raiz.tamannoTotal();
	}

	public boolean copiar(Carpeta destino, Elemento elemento) {
	  boolean copiar = false;
	  if (capacidadDisponible() >= elemento.tamannoTotal())
        copiar = destino.copiar(elemento);
	  return copiar;
	}
	
	public boolean agregarElemento(Carpeta destino, Elemento elemento) {
	  return destino.agregarElemento(elemento);	
	}
	
	public boolean mover(Carpeta origen, Carpeta destino, Elemento elemento) {
      boolean mover = false;		
   	  mover = destino.mover(elemento);
	  if (mover)
		origen.borrar(elemento);
	  return mover;
	}

	public boolean renombrar(Carpeta carpeta, Elemento elemento, String nuevoNombre, String nuevaExtension) {
	   return carpeta.renombrar(elemento, nuevoNombre, nuevaExtension); 	
	}
	
	public boolean borrar(Carpeta carpeta, Elemento elemento) {
	  return carpeta.borrar(elemento);
	}
	
	public Carpeta buscarPadre(Elemento elemento){
		Carpeta padre = null;
		padre = raiz.buscarPadre(elemento);
		return padre;
	}
	
	public ArrayList<Elemento> buscarElementos(String nombre){
		ArrayList<Elemento>  elem = null;
		elem = raiz.buscarElementos(nombre);
		return elem;
	}
	
	public boolean esDescendienteDe(Carpeta carpetaDestino, Carpeta carpetaOrigen) {
	  return carpetaOrigen.esDescendiente(carpetaDestino);	
	}
}
