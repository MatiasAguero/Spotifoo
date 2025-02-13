package Spotifoo;

import Spotifoo.Filtro.Filtro;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author nico
 */
public abstract class Reproducible implements Serializable{
    String nombre;
    int id;
    
    public Reproducible(String nombre){
        this.nombre=nombre;
        this.id = this.hashCode();
    }
    
    public abstract List<Reproducible> filtrarCanciones(Filtro f);
    public abstract List<Cancion> getCanciones();
    public abstract int getCantCanciones();
    public int getId(){
        return this.id;
    }

    public String getNombre() {
        return nombre;
    }
    
    public abstract boolean perteneceArtista(String artista);
    public abstract boolean perteneceGenero(String genero);
    
    public abstract String play();
}
