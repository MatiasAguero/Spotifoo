
package Spotifoo;

import Interfaz.ventanaLogin;
import Spotifoo.DataManager.DAO_FS;
import java.util.ArrayList;
import java.util.Map;

public class Spotifoo {

    public static void main(String[] args) {
        
        DAO_FS bd = DAO_FS.getBaseDatos();
        
        Admin adm = new Admin("admin","1234");
        bd.addCuenta(adm);
        
        Usuario u = new Usuario("nico","1234");
        Usuario m = new Usuario("matias", "1234");
        bd.addCuenta(u);
        bd.addCuenta(m);
        
        ArrayList<ConjuntoCanciones> l = new ArrayList();
        Artista a = new Artista ("Daddy yankee","Reggeaton",l,2001);
        Artista a2 = new Artista ("Iron Maiden","Heavy Metal",l,1978);
        Artista a3 = new Artista ("Mastodon","Heavy Metal",l,1999);
        bd.addArtista(a);
        bd.addArtista(a2);
        bd.addArtista(a3);
        
        Cancion c = new Cancion("Despacito",a,"Reggeaton","2018");
        Cancion c2 = new Cancion("Con Calma",a,"Reggeaton","2019");
        Cancion c3 = new Cancion("Adictiva",a,"Reggeaton","2018");
        Cancion c4 = new Cancion("Baila Baila Baila",a,"Reggeaton","2018");
        Cancion c5 = new Cancion("Date la vuelta",a,"Reggeaton","2019");
        Cancion c6 = new Cancion("Powerslave",a2,"Heavy Metal","1985");
        Cancion c7 = new Cancion("Aces High",a2,"Heavy Metal","1985");
        Cancion c8 = new Cancion("Crystal Skull",a3,"Heavy Metal","2010");
        
        bd.addReprod(c);
        bd.addReprod(c2);
        bd.addReprod(c3);
        bd.addReprod(c4);
        bd.addReprod(c5);
        bd.addReprod(c6);
        bd.addReprod(c7);
        bd.addReprod(c8);
        
        ConjuntoCanciones album = new ConjuntoCanciones("Album de prueba");
        album.agregar(c);
        album.agregar(c2);
        album.setAlbum();
        bd.addReprod(album);
        
        album = new ConjuntoCanciones("Powerslave");
        album.agregar(c6);
        album.agregar(c7);
        album.setAlbum();
        bd.addReprod(album);
        
        u.createPlaylist("Regg");
        u.addListaReproducible(c,"Regg");
        u.addListaReproducible(c2,"Regg");
        u.addListaReproducible(c3,"Regg");
        u.addListaReproducible(c4,"Regg");
        
        new ventanaLogin();
    }
    
}
