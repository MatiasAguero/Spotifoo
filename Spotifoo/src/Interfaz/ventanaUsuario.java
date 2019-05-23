package Interfaz;

import Spotifoo.ConjuntoCanciones;
import java.awt.List;
import Spotifoo.DataManager.BaseDatos;
import Spotifoo.DataManager.GestorLibreria;
import Spotifoo.Filtro.FArtista;
import Spotifoo.Filtro.FGenero;
import Spotifoo.Filtro.FNombre;
import Spotifoo.Filtro.Filtro;
import Spotifoo.Reproducible;
import Spotifoo.Usuario;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
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
import javax.swing.JList;
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
    private JLabel buscarLabel;
    private JRadioButton tituloCheckBox;
    private JRadioButton artistaCheckBox;
    private JRadioButton generoCheckBox;
    private JRadioButton usuarioCheckBox;
    private JRadioButton compuestoCheckBox;
    private JRadioButton condicion;
    private JButton botonBuscar;
    private List resultadoBusquedaList;
    private List playlistsList;
    
    private JButton playButton;
    private JButton previousButton;
    private JButton nextButton;
    
    BaseDatos bd;
    GestorLibreria gestorLibreria;
    Usuario usuario;
    
    FArtista filtroXArtista;
    FGenero filtroXGenero;
    FNombre filtroXNombre;
    
    private Graphics imgCancion;
    
    public ventanaUsuario(Usuario u) {
        initComponents();
        configurarFrame();
        
        bd = BaseDatos.getBaseDatos();
        gestorLibreria = new GestorLibreria(bd);
        usuario = u;
        
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
        
    }
    
    private void eventosVentana(){
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                bd = BaseDatos.getBaseDatos();
                bd.guardarDatos();
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
        
        generarLayoutPlaylist();
        JScrollPane playlists = new JScrollPane(playlistsPanel);
        
        //**********************************************************
        //************INSERTAR EXPLORADOR DE BIBLIOTECA*************
        
        //Crea el divisor superior con la playlist y el explorador de la biblioteca 
        buscadorPanel = new JPanel();
        generarLayoutBuscador();
        JSplitPane topSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, playlists, buscadorPanel);
        topSplit.setDividerSize(1);
        topSplit.setEnabled(false);
        //Crea el divisor inferior, donde va el reproductor
        reproductorPanel = new JPanel();
        generarLayoutReproductor();
        //Inserta en la pestaña Principal todos los componentes creados
        JSplitPane tabPrincipal = new JSplitPane(JSplitPane.VERTICAL_SPLIT, topSplit, reproductorPanel);
        tabs.addTab("Explorar", tabPrincipal);
        
        //tab = new JTextArea();

        //JComponent panel2 = makeTextPanel("Panel #2");
        tabs.insertTab("Mi Bilbioteca", null, new JPanel(), null, 1);
        
        tabPrincipal.setDividerSize(1);
        tabPrincipal.setEnabled(false);
        tabPrincipal.setResizeWeight(0.9);
                
    }
    
    private void generarLayoutReproductor(){

        playButton = new JButton(new ImageIcon("img/play.png"));
        previousButton = new JButton(new ImageIcon("img/previous.png"));
        nextButton = new JButton(new ImageIcon("img/next.png"));
        
        JPanel botonesPanel = new JPanel();
        botonesPanel.setLayout(new BoxLayout(botonesPanel, BoxLayout.LINE_AXIS));
        botonesPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        botonesPanel.add(previousButton);
        botonesPanel.add(playButton);
        botonesPanel.add(nextButton);
        
        reproductorPanel.add(botonesPanel);
        
        /*
        JPanel imgCancionPanel = new JPanel();
        ImageIcon img = new ImageIcon(getClass().getResource("img/despacito.jpg"));
        imgCancion.drawImage(img.getImage(), 30, 30, null);
        reproductorPanel.paint(imgCancion);
        reproductorPanel.add(botonesPanel,BorderLayout.PAGE_END);*/
    }
    
    private void generarLayoutBuscador(){
        textField = new JTextField();
        buscarLabel = new JLabel("Buscar:");;
        tituloCheckBox = new JRadioButton("Titulo");
        artistaCheckBox = new JRadioButton("Artista");
        generoCheckBox = new JRadioButton("Genero");
        usuarioCheckBox = new JRadioButton("Usuario");
        ButtonGroup editableGroup = new ButtonGroup();
        editableGroup.add(tituloCheckBox);
        editableGroup.add(artistaCheckBox);
        editableGroup.add(generoCheckBox);
        editableGroup.add(usuarioCheckBox);
        botonBuscar = new JButton("Buscar");
        resultadoBusquedaList = new List();
        tituloCheckBox.setSelected(true);
        
        GroupLayout layout = new GroupLayout(buscadorPanel);
        buscadorPanel.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        
        layout.setHorizontalGroup(layout.createSequentialGroup()
            .addComponent(buscarLabel)
            .addGroup(layout.createParallelGroup(LEADING)
                .addComponent(textField)
                .addGroup(layout.createSequentialGroup()
                    .addComponent(tituloCheckBox)
                    .addComponent(artistaCheckBox)
                    .addComponent(generoCheckBox)
                    .addComponent(usuarioCheckBox))
                    .addComponent(resultadoBusquedaList))
            .addGroup(layout.createParallelGroup(LEADING)
                .addComponent(botonBuscar))
        );
 
        layout.setVerticalGroup(layout.createSequentialGroup()
            .addGroup(layout.createParallelGroup(BASELINE)
                .addComponent(buscarLabel)
                .addComponent(textField)
                .addComponent(botonBuscar))
            .addGroup(layout.createParallelGroup(LEADING)
                .addComponent(tituloCheckBox)
                .addComponent(artistaCheckBox)
                .addComponent(generoCheckBox)
                .addComponent(usuarioCheckBox))
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
        
        playlistsPanel.add(playlistsLabel);
        playlistsPanel.add(playlistsList);
        
        //Agregacion de las playlists del usuario en la lista
        for (Map.Entry<String, ConjuntoCanciones> entry : usuario.getPlaylist().entrySet()) {
            playlistsList.add(entry.getKey());
        }
    }
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        salir = new javax.swing.JButton();

        salir.setText("Salir");
        salir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                salirActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(294, 294, 294)
                .addComponent(salir, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(443, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(379, 379, 379)
                .addComponent(salir, javax.swing.GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void salirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_salirActionPerformed
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_salirActionPerformed
    
    private void botonBuscarActionPerformed(java.awt.event.ActionEvent evt) {                                         
        String aBuscar = textField.getText();
        resultadoBusquedaList.clear();
        if(artistaCheckBox.isSelected()){
            filtroXArtista = new FArtista(aBuscar);
            filtrarCanciones(filtroXArtista);
        }
        else if(generoCheckBox.isSelected()){
            filtroXGenero = new FGenero(aBuscar);
            filtrarCanciones(filtroXGenero);
        }
        else {
            filtroXNombre = new FNombre(aBuscar); 
            filtrarCanciones(filtroXNombre);
        }
    } 
    
    private void filtrarCanciones(Filtro f){
        for (Reproducible r : gestorLibreria.buscar(f)){
                resultadoBusquedaList.add(r.getNombre());
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
    private javax.swing.JButton salir;
    // End of variables declaration//GEN-END:variables
}
