package Interfaz;

import Spotifoo.Cancion;
import Spotifoo.ConjuntoCanciones;
import java.awt.List;
import Spotifoo.DataManager.BaseDatos;
import Spotifoo.DataManager.GestorLibreria;
import Spotifoo.Filtro.And;
import Spotifoo.Filtro.FArtista;
import Spotifoo.Filtro.FGenero;
import Spotifoo.Filtro.FNombre;
import Spotifoo.Filtro.Filtro;
import Spotifoo.Filtro.Or;
import Spotifoo.Reproducible;
import Spotifoo.Usuario;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Map;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import static javax.swing.GroupLayout.Alignment.BASELINE;
import static javax.swing.GroupLayout.Alignment.LEADING;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;

/**
 *
 * @author nico
 */
public class ventanaUsuario extends javax.swing.JFrame {
    private JPanel tab;
    private JButton boton;
    private Container reproductorPanel;
    private JPanel buscadorPanel;
    private JPanel playlistsPanel;
    private JTextField textField;
    private JTextField textField2;
    private JLabel buscarLabel;
    private JRadioButton tituloRadioButton;
    private JRadioButton artistaRadioButton;
    private JRadioButton generoRadioButton;
    private JCheckBox compuestoCheckBox;
    private JRadioButton andRadioButton;
    private JRadioButton orRadioButton;
    private JButton botonBuscar;
    private JButton botonrestartFiltro;
    private List resultadoBusquedaList;
    private List playlistsList;
    
    private JButton playButton;
    private JButton previousButton;
    private JButton nextButton;
    private JButton addPlaylistButton;
    private final static String URL_PLAY = "img/botones/play.png";
    private final static String URL_PREVIOUS = "img/botones/previous.png";
    private final static String URL_NEXT = "img/botones/next.png";
    
    GestorLibreria gestorLibreria;
    Usuario usuario;
    
    Filtro filtro;
    Filtro filtroComp;
    Filtro filtroAnt;
    
    private JLabel img;
    public ventanaUsuario(Usuario u) {
        initComponents();
        configurarFrame();
        
        gestorLibreria = new GestorLibreria(BaseDatos.getBaseDatos());
        usuario = u;
        Filtro filtroAnt = null;
        
        generarLayoutExplorador();
        
        eventosVentana();
        eventosComponentes();
    }
    
