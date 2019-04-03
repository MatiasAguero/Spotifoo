package Spotifoo;

/**
 *
 * @author nico
 */
public class Cancion implements Reproducible{
    String nombre;
    Artista artista;
    Album album;
    String genero;
    String fecha;
    
    public Cancion(String nombre,Artista artista,Album album,String genero,String fecha){
        this.nombre=nombre;
        this.artista=artista;
        this.album=album;
        this.genero=genero;
        this.fecha=fecha;
    }
    
    @Override
    public void play() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
