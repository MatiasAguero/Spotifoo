/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Spotifoo.DataManager;

import Spotifoo.*;
import java.util.HashMap;

/**
 *
 * @author matias
 */
public class BaseDatos {
    
    HashMap<String, Cuenta> cuentas;
    HashMap<Integer, Reproducible> libreria;

    public BaseDatos() {
        this.cuentas = new HashMap();
        this.libreria = new HashMap();
    }

    public BaseDatos(HashMap<String, Cuenta> users, HashMap<Integer, Reproducible> libreria) {
        this.cuentas = users;
        this.libreria = libreria;
    }
    
    //Realiza la accion de login, comprueba que el usuario exista y que las contraseñas sean las mismas
    public boolean connect(String userName, String contraseña){
        
        Cuenta usuario;
        
        if(cuentas.containsKey(userName)){
            usuario = cuentas.get(userName);
            return usuario.login(contraseña);
        }
        else
            return false;
    }

    public void guardarDatos(){
        FileHandler.saveDB(this);
    }

    public void addCuenta(Cuenta c){
        if(!cuentas.containsKey(c.getUserName()))
            cuentas.put(c.getUserName(), c);
    }
    
    public void delCuenta(Cuenta c){
        if(cuentas.containsKey(c.getUserName()))
            cuentas.remove(c.getUserName());
    }

    public void addReprod(Reproducible r){
        if(!libreria.containsKey(r.getId()))
            libreria.put(r.getId(), r);
    }

    public void delReprod(Reproducible r){
        if(libreria.containsKey(r.getId()))
            libreria.remove(r.getId());
    }

    public Reproducible getReproducible(int id){
        return libreria.get(id);
    }
    
    public HashMap<Integer, Reproducible> getLib(){
        return this.libreria;
    }
    
    protected HashMap<String, Cuenta> getCuentas(){
        return this.cuentas;
    }
}
