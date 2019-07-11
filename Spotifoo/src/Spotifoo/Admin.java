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

    public void delUsuario(Usuario u){
        DAO_FS.getBaseDatos().delCuenta(u);
    }

    public void delReprod(Reproducible r){
        DAO_FS.getBaseDatos().delReprod(r);
    }

    @Override
    public JFrame getFrame() {
        return new ventanaAdministrador(this);
    }
    
}
