package Spotifoo;

import Interfaz.ventanaAdministrador;
import Spotifoo.DataManager.DAO_FS;
import javax.swing.JFrame;

/**
 *
 * @author matias
 */
public class Admin extends Cuenta{

    public Admin(String nombreUsuario, String contraseña) {
        super(nombreUsuario, contraseña);
    }

    public void addReprod(Reproducible r){
        DAO_FS.getBaseDatos().addReprod(r);
        
    }
    
    public void delUsrId(String id){
        DAO_FS.getBaseDatos().delUsrId(id);
    }
    
    public void delReprodId(int id){
        DAO_FS.getBaseDatos().delReprodId(id);
    }
    
    public void addArtista(Artista artista){
        DAO_FS.getBaseDatos().addArtista(artista);
    }

    @Override
    public JFrame getFrame() {
        return new ventanaAdministrador(this);
    }
    
}
