package Spotifoo;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author nico
 */
public class Artista implements Serializable{
    String nombre;
    String genero;
    List<ConjuntoCanciones> discografia;
    int añoFormacion;
    
    public Artista(String nombre,String genero,List<ConjuntoCanciones> discografia,int añoFormacion){
        this.añoFormacion=añoFormacion;
        this.nombre=nombre;
        this.genero=genero;
        this.discografia=discografia;
    }

    public void setAñoFormacion(int añoFormacion) {
        this.añoFormacion = añoFormacion;
    }

    public void setDiscografia(List<ConjuntoCanciones> discografia) {
        this.discografia = discografia;
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
        return discografia;
    }

    public String getGenero() {
        return genero;
    }

    public String getNombre() {
        return nombre;
    }
    
}
