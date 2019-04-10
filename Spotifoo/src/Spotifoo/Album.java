package Spotifoo;

import Spotifoo.Filtro.Filtro;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nico
 */
public class Album extends Reproducible{
    List<Reproducible> canciones;
    
    public Album (String nombre){
            super(nombre);
            canciones = new ArrayList<>();
    }
    public Album(String nombre,List<Reproducible> canciones){
        super(nombre);
        this.canciones=canciones;
    }
    
    public void agregar(Reproducible r){
        canciones.add(r);
    }
    
    @Override
    public List<Cancion> getCanciones(Filtro f){
		List<Cancion> salida = new ArrayList<>();
		for (Reproducible r:canciones){
			salida.addAll(r.getCanciones(f));
		}
		return salida;
	}
}
