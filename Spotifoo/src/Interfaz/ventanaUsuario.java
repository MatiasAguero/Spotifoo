package Interfaz;

import Spotifoo.ConjuntoCanciones;
import Spotifoo.Cancion;
import Spotifoo.DataManager.DAO;
import java.awt.List;
import Spotifoo.DataManager.DAO_FS;
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
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Map;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import static javax.swing.GroupLayout.Alignment.BASELINE;
import static javax.swing.GroupLayout.Alignment.LEADING;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JCheckBox;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.ListSelectionModel;
/**
 *
 * @author nico
 */
public class ventanaUsuario extends javax.swing.JFrame {
    private JPanel tab;
    private JButton boton;
    private Container reproductorPanel;
    private JPanel buscadorPanel;
    private JPanel bibliotecaPanel;
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
    private List resultadoBusquedaList;
    private List playlistsList;
    private JList listaPlaylist;
    
    private JButton botonrestartFiltro;
    private JButton playButton;
    private JButton previousButton;
    private JButton nextButton;
    private JButton addPlaylistButton;
    private final static String URL_PLAY = "img/botones/play.png";
    private final static String URL_PREVIOUS = "img/botones/previous.png";
    private final static String URL_NEXT = "img/botones/next.png";
    
    DAO db;
    Usuario usuario;

    Filtro filtro;
    Filtro filtroComp;
    Filtro filtroAnt;

    private JLabel img;
    private JScrollPane listScroller;
    private JButton botonAdd;
    private JButton botonEdit;
    private JButton botonDel;
    private JPanel playlistOptionPanel;
    private Component listaCanciones;
    private JPanel SongsOptionPanel;
    private JButton botonAdd2;
    private JButton botonDownload;
    private JScrollPane CancionesScroller;
    
    
    public ventanaUsuario(Usuario u) {
        initComponents();
        configurarFrame();
        
        db = DAO_FS.getBaseDatos();
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
//            @Override
//            public void windowClosing(WindowEvent e) {
//                DAO_FS.getBaseDatos().guardarDatos();
//                System.exit(0);
//            }
        });
    }
    
    private void generarLayoutExplorador(){
        //Genera el componente de las pestañas
        JTabbedPane tabs = new JTabbedPane();
        //Lo añade en primer lugar en el frame
        this.add(tabs, 0);
        
        //Genera los componentes para insertar dentro de la pestaña principal
        //Crea el divisor superior con la playlist y el explorador de la biblioteca
        
        //Genera el explorador de biblioteca
        generarLayoutBuscador();
        //El componente donde van las playlist
        generarLayoutPlaylist();
        JScrollPane playlists = new JScrollPane(playlistsPanel);
        JSplitPane topSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, playlists, buscadorPanel);
        topSplit.setDividerSize(0);
        topSplit.setEnabled(false);
        
        //Crea el divisor inferior, donde va el reproductor
        generarLayoutReproductor();
        
        //Inserta en la pestaña Principal todos los componentes creados
        JSplitPane tabPrincipal = new JSplitPane(JSplitPane.VERTICAL_SPLIT, topSplit, reproductorPanel);
        
        
        tabs.addTab("Explorar", tabPrincipal);
        
        //tab = new JTextArea();

        
        this.generarLayoutBiblioteca();
        tabs.insertTab("Mi Biblioteca", null, bibliotecaPanel, null, 1);
        
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
        buscarLabel = new JLabel("Buscar:");
        
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
        botonrestartFiltro = new JButton("Restaurar");
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
    
    private void generarLayoutBiblioteca(){
        
        bibliotecaPanel = new JPanel();
        bibliotecaPanel.setLayout(new GridBagLayout());
        listaPlaylist = new JList(usuario.getPlaylist().entrySet().toArray());
        listaCanciones = new JList(usuario.getBiblioteca().toArray());
        
        listaPlaylist.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        listaPlaylist.setLayoutOrientation(JList.VERTICAL);
        listaPlaylist.setVisibleRowCount(-1);

        listScroller = new JScrollPane(listaPlaylist);
        GridBagConstraints c = new GridBagConstraints();        
        c.fill = GridBagConstraints.VERTICAL;
        c.gridx=0;
        c.gridy=1;
        bibliotecaPanel.add(listScroller, c);
        
        playlistOptionPanel = new JPanel();
        playlistOptionPanel.setLayout(new BoxLayout(playlistOptionPanel, BoxLayout.LINE_AXIS));
        
        botonAdd = new JButton("+");
        playlistOptionPanel.add(botonAdd);
        playlistOptionPanel.add(Box.createRigidArea(new Dimension(5,0)));
        botonEdit = new JButton("E");
        playlistOptionPanel.add(botonEdit);
        playlistOptionPanel.add(Box.createRigidArea(new Dimension(5,0)));
        botonDel = new JButton("X");
        playlistOptionPanel.add(botonDel);
        
        c = new GridBagConstraints();
        c.gridx=0;
        c.gridy=2;
        bibliotecaPanel.add(playlistOptionPanel, c);
                
        c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.gridx=1;
        c.gridy=1;
        bibliotecaPanel.add(new JPanel(), c);
        
        CancionesScroller = new JScrollPane(listaCanciones);
        c = new GridBagConstraints();
        c.fill = GridBagConstraints.VERTICAL;
        c.gridx=2;
        c.gridy=1;
        bibliotecaPanel.add(CancionesScroller, c);
        
        SongsOptionPanel = new JPanel();
        SongsOptionPanel.setLayout(new BoxLayout(SongsOptionPanel, BoxLayout.LINE_AXIS));
        
        botonAdd2 = new JButton("Añadir");
        SongsOptionPanel.add(botonAdd2);
        SongsOptionPanel.add(Box.createRigidArea(new Dimension(5,0)));
        botonDownload = new JButton("Descargar");
        SongsOptionPanel.add(botonDownload);
        
        c = new GridBagConstraints();
        c.gridx=2;
        c.gridy=2;
        bibliotecaPanel.add(SongsOptionPanel, c);
    }
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 821, Short.MAX_VALUE)
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
        for (Reproducible r : db.buscar(f)){
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
