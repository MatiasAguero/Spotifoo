package Spotifoo;

import Spotifoo.DataManager.DAO;
import Spotifoo.DataManager.DAO_FS;
import Spotifoo.Filtro.Filtro;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author nico
 */
public class ConjuntoCanciones extends Reproducible{
    List<Integer> canciones;
    
    public ConjuntoCanciones (String nombre){
        super(nombre);
        canciones = new ArrayList<>();

    }
    
    public ConjuntoCanciones(String nombre,List<Cancion> canciones){
        super(nombre);
        
        List<Integer> l = new ArrayList<>();
        for(Cancion c: canciones)
            l.add(c.getId());
        this.canciones=l;

    }
    
    public void agregar(Reproducible r){
        canciones.add(r.getId());
    }
    
    //Actualiza todas las canciones añadidas a este objeto y les asigna su id
    //como id de album. ***SOLAMENTE VÁLIDO CUANDO SON CANCIONES***
    public void setAlbum(){
        
        DAO bd = DAO_FS.getBaseDatos();
        
        for(Integer i: canciones){
            try{
            Cancion c = ((Cancion)bd.getReproducible(i));
            c.setAlbum(this.id);
            bd.addReprod(c);
            } catch(ClassCastException e){
                Logger.getLogger(ConjuntoCanciones.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }
    
    @Override
    public List<Reproducible> filtrarCanciones(Filtro f){
        DAO bd = DAO_FS.getBaseDatos();
        
        List<Reproducible> salida = new ArrayList<>();
        for (Integer r:canciones){
                salida.addAll(bd.getReproducible(r).filtrarCanciones(f));
        }
        return salida;
    }

    @Override
    public List<Cancion> getCanciones() {
        DAO bd = DAO_FS.getBaseDatos();
        
        List<Cancion> salida = new ArrayList<>();
        for (Integer r:canciones){
                salida.addAll(bd.getReproducible(r).getCanciones());
        }
        return salida;
    }
    
    @Override
    public int getCantCanciones(){
        return canciones.size();
    }
    
    @Override
    public boolean perteneceArtista(String a) {
        DAO bd = DAO_FS.getBaseDatos();
        
        if(!canciones.isEmpty()){
            for (Integer r:canciones){
                if (bd.getReproducible(r).perteneceArtista(a) == false)
                    return false;
            }
            return true;
        }
        else
            return false;
    }
    
    @Override
    public boolean perteneceGenero(String genero){
        DAO bd = DAO_FS.getBaseDatos();
        
        if(!canciones.isEmpty()){
            for (Integer r:canciones){
                if (bd.getReproducible(r).perteneceGenero(genero) == false)
                    return false;
            }
            return true;
        }
        else
            return false;
    }

    @Override
    public String play() {
        return nombre;
    }
    
    @Override
    public String toString(){
        
        return this.getNombre();
    }
    
}
