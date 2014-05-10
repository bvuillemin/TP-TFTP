package Interface;

import java.awt.*;
import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import tptftp.*;

public class Interface extends javax.swing.JFrame {

    public Interface() {
        initComponents();
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        fileChooser = new javax.swing.JFileChooser();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        ErreurEnvoyer = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        TextPaneEnvoyer = new javax.swing.JTextPane();
        SelectionnerFichier = new javax.swing.JButton();
        ValiderEnvoyer = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        ValiderRecevoir = new javax.swing.JToggleButton();
        ErreurRecevoir = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        TextPaneRecevoir = new javax.swing.JTextPane();
        jScrollPane4 = new javax.swing.JScrollPane();
        TextPaneRecevoir1 = new javax.swing.JTextPane();
        SelectionnerFichier1 = new javax.swing.JButton();

        fileChooser.setDialogTitle("Choisissez le fichier à envoyer");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("TP TFTP");

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Pierre Reynaud, Dimitri Rodarie, Benoit Vuillemin");

        ErreurEnvoyer.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        ErreurEnvoyer.setForeground(new java.awt.Color(255, 0, 0));
        ErreurEnvoyer.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jScrollPane2.setViewportView(TextPaneEnvoyer);

        SelectionnerFichier.setText("FIchier...");
        SelectionnerFichier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SelectionnerFichierActionPerformed(evt);
            }
        });

        ValiderEnvoyer.setText("Valider");
        ValiderEnvoyer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ValiderEnvoyerActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ErreurEnvoyer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane2)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(SelectionnerFichier, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ValiderEnvoyer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(ErreurEnvoyer, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(SelectionnerFichier)
                    .addComponent(ValiderEnvoyer))
                .addGap(0, 7, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Envoyer", jPanel1);

        ValiderRecevoir.setText("Valider");
        ValiderRecevoir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ValiderRecevoirActionPerformed(evt);
            }
        });

        ErreurRecevoir.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        ErreurRecevoir.setForeground(new java.awt.Color(255, 0, 0));
        ErreurRecevoir.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jScrollPane3.setViewportView(TextPaneRecevoir);

        jScrollPane4.setViewportView(TextPaneRecevoir1);

        SelectionnerFichier1.setText("Dossier");
        SelectionnerFichier1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SelectionnerFichier1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ErreurRecevoir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jScrollPane4)
                            .addComponent(SelectionnerFichier1, javax.swing.GroupLayout.DEFAULT_SIZE, 136, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(ValiderRecevoir, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
                            .addComponent(jScrollPane3))))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(ErreurRecevoir, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 59, Short.MAX_VALUE)
                    .addComponent(jScrollPane3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(SelectionnerFichier1)
                    .addComponent(ValiderRecevoir))
                .addGap(29, 29, 29))
        );

        jTabbedPane1.addTab("Recevoir", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 471, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(25, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

   
    private void SelectionnerFichierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SelectionnerFichierActionPerformed
        ErreurEnvoyer.setText("");
        int returnVal = fileChooser.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try {
                TextPaneEnvoyer.setText(file.getAbsolutePath());
            } catch (Exception ex) {
                System.out.println("Problème d'accès au fichier" + file.getAbsolutePath());
            }
        } else {
            System.out.println("Annulé.");
        }
    }//GEN-LAST:event_SelectionnerFichierActionPerformed

    
    private void ValiderEnvoyerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ValiderEnvoyerActionPerformed
        try {
            EnvoiTFTP envoi = new EnvoiTFTP(InetAddress.getLocalHost());
            if ((TextPaneEnvoyer.getText() == null) || (TextPaneEnvoyer.getText().equals(""))) {
                ErreurEnvoyer.setText("Aucun fichier sélectionné");
            } else {
                File f = new File(TextPaneEnvoyer.getText());
                if (f.exists()) {
                    envoi.SendFile(TextPaneEnvoyer.getText(), null);
                    //Lancer la fonction de connexion
                } else {
                    ErreurEnvoyer.setText("Fichier non trouvé");
                }
            } 
        } catch (UnknownHostException ex) {
            Logger.getLogger(Interface.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_ValiderEnvoyerActionPerformed

    
    private void ValiderRecevoirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ValiderRecevoirActionPerformed
        try {
            if ((TextPaneRecevoir.getText() == null) || (TextPaneRecevoir.getText().equals(""))) {
                ErreurRecevoir.setText("Aucun fichier sélectionné");
            }
            else {
                ReceptionTFTP reception = new ReceptionTFTP(InetAddress.getLocalHost(),TextPaneRecevoir.getText(), TextPaneRecevoir1.getText());
                reception.ReceiveFile();
            }
        } catch (UnknownHostException ex) {
            Logger.getLogger(Interface.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_ValiderRecevoirActionPerformed

    
    private void SelectionnerFichier1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SelectionnerFichier1ActionPerformed
        ErreurEnvoyer.setText("");
        JFileChooser fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        
        int returnVal = fc.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            try {
                TextPaneRecevoir1.setText(file.getAbsolutePath());
            } catch (Exception ex) {
                System.out.println("Problème d'accès au fichier" + file.getAbsolutePath());
            }
        } else {
            System.out.println("Annulé.");
        }
    }//GEN-LAST:event_SelectionnerFichier1ActionPerformed

    
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
            java.util.logging.Logger.getLogger(Interface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Interface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Interface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Interface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Interface().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel ErreurEnvoyer;
    private javax.swing.JLabel ErreurRecevoir;
    private javax.swing.JButton SelectionnerFichier;
    private javax.swing.JButton SelectionnerFichier1;
    private javax.swing.JTextPane TextPaneEnvoyer;
    private javax.swing.JTextPane TextPaneRecevoir;
    private javax.swing.JTextPane TextPaneRecevoir1;
    private javax.swing.JButton ValiderEnvoyer;
    private javax.swing.JToggleButton ValiderRecevoir;
    private javax.swing.JFileChooser fileChooser;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTabbedPane jTabbedPane1;
    // End of variables declaration//GEN-END:variables
}
