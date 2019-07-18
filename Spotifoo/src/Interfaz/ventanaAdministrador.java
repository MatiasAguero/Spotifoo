package Interfaz;

import Interfaz.accionesSobreTabla.ventanaAgregarArtista;
import Interfaz.accionesSobreTabla.ventanaAgregarMusica;
import Interfaz.accionesSobreTabla.ventanaEditarArtista;
import Interfaz.accionesSobreTabla.ventanaEditarMusica;
import Spotifoo.Admin;
import Spotifoo.Artista;
import Spotifoo.Cuenta;
import Spotifoo.DataManager.DAO;
import Spotifoo.DataManager.DAO_FS;
import Spotifoo.Reproducible;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import static javax.swing.GroupLayout.Alignment.BASELINE;
import static javax.swing.GroupLayout.Alignment.LEADING;
import static javax.swing.GroupLayout.Alignment.TRAILING;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author nicolaz999
 */
public class ventanaAdministrador extends javax.swing.JFrame {

    private static final String OPCION_USR = "Usuarios";
    private static final String OPCION_REP = "Reproducibles";
    private static final String OPCION_ART = "Artistas";
    private String nombreCol;
    DAO bd = DAO_FS.getBaseDatos();
    Admin admin;
    
    private JPanel panelAdmMusica;
    private JPanel panelAdmUsuario;
    private JPanel panelAdmArtistas;
    
    private JTextField textField;
    private JButton agregarButton;
    private JButton borrarButton;
    private JButton editarButton;
    private JButton restaurarButton;
    private JButton buscarButton;
    private JRadioButton idRadioButton;
    private JRadioButton nombreRadioButton;
    private JTable table;
    private JComboBox opcion; 
    private JScrollPane scrollPane;
    public ventanaAdministrador(Admin a) {
        initComponents();
        configurarFrame();
        bd = DAO_FS.getBaseDatos();
        admin = a;
        generarTabs();
        eventosComponentes();
    }
    
    private void configurarFrame(){
        this.setTitle("Spotifoo");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new BoxLayout(getContentPane(),BoxLayout.Y_AXIS));
        setVisible(true);
    }
    
    private void generarTabs(){
        //Genera el componente de las pestañas
        //JTabbedPane tabs = new JTabbedPane();
        //Lo añade en primer lugar en el frame
        //this.add(tabs, 0);
        generarPanelAdmMusica();
        this.add(panelAdmMusica);
       //tabs.add("Administrar Musica",panelAdmMusica);
        //generarPanelAdmUsuario();
        //tabs.add("Adminsitrar Usuarios",panelAdmUsuario);
    }
    
    private void generarPanelAdmMusica(){
        panelAdmMusica = new JPanel();
        
        //Filtrado de la tabla
        textField = new JTextField();
        JLabel buscarLabel = new JLabel("Filtrar:");
        restaurarButton = new JButton("Restaurar");
        buscarButton = new JButton("Buscar");
        idRadioButton = new JRadioButton("id");
        nombreRadioButton = new JRadioButton("nombre");
        ButtonGroup editableGroup = new ButtonGroup();
        editableGroup.add(idRadioButton);
        editableGroup.add(nombreRadioButton);
        idRadioButton.setSelected(true);
        
        //Creacion de tabla
        String[] columnNames={"ID", "Nombre","Album"};
        Object[][] data = getData();
        table = new JTable(data,columnNames);
        TableModel model = new DefaultTableModel(data,columnNames){
            @Override
            public boolean isCellEditable(int row,int column){
                return false;
            }
        };
        
        table.setModel(model);
        table.setPreferredScrollableViewportSize(new Dimension(200, 70));
        scrollPane = new JScrollPane(table);
        
        
        opcion = new JComboBox();
        opcion.addItem(OPCION_REP);
        opcion.addItem(OPCION_USR);
        opcion.addItem(OPCION_ART);
        //Agregar musica
        agregarButton = new JButton("Agregar...");
        //borrar musica
        borrarButton = new JButton("Eliminar");
        borrarButton.setEnabled(false);
        //editar musica
        editarButton = new JButton("Editar");
        
        
        GroupLayout layout = new GroupLayout(panelAdmMusica);
        panelAdmMusica.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        layout.setHorizontalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(TRAILING)
                    .addComponent(scrollPane)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(opcion)
                        .addComponent(agregarButton)
                        .addComponent(borrarButton)
                        .addComponent(editarButton)))
        );
        
        layout.setVerticalGroup(layout.createSequentialGroup()
            .addGroup(layout.createParallelGroup(LEADING)
                .addComponent(scrollPane))
            .addGroup(layout.createParallelGroup(BASELINE)
                .addComponent(opcion)
                .addComponent(agregarButton)
                .addComponent(borrarButton)
                .addComponent(editarButton))
        );
    }
    
    private Object[][] getData(){
        int cant = bd.getLibreria().size();
        Object[][] data = new Object[cant][3];
        int i=0;
        for (Map.Entry<Integer, Reproducible> entry : bd.getLibreria().entrySet()) {
            data[i][0] = entry.getValue().getId();
            data[i][1] = entry.getValue().getNombre();
            if (entry.getValue().getCantCanciones() > 1)
                data[i][2] = "Si";
            else
                data[i][2] = "No";
            i++;
        }
        return data;
    }
    
