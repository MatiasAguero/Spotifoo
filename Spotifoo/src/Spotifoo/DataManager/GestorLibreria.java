/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Spotifoo.DataManager;
import Spotifoo.*;
import Spotifoo.Filtro.*;
import java.util.List;
/**
 *
 * @author matias
 */
public class GestorLibreria {
    
    BaseDatos bd; 
        
    public GestorLibreria(){
        this.bd = FileHandler.loadDB();
    }
    
    public GestorLibreria(BaseDatos bd){
        this.bd=bd;
    }
    
    public List<Reproducible> buscar(Filtro f){
        return bd.getReproducible(f);
    }
    
    public void addReprod(Reproducible r){
        bd.addReprod(r);
    }
    
    public void delReprod(Reproducible r){
        bd.delReprod(r);
    }
    
    public void delCuenta(Cuenta c){
        bd.delCuenta(c);
    }
    
    public void addCuenta(Cuenta c){
        bd.addCuenta(c);
    }
    
}
