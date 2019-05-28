package Spotifoo.Filtro;

import Spotifoo.Reproducible;

/**
 *
 * @author nico
 */
public class FGenero extends FSimple {
    String genero;
    
    public FGenero(String genero){
        this.genero = genero;
    }

    @Override
    public boolean cumple(Reproducible r) {
        return r.perteneceGenero(genero);
    }
    
    
}
