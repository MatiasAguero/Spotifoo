package Spotifoo;

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

    private void addReprod(Reproducible r){
        DAO_FS.getBaseDatos().addReprod(r);
    }

    private void delUsuario(Usuario u){
        DAO_FS.getBaseDatos().delCuenta(u);
    }

    private void delReprod(Reproducible r){
        DAO_FS.getBaseDatos().delReprod(r);
    }

    @Override
    public JFrame getFrame() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
