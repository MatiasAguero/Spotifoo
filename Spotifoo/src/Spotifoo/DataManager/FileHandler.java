package Spotifoo.DataManager;

/**
 *
 * @author matias
 */
import Spotifoo.Cuenta;
import Spotifoo.Reproducible;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.HashMap;

public class FileHandler {

    public static final String FS = System.getProperty("file.Separator");
    public static final String PATH_APP_DATA = FS+"resources"+FS;
    public static final String PATH_APP_BD_USERS = FS+"resources"+FS+"bd"+FS+"users";
    public static final String PATH_APP_BD_LIB = FS+"resources"+FS+"bd"+FS+"lib";
    
    //Carga un archivo de musica guardado de manera local en la direccion indicada por parametro
    public static Reproducible loadFile(String path){


        ObjectInputStream ois;
        FileInputStream fis;
        Reproducible r;

        try {
            fis = new FileInputStream(path);
            ois = new ObjectInputStream(fis);
            r = (Reproducible) ois.readObject();
            ois.close();
            return r;
        } catch (ClassNotFoundException | FileNotFoundException ex) {
                Logger.getLogger(FileHandler.class.getName()).log(Level.SEVERE, null, ex);
            
        } catch (IOException ex) {
            Logger.getLogger(FileHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    //Guarda un arcivo de musica de manera local en la direccion pasada por parametro
    public static void saveLocal(String path, Reproducible r) {

        ObjectOutputStream oos;
        FileOutputStream fos;
        
        try {
            fos = new FileOutputStream(path);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(r);            
            oos.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FileHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FileHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }

    /*Guarda una cancion en un servidor remoto ***GOOGLE DRIVE***
    public static void saveToServer(Reproducible, Server) {
    }

    Carga una cancion desde un servidor remoto ***GOOGLE DRIVE***
    public static Reproducible getFromServer(Server) {
    }*/

    //Guarda todos los datos de la base de datos en archivos
    public static void saveDB(BaseDatos bd){
        
        ObjectOutputStream oos;
        FileOutputStream fos;

        try {
            
            //Trunca los archivos originales para escribir los datos nuevos
            new PrintWriter(PATH_APP_BD_LIB).close();
            new PrintWriter(PATH_APP_BD_USERS).close();
            
            //Guardado del archivo de libreria
            fos = new FileOutputStream(PATH_APP_BD_LIB);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(bd.getLib());     
            oos.close();
            
            //Guardado del archivo de usuarios
            fos = new FileOutputStream(PATH_APP_BD_USERS);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(bd.getCuentas());
            oos.close();
            
        } catch (FileNotFoundException ex) {
                Logger.getLogger(FileHandler.class.getName()).log(Level.SEVERE, null, ex);
            
        } catch (IOException ex) {
            Logger.getLogger(FileHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    //Carga los datos de la base de datos para su uso en la app
    public static BaseDatos loadDB(){
        
        ObjectInputStream ois;
        FileInputStream fis;
        HashMap<Integer, Reproducible> r;
        HashMap<String, Cuenta> u;

        try {
            
            //Carga el archivo de la libreria
            fis = new FileInputStream(PATH_APP_BD_LIB);
            ois = new ObjectInputStream(fis);
            r = (HashMap<Integer, Reproducible>) ois.readObject();     
            ois.close();
            
            //Carga el archivo de los usuarios
            fis = new FileInputStream(PATH_APP_BD_USERS);
            ois = new ObjectInputStream(fis);
            u = (HashMap<String, Cuenta>) ois.readObject();
            ois.close();
            
            //Genera la base de datos
            BaseDatos bd = new BaseDatos(u, r);
            return bd;
            
        } catch (ClassNotFoundException | FileNotFoundException ex) {
                Logger.getLogger(FileHandler.class.getName()).log(Level.SEVERE, null, ex);
            
        } catch (IOException ex) {
            Logger.getLogger(FileHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
        
    }

}
