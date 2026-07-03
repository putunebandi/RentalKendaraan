/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package frontend;


import backend.DBHelper;
import java.sql.ResultSet;
import backend.Tarif;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Rizki
 */
public class FrmTarif extends javax.swing.JFrame {
 
private int idTarif = 0;
    private final DecimalFormat rupiah;
    
    {
        DecimalFormatSymbols simbol = new DecimalFormatSymbols();
        simbol.setGroupingSeparator('.');
        simbol.setDecimalSeparator(',');
        rupiah = new DecimalFormat("#,###");
        rupiah.setDecimalFormatSymbols(simbol);
    }

    public FrmTarif() {
        initComponents();
        tampilData();
        kosongkanForm(); 
        
        // Pasang pendeteksi ketikan otomatis berformat titik untuk semua kolom angka
        aturFormatRupiahKetik(txtTarifHarian);
        aturFormatRupiahKetik(txtTarifSopir);
        aturFormatRupiahKetik(txtDenda);
    }

    /**
     * Logika untuk memformat teks dengan titik secara real-time saat user mengetik
     */
    private void aturFormatRupiahKetik(JTextField field) {
        field.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent evt) {
                String teks = field.getText().replace(".", "").trim();
                if (!teks.isEmpty()) {
                    try {
                        double angka = Double.parseDouble(teks);
                        field.setText(rupiah.format(angka));
                    } catch (NumberFormatException e) {
                        // Jika bukan angka, abaikan
                    }
                }
            }
        });
    }

    private void kosongkanForm() {
        idTarif = 0;
        txtTarifHarian.setText("");
        txtTarifSopir.setText("");
        txtDenda.setText("");
        btnSimpan.setText("Simpan");
        
        tampilComboKendaraanBelumAdaTarif();
        
        cmbKendaraan.setEnabled(true); 
        cmbKendaraan.setSelectedIndex(-1); 
        
        txtTarifHarian.setEnabled(false);
        txtTarifSopir.setEnabled(false);
        txtDenda.setEnabled(false);
        
        btnSimpan.setEnabled(false);
        btnHapus.setEnabled(false);
        
        tblTarif.clearSelection();
    }

    private void tampilComboKendaraanBelumAdaTarif() {
        ((javax.swing.DefaultComboBoxModel)cmbKendaraan.getModel()).removeAllElements();
        
        String query = "SELECT id_kendaraan, kode_kendaraan, no_polisi, merk FROM kendaraan "
                     + "WHERE id_kendaraan NOT IN (SELECT id_kendaraan FROM tarif)";
        ResultSet rs = DBHelper.selectQuery(query);
        
        try {
            while (rs.next()) {
                Tarif itemCombo = new Tarif();
                itemCombo.setIdKendaraan(rs.getInt("id_kendaraan")); 
                itemCombo.setKodeKendaraan(rs.getString("kode_kendaraan"));
                itemCombo.setNoPolisi(rs.getString("no_polisi"));
                itemCombo.setColMerk(rs.getString("merk"));
                
                ((javax.swing.DefaultComboBoxModel)cmbKendaraan.getModel()).addElement(itemCombo);
            }
        } catch (Exception e) {
            System.out.println("Error Load Combo: " + e.getMessage());
        }
    }

    private void tampilData() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Kode");
        model.addColumn("No Polisi");
        model.addColumn("Merk");
        model.addColumn("Tarif");
        model.addColumn("Tarif Sopir");
        model.addColumn("Denda");

        ArrayList<Tarif> list = Tarif.getAll();
        for (Tarif t : list) {
            Object[] row = {
                t.getIdTarif(),
                t.getKodeKendaraan(),
                t.getNoPolisi(),
                t.getColMerk(),
                rupiah.format(t.getTarifHarian()),     
                rupiah.format(t.getTarifSopirHarian()),
                rupiah.format(t.getDendaPerHari())     
            };
            model.addRow(row);
        }
        tblTarif.setModel(model);
        
        tblTarif.getColumnModel().getColumn(0).setMinWidth(0);
        tblTarif.getColumnModel().getColumn(0).setMaxWidth(0);
        tblTarif.getColumnModel().getColumn(0).setWidth(0);
    }

    private void tampilDataSearch(String keyword) {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Kode");
        model.addColumn("No Polisi");
        model.addColumn("Merk");
        model.addColumn("Tarif");
        model.addColumn("Tarif Sopir");
        model.addColumn("Denda");

        ArrayList<Tarif> list = Tarif.search(keyword);
        for (Tarif t : list) {
            Object[] row = {
                t.getIdTarif(),
                t.getKodeKendaraan(),
                t.getNoPolisi(),
                t.getColMerk(),
                rupiah.format(t.getTarifHarian()),
                rupiah.format(t.getTarifSopirHarian()),
                rupiah.format(t.getDendaPerHari())
            };
            model.addRow(row);
        }
        tblTarif.setModel(model);
        
        tblTarif.getColumnModel().getColumn(0).setMinWidth(0);
        tblTarif.getColumnModel().getColumn(0).setMaxWidth(0);
        tblTarif.getColumnModel().getColumn(0).setWidth(0);
    }

    private double bersihkanFormatAngka(String text) {
        String clean = text.replace(".", "").replace(",", "").trim();
        return clean.isEmpty() ? 0 : Double.parseDouble(clean);
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
        txtTarifHarian = new javax.swing.JTextField();
        txtTarifSopir = new javax.swing.JTextField();
        txtDenda = new javax.swing.JTextField();
        btnSimpan = new javax.swing.JButton();
        btnClear = new javax.swing.JButton();
        btnHapus = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblTarif = new javax.swing.JTable();
        txtCari = new javax.swing.JTextField();
        btnCari = new javax.swing.JButton();
        cmbKendaraan = new javax.swing.JComboBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Kendaraan");

        jLabel2.setText("Tarif Harian");

        jLabel3.setText("Tarif Sopir");

        jLabel4.setText("Denda / Hari");

        txtDenda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDendaActionPerformed(evt);
            }
        });

        btnSimpan.setText("Simpan");
        btnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanActionPerformed(evt);
            }
        });

        btnClear.setText("Clear");
        btnClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearActionPerformed(evt);
            }
        });

        btnHapus.setText("Hapus");
        btnHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel7.setText("FORM TARIF");

        tblTarif.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Kode", "No Polisi", "Merk", "Tarif", "Tarif Sopir", "Denda"
            }
        ));
        tblTarif.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblTarifMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblTarif);

        btnCari.setText("Cari");
        btnCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCariActionPerformed(evt);
            }
        });

        cmbKendaraan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbKendaraan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbKendaraanActionPerformed(evt);
            }
        });

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
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtDenda, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtTarifSopir, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtTarifHarian, javax.swing.GroupLayout.DEFAULT_SIZE, 222, Short.MAX_VALUE)
                                    .addComponent(cmbKendaraan, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnSimpan)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnClear)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnHapus)))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(txtCari, javax.swing.GroupLayout.PREFERRED_SIZE, 435, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnCari, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(26, 26, 26))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 525, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(15, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(btnCari)
                    .addComponent(txtCari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbKendaraan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtTarifHarian, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txtTarifSopir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel4)
                            .addComponent(txtDenda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnClear, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnHapus, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(50, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanActionPerformed
       if (txtTarifHarian.getText().trim().isEmpty()
                || txtTarifSopir.getText().trim().isEmpty()
                || txtDenda.getText().trim().isEmpty()
                || cmbKendaraan.getSelectedItem() == null) {
            
            JOptionPane.showMessageDialog(this, "Semua data wajib diisi!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            Tarif t = new Tarif();
            t.setIdTarif(idTarif);
            
            Tarif kTerpilih = (Tarif) cmbKendaraan.getSelectedItem();
            t.setIdKendaraan(kTerpilih.getIdKendaraan());
            
            t.setTarifHarian(bersihkanFormatAngka(txtTarifHarian.getText()));
            t.setTarifSopirHarian(bersihkanFormatAngka(txtTarifSopir.getText()));
            t.setDendaPerHari(bersihkanFormatAngka(txtDenda.getText()));

            boolean statusEdit = idTarif != 0;
            boolean sukses = Tarif.simpan(t); 

            if (sukses) {
                if (statusEdit) {
                    JOptionPane.showMessageDialog(this, "Data tarif berhasil diupdate.");
                } else {
                    JOptionPane.showMessageDialog(this, "Data tarif baru berhasil disimpan.");
                }
                tampilData();
                kosongkanForm();
            } else {
                JOptionPane.showMessageDialog(this, "Gagal memproses data!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Format nominal angka salah!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnSimpanActionPerformed

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
        kosongkanForm();
        txtCari.setText("");
        tampilData();
    }//GEN-LAST:event_btnClearActionPerformed

    private void btnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusActionPerformed

    if (idTarif == 1) return;


    Object[] options = {"No", "Yes"};


    int konfirmasi = JOptionPane.showOptionDialog(
            this, 
            "Yakin ingin menghapus data tarif ini?", 
            "Konfirmasi", 
            JOptionPane.YES_NO_OPTION, 
            JOptionPane.QUESTION_MESSAGE, 
            null, 
            options, 
            options[1]
    );

 
    if (konfirmasi == 1) { 
        if (Tarif.hapus(idTarif)) {
            JOptionPane.showMessageDialog(this, "Data berhasil dihapus.");
            tampilData();
            kosongkanForm();
        } else {
           
            JOptionPane.showMessageDialog(this, 
                "Gagal menghapus! Data tarif mobil ini sudah memiliki transaksi penyewaan.", 
                "Peringatan Keamanan Data", 
                JOptionPane.WARNING_MESSAGE);
            kosongkanForm(); 
              }
         }
    }//GEN-LAST:event_btnHapusActionPerformed

    private void btnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCariActionPerformed
        String keyword = txtCari.getText().trim();
        if (keyword.isEmpty()) {
            tampilData();
        } else {
            tampilDataSearch(keyword);
        }
    }//GEN-LAST:event_btnCariActionPerformed

    private void tblTarifMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblTarifMouseClicked
int baris = tblTarif.getSelectedRow();
        if (baris == -1) return;

        idTarif = Integer.parseInt(tblTarif.getValueAt(baris, 0).toString());
        Tarif t = Tarif.getById(idTarif);

        cmbKendaraan.setEnabled(true);
        txtTarifHarian.setEnabled(true);
        txtTarifSopir.setEnabled(true);
        txtDenda.setEnabled(true);
        btnSimpan.setEnabled(true);
        btnHapus.setEnabled(true);
        btnSimpan.setText("Update");

        ((javax.swing.DefaultComboBoxModel)cmbKendaraan.getModel()).removeAllElements();
        ((javax.swing.DefaultComboBoxModel)cmbKendaraan.getModel()).addElement(t);
        cmbKendaraan.setSelectedItem(t);

        txtTarifHarian.setText(rupiah.format(t.getTarifHarian()));
        txtTarifSopir.setText(rupiah.format(t.getTarifSopirHarian()));
        txtDenda.setText(rupiah.format(t.getDendaPerHari()));

        // FITUR UTAMA REQUESTED: Aktifkan text cursor di kolom input pertama yang aktif saat tabel diklik
        txtTarifHarian.requestFocus();
        txtTarifHarian.selectAll();
    }//GEN-LAST:event_tblTarifMouseClicked

    private void txtDendaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDendaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDendaActionPerformed

    private void cmbKendaraanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbKendaraanActionPerformed
        // TODO add your handling code here:
        if (cmbKendaraan.getSelectedIndex() != -1 && idTarif == 0) {
            txtTarifHarian.setEnabled(true);
            txtTarifSopir.setEnabled(true);
            txtDenda.setEnabled(true);
            btnSimpan.setEnabled(true);
            btnHapus.setEnabled(false);
        }
    }//GEN-LAST:event_cmbKendaraanActionPerformed

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
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
           System.out.println("Error LookAndFeel: " + ex.getMessage());
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new FrmTarif().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCari;
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnHapus;
    private javax.swing.JButton btnSimpan;
    private javax.swing.JComboBox cmbKendaraan;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tblTarif;
    private javax.swing.JTextField txtCari;
    private javax.swing.JTextField txtDenda;
    private javax.swing.JTextField txtTarifHarian;
    private javax.swing.JTextField txtTarifSopir;
    // End of variables declaration//GEN-END:variables
}
