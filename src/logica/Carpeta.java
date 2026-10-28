package logica;

import java.util.ArrayList;
import java.util.Calendar;

public class Carpeta extends Elemento implements Borrador, Renombrador, Copiador, Movedor {

	private ArrayList<Elemento> contenido;

	public Carpeta(String nombre, String extension) {
		super(Calendar.getInstance().getTime(),extension,nombre);
		this.contenido = new ArrayList<Elemento>();
	}

	public ArrayList<Elemento> getContenido() {
		return contenido;
	}

	@Override
	public boolean borrar(Elemento elemento) {
		boolean eliminado = false;
		Elemento elem = buscarElementoUnNivel(elemento);
		if (elem != null){
		   contenido.remove(elem);
		   eliminado = true;
		}
		return eliminado;
	}

	@Override
	public boolean renombrar(Elemento elemento, String nuevoNombre, String nuevaExtension) {
		Elemento elem = buscarElementoUnNivel(nuevoNombre, nuevaExtension);
		boolean renombrar = false;
		if (elem == null){
			elemento.setExtension(nuevaExtension);
			elemento.setNombre(nuevoNombre);
			renombrar = true;
		}
		return renombrar;
	}

	@Override
	public double tamannoTotal() {
		double tamanno=0;
		for (int i = 0; i < contenido.size(); i++) 
			tamanno += contenido.get(i).tamannoTotal();
		return tamanno;
	}

	@Override
	public boolean copiar(Elemento elemento) {
	  boolean copiado = false;	
	  if (esFactibleCopiarMover(elemento))	{
		Elemento copia = elemento.clone();  
   	    contenido.add(copia);
   	    copiado = true;
	  }
	  return copiado;
	}

	public boolean esFactibleCopiarMover(Elemento elemento) {
	  boolean factible = false;
      if (buscarElementoUnNivel(elemento) == null) 
    	if (buscarElementoUnNivel(elemento.getNombre(), elemento.getExtension()) == null)    
          if (elemento instanceof Carpeta) { 
            if (!elemento.equals(this)) 
			  if (!((Carpeta)elemento).esDescendiente(this)) 
			    factible = true;
          }
          else
        	factible = true;  
	  return factible;
	}
	
	public boolean agregarElemento(Elemento elem) {
	  boolean agregar = false;
	  Elemento enc = buscarElementoUnNivel(elem.getNombre(), elem.getExtension());
	  if (enc == null) {
		contenido.add(elem); 
		agregar = true;
	  }
	  return agregar;
	}
	
	public ArrayList<Elemento> buscarElementosUnNivel(String nombre){
		ArrayList<Elemento> elem = new ArrayList<Elemento>(); 
		String nombreMay = nombre.toUpperCase();
		int i = 0;
		String campoNombreMay;
		String campoExtMay;
		while (i < contenido.size()) {
			campoNombreMay = contenido.get(i).getNombre().toUpperCase();
			campoExtMay = contenido.get(i).getExtension().toUpperCase();
			if (campoNombreMay.contains(nombreMay) || campoExtMay.contains(nombreMay)) 
				elem.add(contenido.get(i));
			i++;
		}
		return elem;
	}
	
	
	public Elemento buscarElementoUnNivel(String nombre, String extension){
		Elemento elem = null; 
		int i = 0;
		while (i < contenido.size() && elem == null ) {
			if (contenido.get(i).getNombre().equalsIgnoreCase(nombre) &&
					contenido.get(i).getExtension().equalsIgnoreCase(extension)) {
				elem = contenido.get(i);
			}
			i++;
		}
		return elem;
	}
	
	public Elemento buscarElementoUnNivel(Elemento elem){
		Elemento elem1 = null; 
		int i = 0;
		while (i < contenido.size() && elem1 == null ) {
			if (contenido.get(i).equals(elem))			
				elem1 = contenido.get(i);
			i++;
		}
		return elem1;
	}
	
	public Carpeta buscarPadre(Elemento elem){
		Carpeta padre = null; 
		int i = 0;
		while (i < contenido.size() && padre == null ) {
			if (contenido.get(i) instanceof Carpeta) {
				Elemento elem1 = buscarElementoUnNivel(elem);
				if(elem1 != null)
					padre = this;
				else
					padre = ((Carpeta)contenido.get(i)).buscarPadre(elem);
			}	
			else 
				if (contenido.get(i).equals(elem)) 
					padre = this;
			i++;
		}
		return padre;
	}
	
	public ArrayList<Elemento> buscarElementos(String nombre){
		ArrayList<Elemento> elem1 = new ArrayList<Elemento>(); 
		int i = 0;
		elem1.addAll(buscarElementosUnNivel(nombre));
		while (i < contenido.size()) {
			if (contenido.get(i) instanceof Carpeta)
				elem1.addAll(((Carpeta)contenido.get(i)).buscarElementos(nombre));
			i++;
		}
		return elem1;
	}

	@Override
	public boolean mover(Elemento aMover) { 
	  boolean movido = false;
	  if (esFactibleCopiarMover(aMover)) {
		 contenido.add(aMover);
		 movido = true;
	  }
	  return movido;
	}

	public boolean esDescendiente(Carpeta carpeta) {
	  boolean desc = false;
	  int i = 0;
	  Elemento elemento;
	  while (i < contenido.size() && !desc) {
		elemento = contenido.get(i);
		if (elemento instanceof Carpeta)
		  if (((Carpeta)elemento).equals(carpeta))
			desc = true;
		  else
			desc = ((Carpeta)elemento).esDescendiente(carpeta);  
	    i++;	
	  }
	  return desc;
	}
	
	@Override
	public Carpeta clone() {
	  Carpeta carp = new Carpeta(this.nombre, this.extension); 	
	  carp.setFecha(this.fecha);
	  ArrayList<Elemento> nuevaLista = new ArrayList<Elemento>();
	  for (int i = 0; i < contenido.size(); i++)
        nuevaLista.add(contenido.get(i).clone());    		  
	  carp.contenido = nuevaLista;
	  return carp;	
	}
}
