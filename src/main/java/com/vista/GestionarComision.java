package com.vista;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import com.conexion.Conexion;
import com.dao.ControlComision;
import com.modelo.Comision;
import javax.swing.DefaultComboBoxModel;
import java.sql.*;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author KamiVela
 */
public class GestionarComision extends javax.swing.JFrame {

    DefaultComboBoxModel modelo;
    DefaultComboBoxModel modelo2;
    Comision comi = new Comision();
    ControlComision dao = new ControlComision();

    public GestionarComision() {
        initComponents();
        modelo = new DefaultComboBoxModel();
        modelo2 = new DefaultComboBoxModel();

        tabla();
        tbComisiones.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        cargarlistaPersonas();
        cargarlistaUsuarios();
        TableColumnModel columnModel = tbComisiones.getColumnModel();

        columnModel.getColumn(0).setPreferredWidth(50);
        columnModel.getColumn(1).setPreferredWidth(100);
        columnModel.getColumn(2).setPreferredWidth(100);
        columnModel.getColumn(3).setPreferredWidth(100);
        columnModel.getColumn(4).setPreferredWidth(100);
        columnModel.getColumn(5).setPreferredWidth(100);
        columnModel.getColumn(6).setPreferredWidth(100);
        columnModel.getColumn(7).setPreferredWidth(100);

    }

    public void cargarlistaPersonas() {
        Conexion con = new Conexion();
        Connection cn;
        ResultSet res;
        try {
            con.conectar();
            String sql = "Select * from personas";
            PreparedStatement pre = con.getCon().prepareCall(sql);
            res = pre.executeQuery();
            modelo.removeAllElements();
            while (res.next()) {
                modelo.addElement(res.getString("Nombre"));
            }
            jComboPersonas.setModel(modelo);
            con.desconectar();
        } catch (Exception e) {
            System.out.println("ERROR: failed to load HSQLDB JDBC driver.");
            e.printStackTrace();
        }

    }

    public void cargarlistaUsuarios() {
        Conexion con = new Conexion();
        Connection cn;
        ResultSet res;
        try {
            con.conectar();
            String sql = "Select * from usuarios";
            PreparedStatement pre = con.getCon().prepareCall(sql);
            res = pre.executeQuery();
            modelo2.removeAllElements();
            while (res.next()) {
                modelo2.addElement(res.getString("Usuario"));
            }
            jComboUsuarios.setModel(modelo2);
            con.desconectar();
        } catch (Exception e) {
            System.out.println("ERROR: failed to load HSQLDB JDBC driver.");
            e.printStackTrace();
        }

    }

    public void insertar() throws Exception {
        comi.setId(ICONIFIED);
        comi.setPersona(this.jComboPersonas.getSelectedItem().toString());
        comi.setUsuario(this.jComboUsuarios.getSelectedItem().toString());
        comi.setEnero(Double.parseDouble(this.jSEnero.getValue().toString()));
        comi.setFebrero(Double.parseDouble(this.jSFebrero.getValue().toString()));
        comi.setMarzo(Double.parseDouble(this.jSMarzo.getValue().toString()));
        comi.setPromedio((Double.parseDouble(this.jSEnero.getValue().toString()) + Double.parseDouble(this.jSFebrero.getValue().toString()) + Double.parseDouble(this.jSMarzo.getValue().toString())) / 3);
        comi.setTotal(Double.parseDouble(this.jSEnero.getValue().toString()) + Double.parseDouble(this.jSFebrero.getValue().toString()) + Double.parseDouble(this.jSMarzo.getValue().toString()));
        dao.insertar(comi);
        tabla();

        JOptionPane.showMessageDialog(null, "Datos insertados");

    }

