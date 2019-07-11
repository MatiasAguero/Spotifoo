package Spotifoo.DataManager;

import Spotifoo.Artista;
import Spotifoo.Cancion;
import Spotifoo.Filtro.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import Spotifoo.Cuenta;
import Spotifoo.Reproducible;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.nio.file.Files;
import javax.swing.JOptionPane;

/**
 *
 * @author matias
 */
public class DAO_FS extends DAO{
    
    public static final String FS = System.getProperty("file.separator");
    public static final String PATH_APP_DATA = "."+FS+"resources";
    public static final String PATH_APP_BD_USERS = PATH_APP_DATA+"/bd/users";
    public static final String PATH_APP_BD_LIB = PATH_APP_DATA+"/bd/lib";
    public static final String PATH_APP_BD_ARTISTAS = PATH_APP_DATA+"/bd/artists";
    
    protected static DAO_FS instanciaDaoFs;
    
    private DAO_FS() {
        this.cuentas = new HashMap();
        this.libreria = new HashMap();
        this.artistas = new HashMap();
    }

    public static DAO_FS getBaseDatos(){
        if (instanciaDaoFs == null){
            instanciaDaoFs = new DAO_FS();
            instanciaDaoFs.setCuentas();
            instanciaDaoFs.setLibreria();
            instanciaDaoFs.setArtistas();
            return instanciaDaoFs;
        }
        return instanciaDaoFs;
    }

