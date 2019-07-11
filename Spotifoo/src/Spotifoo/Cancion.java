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
    
    public Cancion(String nombre,Artista artista,ConjuntoCanciones album,String genero,String fecha){
        super(nombre);
        this.genero=genero;
        this.idArtista=artista.getId();
        this.album=album.getId();
        this.fecha=fecha;
        
        updateDB();
    }
    
    public Cancion(String nombre,Artista artista,String genero,String fecha){
        super(nombre);
        this.genero=genero;
        this.idArtista=artista.getId();
        this.album=Integer.MIN_VALUE;
        this.fecha=fecha;
        updateDB();
    }
    
    public Cancion(String nombre,String genero,String fecha){
        super(nombre);
        this.genero=genero;
        this.idArtista=Integer.MIN_VALUE;
        this.album=Integer.MIN_VALUE;
        this.fecha=fecha;
    }
    
    private void updateDB(){
        DAO bd = DAO_FS.getBaseDatos();
        bd.addReprod(this);
    }
    
    @Override
    public List<Reproducible> filtrarCanciones(Filtro f) {
        List<Reproducible> salida = new ArrayList<>();
        if (f.cumple(this))
                salida.add(this);
        return salida;
    }
    
    @Override
    public List<Reproducible> getCanciones(){
        List<Reproducible> salida =new ArrayList<Reproducible>();
        salida.add(this);
        return salida;
    }

    public void setAlbum(int album) {
        this.album = album;
        updateDB();
    }

    public void setArtista(Artista artista) {
        this.idArtista = artista.getId();
        updateDB();
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
        updateDB();
    }

    public void setGenero(String genero) {
        this.genero = genero;
        updateDB();
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
        updateDB();
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
    
    
}
