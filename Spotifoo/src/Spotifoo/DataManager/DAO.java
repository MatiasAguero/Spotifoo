package Spotifoo.DataManager;
import Spotifoo.*;
import Spotifoo.Filtro.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 *
 * @author matias
 */
public abstract class DAO {
    
    protected HashMap<String, Cuenta> cuentas;
    protected HashMap<Integer, Reproducible> libreria;
    protected HashMap<Integer, Artista> artistas;
    
    //Recupera los datos de las cuentas y los carga en memoria
    protected abstract void setCuentas();
    
    //Recupera los datos de la libreria y los carga en memoria
    protected abstract void setLibreria();
    
    //Recupera los datos de los artistas y los carga en memoria
    protected abstract void setArtistas();
    
    //Actualiza los datos de la libreria fisicamente
    protected abstract void saveLib();
    
    //Actualiza los datos de las cuentas fisicamente
    protected abstract void saveUsers();
    
    //Actualiza los datos de los artistas fisicamente
    protected abstract void saveArt();
    
    public List<Reproducible> buscar(Filtro f){
        List<Reproducible> salida = new ArrayList<>();
        for (Map.Entry<Integer, Reproducible> entry : libreria.entrySet()) {
            if (f.cumple(entry.getValue()))
                salida.add(entry.getValue());
        }
        return salida;
    }
    
    public void addReprod(Reproducible r){
        libreria.put(r.getId(), r);
        this.saveLib();
    }
    
    public void delReprod(Reproducible r){
        libreria.remove(r.getId());
        this.saveLib();
    }
    
    public void delCuenta(Cuenta c){
        if(cuentas.containsKey(c.getUserName())){
            cuentas.remove(c.getUserName());
            this.saveUsers();
        }
    }
    
    public boolean existsCuenta(Cuenta c){
        if(cuentas.containsKey(c.getUserName()))
            return true;
        return false;
    }
    
    public void addCuenta(Cuenta c){
        
        cuentas.put(c.getUserName(), c);
        this.saveUsers();

    }
    
    public void addArtista(Artista a){
        artistas.put(a.getId(), a);
        this.saveArt();
    }
    
    public void delArtista(Artista a){
        artistas.remove(a.getId());
        this.saveArt();
    }
    
    public Reproducible getReproducible(int id){
        return libreria.get(id);
    }
 
    public Artista getArtista(int id){
        return artistas.get(id);
    }
    
    public Reproducible getReproducible(String nombre){
        for (Map.Entry<Integer, Reproducible> entry : libreria.entrySet()) {
                if (entry.getValue().getNombre().equals(nombre)){
                    System.out.println(entry.getValue().getNombre());
                    return entry.getValue();
                }
            }
        return null;
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
    
    public Artista getArtistaNombre(String nombre){
        for (Map.Entry<Integer, Artista> entry : artistas.entrySet()) {
                if (entry.getValue().getNombre().equals(nombre))
                    return entry.getValue();
            }
        return null;
    }
    
    public void delReprodId(Integer id){
        System.out.println(id);
        if (libreria.containsKey(id)){
            libreria.remove(id);
            this.saveLib();
        }
            
    }
    
    public void delUsrId(String id){
        System.out.println(id);
        if (cuentas.containsKey(id)){
            cuentas.remove(id);
            this.saveUsers();
        }
            
    }
}
