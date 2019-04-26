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
    
    public abstract List<Cancion> getCanciones(Filtro f);
    
    public int getId(){
        return this.id;
    }
    
    public void play(){
        //*****TO-DO*******
    }
}
