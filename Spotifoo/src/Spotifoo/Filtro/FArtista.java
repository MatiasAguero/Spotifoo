/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Spotifoo.Filtro;

import Spotifoo.Artista;
import Spotifoo.Reproducible;

/**
 *
 * @author nico
 */
public class FArtista extends FSimple {
    String artista;
    
    public FArtista(String artista){
        this.artista = artista;
    }
    
    @Override
    public boolean cumple(Reproducible r) {
        return r.perteneceArtista(artista);
    }
}