//    private void generarPanelAdmUsuario(){
//        panelAdmUsuario = new JPanel();
//    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 506, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 340, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents
    private void eventosComponentes(){
        
        agregarButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                agregarButtonActionPerformed(evt);
            }

            private void agregarButtonActionPerformed(ActionEvent evt) {
                if (opcion.getSelectedItem().equals(OPCION_REP))
                    new ventanaAgregarMusica(table,admin);
                else
                    new ventanaAgregarArtista(table,admin);
            }
        });
        
        borrarButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                borrarButtonActionPerformed(evt);
            }

            private void borrarButtonActionPerformed(ActionEvent evt) {
                
                DefaultTableModel model = (DefaultTableModel) table.getModel();
                
                if(opcion.getSelectedItem().equals(OPCION_REP))
                    admin.delReprodId((Integer)model.getValueAt(table.getSelectedRow(), 0));
                else if(opcion.getSelectedItem().equals(OPCION_ART))
                    admin.delArtista((Integer)model.getValueAt(table.getSelectedRow(), 0));
                else{
                    admin.delUsrId((String)model.getValueAt(table.getSelectedRow(), 0));
                }
                model.removeRow(table.getSelectedRow());
                borrarButton.setEnabled(false);
            }
        });
        
        editarButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editarButtonActionPerformed(evt);
            }

            private void editarButtonActionPerformed(ActionEvent evt) {
                if (table.getSelectedRow()!=-1){
                    if(opcion.getSelectedItem().equals(OPCION_REP))
                        new ventanaEditarMusica(table,admin);
                    else
                        new ventanaEditarArtista(table,admin);
                } 
                else{
                    JOptionPane.showMessageDialog (null, "No se ha seleccionado ningun elemento", "Atencion!", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        
        opcion.addActionListener (new ActionListener () {
        @Override
        public void actionPerformed(ActionEvent e) {
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            while (table.getRowCount()!=0)
                model.removeRow(table.getRowCount()-1);

            if (opcion.getSelectedItem().equals(OPCION_REP)){
                agregarButton.setEnabled(true);
                editarButton.setEnabled(true);
                model.setDataVector(getData(), new Object[]{"Id", "Nombre","Album"});
            }
            else if (opcion.getSelectedItem().equals(OPCION_USR)){
                agregarButton.setEnabled(false);
                editarButton.setEnabled(false);
                model.setColumnIdentifiers(new Object[]{"Nombre", "Constraseña"});
                for (Map.Entry<String, Cuenta> entry : bd.getCuentas().entrySet()) 
                    model.addRow(new Object[]{entry.getKey(),entry.getValue().getContraseña()});
            }
            else{
                agregarButton.setEnabled(true);
                editarButton.setEnabled(true);
                model.setColumnIdentifiers(new Object[]{"Id", "Nombre"});
                for (Map.Entry<Integer, Artista> entry : bd.getArtista().entrySet()) 
                    model.addRow(new Object[]{entry.getKey(),entry.getValue().getNombre()});
            }
            borrarButton.setEnabled(false);
        }  
        });
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
        public void valueChanged(ListSelectionEvent event) {
            borrarButton.setEnabled(true);
        }
    });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
