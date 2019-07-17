package Spotifoo;

import Spotifoo.DataManager.DAO;
import Spotifoo.DataManager.DAO_FS;
import Spotifoo.Filtro.Filtro;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nico
 */
public class Cancion extends Reproducible{
    int idArtista;
    int album;
    String fecha;
    String genero;
    boolean userOwned;
    
    public Cancion(String nombre,Artista artista,ConjuntoCanciones album,String genero,String fecha){
        super(nombre);
        this.genero=genero;
        this.idArtista=artista.getId();
        this.album=album.getId();
        this.fecha=fecha;

    }
    
    public Cancion(String nombre,Artista artista,String genero,String fecha){
        super(nombre);
        this.genero=genero;
        this.idArtista=artista.getId();
        this.album=Integer.MIN_VALUE;
        this.fecha=fecha;
    }
    
    public Cancion(String nombre,String genero,String fecha){
        super(nombre);
        this.genero=genero;
        this.idArtista=Integer.MIN_VALUE;
        this.album=Integer.MIN_VALUE;
        this.fecha=fecha;
    }
        
    @Override
    public List<Reproducible> filtrarCanciones(Filtro f) {
        List<Reproducible> salida = new ArrayList<>();
        if (f.cumple(this))
                salida.add(this);
        return salida;
    }
    
    @Override
    public List<Cancion> getCanciones(){
        List<Cancion> salida =new ArrayList<Cancion>();
        salida.add(this);
        return salida;
    }
    
    @Override
    public int getCantCanciones(){
        return 1;
    }

    public void setAlbum(int album) {
        this.album = album;

    }

    public void setArtista(Artista artista) {
        this.idArtista = artista.getId();
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

    public ConjuntoCanciones getAlbum() {
        if(this.album == Integer.MIN_VALUE){
            DAO bd = DAO_FS.getBaseDatos();
            return (ConjuntoCanciones) bd.getReproducible(this.album);
        }
        else return null;
    }

    public Artista getArtista() {
        if(this.idArtista != Integer.MIN_VALUE){
            DAO bd = DAO_FS.getBaseDatos();
            return bd.getArtista(this.idArtista);
        }
        else return null;
    }

    public boolean isAlbumSet(){
        return album != Integer.MIN_VALUE;
    }
    
    public String getFecha() {
        return fecha;
    }

    public String getGenero() {
        return genero;
    }

    @Override
    public String getNombre() {
        return nombre;
    }

    @Override
    public boolean perteneceArtista(String a) {
        DAO bd = DAO_FS.getBaseDatos();
        return bd.getArtista(this.idArtista).getNombre().equals(a);
    }
    @Override
    public boolean perteneceGenero(String genero){
        return this.genero.equals(genero);
    }

    @Override
    public String play() {
        if(!this.isAlbumSet())
            return DAO_FS.getBaseDatos().getImg(nombre);
        else {
            String s = DAO_FS.getBaseDatos().getReproducible(album).getNombre();
            return DAO_FS.getImg(s);
        }
            
    }
    
    @Override
    public String toString(){
        if(this.getArtista() == null)
            return this.getNombre() +" | " + this.getGenero();
        return this.getNombre() +" | " + this.getArtista().getNombre() + " | "+ this.getGenero();
    }
    
    
    
}
