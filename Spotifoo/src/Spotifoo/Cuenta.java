package Spotifoo;

import java.io.Serializable;

/**
 *
 * @author nico
 */
public abstract class Cuenta implements Serializable, Conectable{
    String nombreUsuario;
    String contraseña;

    public Cuenta(String nombreUsuario,String contraseña){
        this.nombreUsuario=nombreUsuario;
        this.contraseña=contraseña;
    }
    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getUserName(){
        return this.nombreUsuario;
    }
    
    public void setUserName(String userName) {
        this.nombreUsuario = userName;
    }    
    
    @Override
    public boolean login(String contraseña){
        return this.contraseña.equals(contraseña);
        //IMPLEMENTAR DOS INTERFACES DIFERENTES SEGUN EL TIPO DE USUARIO
    }
    
    @Override
    public void logout(){
        //ACA PARA UN USUARIO COMUN SE DEBERIA ACTUALIZAR LA BASE DE DATOS CON
        //LOS CAMBIOS REALIZADOS A LAS LISTAS DE REPRODUCCION PERSONALES
    }
}
