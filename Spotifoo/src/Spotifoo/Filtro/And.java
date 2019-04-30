/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Spotifoo.Filtro;

import Spotifoo.Reproducible;

/**
 *
 * @author Nico
 */
public class And extends FCompuesto{
    public And(Filtro f1, Filtro f2){
		this.f1 = f1;
		this.f2 = f2;
    }
    public boolean cumple (Reproducible r){
            return f1.cumple(r) && f2.cumple(r);
    }
}
