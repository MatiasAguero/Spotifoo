/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaz;

import Spotifoo.DataManager.BaseDatos;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.BoxLayout;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;

/**
 *
 * @author nico
 */
public class ventanaUsuario extends javax.swing.JFrame {
    
    BaseDatos bd;
    public ventanaUsuario() {
        initComponents();
        setLocationRelativeTo(null);
        this.setTitle("Spotifoo");
        getContentPane().setLayout(new BoxLayout(getContentPane(),BoxLayout.Y_AXIS));
        
        //Genera el componente de las pestañas
        JTabbedPane tabs = new JTabbedPane();
        //Lo añade en primer lugar en el frame
        this.add(tabs, 0);
        
        //Genera los componentes para insertar dentro de la pestaña principal
        //El componente donde van las playlist
        JScrollPane playlists = new JScrollPane(new JList());
        
        //**********************************************************
        //************INSERTAR EXPLORADOR DE BIBLIOTECA*************
        
        //Crea el divisor superior con la playlist y el explorador de la biblioteca 
        JSplitPane topSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, playlists, new JPanel());
        topSplit.setDividerSize(1);
        topSplit.setEnabled(false);
        //Crea el divisor inferior, donde va el reproductor
        JPanel reproductor = new JPanel();
        
        //Inserta en la pestaña Principal todos los componentes creados
        JSplitPane tabPrincipal = new JSplitPane(JSplitPane.VERTICAL_SPLIT, topSplit, reproductor);
        tabs.addTab("Explorar", tabPrincipal);
        
        //tab = new JTextArea();

        //JComponent panel2 = makeTextPanel("Panel #2");
        tabs.insertTab("Mi Bilbioteca", null, new JPanel(), null, 1);
        
        tabPrincipal.setDividerSize(1);
        tabPrincipal.setEnabled(false);
        tabPrincipal.setResizeWeight(0.9);
                
        /*JPanel tab = new JPanel();
        JScrollPane scrollPanel=new JScrollPane(tab);
        tabs.addTab("Explorar", scrollPanel);
        
        
        tab = new JPanel();
        scrollPanel=new JScrollPane(tab);
        tabs.addTab("Tu Biblioteca", scrollPanel);*/
        addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                bd = BaseDatos.getBaseDatos();
                bd.guardarDatos();
                System.exit(0);
            }
        });
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();

        jButton1.setText("Salir");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(359, 359, 359)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(378, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(379, 379, 379)
                .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ventanaUsuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ventanaUsuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ventanaUsuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ventanaUsuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ventanaUsuario().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    // End of variables declaration//GEN-END:variables
}
