/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package frontend;

import backend.*;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;
import javax.swing.UnsupportedLookAndFeelException;



 
/**
 *
 * @author vyn
 */
public class FrmSopir extends javax.swing.JFrame {

    private int hiddenIdSopir = 0;

    public FrmSopir() {
        initComponents();
        kosongkanForm();
        txtSopir.setEditable(false);
    }
    
    // Di bawah ini baru lanjut ke kosongkanForm()...

    // =========================================================================
    // FUNGSI UTAMA (LOGIKA FORM & TABEL)
    // =========================================================================
    
    public void kosongkanForm() {
        hiddenIdSopir = 0;
        txtNama.setText("");
        txtSim.setText("");
        txtnotlpn.setText("");
        txtCari.setText(""); 
        
        try {
            txtSopir.setText(Sopir.generateKodeOtomatis()); 
        } catch (Exception e) {
            txtSopir.setText("Spr-001");
        }
        
        btnSimpan.setText("Simpan");

        jComboBox1.removeAllItems();
        jComboBox1.addItem("tersedia");
        jComboBox1.addItem("nonaktif");
        jComboBox1.setSelectedIndex(0);
        jComboBox1.setEnabled(false); 
        
        tampilkanData(); 
    }

    public final void tampilkanData() {
        String[] kolom = {"ID Sopir", "Kode Sopir", "Nama Sopir", "No SIM", "No Telpon", "Status"};
        ArrayList<Sopir> list = Sopir.getAll();
        Object rowData[] = new Object[6];

        tblSopir.setModel(new DefaultTableModel(new Object[][]{}, kolom));

        for (Sopir s : list) {
            rowData[0] = s.getIdSopir();
            rowData[1] = s.getKodeSopir();
            rowData[2] = s.getNamaSopir();
            rowData[3] = s.getNoSim();
            rowData[4] = s.getNoTelp();
            rowData[5] = s.getStatus();

            ((DefaultTableModel) tblSopir.getModel()).addRow(rowData);
        }
    }

    public final void cari(String keyword) {
        String[] kolom = {"ID Sopir", "Kode Sopir", "Nama Sopir", "No SIM", "No Telpon", "Status"};
        ArrayList<Sopir> list = Sopir.search(keyword);
        Object rowData[] = new Object[6];

        tblSopir.setModel(new DefaultTableModel(new Object[][]{}, kolom));

        for (Sopir s : list) {
            rowData[0] = s.getIdSopir();
            rowData[1] = s.getKodeSopir();
            rowData[2] = s.getNamaSopir();
            rowData[3] = s.getNoSim();
            rowData[4] = s.getNoTelp();
            rowData[5] = s.getStatus();

            ((DefaultTableModel) tblSopir.getModel()).addRow(rowData);
        }
    }

