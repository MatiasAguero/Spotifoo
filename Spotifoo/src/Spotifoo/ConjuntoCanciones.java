package Spotifoo;

import Spotifoo.Filtro.Filtro;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nico
 */
public class ConjuntoCanciones extends Reproducible{
    List<Reproducible> canciones;
    
    public ConjuntoCanciones (String nombre){
            super(nombre);
            canciones = new ArrayList<>();
    }
    public ConjuntoCanciones(String nombre,List<Reproducible> canciones){
        super(nombre);
        this.canciones=canciones;
    }
    
    public void agregar(Reproducible r){
        canciones.add(r);
    }
    
    @Override
    public List<Reproducible> filtrarCanciones(Filtro f){
        List<Reproducible> salida = new ArrayList<>();
        for (Reproducible r:canciones){
                salida.addAll(r.filtrarCanciones(f));
        }
        return salida;
    }

    public List<Reproducible> getCanciones() {
        List<Reproducible> salida = new ArrayList<>();
        for (Reproducible r:canciones){
                salida.addAll(r.getCanciones());
        }
        return salida;
    }
}