    public void modificar() {
        try {
            comi.setId(Integer.parseInt(this.txtcod.getText()));
            comi.setPersona(this.jComboPersonas.getSelectedItem().toString());
            comi.setUsuario(this.jComboUsuarios.getSelectedItem().toString());

            comi.setEnero(Double.parseDouble(this.jSEnero.getValue().toString()));
            comi.setFebrero(Double.parseDouble(this.jSFebrero.getValue().toString()));
            comi.setMarzo(Double.parseDouble(this.jSMarzo.getValue().toString()));
            comi.setPromedio((Double.parseDouble(this.jSEnero.getValue().toString()) + Double.parseDouble(this.jSFebrero.getValue().toString()) + Double.parseDouble(this.jSMarzo.getValue().toString())) / 3);
            comi.setTotal(Double.parseDouble(this.jSEnero.getValue().toString()) + Double.parseDouble(this.jSFebrero.getValue().toString()) + Double.parseDouble(this.jSMarzo.getValue().toString()));

            int SioNo = JOptionPane.showConfirmDialog(this, "Desea modificar?", "Confirmacion",
                    JOptionPane.YES_NO_OPTION);
            if (SioNo == 0) {
                dao.modificar(comi);
                JOptionPane.showMessageDialog(this, "Datos modificados");
                tabla();
                limpiar();
            } else {
                limpiar();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void eliminar() {
        try {
            comi.setId(Integer.parseInt(this.txtcod.getText()));
            int SioNo = JOptionPane.showConfirmDialog(this, "Desea eliminar?", "Confirmacion",
                    JOptionPane.YES_NO_OPTION);
            if (SioNo == 0) {
                dao.eliminar(comi);
                JOptionPane.showMessageDialog(this, "Datos modificados");
                tabla();
            } else {
                limpiar();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void tabla() {
        String[] columnas = {"Id", "Persona", "Usuario", "Enero", "Febrero", "Marzo", "Promedio", "Total"};
        Object[] obj = new Object[8];
        DefaultTableModel tabla = new DefaultTableModel(null, columnas);
        List ls;
        try {
            ls = dao.mostrar();
            for (int i = 0; i < ls.size(); i++) {
                comi = (Comision) ls.get(i);
                obj[0] = comi.getId();
                obj[1] = comi.getPersona();
                obj[2] = comi.getUsuario();
                obj[3] = comi.getEnero();
                obj[4] = comi.getFebrero();
                obj[5] = comi.getMarzo();
                obj[6] = comi.getPromedio();
                obj[7] = comi.getTotal();
                tabla.addRow(obj);
            }
            this.tbComisiones.setModel(tabla);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void limpiar() {
        this.txtcod.setText("");
        this.jComboPersonas.setSelectedIndex(0);
        this.jComboUsuarios.setSelectedIndex(0);
        this.jSEnero.getModel().setValue(0);
        this.jSFebrero.getModel().setValue(0);
        this.jSMarzo.getModel().setValue(0);
        this.jSPromedio.getModel().setValue(0);
        this.jSTotal.getModel().setValue(0);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel10 = new javax.swing.JLabel();
        buttonGroup1 = new javax.swing.ButtonGroup();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbComisiones = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        btnEliminar = new javax.swing.JButton();
        btnModificar = new javax.swing.JButton();
        btnLimpiar = new javax.swing.JButton();
        btnInsertar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtcod = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jComboPersonas = new javax.swing.JComboBox<>();
        jLabel12 = new javax.swing.JLabel();
        jComboUsuarios = new javax.swing.JComboBox<>();
        jSEnero = new javax.swing.JSpinner();
        jLabel7 = new javax.swing.JLabel();
        jSFebrero = new javax.swing.JSpinner();
        jSMarzo = new javax.swing.JSpinner();
        jLabel15 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jSPromedio = new javax.swing.JSpinner();
        jLabel19 = new javax.swing.JLabel();
        jSTotal = new javax.swing.JSpinner();
        btnPersonas = new javax.swing.JButton();
        btnUsuarios = new javax.swing.JButton();

        jLabel10.setText("jLabel10");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tbComisiones.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tbComisiones.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbComisionesMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbComisiones);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 510, 600, 130));

        jPanel1.setBackground(new java.awt.Color(255, 153, 153));

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Gestionar Comision");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(196, Short.MAX_VALUE)
                .addComponent(jLabel11)
                .addContainerGap(181, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel11)
                .addContainerGap(22, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 730, -1));

        btnEliminar.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        btnEliminar.setText("Eliminar");
        btnEliminar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnEliminarMouseClicked(evt);
            }
        });
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });
        getContentPane().add(btnEliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 290, 150, 70));

        btnModificar.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        btnModificar.setText("Modificar");
        btnModificar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnModificarMouseClicked(evt);
            }
        });
        btnModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarActionPerformed(evt);
            }
        });
        getContentPane().add(btnModificar, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 200, 150, 70));

        btnLimpiar.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        btnLimpiar.setText("Limpiar");
        btnLimpiar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnLimpiarMouseClicked(evt);
            }
        });
        btnLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarActionPerformed(evt);
            }
        });
        getContentPane().add(btnLimpiar, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 380, 150, 70));

        btnInsertar.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        btnInsertar.setText("Insetar");
        btnInsertar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnInsertarMouseClicked(evt);
            }
        });
        btnInsertar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInsertarActionPerformed(evt);
            }
        });
        getContentPane().add(btnInsertar, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 110, 150, 70));

        jLabel1.setText("Codigo:");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 140, -1, -1));

        jLabel5.setText("Enero:");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 270, -1, -1));

        txtcod.setEnabled(false);
        txtcod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtcodActionPerformed(evt);
            }
        });
        getContentPane().add(txtcod, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 140, 160, -1));

        jLabel9.setText("Personas:");
        getContentPane().add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 180, -1, -1));

        jComboPersonas.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        getContentPane().add(jComboPersonas, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 180, 160, -1));

        jLabel12.setText("Usuarios:");
        getContentPane().add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 220, -1, -1));

        jComboUsuarios.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        getContentPane().add(jComboUsuarios, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 220, 160, -1));
        getContentPane().add(jSEnero, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 260, 160, 30));

        jLabel7.setText("Febrero:");
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 320, -1, -1));
        getContentPane().add(jSFebrero, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 310, 160, 30));
        getContentPane().add(jSMarzo, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 360, 160, 30));

        jLabel15.setText("Marzo:");
        getContentPane().add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 370, -1, -1));

        jLabel17.setText("Promedio:");
        getContentPane().add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 420, -1, -1));

        jSPromedio.setEnabled(false);
        getContentPane().add(jSPromedio, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 410, 160, 30));

        jLabel19.setText("Total:");
        getContentPane().add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 470, -1, -1));

        jSTotal.setEnabled(false);
        getContentPane().add(jSTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 460, 160, 30));

        btnPersonas.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        btnPersonas.setText("Personas");
        btnPersonas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnPersonasMouseClicked(evt);
            }
        });
        btnPersonas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPersonasActionPerformed(evt);
            }
        });
        getContentPane().add(btnPersonas, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 660, 150, 70));

        btnUsuarios.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        btnUsuarios.setText("Usuarios");
        btnUsuarios.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnUsuariosMouseClicked(evt);
            }
        });
        btnUsuarios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUsuariosActionPerformed(evt);
            }
        });
        getContentPane().add(btnUsuarios, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 660, 150, 70));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnModificarActionPerformed

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnLimpiarActionPerformed

    private void btnInsertarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInsertarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnInsertarActionPerformed

    private void btnInsertarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnInsertarMouseClicked
        // TODO add your handling code here:
        try {
            insertar();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnInsertarMouseClicked

    private void tbComisionesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbComisionesMouseClicked

        // TODO add your handling code here:
        int fila = this.tbComisiones.getSelectedRow();
        this.txtcod.setText(String.valueOf(this.tbComisiones.getValueAt(fila, 0)));
        this.jComboPersonas.setSelectedItem(String.valueOf(this.tbComisiones.getValueAt(fila, 1)));
        this.jComboUsuarios.setSelectedItem(String.valueOf(this.tbComisiones.getValueAt(fila, 2)));
        this.jSEnero.setValue(this.tbComisiones.getValueAt(fila, 3));
        this.jSFebrero.setValue(this.tbComisiones.getValueAt(fila, 4));
        this.jSMarzo.setValue(this.tbComisiones.getValueAt(fila, 5));
        this.jSPromedio.setValue(this.tbComisiones.getValueAt(fila, 6));
        this.jSTotal.setValue(this.tbComisiones.getValueAt(fila, 7));


    }//GEN-LAST:event_tbComisionesMouseClicked

    private void btnModificarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnModificarMouseClicked
        // TODO add your handling code here:
        modificar();
    }//GEN-LAST:event_btnModificarMouseClicked

    private void btnEliminarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEliminarMouseClicked
        // TODO add your handling code here:
        eliminar();
    }//GEN-LAST:event_btnEliminarMouseClicked

    private void btnLimpiarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLimpiarMouseClicked
        // TODO add your handling code here:
        limpiar();
    }//GEN-LAST:event_btnLimpiarMouseClicked

    private void txtcodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtcodActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtcodActionPerformed

    private void btnPersonasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPersonasMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btnPersonasMouseClicked

    private void btnPersonasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPersonasActionPerformed

        GestionarPersona gp = new GestionarPersona();

        gp.setVisible(true);

        // TODO add your handling code here:
    }//GEN-LAST:event_btnPersonasActionPerformed

    private void btnUsuariosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnUsuariosMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btnUsuariosMouseClicked

    private void btnUsuariosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUsuariosActionPerformed
        // TODO add your handling code here:

        GestionarUsuario gu = new GestionarUsuario();

        gu.setVisible(true);
    }//GEN-LAST:event_btnUsuariosActionPerformed

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
            java.util.logging.Logger.getLogger(GestionarComision.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GestionarComision.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GestionarComision.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GestionarComision.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GestionarComision().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnInsertar;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JButton btnModificar;
    private javax.swing.JButton btnPersonas;
    private javax.swing.JButton btnUsuarios;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> jComboPersonas;
    private javax.swing.JComboBox<String> jComboUsuarios;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSpinner jSEnero;
    private javax.swing.JSpinner jSFebrero;
    private javax.swing.JSpinner jSMarzo;
    private javax.swing.JSpinner jSPromedio;
    private javax.swing.JSpinner jSTotal;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tbComisiones;
    private javax.swing.JTextField txtcod;
    // End of variables declaration//GEN-END:variables
}