    private void simpanDataLogika() {
        if (txtSopir.getText().trim().isEmpty() || txtNama.getText().trim().isEmpty() 
                || txtSim.getText().trim().isEmpty() || txtnotlpn.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Semua data wajib diisi!", "Validasi Form", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String noSim = txtSim.getText().trim();
        String noTelp = txtnotlpn.getText().trim();

        boolean isSimValid = noSim.matches("[0-9]+");
        boolean isTelpValid = noTelp.matches("[0-9]+");

        if (!isSimValid && !isTelpValid) {
            JOptionPane.showMessageDialog(this, "No. SIM dan No. Telepon harus berupa angka!", "Validasi Format", JOptionPane.WARNING_MESSAGE);
            txtSim.requestFocus();
            return;
        }

        if (!isSimValid) {
            JOptionPane.showMessageDialog(this, "No. SIM harus berupa angka!", "Validasi Format", JOptionPane.WARNING_MESSAGE);
            txtSim.requestFocus(); 
            return;
        }

        if (!isTelpValid) {
            JOptionPane.showMessageDialog(this, "No. Telepon harus berupa angka!", "Validasi Format", JOptionPane.WARNING_MESSAGE);
            txtnotlpn.requestFocus(); 
            return;
        }

        if (Sopir.cekSIM(noSim, hiddenIdSopir)) {
            JOptionPane.showMessageDialog(this, "No SIM '" + noSim + "' sudah digunakan oleh sopir lain!", "Duplikasi Data", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Sopir s = new Sopir();
        s.setIdSopir(hiddenIdSopir); 
        s.setKodeSopir(txtSopir.getText().trim());
        s.setNamaSopir(txtNama.getText().trim());
        s.setNoSim(noSim);
        s.setNoTelp(noTelp);
        s.setStatus(jComboBox1.getSelectedItem().toString()); 
        s.save();
        
        tampilkanData();
        
        if (hiddenIdSopir == 0) {
            JOptionPane.showMessageDialog(this, "Data sopir baru berhasil disimpan!", "Sukses Simpan", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Data sopir berhasil diperbarui!", "Sukses Update", JOptionPane.INFORMATION_MESSAGE);
        }
        
        kosongkanForm(); 
    }

   private void hapusDataLogika() {
    if (hiddenIdSopir == 0) {
        JOptionPane.showMessageDialog(this, "Pilih baris di tabel terlebih dahulu untuk dihapus!", "Peringatan", JOptionPane.WARNING_MESSAGE);
        return;
    }
    
    DefaultTableModel model = (DefaultTableModel) tblSopir.getModel();
    int row = tblSopir.getSelectedRow();
    if (model.getValueAt(row, 5).toString().equals("bertugas")) {
        JOptionPane.showMessageDialog(this, "Sopir yang sedang 'bertugas' di lapangan tidak boleh dihapus!", "Akses Ditolak", JOptionPane.ERROR_MESSAGE);
        return;
    }
    
    // --- DI SINI PERUBAHANNYA ---
    // Kita buat array pilihan tombol. Yang ditulis pertama akan muncul di sebelah KIRI.
    Object[] options = {"NO", "YES"}; 
    
    int opsi = JOptionPane.showOptionDialog(
            this, 
            "Apakah Anda yakin ingin menghapus data sopir ini?", 
            "Konfirmasi Hapus",
            JOptionPane.YES_NO_OPTION, 
            JOptionPane.QUESTION_MESSAGE, 
            null, 
            options, // Menggunakan array tombol yang kita buat
            options[1] // Default tombol yang fokus aktif (Yes)
    );
    
    // Karena kita pakai showOptionDialog, tombol pertama (index 0) adalah "Yes"
    if (opsi == 1) { 
        Sopir s = new Sopir();
        s.setIdSopir(hiddenIdSopir);
        s.delete();
        kosongkanForm();
        tampilkanData();
        JOptionPane.showMessageDialog(this, "Data sopir berhasil dihapus!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
    }
}
    private void klikTabelLogika() {
        DefaultTableModel model = (DefaultTableModel) tblSopir.getModel();
        int row = tblSopir.getSelectedRow();
        
        if (row >= 0) {
            hiddenIdSopir = Integer.parseInt(model.getValueAt(row, 0).toString());
            
            txtSopir.setText(model.getValueAt(row, 1).toString());
            txtNama.setText(model.getValueAt(row, 2).toString());
            txtSim.setText(model.getValueAt(row, 3).toString());
            txtnotlpn.setText(model.getValueAt(row, 4).toString());
            
            btnSimpan.setText("Update");
            
            String statusTabel = model.getValueAt(row, 5).toString();
            
            if (statusTabel.equals("bertugas")) {
                jComboBox1.removeAllItems();
                jComboBox1.addItem("bertugas");
                jComboBox1.setSelectedIndex(0);
                jComboBox1.setEnabled(false); 
            } else {
                jComboBox1.removeAllItems();
                jComboBox1.addItem("tersedia");
                jComboBox1.addItem("nonaktif");
                jComboBox1.setSelectedItem(statusTabel);
                jComboBox1.setEnabled(true); 
            }
        }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtSopir = new javax.swing.JTextField();
        txtNama = new javax.swing.JTextField();
        txtSim = new javax.swing.JTextField();
        txtnotlpn = new javax.swing.JTextField();
        btnSimpan = new javax.swing.JButton();
        btnHapus = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        txtCari = new javax.swing.JTextField();
        btnCari = new javax.swing.JButton();
        jComboBox1 = new javax.swing.JComboBox<>();
        btnClear = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblSopir = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Kode Sopir");

        jLabel2.setText("Nama Sopir");

        jLabel3.setText("No Sim");

        jLabel4.setText("No Telepon");

        jLabel5.setText("Status");

        txtSopir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSopirActionPerformed(evt);
            }
        });

        txtNama.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNamaActionPerformed(evt);
            }
        });

