package Spotifoo.DataManager;
import Spotifoo.*;
import Spotifoo.Filtro.*;
import java.util.HashMap;
import java.util.List;
/**
 *
 * @author matias
 */
public abstract class DAO {
    
    protected HashMap<String, Cuenta> cuentas;
    protected HashMap<Integer, Reproducible> libreria;
    protected HashMap<Integer, Artista> artistas;
    
    protected abstract void setCuentas();
    
    protected abstract void setLibreria();
    
    protected abstract void setArtistas();
    
    public abstract List<Reproducible> buscar(Filtro f);
    
    public abstract void addReprod(Reproducible r);
    
    public abstract void delReprod(Reproducible r);
    
    public abstract void delCuenta(Cuenta c);
    
    public abstract void addCuenta(Cuenta c);
    
    public abstract void addArtista(Artista a);
    
    public abstract void delArtista(Artista a);
    
    public Reproducible getReproducible(int id){
        return libreria.get(id);
    }
 
    public Artista getArtista(int id){
        return artistas.get(id);
    }
    
    public HashMap<String, Cuenta> getCuentas(){
        return cuentas;
    }
    
    public HashMap<Integer, Reproducible> getLibreria(){
        return libreria;
    }
    
    public HashMap<Integer, Artista> getArtista(){
        return artistas;
    }
}
