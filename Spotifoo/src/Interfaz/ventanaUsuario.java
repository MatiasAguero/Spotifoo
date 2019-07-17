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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.Hashtable;
import java.util.Map;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import static javax.swing.GroupLayout.Alignment.BASELINE;
import static javax.swing.GroupLayout.Alignment.LEADING;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
/**
 *
 * @author nico
 */
public class ventanaUsuario extends javax.swing.JFrame {
    private Container reproductorPanel;
    private JPanel buscadorPanel;
    private JPanel bibliotecaPanel;
    private JPanel playlistsPanel;
    private JTextField textField;
    private JLabel buscarLabel;
    private JRadioButton tituloRadioButton;
    private JRadioButton artistaRadioButton;
    private JRadioButton generoRadioButton;
    private JCheckBox compuestoCheckBox;
    private JRadioButton andRadioButton;
    private JRadioButton orRadioButton;
    private JButton botonBuscar;
    private JList resultadoBusquedaList;
    private JList playlistsList;
    private JList listaPlaylist;
    private JList listaCanciones;
    private JList reprEnCurso;
    
    private JButton botonrestartFiltro;
    private JButton playButton;
    private JButton previousButton;
    private JButton nextButton;
    private JButton addPlaylistButton;
    private final static String URL_PLAY = DAO_FS.PATH_APP_IMG+"botones/play.png";
    private final static String URL_PREVIOUS = DAO_FS.PATH_APP_IMG+"botones/previous.png";
    private final static String URL_NEXT = DAO_FS.PATH_APP_IMG+"botones/next.png";
    
    DAO db;
    Usuario usuario;

    Filtro filtro;
    Filtro filtroComp;
    Filtro filtroAnt;

    private JTabbedPane tabs;
    private JLabel img;
    private JScrollPane listScroller;
    private JButton botonAdd;
    private JButton botonEdit;
    private JButton botonDel;
    private JButton botonAdd2;
    private JButton botonDownload;
    private JScrollPane CancionesScroller;
    private DefaultListModel playlistModel;
    private DefaultListModel cancionesModel;
    private DefaultListModel busquedaModel;
    private DefaultListModel pl2Model;
    private DefaultListModel reprModel;
    private JPanel imgPanel;
    private JLabel nombreCancion;
    private JButton botonDelCanc;
    
    
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
        
        MouseListener mouseListener = new MouseAdapter() {
            public void mouseClicked(MouseEvent mouseEvent) {
                JList<Reproducible> l = (JList) mouseEvent.getSource();
                Reproducible r;
                
                if (mouseEvent.getClickCount() == 2 && mouseEvent.getButton() == MouseEvent.BUTTON1) {
                    int index = l.locationToIndex(mouseEvent.getPoint());
                    
                    if (index >= 0) {
                        r = l.getModel().getElementAt(index);
                        
                        if(r.getCantCanciones() == 1)
                            reprModel.addElement(r);
                        else {
                            for(Cancion c : r.getCanciones())
                                reprModel.addElement(c);
                        }
                            
                    }
                }
            }
        };
        
        resultadoBusquedaList.addMouseListener(mouseListener);
        playlistsList.addMouseListener(mouseListener);
        listaPlaylist.addMouseListener(mouseListener);
        listaCanciones.addMouseListener(mouseListener);
        
