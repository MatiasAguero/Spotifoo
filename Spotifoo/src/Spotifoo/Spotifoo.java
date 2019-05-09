
package Spotifoo;

import Interfaz.ventanaLogin;
import Spotifoo.DataManager.BaseDatos;
import Spotifoo.DataManager.GestorLibreria;
import Spotifoo.Filtro.*;
import java.util.List;

public class Spotifoo {

    public static void main(String[] args) {
        Usuario u1 = new Usuario("nico","1234");
        Usuario u2 = new Usuario("matias","1234");
        
        BaseDatos bd = new BaseDatos();
        bd.addCuenta(u1);
        bd.addCuenta(u2);
        
        Artista a = new Artista ("Dadday yanke","Reggeaton",null,2001);
        Cancion c = new Cancion("Despacito",a,"Reggeaton","2018");
        
        bd.addReprod(c);
        FNombre filtroXNombreCancion = new FNombre("Despacito");
        
        GestorLibreria gestor = new GestorLibreria(bd);
        List<Reproducible> resultado = gestor.buscar(filtroXNombreCancion);
        
        new ventanaLogin(bd).setVisible(true);
        //bd.guardarDatos();
    }
    
}
