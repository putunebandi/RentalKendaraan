/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author HYPE AMD
 */
public class Penyewaan {
    private int idSewa;
    private String noSewa;
    private String tglSewa;
    private int idPelanggan;
    private String tglMulai;
    private String tglRencanaKembali;
    private String status;
    private String catatan;

    public Penyewaan() {
    }

    // ===== Getter & Setter =====
    public int getIdSewa() { return idSewa; }
    public void setIdSewa(int idSewa) { this.idSewa = idSewa; }

    public String getNoSewa() { return noSewa; }
    public void setNoSewa(String noSewa) { this.noSewa = noSewa; }

    public String getTglSewa() { return tglSewa; }
    public void setTglSewa(String tglSewa) { this.tglSewa = tglSewa; }

    public int getIdPelanggan() { return idPelanggan; }
    public void setIdPelanggan(int idPelanggan) { this.idPelanggan = idPelanggan; }

    public String getTglMulai() { return tglMulai; }
    public void setTglMulai(String tglMulai) { this.tglMulai = tglMulai; }

    public String getTglRencanaKembali() { return tglRencanaKembali; }
    public void setTglRencanaKembali(String tglRencanaKembali) { this.tglRencanaKembali = tglRencanaKembali; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getCatatan() { return catatan; }
    public void setCatatan(String catatan) { this.catatan = catatan; }

    // ===== Method backend (static, tanpa DAO) =====

    /**
     * Generate No Sewa otomatis dengan format SW-yyyyMMdd-XXX
     * Contoh: SW-20260701-001
     */
    public static String generateNoSewa() {
        SimpleDateFormat sdfKey = new SimpleDateFormat("yyyyMMdd");
        String tglKey = sdfKey.format(new Date());
        String prefix = "SW-" + tglKey + "-";
        String noSewaBaru = prefix + "001";

        String query = "SELECT no_sewa FROM penyewaan WHERE no_sewa LIKE '" + prefix + "%' ORDER BY no_sewa DESC LIMIT 1";
        ResultSet rs = DBHelper.selectQuery(query);

        try {
            if (rs != null && rs.next()) {
                String noTerakhir = rs.getString("no_sewa");
                String urutanStr = noTerakhir.substring(noTerakhir.length() - 3);
                int urutanBaru = Integer.parseInt(urutanStr) + 1;
                noSewaBaru = prefix + String.format("%03d", urutanBaru);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return noSewaBaru;
    }

    /**
     * Simpan data header penyewaan, mengembalikan id_sewa hasil auto increment.
     */
    public static int simpan(Penyewaan p) {
        String query = "INSERT INTO penyewaan "
                + "(no_sewa, tgl_sewa, id_pelanggan, tgl_mulai, tgl_rencana_kembali, status, catatan) VALUES ('"
                + p.getNoSewa() + "', '"
                + p.getTglSewa() + "', "
                + p.getIdPelanggan() + ", '"
                + p.getTglMulai() + "', '"
                + p.getTglRencanaKembali() + "', '"
                + p.getStatus() + "', '"
                + p.getCatatan().replace("'", "''") + "')";

        return DBHelper.insertQueryGetId(query);
    }
}
