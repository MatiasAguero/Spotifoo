package Spotifoo;

import Interfaz.ventanaUsuario;
import Spotifoo.DataManager.DAO;
import Spotifoo.DataManager.DAO_FS;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JFrame;

/**
 *
 * @author nico
 */
public class Usuario extends Cuenta{
    
    List<Reproducible> bibliotecaPersonal;
    HashMap<String,ConjuntoCanciones> playlist;
    
    public Usuario(String nombreUsuario, String contraseña) {
        super(nombreUsuario, contraseña);
        bibliotecaPersonal = new ArrayList();
        playlist = new HashMap();
    }
    
    private void updateDB(){
        DAO bd = DAO_FS.getBaseDatos();
        bd.addCuenta(this);
    }
    
    public List<Reproducible> getBiblioteca(){
        return bibliotecaPersonal;
    }

    public void addElem(Reproducible r){
        this.bibliotecaPersonal.add(r);
        updateDB();
    }
    
    public void delPlaylist(String p){
        playlist.remove(p);
        updateDB();
    }
    
    public void delElem(Reproducible r){
        this.bibliotecaPersonal.remove(r);
        updateDB();
    }
    
    public void createPlaylist(String nombrePlaylist){
        playlist.put(nombrePlaylist, new ConjuntoCanciones(nombrePlaylist));
        updateDB();
    }
    
    public void addListaReproducible(Reproducible r,String nombrePlayList){
         for (Map.Entry<String,ConjuntoCanciones> entry : playlist.entrySet()) {
            if (entry.getKey().equals(nombrePlayList))
                entry.getValue().agregar(r);
        }
        updateDB();
    }

    public Reproducible getCancion(String nombre){
        
        for(Reproducible r : bibliotecaPersonal)
            if(r.getNombre().equals(nombre))
                return r;
        
        return null;
    }
    
    public Reproducible getPlaylist(String nombre){
        
        for (Map.Entry<String,ConjuntoCanciones> entry : playlist.entrySet()) {
            if (entry.getKey().equals(nombre))
                return entry.getValue();
        }
        return null;
    }
    
    public HashMap<String, ConjuntoCanciones> getPlaylist() {
        return playlist;
    }  

    @Override
    public JFrame getFrame() {
        return new ventanaUsuario(this);
    }
}
