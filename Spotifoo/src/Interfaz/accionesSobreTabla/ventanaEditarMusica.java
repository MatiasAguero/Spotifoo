package Interfaz.accionesSobreTabla;

import Spotifoo.Admin;
import Spotifoo.Cancion;
import Spotifoo.DataManager.DAO_FS;
import Spotifoo.Reproducible;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import static javax.swing.GroupLayout.Alignment.BASELINE;
import static javax.swing.GroupLayout.Alignment.LEADING;
import static javax.swing.GroupLayout.Alignment.TRAILING;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author nico
 */
public class ventanaEditarMusica extends javax.swing.JFrame {
   
    JTable tableVentanaMusica;
    Admin admin;
    JTable table;
    JButton aplicarButton;
    
    List<Cancion> canciones;
    public ventanaEditarMusica(JTable table,Admin admin) {
        initComponents();
        this.tableVentanaMusica = table;
        this.admin = admin;
        this.canciones = new ArrayList<>();
        configFrame();
        generarLayout();
        setTableRow();
        eventosComponentes();
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void configFrame() {
        this.setTitle("Spotifoo");
        setSize(800, 200);
        setLocationRelativeTo(null);
        getContentPane().setLayout(new BoxLayout(getContentPane(),BoxLayout.Y_AXIS));
        setVisible(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE); 
    }

    private void generarLayout() {
        String[] columnNames={"Nombre", "Artista","Genero","Año"};
        Object[][] data = {};
        table = new JTable(data,columnNames);
        TableModel model = new DefaultTableModel(data,columnNames){
            @Override
            public boolean isCellEditable(int row,int column){
                return true;
            }
        };
        table.setModel(model);
        table.setPreferredScrollableViewportSize(new Dimension(70, 70));
        JScrollPane scrollPane = new JScrollPane(table);
        
        aplicarButton = new JButton("Aplicar");
        
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        layout.setHorizontalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(scrollPane))
                    .addComponent(aplicarButton))
        );
        layout.setVerticalGroup(layout.createSequentialGroup()
            .addGroup(layout.createParallelGroup(BASELINE)
                .addComponent(scrollPane))
            .addGroup(layout.createParallelGroup(LEADING)
                .addComponent(aplicarButton))
        );
    }

    private void eventosComponentes() {
        aplicarButton.addActionListener(new java.awt.event.ActionListener() {
        @Override
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            aplicarButtonActionPerformed(evt);
        }

        private void aplicarButtonActionPerformed(ActionEvent evt) {
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            DefaultTableModel modelVentanaMusica = (DefaultTableModel) tableVentanaMusica.getModel();
            int i = 0;
            for (Cancion c : canciones){
                for(int j=0 ; j < tableVentanaMusica.getRowCount();j++){
                    if (modelVentanaMusica.getValueAt(j, 1).equals(c.getNombre()))
                        modelVentanaMusica.removeRow(j);
                }
                c.setNombre((String) model.getValueAt(i, 0));
                c.setArtista(DAO_FS.getBaseDatos().getArtistaNombre((String) model.getValueAt(i, 1)));
                c.setGenero((String) model.getValueAt(i, 2));
                c.setFecha((String) model.getValueAt(i, 3));
                admin.addReprod(c);
                
                modelVentanaMusica.addRow(new Object[]{c.getId(),c.getNombre()});
                i++;
            }
        }
    });
    }

    private void setTableRow() {
        DefaultTableModel model = (DefaultTableModel) tableVentanaMusica.getModel();
        Reproducible repro = DAO_FS.getBaseDatos().getReproducible((Integer)model.getValueAt(tableVentanaMusica.getSelectedRow(), 0));
        
        model = (DefaultTableModel) table.getModel();
        Cancion c;
        String album;
        for(Reproducible r: repro.getCanciones()){ 
            c = (Cancion) r;
            model.addRow(new Object[]{c.getNombre(),c.getArtista().getNombre(),
                c.getGenero(),c.getFecha()});
            canciones.add(c);
        }
        
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
