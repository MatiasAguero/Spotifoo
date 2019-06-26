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
    
    protected abstract void setCuentas();
    
    protected abstract void setLibreria();
    
    public abstract List<Reproducible> buscar(Filtro f);
    
    public abstract void addReprod(Reproducible r);
    
    public abstract void delReprod(Reproducible r);
    
    public abstract Reproducible getReproducible(int id);
    
    public abstract void delCuenta(Cuenta c);
    
    public abstract void addCuenta(Cuenta c);
 
    public HashMap<String, Cuenta> getCuentas(){
        return cuentas;
    }
    
    public HashMap<Integer, Reproducible> getLibreria(){
        return libreria;
    }
}
