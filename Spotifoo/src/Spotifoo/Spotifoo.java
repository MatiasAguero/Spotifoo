
package Spotifoo;

import Interfaz.ventanaLogin;
import Spotifoo.DataManager.BaseDatos;
import Spotifoo.DataManager.FileHandler;
import Spotifoo.DataManager.GestorLibreria;
import Spotifoo.Filtro.*;
import java.util.ArrayList;
import java.util.List;

public class Spotifoo {

    public static void main(String[] args) {
        
        BaseDatos bd = FileHandler.loadDB();
        
        ArrayList<ConjuntoCanciones> l = new ArrayList();
        
        Artista a = new Artista ("Daddy yankee","Reggeaton",l,2001);
        Cancion c = new Cancion("Despacito",a,"Reggeaton","2018");
        
        bd.addReprod(c);
        FNombre filtroXNombreCancion = new FNombre("Despacito");
        
        GestorLibreria gestor = new GestorLibreria(bd);
        List<Reproducible> resultado = gestor.buscar(filtroXNombreCancion);

        new ventanaLogin(bd).setVisible(true);
        bd.guardarDatos();
    }
    
}
