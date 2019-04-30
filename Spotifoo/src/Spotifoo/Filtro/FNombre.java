/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Spotifoo.Filtro;

import Spotifoo.Reproducible;

/**
 *
 * @author Grachi-Nestitor
 */
public class FNombre extends FSimple {
    
    String nombre;
    public FNombre(String nombre){
        this.nombre=nombre;
    }
    @Override
    public boolean cumple(Reproducible r) {
        return r.getNombre().equals(nombre); 
    }
    
}