    private void eventosComponentes(){
        botonBuscar.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonBuscarActionPerformed(evt);
            }
        });
        playButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                playButtonActionPerformed(evt);
            }

            private void playButtonActionPerformed(ActionEvent evt) {
                String cancionSeleccionada = resultadoBusquedaList.getSelectedItem();
                String[] cancion = cancionSeleccionada.split(" | ");
                setImg("img/canciones/"+cancion[0].replace(" ","_")+".jpg");
            }
        });
        botonrestartFiltro.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                filtroAnt = null;
            }
        });
    }
    
    private void eventosVentana(){
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                BaseDatos.getBaseDatos().guardarDatos();
                System.exit(0);
            }
        });
    }
    
    private void generarLayoutExplorador(){
        //Genera el componente de las pestañas
        JTabbedPane tabs = new JTabbedPane();
        //Lo añade en primer lugar en el frame
        this.add(tabs, 0);
        
        //Genera los componentes para insertar dentro de la pestaña principal
        //El componente donde van las playlist
        
        //**********************************************************
        //************INSERTAR EXPLORADOR DE BIBLIOTECA*************
        
        //Crea el divisor superior con la izqSplit y el explorador de la biblioteca 
        generarLayoutBuscador();
        generarLayoutPlaylist();
        JScrollPane playlists = new JScrollPane(playlistsPanel);
        JSplitPane topSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, playlists, buscadorPanel);
        topSplit.setDividerSize(0);
        topSplit.setEnabled(false);
        
        //Crea el divisor inferior, donde va el reproductor
        
        //Inserta en la pestaña Principal todos los componentes creados
        generarLayoutReproductor();
        JSplitPane tabPrincipal = new JSplitPane(JSplitPane.VERTICAL_SPLIT, topSplit, reproductorPanel);
        
        
        tabs.addTab("Explorar", tabPrincipal);
        
        //tab = new JTextArea();

        //JComponent panel2 = makeTextPanel("Panel #2");
        tabs.insertTab("Mi Bilbioteca", null, new JPanel(), null, 1);
        
        tabPrincipal.setDividerSize(0);
        tabPrincipal.setEnabled(false);
        tabPrincipal.setResizeWeight(0.9);
                
    }
    
    private void generarLayoutReproductor(){
        reproductorPanel = new JPanel();
        playButton = new JButton(new ImageIcon(URL_PLAY));
        previousButton = new JButton(new ImageIcon(URL_PREVIOUS));
        nextButton = new JButton(new ImageIcon(URL_NEXT));
        playButton.setBackground(Color.WHITE);
        previousButton.setBackground(Color.WHITE);
        nextButton.setBackground(Color.WHITE);
                
        JPanel botonesPanel = new JPanel();
        botonesPanel.setLayout(new BoxLayout(botonesPanel, BoxLayout.LINE_AXIS));
        botonesPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        botonesPanel.add(previousButton);
        botonesPanel.add(playButton);
        botonesPanel.add(nextButton);
        
        reproductorPanel.add(botonesPanel);
        
    }
    
    private void setImg(String url){
        img.setIcon (new ImageIcon(url)); 
    }
    
    private void generarLayoutBuscador(){
        buscadorPanel = new JPanel();
        textField = new JTextField();
        buscarLabel = new JLabel("Buscar:");;
        tituloRadioButton = new JRadioButton("Titulo");
        artistaRadioButton = new JRadioButton("Artista");
        generoRadioButton = new JRadioButton("Genero");
        compuestoCheckBox = new JCheckBox("Compuesto");
        andRadioButton = new JRadioButton("And");
        orRadioButton = new JRadioButton("Or");
        ButtonGroup editableGroup = new ButtonGroup();
        editableGroup.add(tituloRadioButton);
        editableGroup.add(artistaRadioButton);
        editableGroup.add(generoRadioButton);
        ButtonGroup editableGroup2 = new ButtonGroup();
        editableGroup2.add(andRadioButton);
        editableGroup2.add(orRadioButton);
        botonBuscar = new JButton("Buscar");
        botonrestartFiltro = new JButton("Restablecer");
        resultadoBusquedaList = new List();
        tituloRadioButton.setSelected(true);
        andRadioButton.setSelected(true);
        
        GroupLayout layout = new GroupLayout(buscadorPanel);
        buscadorPanel.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        
        layout.setHorizontalGroup(layout.createSequentialGroup()
            .addComponent(buscarLabel)
            .addGroup(layout.createParallelGroup(LEADING)
                .addComponent(textField)
                .addGroup(layout.createSequentialGroup()
                    .addComponent(tituloRadioButton)
                    .addComponent(artistaRadioButton)
                    .addComponent(generoRadioButton)
                    .addComponent(compuestoCheckBox)
                    .addComponent(andRadioButton)
                    .addComponent(orRadioButton))
                .addComponent(resultadoBusquedaList))
            .addGroup(layout.createParallelGroup(LEADING)
                .addComponent(botonBuscar)
                .addComponent(botonrestartFiltro))
        );
 
        layout.setVerticalGroup(layout.createSequentialGroup()
            .addGroup(layout.createParallelGroup(BASELINE)
                .addComponent(buscarLabel)
                .addComponent(textField)
                .addComponent(botonBuscar))
            .addGroup(layout.createParallelGroup(LEADING)
                .addComponent(tituloRadioButton)
                .addComponent(artistaRadioButton)
                .addComponent(generoRadioButton)
                .addComponent(compuestoCheckBox)
                .addComponent(andRadioButton)
                .addComponent(orRadioButton)
                .addComponent(botonrestartFiltro)) 
            .addGroup(layout.createParallelGroup(LEADING)
                .addComponent(resultadoBusquedaList))
        );
    }

    private void generarLayoutPlaylist(){
        playlistsPanel = new JPanel();
        playlistsPanel.setLayout(new BoxLayout(playlistsPanel, BoxLayout.Y_AXIS));
        
        JLabel playlistsLabel = new JLabel("Playlists");
        playlistsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        playlistsList = new List();
        addPlaylistButton = new JButton("Agregar playlist");
        addPlaylistButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        img = new JLabel();
        img.setAlignmentX(Component.CENTER_ALIGNMENT);
                
        playlistsPanel.add(playlistsLabel);
        playlistsPanel.add(playlistsList);
        playlistsPanel.add(addPlaylistButton);
        playlistsPanel.add(img);
        
        //Agregacion de las playlists del usuario en la lista
        for (Map.Entry<String, ConjuntoCanciones> entry : usuario.getPlaylist().entrySet()) {
            playlistsList.add(entry.getKey());
        }
    }
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 956, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 521, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    private void botonBuscarActionPerformed(java.awt.event.ActionEvent evt) {                                         
        String aBuscar = textField.getText();
        resultadoBusquedaList.clear();
        if(artistaRadioButton.isSelected()){
            filtro = new FArtista(aBuscar);
            if (compuestoCheckBox.isSelected() && filtroAnt != null){
                if (andRadioButton.isSelected())
                    filtro = new And(filtro,filtroAnt );
                else
                    filtro = new Or(filtro,filtroAnt );  
            }
            filtrarCanciones(filtro);
        }
        else if(generoRadioButton.isSelected() ){
            filtro = new FGenero(aBuscar);
            if (compuestoCheckBox.isSelected() && filtroAnt != null){
                if (andRadioButton.isSelected())
                    filtro = new And(filtro,filtroAnt );
                else
                    filtro = new Or(filtro,filtroAnt );  
            }
            filtrarCanciones(filtro);
        }
        else{
            filtro = new FNombre(aBuscar); 
            if (compuestoCheckBox.isSelected() && filtroAnt != null){
                if (andRadioButton.isSelected())
                    filtro = new And(filtro,filtroAnt );
                else
                    filtro = new Or(filtro,filtroAnt );  
            }
            filtrarCanciones(filtro);
        }
        filtroAnt = filtro;
    } 
    
    private void filtrarCanciones(Filtro f){
        for (Reproducible r : gestorLibreria.buscar(f)){
                Cancion c = (Cancion) r;
                resultadoBusquedaList.add(c.getNombre() +" | " + c.getArtista().getNombre() + " | "+ c.getGenero());
            }
    }

    private void configurarFrame(){
        this.setTitle("Spotifoo");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new BoxLayout(getContentPane(),BoxLayout.Y_AXIS));
        setVisible(true);
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables


}
