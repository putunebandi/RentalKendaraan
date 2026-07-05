package backend;

import backend.DBHelper;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * Model Backend untuk Entitas Tarif Kendaraan
 * Proteksi Hapus: Hanya memblokir kendaraan yang benar-benar sudah memiliki transaksi.
 * @author HYPE AMD / Rizki
 */
public class Tarif {
    private int idTarif;
    private int idKendaraan;
    private double tarifHarian;
    private double tarifSopirHarian;
    private double dendaPerHari;
    
    // Properti bantu untuk menampung data hasil JOIN dari tabel kendaraan
    private String kodeKendaraan;
    private String noPolisi;
    private String colMerk;

    public Tarif() {
    }

    public Tarif(int idKendaraan, double tarifHarian, double tarifSopirHarian, double dendaPerHari) {
        this.idKendaraan = idKendaraan;
        this.tarifHarian = tarifHarian;
        this.tarifSopirHarian = tarifSopirHarian;
        this.dendaPerHari = dendaPerHari;
    }

    // ==================== GETTER & SETTER ====================
    public int getIdTarif() {
        return idTarif;
    }

    public void setIdTarif(int idTarif) {
        this.idTarif = idTarif;
    }

    public int getIdKendaraan() {
        return idKendaraan;
    }

    public void setIdKendaraan(int idKendaraan) {
        this.idKendaraan = idKendaraan;
    }

    public double getTarifHarian() {
        return tarifHarian;
    }

    public void setTarifHarian(double tarifHarian) {
        this.tarifHarian = tarifHarian;
    }

    public double getTarifSopirHarian() {
        return tarifSopirHarian;
    }

    public void setTarifSopirHarian(double tarifSopirHarian) {
        this.tarifSopirHarian = tarifSopirHarian;
    }

    public double getDendaPerHari() {
        return dendaPerHari;
    }

    public void setDendaPerHari(double dendaPerHari) {
        this.dendaPerHari = dendaPerHari;
    }

    public String getKodeKendaraan() {
        return kodeKendaraan;
    }

    public void setKodeKendaraan(String kodeKendaraan) {
        this.kodeKendaraan = kodeKendaraan;
    }

    public String getNoPolisi() {
        return noPolisi;
    }

    public void setNoPolisi(String noPolisi) {
        this.noPolisi = noPolisi;
    }

    public String getColMerk() {
        return colMerk;
    }

    public void setColMerk(String colMerk) {
        this.colMerk = colMerk;
    }

    @Override
    public String toString() {
        return this.kodeKendaraan + " | " + this.colMerk + " | " + this.noPolisi;
    }

    // ==================== DATA ACCESS OBJECT (DAO) ====================
    
    public static Tarif getById(int id) {
        Tarif t = new Tarif();
        String query = "SELECT t.*, k.kode_kendaraan, k.no_polisi, k.merk "
                     + "FROM tarif t INNER JOIN kendaraan k ON t.id_kendaraan = k.id_kendaraan "
                     + "WHERE t.id_tarif = " + id;
        ResultSet rs = DBHelper.selectQuery(query);

        try {
            if (rs.next()) {
                t.setIdTarif(rs.getInt("id_tarif"));
                t.setIdKendaraan(rs.getInt("id_kendaraan"));
                t.setTarifHarian(rs.getDouble("tarif_harian"));
                t.setTarifSopirHarian(rs.getDouble("tarif_sopir_harian"));
                t.setDendaPerHari(rs.getDouble("denda_per_hari"));
                t.setKodeKendaraan(rs.getString("kode_kendaraan"));
                t.setNoPolisi(rs.getString("no_polisi"));
                t.setColMerk(rs.getString("merk"));
            }
        } catch (Exception e) {
            System.out.println("Error getById Tarif: " + e.getMessage());
        }
        return t;
    }

    public static ArrayList<Tarif> getAll() {
        ArrayList<Tarif> list = new ArrayList<>();
        String query = "SELECT t.*, k.kode_kendaraan, k.no_polisi, k.merk "
                     + "FROM tarif t INNER JOIN kendaraan k ON t.id_kendaraan = k.id_kendaraan "
                     + "ORDER BY t.id_tarif ASC";
        ResultSet rs = DBHelper.selectQuery(query);

        try {
            while (rs.next()) {
                Tarif t = new Tarif();
                t.setIdTarif(rs.getInt("id_tarif"));
                t.setIdKendaraan(rs.getInt("id_kendaraan"));
                t.setTarifHarian(rs.getDouble("tarif_harian"));
                t.setTarifSopirHarian(rs.getDouble("tarif_sopir_harian"));
                t.setDendaPerHari(rs.getDouble("denda_per_hari"));
                t.setKodeKendaraan(rs.getString("kode_kendaraan"));
                t.setNoPolisi(rs.getString("no_polisi"));
                t.setColMerk(rs.getString("merk"));
                list.add(t);
            }
        } catch (Exception e) {
            System.out.println("Error getAll Tarif: " + e.getMessage());
        }
        return list;
    }