    @Override
    protected void setCuentas() {
        
        ObjectInputStream ois;
        FileInputStream fis;
        File fUsr = new File(PATH_APP_BD_USERS);

        try {
            
            if(fUsr.exists()){
                //Carga el archivo de los usuarios
                fis = new FileInputStream(PATH_APP_BD_USERS);
                ois = new ObjectInputStream(fis);
                this.cuentas = (HashMap<String, Cuenta>) ois.readObject();
                ois.close();
            }
            
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(DAO_FS.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @Override
    protected void setLibreria() {
        
        ObjectInputStream ois;
        FileInputStream fis;
        File fLib = new File(PATH_APP_BD_LIB);

        try {
            
            if(fLib.exists()){
                //Carga el archivo de la libreria
                fis = new FileInputStream(PATH_APP_BD_LIB);
                ois = new ObjectInputStream(fis);
                this.libreria = (HashMap<Integer, Reproducible>) ois.readObject();     
                ois.close();
            }
                       
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(DAO_FS.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    protected void setArtistas() {

        ObjectInputStream ois;
        FileInputStream fis;
        File fLib = new File(PATH_APP_BD_ARTISTAS);

        try {
            
            if(fLib.exists()){
                //Carga el archivo de artistas
                fis = new FileInputStream(PATH_APP_BD_ARTISTAS);
                ois = new ObjectInputStream(fis);
                this.artistas = (HashMap<Integer, Artista>) ois.readObject();     
                ois.close();
            }
                       
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(DAO_FS.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //Carga una cancion que esta en el sistema de archivos local y se lo 
    //construye al usuario para que lo pueda añadir a su biblioteca personal
    public Reproducible loadFile(File f){

        BufferedReader br;
        Cancion c;
        String linea;
        String nombre = "";
        String fecha = "";
        String genero = "";
        String artista = "";
        String album = "";
        int cont = 0;
        
        /*Formato:
            Nombre
            idArtista
            idAlbum
            fecha
            genero
        */
        
        try {
            //Carga linea por linea cada atributo
            br = new BufferedReader(new FileReader(f));
            while((linea = br.readLine()) != null){
                switch(cont) {
                    case 0: nombre = linea;
                            break;
                    case 1: artista = linea;
                            break;
                    case 2: album = linea;
                            break;
                    case 3: fecha = linea;
                            break;
                    case 4: genero = linea;
                            break;
                }
                cont++;
            }
            br.close();
            //Genera la cancion
            c = new Cancion(nombre, genero, fecha);
            
            //En caso de que el nombre del artista coincida con uno ya creado
            //asigna el valor de id de dicho artista a la cancion
            for (Map.Entry<Integer, Artista> entry : artistas.entrySet()) {
                if(entry.getValue().getNombre().equals(artista))
                    c.setArtista(entry.getValue());
            }      
            
            //En caso de que el nombre del album coincida con uno ya creado
            //asigna el valor de id de dicho album a la cancion
            for (Map.Entry<Integer, Reproducible> entry : libreria.entrySet()) {
                if(entry.getValue().getNombre().equals(album))
                    c.setAlbum(entry.getValue().getId());
            }
            
            return c;
            
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog (null, "Archivo no encontrado", "Error", JOptionPane.ERROR_MESSAGE);
            
        } catch (IOException ex) {
            Logger.getLogger(DAO_FS.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }    

    //Guarda un arcivo de musica de manera local en la direccion pasada por parametro
    public static void saveLocal(File f, Reproducible r) {

        BufferedWriter bw;
        
        /*Formato:
            Nombre
            idArtista
            idAlbum
            fecha
            genero
        */
        
        try {
            bw = new BufferedWriter(new FileWriter(f));
            
            bw.write(((Cancion) r).getNombre());
            bw.write(((Cancion) r).getArtista().getId());
            bw.write(((Cancion) r).getAlbum().getId());
            bw.write(((Cancion) r).getFecha());
            bw.write(((Cancion) r).getGenero());
            bw.close();
            
        }  catch (IOException ex) {
            Logger.getLogger(DAO_FS.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
    
    private void saveLib(){
        
        ObjectOutputStream oos;
        FileOutputStream fos;

        try {
            Files.createDirectories(new File(PATH_APP_DATA+"/bd").toPath());
            //Trunca el archivo original para escribir los datos nuevos
            new PrintWriter(PATH_APP_BD_LIB).close();
            
            //Guardado del archivo de libreria
            fos = new FileOutputStream(PATH_APP_BD_LIB);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(this.libreria);     
            oos.close();
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(DAO_FS.class.getName()).log(Level.SEVERE, null, ex);
            
        } catch (IOException ex) {
            Logger.getLogger(DAO_FS.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    private void saveUsers(){
        ObjectOutputStream oos;
        FileOutputStream fos;

        try {
            Files.createDirectories(new File(PATH_APP_DATA+"/bd").toPath());
            //Trunca el archivo original para escribir los datos nuevos
            new PrintWriter(PATH_APP_BD_USERS).close();
            
            //Guardado del archivo de usuarios
            fos = new FileOutputStream(PATH_APP_BD_USERS);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(this.cuentas);
            oos.close();
            
        } catch (FileNotFoundException ex) {
                Logger.getLogger(DAO_FS.class.getName()).log(Level.SEVERE, null, ex);
            
        } catch (IOException ex) {
            Logger.getLogger(DAO_FS.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    
    }
    
    private void saveArt(){
        ObjectOutputStream oos;
        FileOutputStream fos;

        try {
            Files.createDirectories(new File(PATH_APP_DATA+"/bd").toPath());
            //Trunca el archivo original para escribir los datos nuevos
            new PrintWriter(PATH_APP_BD_ARTISTAS).close();
            
            //Guardado del archivo de artistas
            fos = new FileOutputStream(PATH_APP_BD_ARTISTAS);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(this.artistas);
            oos.close();
            
        } catch (FileNotFoundException ex) {
                Logger.getLogger(DAO_FS.class.getName()).log(Level.SEVERE, null, ex);
            
        } catch (IOException ex) {
            Logger.getLogger(DAO_FS.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    
    }
    
    //Realiza la accion de login, comprueba que el usuario exista y que las contraseñas sean las mismas
    private boolean connect(String userName, String contraseña){
        
        Cuenta usuario;
        
        if(cuentas.containsKey(userName)){
            usuario = cuentas.get(userName);
            return usuario.login(contraseña);
        }
        else
            return false;
    }
    
    public Cuenta getCuenta(String userName, String contraseña){
        if (connect(userName,contraseña))
            return cuentas.get(userName);
        return null;
    }

    @Override
    public void addCuenta(Cuenta c){
        if(!cuentas.containsKey(c.getUserName())){
            cuentas.put(c.getUserName(), c);
            this.saveUsers();
        }
    }
    
    @Override
    public void delCuenta(Cuenta c){
        if(cuentas.containsKey(c.getUserName())){
            cuentas.remove(c.getUserName());
            this.saveUsers();
        }
    }

    @Override
    public void addReprod(Reproducible r){
        
        System.out.println(r.getId()+" "+r.getNombre());
        libreria.put(r.getId(), r);
        this.saveLib();

    }

    @Override
    public void delReprod(Reproducible r){
        
        libreria.remove(r.getId());
        this.saveLib();

    }
    
    @Override
    public List<Reproducible> buscar(Filtro f){
        List<Reproducible> salida = new ArrayList<>();
        for (Map.Entry<Integer, Reproducible> entry : libreria.entrySet()) {
            if (f.cumple(entry.getValue()))
                salida.add(entry.getValue());
        }
        return salida;
    }

    @Override
    public void addArtista(Artista a) {
        artistas.put(a.getId(), a);
        this.saveArt();
    }

    @Override
    public void delArtista(Artista a) {
        artistas.remove(a.getId());
        this.saveArt();
    }

}