        botonBuscar.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonBuscarActionPerformed(evt);
            }
        });
        addPlaylistButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonAgregarAPlaylistActionPerformed(evt);
            }
            private void botonAgregarAPlaylistActionPerformed(ActionEvent evt){
                if(resultadoBusquedaList.getSelectedIndex() != -1){
                    if(playlistsList.getSelectedIndex() != -1){
                        usuario.addListaReproducible(((Reproducible) resultadoBusquedaList.getSelectedValue()),
                                ((ConjuntoCanciones) playlistsList.getSelectedValue()).toString());
                    }
                    else
                        JOptionPane.showMessageDialog (null, "No se ha seleccionado ninguna playlist", "Atencion!", JOptionPane.WARNING_MESSAGE);
                }
                else
                    JOptionPane.showMessageDialog (null, "No se ha seleccionado ninguna cancion para añadir", "Atencion!", JOptionPane.WARNING_MESSAGE);
            }
        });
        playButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                playButtonActionPerformed(evt);
            }

            private void playButtonActionPerformed(ActionEvent evt) {
                
                reprEnCurso.setSelectedIndex(0);
                nombreCancion.setText(reprModel.get(0).toString());
                setImg(((Reproducible) reprEnCurso.getSelectedValue()).play());
                
                
            }
        });
        
        previousButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                previousButtonActionPerformed(evt);
            }
            
            private void previousButtonActionPerformed(ActionEvent evt) {
                if(reprEnCurso.getSelectedIndex() > 0) {
                    reprEnCurso.setSelectedIndex(reprEnCurso.getSelectedIndex()-1);
                    nombreCancion.setText(reprEnCurso.getSelectedValue().toString());
                    setImg(((Reproducible) reprEnCurso.getSelectedValue()).play());
                }
            }
        });
        
        nextButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nextButtonActionPerformed(evt);
            }
            
            private void nextButtonActionPerformed(ActionEvent evt) {
                if(reprEnCurso.getSelectedIndex() < reprModel.getSize()-1) {
                    reprEnCurso.setSelectedIndex(reprEnCurso.getSelectedIndex()+1);
                    nombreCancion.setText(reprEnCurso.getSelectedValue().toString());
                    setImg(((Reproducible) reprEnCurso.getSelectedValue()).play());
                }
            }
        });
        
        botonrestartFiltro.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                filtroAnt = null;
            }
        });
        
    }
    
    private class ButtonActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent event){
            String action = event.getActionCommand();
            final JFileChooser fc = new JFileChooser();
            String input;
            switch(action){
                case "ADD":
                    //Añade una nueva playlist
                    input = JOptionPane.showInputDialog("Inserte nombre de playlist");
                    usuario.createPlaylist(input);
                    playlistModel.insertElementAt(input, 0);
                    pl2Model.insertElementAt(input, 0);
                    break;
                case "ADD2":
                    //Añade una nueva cancion
                    int returnVal = fc.showOpenDialog(bibliotecaPanel);
                    if (returnVal == JFileChooser.APPROVE_OPTION) {
                        File file = fc.getSelectedFile();
                        Reproducible r = ((DAO_FS) db).loadFile(file);
                        usuario.addElem(r);
                        cancionesModel.insertElementAt((Cancion) r, 0);
                    }
                    break;
                case "DEL":
                    //Elimina una playlist
                    ConjuntoCanciones playlist = ((ConjuntoCanciones)listaPlaylist.getSelectedValue());
                    if(playlist != null){
                        usuario.delPlaylist(playlist.getNombre());
                        playlistModel.removeElementAt(listaPlaylist.getSelectedIndex());
                        pl2Model.removeElement(playlist);
                    }
                    else
                        JOptionPane.showMessageDialog (null, "No se ha seleccionado ninguna playlist", "Atencion!", JOptionPane.WARNING_MESSAGE);
                    break;
                case "DELC":
                    //Elimina una cancion
                    Cancion c = ((Cancion)listaCanciones.getSelectedValue());
                    if(c != null){
                        usuario.delElem(c);
                        cancionesModel.removeElementAt(listaCanciones.getSelectedIndex());
                    }
                    else
                        JOptionPane.showMessageDialog (null, "No se ha seleccionado ninguna cancion", "Atencion!", JOptionPane.WARNING_MESSAGE);
                    break;
                case "EDIT":
                    //Edita una playlist
                    break;
                case "DESCARGAR":
                    //Guarda una cancion en el sistema de archivos local
                    int userSelection = fc.showSaveDialog(bibliotecaPanel);
 
                    if (userSelection == JFileChooser.APPROVE_OPTION) {
                        File f = fc.getSelectedFile();
                        c = (Cancion) listaCanciones.getSelectedValue();
                        if(c != null)
                            DAO_FS.saveLocal(f, c);
                        else
                            JOptionPane.showMessageDialog (null, "No se ha seleccionado ninguna cancion", "Atencion!", JOptionPane.WARNING_MESSAGE);
                    }
                    break;
            }
        }
    }

    private void filtrarCanciones(Filtro f){
        
        for (Reproducible r : db.buscar(f))
           busquedaModel.addElement(r);
        
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
        tabs = new JTabbedPane();
        //Lo añade en primer lugar en el frame
        //this.add(tabs, 0);
        
        //Genera los componentes para insertar dentro de la pestaña principal
        //Genera el explorador de biblioteca
        this.generarLayoutBuscador();
        //El componente donde van las playlist
        this.generarLayoutPlaylist();
        JScrollPane playlists = new JScrollPane(playlistsPanel);
        playlists.setMinimumSize(new Dimension(100,500));
        
        //Crea el divisor superior con la playlist y el explorador de la biblioteca
        JSplitPane topSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, playlists, buscadorPanel);
        topSplit.setResizeWeight(1);
        topSplit.setDividerSize(0);
        topSplit.setEnabled(false);
        
        //Crea el divisor inferior, donde va el reproductor
        this.generarLayoutReproductor();
        
        //Inserta en la pestaña Principal todos los componentes creados
        JSplitPane tabPrincipal = new JSplitPane(JSplitPane.VERTICAL_SPLIT, tabs, reproductorPanel);
        tabPrincipal.setResizeWeight(1);
        this.add(tabPrincipal);
        tabs.addTab("Explorar", topSplit);
        
        //tab = new JTextArea();
            
        this.generarLayoutBiblioteca();
        tabs.insertTab("Mi Biblioteca", null, bibliotecaPanel, null, 1);
        
        tabPrincipal.setDividerSize(0);
        tabPrincipal.setEnabled(false);
                
    }
    
    private void generarLayoutReproductor(){

        //Instanciacion de componentes
        JSlider volumen = new JSlider(0, 100, 100);
        JSlider reprTime = new JSlider(JSlider.HORIZONTAL, 0, 100, 0);
        reprModel = new DefaultListModel();
        reprEnCurso = new JList(reprModel);
        JScrollPane scroller = new JScrollPane(reprEnCurso);
        nombreCancion = new JLabel("Reproductor en espera");
        playButton = new JButton(new ImageIcon(URL_PLAY));
        previousButton = new JButton(new ImageIcon(URL_PREVIOUS));
        nextButton = new JButton(new ImageIcon(URL_NEXT));
        img = new JLabel();
        imgPanel = new JPanel();
        reproductorPanel = new JPanel();
        
        //Configuracion Layout
        GroupLayout layout = new GroupLayout(reproductorPanel);
        reproductorPanel.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        
        //Configuracion panel de imagen (a la izquierda)
        imgPanel.setMinimumSize(new Dimension(150, 150));
        imgPanel.setPreferredSize(new Dimension(150, 150));
        imgPanel.add(img);
        
        //Configuracion de los botones de reproduccion
        playButton.setBackground(Color.WHITE);
        playButton.setToolTipText("Reproducir");
        previousButton.setBackground(Color.WHITE);
        previousButton.setToolTipText("Anterior Cancion");
        nextButton.setBackground(Color.WHITE);
        nextButton.setToolTipText("Siguiente Cancion");
        
        //Configuracion del slider de volumen
        volumen.setMajorTickSpacing(50);
        volumen.setPaintTicks(true);

        //Creacion de las etiquetas inferiores del slider de volumen
        Hashtable labelTable = new Hashtable();
        labelTable.put( new Integer( 0 ), new JLabel("0%") );
        labelTable.put( new Integer( 50 ), new JLabel("50%") );
        labelTable.put( new Integer( 100 ), new JLabel("100%") );
        volumen.setLabelTable( labelTable );
        volumen.setPaintLabels(true);
        
        //Configuracion del slider de reproduccion
        //Creacion de las etiquetas inferiores del slider de reproduccion
        Hashtable labelTable2 = new Hashtable();
        labelTable2.put( new Integer( 0 ), new JLabel("0:00") );
        labelTable2.put( new Integer( 100 ), new JLabel("99:99") );
        reprTime.setLabelTable( labelTable2 );
        reprTime.setPaintLabels(true);
        
        //Distribucion de los componentes en el layout
        //Horizontal
        layout.setHorizontalGroup(layout.createSequentialGroup()
            .addComponent(imgPanel)
            .addGroup(layout.createParallelGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(nombreCancion)
                    .addComponent(reprTime)
                )
                .addGroup(layout.createSequentialGroup()
                    .addComponent(previousButton)
                    .addComponent(playButton)
                    .addComponent(nextButton)
                    .addComponent(volumen)
                )
            )
            .addComponent(scroller)
        );
        //Vertical
        layout.setVerticalGroup(layout.createSequentialGroup()
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                .addComponent(imgPanel)
                .addGroup(layout.createSequentialGroup()
                    .addComponent(nombreCancion)
                    .addComponent(reprTime)
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                        .addComponent(previousButton)
                        .addComponent(playButton)
                        .addComponent(nextButton)
                        .addComponent(volumen)
                    )
                )
                .addComponent(scroller)
            )
            
        );
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

        botonrestartFiltro= new JButton("Restablecer");

        busquedaModel = new DefaultListModel();
        resultadoBusquedaList = new JList(busquedaModel);
        resultadoBusquedaList.setName("busqueda");
        JScrollPane scrll = new JScrollPane(resultadoBusquedaList);
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
                    .addComponent(scrll))
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
                .addComponent(scrll))
        );
    }

    private void generarLayoutPlaylist(){
        playlistsPanel = new JPanel();
        playlistsPanel.setLayout(new BoxLayout(playlistsPanel, BoxLayout.Y_AXIS));
        
        JLabel playlistsLabel = new JLabel("Playlists");
        playlistsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        pl2Model = new DefaultListModel();
        playlistsList = new JList(pl2Model);
        playlistsList.setName("pl");
        JScrollPane scrll = new JScrollPane(playlistsList);
        scrll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrll.setPreferredSize(new Dimension(100, 100));
        addPlaylistButton = new JButton("Agregar a playlist");
        addPlaylistButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        playlistsPanel.add(playlistsLabel);
        playlistsPanel.add(scrll);
        
        playlistsPanel.add(addPlaylistButton);
        
        //Agregacion de las playlists del usuario en la lista
        for (Map.Entry<String, ConjuntoCanciones> entry : usuario.getPlaylist().entrySet()) {
            pl2Model.addElement(entry.getValue());
        }
    }
    
    private void generarLayoutBiblioteca(){
        
        ButtonActionListener al = new ButtonActionListener();
        
        bibliotecaPanel = new JPanel();

        playlistModel = new DefaultListModel();
        cancionesModel = new DefaultListModel();
        loadModels();
        listaPlaylist = new JList(playlistModel);
        listaPlaylist.setName("playlist");
        listaPlaylist.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        listaPlaylist.setLayoutOrientation(JList.VERTICAL);
        //listaPlaylist.setVisibleRowCount(-1);

        listaCanciones = new JList(cancionesModel);
        listaCanciones.setName("canciones");
        listaCanciones.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        listaCanciones.setLayoutOrientation(JList.VERTICAL);
        
        JLabel lPlaylist = new JLabel("Playlists");
        JLabel lCanciones = new JLabel("Canciones");
        botonAdd = new JButton("+");
        botonAdd.addActionListener(al);
        botonAdd.setActionCommand("ADD");
        botonAdd.setToolTipText("Añadir Playlist");
        botonEdit = new JButton("E");
        botonEdit.addActionListener(al);
        botonEdit.setActionCommand("EDIT");
        botonEdit.setToolTipText("Editar Playlist");
        botonDel = new JButton("X");
        botonDel.addActionListener(al);
        botonDel.setActionCommand("DEL");
        botonDel.setToolTipText("Eliminar Playlist");
        botonAdd2 = new JButton("Añadir");
        botonAdd2.addActionListener(al);
        botonAdd2.setActionCommand("ADD2");
        botonAdd2.setToolTipText("Subir Cancion");
        botonDownload = new JButton("Descargar");
        botonDownload.addActionListener(al);
        botonDownload.setActionCommand("DESCARGAR");
        botonDownload.setToolTipText("Descargar Cancion");
        botonDelCanc = new JButton("X");
        botonDelCanc.addActionListener(al);
        botonDelCanc.setActionCommand("DELC");
        botonDelCanc.setToolTipText("Eliminar Cancion");
        
        GroupLayout layout = new GroupLayout(bibliotecaPanel);
        bibliotecaPanel.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        
        listScroller = new JScrollPane(listaPlaylist);
        
        CancionesScroller = new JScrollPane(listaCanciones);
        
        layout.setHorizontalGroup(
            layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                    .addComponent(lPlaylist)
                    .addComponent(listScroller)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(botonAdd)
                        .addComponent(botonEdit)
                        .addComponent(botonDel)
                    )
                )
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                    .addComponent(lCanciones)
                    .addComponent(CancionesScroller)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(botonAdd2)
                        .addComponent(botonDelCanc)
                        .addComponent(botonDownload)
                    )
                )
        );
        layout.setVerticalGroup(
            layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(lPlaylist)
                    .addComponent(lCanciones)
                )
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(listScroller)
                    .addComponent(CancionesScroller)
                )
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(botonAdd)
                    .addComponent(botonEdit)
                    .addComponent(botonDel)
                    .addComponent(botonAdd2)
                    .addComponent(botonDelCanc)
                    .addComponent(botonDownload)
                )
        );
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
        busquedaModel.clear();
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

    private void loadModels() {
        
        for (Map.Entry<String, ConjuntoCanciones> entry : usuario.getPlaylist().entrySet()) {
            playlistModel.addElement(entry.getValue());
        }
        
        for (Reproducible elem : usuario.getBiblioteca()) {
            cancionesModel.addElement(elem);
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
