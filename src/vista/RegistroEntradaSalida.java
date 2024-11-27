/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package vista;

import java.text.SimpleDateFormat;
import java.util.Date;
public class RegistroEntradaSalida extends javax.swing.JFrame {

    /**
     * Creates new form RegistroEntradaSalida
     */
    public RegistroEntradaSalida() {
        initComponents();
        mostrarFechaYHora();
        this.setLocationRelativeTo(null);
    }
    
     // Método para mostrar la fecha y la hora en los respectivos JLabel
    private void mostrarFechaYHora() {
        // Crear un objeto Date con la fecha y hora actuales
        Date fechaActual = new Date();
        
        // Formatear la fecha
        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
        String fechaFormateada = formatoFecha.format(fechaActual);
        
        // Formatear la hora
        SimpleDateFormat formatoHora = new SimpleDateFormat("HH:mm:ss");
        String horaFormateada = formatoHora.format(fechaActual);
        
        // Mostrar la fecha en el JLabel de la fecha
        labelFecha.setText(fechaFormateada);
        
        // Mostrar la hora en el JLabel de la hora
        labelHora.setText(horaFormateada);
    }
     // Variables declaration - do not modify
    //private javax.swing.JLabel labelFecha;
    // End of variables declaration

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnMarcarEntrada = new javax.swing.JButton();
        btnMarcarSalida = new javax.swing.JButton();
        btnCerrar = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        labelFecha = new javax.swing.JLabel();
        labelHora = new javax.swing.JLabel();
        btnMarcarEntrada1 = new javax.swing.JButton();
        btnMarcarSalida1 = new javax.swing.JButton();
        btnCerrar1 = new javax.swing.JButton();

        btnMarcarEntrada.setFont(new java.awt.Font("Helvetica Neue", 0, 18)); // NOI18N
        btnMarcarEntrada.setText("Marcar Entrada");

        btnMarcarSalida.setFont(new java.awt.Font("Helvetica Neue", 0, 18)); // NOI18N
        btnMarcarSalida.setText("Marcar Salida");

        btnCerrar.setBackground(new java.awt.Color(51, 204, 255));
        btnCerrar.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        btnCerrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/cerrar2.png"))); // NOI18N
        btnCerrar.setText("CERRAR");
        btnCerrar.setBorder(null);
        btnCerrar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCerrarActionPerformed(evt);
            }
        });

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel2.setBackground(new java.awt.Color(0, 102, 102));

        jLabel1.setFont(new java.awt.Font("Calibri", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("REGISTRAR ASISTENCIA");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(118, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 455, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(95, 95, 95))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 54, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Fecha y Hora Actual", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Calibri", 1, 12))); // NOI18N

        labelFecha.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        labelFecha.setText("FECHA");

        labelHora.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        labelHora.setText("HORA");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(81, 81, 81)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelHora)
                    .addComponent(labelFecha))
                .addContainerGap(96, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelFecha)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(labelHora)
                .addGap(29, 29, 29))
        );

        btnMarcarEntrada1.setFont(new java.awt.Font("Helvetica Neue", 0, 18)); // NOI18N
        btnMarcarEntrada1.setText("Marcar Entrada");

        btnMarcarSalida1.setFont(new java.awt.Font("Helvetica Neue", 0, 18)); // NOI18N
        btnMarcarSalida1.setText("Marcar Salida");

        btnCerrar1.setBackground(new java.awt.Color(51, 204, 255));
        btnCerrar1.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        btnCerrar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/cerrar2.png"))); // NOI18N
        btnCerrar1.setText("CERRAR");
        btnCerrar1.setBorder(null);
        btnCerrar1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCerrar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCerrar1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(207, 207, 207)
                        .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 206, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnCerrar1, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnMarcarSalida1, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnMarcarEntrada1, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(243, 243, 243))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(85, 85, 85)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(66, 66, 66)
                .addComponent(btnMarcarEntrada1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnMarcarSalida1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 95, Short.MAX_VALUE)
                .addComponent(btnCerrar1))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 6, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarActionPerformed
        dispose();
    }//GEN-LAST:event_btnCerrarActionPerformed

    private void btnCerrar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrar1ActionPerformed
        dispose();
    }//GEN-LAST:event_btnCerrar1ActionPerformed

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
            java.util.logging.Logger.getLogger(RegistroEntradaSalida.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(RegistroEntradaSalida.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(RegistroEntradaSalida.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(RegistroEntradaSalida.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new RegistroEntradaSalida().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCerrar;
    private javax.swing.JButton btnCerrar1;
    private javax.swing.JButton btnMarcarEntrada;
    private javax.swing.JButton btnMarcarEntrada1;
    private javax.swing.JButton btnMarcarSalida;
    private javax.swing.JButton btnMarcarSalida1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JLabel labelFecha;
    private javax.swing.JLabel labelHora;
    // End of variables declaration//GEN-END:variables
}
//