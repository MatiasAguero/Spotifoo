package Spotifoo;

import Spotifoo.DataManager.FileHandler;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nico
 */
public class Usuario extends Cuenta{
    
    List<Reproducible> lstRepr;
    
    public Usuario(String nombreUsuario, String contraseña) {
        super(nombreUsuario, contraseña);
        lstRepr = new ArrayList();
    }
    
    public List<Reproducible> getLista(){
        return lstRepr;
    }

    public void addElem(Reproducible r){
        this.lstRepr.add(r);
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
}
