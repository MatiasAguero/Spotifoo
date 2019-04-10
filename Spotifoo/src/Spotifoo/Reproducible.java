package Spotifoo;

import Spotifoo.Filtro.Filtro;
import java.util.List;

/**
 *
 * @author nico
 */
public abstract class Reproducible {
    String nombre;
    
    public Reproducible(String nombre){
        this.nombre=nombre;
    }
    
    public abstract List<Cancion> getCanciones(Filtro f);
}