    public static ArrayList<Tarif> search(String keyword) {
        ArrayList<Tarif> list = new ArrayList<>();
        String query = "SELECT t.*, k.kode_kendaraan, k.no_polisi, k.merk "
                     + "FROM tarif t INNER JOIN kendaraan k ON t.id_kendaraan = k.id_kendaraan "
                     + "WHERE k.kode_kendaraan LIKE '%" + keyword + "%' "
                     + "OR k.no_polisi LIKE '%" + keyword + "%' "
                     + "OR k.merk LIKE '%" + keyword + "%' "
                     + "OR t.tarif_harian LIKE '%" + keyword + "%' "      
                     + "OR t.tarif_sopir_harian LIKE '%" + keyword + "%' " 
                     + "OR t.denda_per_hari LIKE '%" + keyword + "%' "    
                     + "ORDER BY t.id_tarif ASC";
        ResultSet rs = DBHelper.selectQuery(query);

        try {
            while (rs.next()) {
                Tarif t = new Tarif();
                t.setIdTarif(rs.getInt("id_tarif"));
                t.setIdKendaraan(rs.getInt("id_kendaraan"));
                t.setTarifHarian(rs.getDouble("tarif_harian"));
                t.setTarifSopirHarian(rs.getDouble("tarif_sopir_harian"));
                t.setDendaPerHari(rs.getDouble("denda_per_hari"));
                t.setKodeKendaraan(rs.getString("kode_kendaraan"));
                t.setNoPolisi(rs.getString("no_polisi"));
                t.setColMerk(rs.getString("merk"));
                list.add(t);
            }
        } catch (Exception e) {
            System.out.println("Error search Tarif: " + e.getMessage());
        }
        return list;
    }

    public static boolean simpan(Tarif t) {
        String query;
        if (t.getIdTarif() == 0) {
            query = "INSERT INTO tarif (id_kendaraan, tarif_harian, tarif_sopir_harian, denda_per_hari) VALUES ("
                  + "  " + t.getIdKendaraan() + ", "
                  + "  " + t.getTarifHarian() + ", "
                  + "  " + t.getTarifSopirHarian() + ", "
                  + "  " + t.getDendaPerHari() + " "
                  + ")";
        } else {
            query = "UPDATE tarif SET "
                  + "  id_kendaraan = " + t.getIdKendaraan() + ", "
                  + "  tarif_harian = " + t.getTarifHarian() + ", "
                  + "  tarif_sopir_harian = " + t.getTarifSopirHarian() + ", "
                  + "  denda_per_hari = " + t.getDendaPerHari() + " "
                  + "  WHERE id_tarif = " + t.getIdTarif();
        }
        return DBHelper.executeQuery(query);
    }

    public static boolean hapus(int id) {

    // 1. Cek langsung ke tabel detail transaksi (penyewaan_detail) berdasarkan id_tarif
    // Kita cek apakah ID tarif ini sudah pernah nampang di struk/detail penyewaan
    String queryCek = "SELECT COUNT(*) AS total FROM penyewaan_detail WHERE id_tarif = " + id;
    ResultSet rs = DBHelper.selectQuery(queryCek);
    
    try {
        // Pastikan rs tidak null sebelum memanggil next()
        if (rs != null && rs.next()) {
            int totalTransaksi = rs.getInt("total");
            
            // Jika > 0, berarti sudah masuk manifes transaksi, blokir!
            if (totalTransaksi > 0) {
                System.out.println("Gagal Hapus: Data tarif ini sudah masuk ke detail transaksi!");
                return false; 
            }
        }
    } catch (Exception e) {
        System.out.println("Error validasi hapus tarif: " + e.getMessage());
        return false;
    }

    // 2. Jika totalTransaksi == 0 (Baru disimpan & Belum pernah disewa), eksekusi hapus
    String queryHapus = "DELETE FROM tarif WHERE id_tarif = " + id;
    return DBHelper.executeQuery(queryHapus);
    }
}
