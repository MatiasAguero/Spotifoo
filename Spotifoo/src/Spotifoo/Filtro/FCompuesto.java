package Spotifoo.Filtro;

import Spotifoo.Reproducible;

/**
 *
 * @author nico
 */
public abstract class FCompuesto implements Filtro {
    Filtro f1;
    Filtro f2;
    public abstract boolean cumple(Reproducible r);
}
