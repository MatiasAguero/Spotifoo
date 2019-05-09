package Spotifoo;

import Interfaz.ventanaUsuario;
import Spotifoo.DataManager.FileHandler;
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
    
    public List<Reproducible> getBiblioteca(){
        return bibliotecaPersonal;
    }

    public void addElem(Reproducible r){
        this.bibliotecaPersonal.add(r);
    }
    
    public void addListaReproducible(Reproducible r,String nombrePlayList){
         for (Map.Entry<String,ConjuntoCanciones> entry : playlist.entrySet()) {
            if (entry.getKey().equals(nombrePlayList))
                    entry.getValue().agregar(r);
        }
    }

    public void loadLocal(String path){
        this.addElem(FileHandler.loadFile(path));
    }

    public void saveServer(String path){
        //TODO....
    }

    public void descargarRepr(Reproducible r){
        //TODO....
    }

    @Override
    public JFrame getFrame() {
        return new ventanaUsuario();
    }
}
