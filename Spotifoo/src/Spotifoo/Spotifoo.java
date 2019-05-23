
package Spotifoo;

import Interfaz.ventanaLogin;
import Spotifoo.DataManager.BaseDatos;
import Spotifoo.DataManager.FileHandler;
import Spotifoo.DataManager.GestorLibreria;
import Spotifoo.Filtro.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Spotifoo {

    public static void main(String[] args) {
        
        BaseDatos bd = BaseDatos.getBaseDatos();
        Usuario u = new Usuario("nico","1234");
        bd.addCuenta(u);
        
        ArrayList<ConjuntoCanciones> l = new ArrayList();
        
        Artista a = new Artista ("Daddy yankee","Reggeaton",l,2001);
        Cancion c = new Cancion("Despacito",a,"Reggeaton","2018");
        Cancion c2 = new Cancion("Con Calma",a,"Reggeaton","2019");
        Cancion c3 = new Cancion("Adictiva",a,"Reggeaton","2018");
        Cancion c4 = new Cancion("Baila Baila Baila",a,"Reggeaton","2018");
        
        
        u.createPlaylist("Regg");
        u.addListaReproducible(c,"Regg");
        u.addListaReproducible(c2,"Regg");
        u.addListaReproducible(c3,"Regg");
        u.addListaReproducible(c4,"Regg");
        
        bd.addReprod(c);
        bd.addReprod(c2);
        bd.addReprod(c3);
        bd.addReprod(c4);
        FNombre filtroXNombreCancion = new FNombre("Despacito");
        
        GestorLibreria gestor = new GestorLibreria(bd);
        List<Reproducible> resultado = gestor.buscar(filtroXNombreCancion);

        new ventanaLogin();
    }
    
}
