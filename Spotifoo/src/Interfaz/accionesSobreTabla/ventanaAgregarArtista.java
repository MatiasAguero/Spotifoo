/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaz.accionesSobreTabla;

import Spotifoo.Admin;
import Spotifoo.Artista;
import Spotifoo.ConjuntoCanciones;
import Spotifoo.DataManager.DAO_FS;
import Spotifoo.Reproducible;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import static javax.swing.GroupLayout.Alignment.BASELINE;
import static javax.swing.GroupLayout.Alignment.LEADING;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author nicolaz999
 */
public class ventanaAgregarArtista extends javax.swing.JFrame {
    private JButton añadirArtistaTabla;
    private JButton restaurarTabla;
    private JTextField nombreArtistaTextField;
    private JButton agregar;
    private JComboBox listGeneros;
    private JComboBox listDiscografias;
    
    private JTextField albumTextField;
    private JTable table;
    private JTextField añoTextField;
    
    JTable tableVentanaMusica;
    Admin admin;
    List<ConjuntoCanciones> discografiasAñadidas;
    List<Reproducible> discografias;
    /**
     * Creates new form ventanaAgregarArtista
     */
    public ventanaAgregarArtista(JTable table,Admin admin) {
        discografiasAñadidas = new ArrayList<>();
        discografias = new ArrayList<>();
        tableVentanaMusica = table;
        this.admin = admin;
        initComponents();
        configFrame();
        generarLayout();
        eventosComponentes();
    }
    
    private void generarLayout(){
        JLabel nombreArtista = new JLabel("Nombre:");
        nombreArtistaTextField = new JTextField();
        JLabel genero = new JLabel("Genero");
        String[] generos={ "Blues","Corrido","Country","Cumbia","Disco","Electrónica","Flamenco",
                           "Folk", "Funk", "Gospel", "Heavy Metal", "Hip Hop","Indie",    "Jazz",
                           "Merengue","Pop", "Punk", "Ranchera","Rap", "Reggae","Reggaeton","Rumba",
                           "Rock", "Rock and Roll", "Salsa", "Samba", "Tango","Vallenato"};
        listGeneros = new JComboBox(generos); 
        JLabel añoLabel = new JLabel("Año:");
        añoTextField = new JTextField();
        
        listDiscografias = new JComboBox(); 
        for (Map.Entry<Integer, Reproducible> entry : DAO_FS.getBaseDatos().getLibreria().entrySet()) {
            if (entry.getValue().getCantCanciones() > 1){
                discografias.add(entry.getValue());
                listDiscografias.addItem(entry.getValue());
            }
        }
        
        JLabel discografias = new JLabel("Discografias:");
        añadirArtistaTabla = new JButton("+");
        restaurarTabla = new JButton("Restablecer");
        
        //Creacion de tabla
        String[] columnNames={"Discografia"};
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
        
        agregar = new JButton("Agregar");
        
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        layout.setHorizontalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup()
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(nombreArtista)
                        .addComponent(nombreArtistaTextField)
                        .addComponent(genero)
                        .addComponent(listGeneros)
                        .addComponent(añoLabel)
                        .addComponent(añoTextField))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(discografias)
                        .addComponent(listDiscografias)
                        .addComponent(añadirArtistaTabla)
                        .addComponent(restaurarTabla))
                    .addComponent(scrollPane)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(agregar)))
        );
        layout.setVerticalGroup(layout.createSequentialGroup()
            .addGroup(layout.createParallelGroup(BASELINE)
                .addComponent(nombreArtista)
                .addComponent(nombreArtistaTextField)
                .addComponent(genero)
                .addComponent(listGeneros)
                .addComponent(añoLabel)
                .addComponent(añoTextField))
            .addGroup(layout.createParallelGroup(BASELINE)
                .addComponent(discografias)
                .addComponent(listDiscografias)
                .addComponent(añadirArtistaTabla)
                .addComponent(restaurarTabla))
            .addGroup(layout.createParallelGroup(LEADING)
                .addComponent(scrollPane))
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                .addComponent(agregar))
        );
    }
    private void eventosComponentes(){
        añadirArtistaTabla.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                añadirArtistaTablaActionPerformed(evt);
            }
            private void añadirArtistaTablaActionPerformed(ActionEvent evt) {
                DefaultTableModel model = (DefaultTableModel) table.getModel();
                model.addRow(new Object[]{listDiscografias.getItemAt(listDiscografias.getSelectedIndex())});

                discografiasAñadidas.add((ConjuntoCanciones)discografias.get(listDiscografias.getSelectedIndex()));
            }});
        
        agregar.addActionListener(new java.awt.event.ActionListener() {
        @Override
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            DefaultTableModel model = (DefaultTableModel) tableVentanaMusica.getModel();
            Artista a = new Artista(nombreArtistaTextField.getText(),
                  (String)listGeneros.getSelectedItem(),
                    discografiasAñadidas,
                        Integer.parseInt(añoTextField.getText()));
            admin.addArtista(a);
            model.addRow(new Object[]{a.getId(),a.getNombre()});
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

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
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
