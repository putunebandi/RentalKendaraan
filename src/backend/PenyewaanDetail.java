/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend;

/**
 *
 * @author HYPE AMD
 */
public class PenyewaanDetail {
    private int idDetail;
    private int idSewa;
    private int idKendaraan;
    private Integer idSopir;   // boleh null (sewa tanpa supir)
    private int idTarif;
    private int lamaHari;
    private double hargaSewa;
    private double hargaSopir;
    private double subtotal;

    // Field tambahan, khusus untuk ditampilkan di JTable (tidak disimpan ke DB)
    private String kodeKendaraan;
    private String namaSopir;

    public PenyewaanDetail() {
    }

    // ===== Getter & Setter =====
    public int getIdDetail() { return idDetail; }
    public void setIdDetail(int idDetail) { this.idDetail = idDetail; }

    public int getIdSewa() { return idSewa; }
    public void setIdSewa(int idSewa) { this.idSewa = idSewa; }

    public int getIdKendaraan() { return idKendaraan; }
    public void setIdKendaraan(int idKendaraan) { this.idKendaraan = idKendaraan; }

    public Integer getIdSopir() { return idSopir; }
    public void setIdSopir(Integer idSopir) { this.idSopir = idSopir; }

    public int getIdTarif() { return idTarif; }
    public void setIdTarif(int idTarif) { this.idTarif = idTarif; }

    public int getLamaHari() { return lamaHari; }
    public void setLamaHari(int lamaHari) { this.lamaHari = lamaHari; }

    public double getHargaSewa() { return hargaSewa; }
    public void setHargaSewa(double hargaSewa) { this.hargaSewa = hargaSewa; }

    public double getHargaSopir() { return hargaSopir; }
    public void setHargaSopir(double hargaSopir) { this.hargaSopir = hargaSopir; }

    public double getSubtotal() { return subtotal; }
    public void setSubtotal(double subtotal) { this.subtotal = subtotal; }

    public String getKodeKendaraan() { return kodeKendaraan; }
    public void setKodeKendaraan(String kodeKendaraan) { this.kodeKendaraan = kodeKendaraan; }

    public String getNamaSopir() { return namaSopir; }
    public void setNamaSopir(String namaSopir) { this.namaSopir = namaSopir; }

    // ===== Method backend (static, tanpa DAO) =====

    public static boolean simpan(PenyewaanDetail d) {
        String idSopirValue = (d.getIdSopir() == null) ? "NULL" : String.valueOf(d.getIdSopir());

        String query = "INSERT INTO penyewaan_detail "
                + "(id_sewa, id_kendaraan, id_sopir, id_tarif, lama_hari, harga_sewa, harga_sopir, subtotal) VALUES ("
                + d.getIdSewa() + ", "
                + d.getIdKendaraan() + ", "
                + idSopirValue + ", "
                + d.getIdTarif() + ", "
                + d.getLamaHari() + ", "
                + d.getHargaSewa() + ", "
                + d.getHargaSopir() + ", "
                + d.getSubtotal() + ")";

        return DBHelper.executeQuery(query);
    }

    public static boolean updateStatusKendaraan(int idKendaraan, String status) {
        String query = "UPDATE kendaraan SET status = '" + status + "' WHERE id_kendaraan = " + idKendaraan;
        return DBHelper.executeQuery(query);
    }

    public static boolean updateStatusSopir(int idSopir, String status) {
        String query = "UPDATE sopir SET status = '" + status + "' WHERE id_sopir = " + idSopir;
        return DBHelper.executeQuery(query);
    }
}

