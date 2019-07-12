package Interfaz.accionesSobreTabla;

import Spotifoo.Admin;
import Spotifoo.Artista;
import Spotifoo.Cancion;
import Spotifoo.ConjuntoCanciones;
import Spotifoo.DataManager.DAO_FS;
import Spotifoo.Reproducible;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.*;
import static javax.swing.GroupLayout.Alignment.BASELINE;
import static javax.swing.GroupLayout.Alignment.LEADING;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author nico
 */
public class ventanaAgregarMusica extends javax.swing.JFrame {
    
    private JButton añadirCancionTabla;
    private JButton restaurarTabla;
    private JTextField nombreTextField;
    private JButton agregar;
    private JRadioButton canciones;
    private JRadioButton nuevoAlbum;
    
    private JTextField albumTextField;
    private JTable table;
    private JComboBox listArtista;
    private JComboBox listGenero;
    private JTextField añoTextField;
    
    List<Cancion> cancionesAñadidas;
    
    JTable tableVentanaMusica;
    Admin admin;
    public ventanaAgregarMusica(JTable table,Admin admin) {
        initComponents();
        configFrame();
        generarLayout();
        eventosComponentes();
        cancionesAñadidas = new ArrayList<>();
        tableVentanaMusica = table;
        this.admin = admin;
    }
    
    private void generarLayout(){
        JLabel nombre = new JLabel("Nombre:");
        nombreTextField = new JTextField();
        JLabel artista = new JLabel("Artista");
        listArtista = new JComboBox(); 
        for (Map.Entry<Integer, Artista> entry : DAO_FS.getBaseDatos().getArtista().entrySet()) {
            listArtista.addItem(entry.getValue().getNombre());
        }
        JLabel genero = new JLabel("Genero");
        String[] generos={ "Blues","Corrido","Country","Cumbia","Disco","Electrónica","Flamenco",
                           "Folk", "Funk", "Gospel", "Heavy Metal", "Hip Hop","Indie",    "Jazz",
                           "Merengue","Pop", "Punk", "Ranchera","Rap", "Reggae","Reggaeton","Rumba",
                           "Rock", "Rock and Roll", "Salsa", "Samba", "Tango","Vallenato"};
        listGenero = new JComboBox(generos); 
        JLabel añoLabel = new JLabel("Año:");
        añoTextField = new JTextField();
        añadirCancionTabla = new JButton("+");
        restaurarTabla = new JButton("Restablecer");
        agregar = new JButton("Agregar");
        
        canciones = new JRadioButton("Canciones");
        nuevoAlbum = new JRadioButton("Nuevo Album:");
        ButtonGroup editableGroup = new ButtonGroup();
        editableGroup.add(canciones);
        editableGroup.add(nuevoAlbum);
        canciones.setSelected(true);
        
        albumTextField = new JTextField();
        
        //Creacion de tabla
        String[] columnNames={"Nombre", "Artista","Genero","Año"};
        Object[][] data = {};
        table = new JTable(data,columnNames);
        TableModel model = new DefaultTableModel(data,columnNames){
            @Override
            public boolean isCellEditable(int row,int column){
                return false;
            }
        };
        table.setModel(model);
        table.setPreferredScrollableViewportSize(new Dimension(70, 70));
        JScrollPane scrollPane = new JScrollPane(table);
        
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        layout.setHorizontalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(nombre)
                        .addComponent(nombreTextField)
                        .addComponent(artista)
                        .addComponent(listArtista)
                        .addComponent(genero)
                        .addComponent(listGenero)
                        .addComponent(añoLabel)
                        .addComponent(añoTextField)
                        .addComponent(añadirCancionTabla)
                        .addComponent(restaurarTabla))
                    .addComponent(scrollPane)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(canciones)
                        .addComponent(nuevoAlbum)
                        .addComponent(albumTextField)
                        .addComponent(agregar)))
        );
        layout.setVerticalGroup(layout.createSequentialGroup()
            .addGroup(layout.createParallelGroup(BASELINE)
                .addComponent(nombre)
                .addComponent(nombreTextField)
                .addComponent(artista)
                .addComponent(listArtista)
                .addComponent(genero)
                .addComponent(listGenero)
                .addComponent(añoLabel)
                .addComponent(añoTextField)
                .addComponent(añadirCancionTabla)
                .addComponent(restaurarTabla))
            .addGroup(layout.createParallelGroup(LEADING)
                .addComponent(scrollPane))
            .addGroup(layout.createParallelGroup(BASELINE)
                .addComponent(canciones)
                .addComponent(nuevoAlbum)
                .addComponent(albumTextField)
                .addComponent(agregar))
        );
    }
    
    private void eventosComponentes(){
    añadirCancionTabla.addActionListener(new java.awt.event.ActionListener() {
        @Override
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            añadirCancionTablaActionPerformed(evt);
        }

        private void añadirCancionTablaActionPerformed(ActionEvent evt) {
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.addRow(new Object[]{nombreTextField.getText(),listArtista.getSelectedItem(),
                         listGenero.getSelectedItem(),añoTextField.getSelectedText()});
            cancionesAñadidas.add(new Cancion(nombreTextField.getText(),  DAO_FS.getBaseDatos().
                                    getArtistaNombre((String)listArtista.getSelectedItem()),
                                    (String)listGenero.getSelectedItem(),añoTextField.getSelectedText()));
        }
    });
    restaurarTabla.addActionListener(new java.awt.event.ActionListener() {
        @Override
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            restaurarTablaActionPerformed(evt);
        }

        private void restaurarTablaActionPerformed(ActionEvent evt) {
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            for (int i = table.getRowCount() -1; i >= 0; i--){
                model.removeRow(i);
            }
            cancionesAñadidas.clear();
        }
    });
    agregar.addActionListener(new java.awt.event.ActionListener() {
        @Override
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            agregarActionPerformed(evt);
        }

        private void agregarActionPerformed(ActionEvent evt) {
            DefaultTableModel model = (DefaultTableModel) tableVentanaMusica.getModel();
            if (canciones.isSelected()){
                //Agregar a la base de datos
                for(Cancion c: cancionesAñadidas){
                    model.addRow(new Object[]{c.getId(),c.getNombre()});
                    admin.addReprod(c);
                }
            } else{
                String nombreAlbum = albumTextField.getText();
                if (!nombreAlbum.equals("")){
                    ConjuntoCanciones album = new ConjuntoCanciones(nombreAlbum);
                    for(Cancion c: cancionesAñadidas){
                        album.agregar(c);
                    }
                    album.setAlbum();
                    admin.addReprod(album);
                    model.addRow(new Object[]{album.getId(),nombreAlbum});
                    }
            }
        }
    });
    this.addWindowListener( new WindowAdapter() {
            public void windowClosing( WindowEvent evt ) {
                setVisible(false);
            }  
    });
    }
        
    private void configFrame(){
        this.setTitle("Spotifoo");
        setSize(1100, 300);
        setLocationRelativeTo(null);
        getContentPane().setLayout(new BoxLayout(getContentPane(),BoxLayout.Y_AXIS));
        setVisible(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE); 
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
