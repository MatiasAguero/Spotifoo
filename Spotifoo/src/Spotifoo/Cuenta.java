package Spotifoo;

/**
 *
 * @author nico
 */
public abstract class Cuenta {
    String nombreUsuario;
    String contraseña;

    public Cuenta(String nombreUsuario,String contraseña){
        this.nombreUsuario=nombreUsuario;
        this.contraseña=contraseña;
    }
    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public void setUserName(String userName) {
        this.nombreUsuario = nombreUsuario;
    }
    
}
