package Spotifoo;

import Spotifoo.Filtro.Filtro;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nico
 */
public class Cancion extends Reproducible{
    Artista artista;
    Album album;
    String fecha;
    String genero;
    
    public Cancion(String nombre,Artista artista,Album album,String genero,String fecha){
        super(nombre);
        this.genero=genero;
        this.artista=artista;
        this.album=album;
        this.fecha=fecha;
        
    }
    
    @Override
    public List<Cancion> getCanciones(Filtro f) {
        List<Cancion> salida = new ArrayList<>();
        if (f.cumple(this))
                salida.add(this);
        return salida;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public void setArtista(Artista artista) {
        this.artista = artista;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Album getAlbum() {
        return album;
    }

    public Artista getArtista() {
        return artista;
    }

    public String getFecha() {
        return fecha;
    }

    public String getGenero() {
        return genero;
    }

    public String getNombre() {
        return nombre;
    }
    
    
}
