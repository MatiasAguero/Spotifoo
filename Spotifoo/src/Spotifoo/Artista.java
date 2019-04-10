package Spotifoo;

import java.util.List;

/**
 *
 * @author nico
 */
public class Artista {
    String nombre;
    String genero;
    List<Album> discografia;
    int añoFormacion;
    
    public Artista(String nombre,String genero,List<Album> discografia,int añoFormacion){
        this.añoFormacion=añoFormacion;
        this.nombre=nombre;
        this.genero=genero;
        this.discografia=discografia;
    }

    public void setAñoFormacion(int añoFormacion) {
        this.añoFormacion = añoFormacion;
    }

    public void setDiscografia(List<Album> discografia) {
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

    public List<Album> getDiscografia() {
        return discografia;
    }

    public String getGenero() {
        return genero;
    }

    public String getNombre() {
        return nombre;
    }
    
}
