package Spotifoo.Filtro;

import Spotifoo.Reproducible;

/**
 *
 * @author nico
 */
public class Or extends FCompuesto{
	public Or(Filtro f1, Filtro f2){
		this.f1 =f1;
		this.f2=f2;
	}
	public boolean cumple (Reproducible r){
		return f1.cumple(r)|| f2.cumple(r);
	}
}