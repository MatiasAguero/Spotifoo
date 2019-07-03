package Spotifoo;

import Spotifoo.DataManager.DAO;
import Spotifoo.DataManager.DAO_FS;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nico
 */
public class Artista implements Serializable{
    String nombre;
    int id;
    String genero;
    List<Integer> discografia;
    int añoFormacion;
    
    public Artista(String nombre,String genero,List<ConjuntoCanciones> discografia,int añoFormacion){
        this.añoFormacion=añoFormacion;
        this.nombre=nombre;
        this.genero=genero;
        this.id = this.hashCode();
        
        List<Integer> l = new ArrayList<>();
        for(ConjuntoCanciones c: discografia)
            l.add(c.getId());
        
        this.discografia=l;
    }

    public void setAñoFormacion(int añoFormacion) {
        this.añoFormacion = añoFormacion;
    }

    public void setDiscografia(List<ConjuntoCanciones> discografia) {
        
        List<Integer> l = new ArrayList<>();
        for(ConjuntoCanciones c: discografia)
            l.add(c.getId());
        
        this.discografia=l;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getAñoFormacion() {
        return añoFormacion;
    }

    public List<ConjuntoCanciones> getDiscografia() {
        
        DAO bd = DAO_FS.getBaseDatos();
        
        List<ConjuntoCanciones> l = new ArrayList<>();
        for(Integer i: discografia)
            l.add((ConjuntoCanciones) bd.getReproducible(i));
        
        return l;
    }

    public int getId() {
        return id;
    }

    public String getGenero() {
        return genero;
    }

    public String getNombre() {
        return nombre;
    }
    
    public boolean equals(Artista a){
        return a.getNombre().equals(nombre) && a.getGenero().equals(genero) &&
                a.getAñoFormacion() == añoFormacion;
    }
}