        txtSim.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSimActionPerformed(evt);
            }
        });

        txtnotlpn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtnotlpnActionPerformed(evt);
            }
        });

        btnSimpan.setText("Simpan");
        btnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanActionPerformed(evt);
            }
        });

        btnHapus.setText("Hapus");
        btnHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel7.setText("MASTER SOPIR");

        txtCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCariActionPerformed(evt);
            }
        });

        btnCari.setText("Cari");
        btnCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCariActionPerformed(evt);
            }
        });

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tersedia", "Nonaktif" }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        btnClear.setText("Clear");
        btnClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearActionPerformed(evt);
            }
        });

        tblSopir.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Kode Sopir", "Nama Sopir", "No SIM", "No Telepon", "Status"
            }
        ));
        tblSopir.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblSopirMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblSopir);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(279, 279, 279)
                        .addComponent(jLabel7))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtSopir))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtnotlpn))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(105, 105, 105)
                                .addComponent(jComboBox1, 0, 188, Short.MAX_VALUE)
                                .addGap(143, 143, 143))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtNama))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(20, 20, 20)
                                        .addComponent(btnSimpan)
                                        .addGap(18, 18, 18)
                                        .addComponent(btnClear)
                                        .addGap(18, 18, 18)
                                        .addComponent(btnHapus)))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtSim)))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtCari, javax.swing.GroupLayout.PREFERRED_SIZE, 447, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnCari))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 452, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(15, 15, 15))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtSopir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCari)
                    .addComponent(txtCari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txtNama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(9, 9, 9)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txtSim, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(9, 9, 9)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(txtnotlpn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(9, 9, 9)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(70, 70, 70)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnSimpan)
                            .addComponent(btnClear)
                            .addComponent(btnHapus)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(56, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtSopirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSopirActionPerformed

    }//GEN-LAST:event_txtSopirActionPerformed

    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanActionPerformed
                                      simpanDataLogika();    

    }//GEN-LAST:event_btnSimpanActionPerformed

    private void btnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusActionPerformed
hapusDataLogika();
    }//GEN-LAST:event_btnHapusActionPerformed

    
    private void btnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCariActionPerformed
     
    cari(txtCari.getText());
    }//GEN-LAST:event_btnCariActionPerformed

    private void txtnotlpnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtnotlpnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtnotlpnActionPerformed

    private void txtNamaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNamaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNamaActionPerformed

    private void txtSimActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSimActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSimActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void txtCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCariActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_txtCariActionPerformed

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
        // TODO add your handling code here:
      kosongkanForm();
    }//GEN-LAST:event_btnClearActionPerformed

    private void tblSopirMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSopirMouseClicked
        // TODO add your handling code here:
        klikTabelLogika();
    }//GEN-LAST:event_tblSopirMouseClicked

        public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmSopir.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(() -> {
            new FrmSopir().setVisible(true);
        });
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCari;
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnHapus;
    private javax.swing.JButton btnSimpan;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblSopir;
    private javax.swing.JTextField txtCari;
    private javax.swing.JTextField txtNama;
    private javax.swing.JTextField txtSim;
    private javax.swing.JTextField txtSopir;
    private javax.swing.JTextField txtnotlpn;
    // End of variables declaration//GEN-END:variables

}