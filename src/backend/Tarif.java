package backend;

import java.util.ArrayList;
import java.sql.*;

public class Tarif {
    private int idTarif;
    private Kendaraan kendaraan; // Relasi objek ke class Kendaraan
    private double tarifHarian;
    private double tarifSopir;
    private double denda;

    // Constructor Kosong
    public Tarif() {
        this.kendaraan = new Kendaraan();
    }

    // Constructor dengan Parameter
    public Tarif(Kendaraan kendaraan, double tarifHarian, double tarifSopir, double denda) {
        this.kendaraan = kendaraan;
        this.tarifHarian = tarifHarian;
        this.tarifSopir = tarifSopir;
        this.denda = denda;
    }

    // =========================================================================
    // GETTER DAN SETTER
    // =========================================================================
    public int getIdTarif() {
        return idTarif;
    }

    public void setIdTarif(int idTarif) {
        this.idTarif = idTarif;
    }

    public Kendaraan getKendaraan() {
        return kendaraan;
    }

    public void setKendaraan(Kendaraan kendaraan) {
        this.kendaraan = kendaraan;
    }

    public double getTarifHarian() {
        return tarifHarian;
    }

    public void setTarifHarian(double tarifHarian) {
        this.tarifHarian = tarifHarian;
    }

    public double getTarifSopir() {
        return tarifSopir;
    }

    public void setTarifSopir(double tarifSopir) {
        this.tarifSopir = tarifSopir;
    }

    public double getDenda() {
        return denda;
    }

    public void setDenda(double denda) {
        this.denda = denda;
    }

    // =========================================================================
    // METHOD CRUD (Koneksi Database)
    // =========================================================================
    
    public static Tarif getById(int id) {
        Tarif t = new Tarif();
        ResultSet rs = DBHelper.selectQuery("SELECT * FROM tarif WHERE idtarif = '" + id + "'");

        try {
            while (rs.next()) {
                t = new Tarif();
                t.setIdTarif(rs.getInt("idtarif"));
                t.setKendaraan(Kendaraan.getById(rs.getInt("idkendaraan"))); // Load data kendaraan otomatis
                t.setTarifHarian(rs.getDouble("tarif_harian"));
                t.setTarifSopir(rs.getDouble("tarif_sopir"));
                t.setDenda(rs.getDouble("denda"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return t;
    }

    public static ArrayList<Tarif> getAll() {
        ArrayList<Tarif> ListTarif = new ArrayList<>();
        ResultSet rs = DBHelper.selectQuery("SELECT * FROM tarif");

        try {
            while (rs.next()) {
                Tarif t = new Tarif();
                t.setIdTarif(rs.getInt("idtarif"));
                t.setKendaraan(Kendaraan.getById(rs.getInt("idkendaraan")));
                t.setTarifHarian(rs.getDouble("tarif_harian"));
                t.setTarifSopir(rs.getDouble("tarif_sopir"));
                t.setDenda(rs.getDouble("denda"));

                ListTarif.add(t);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ListTarif;
    }

    public static ArrayList<Tarif> search(String keyword) {
        ArrayList<Tarif> ListTarif = new ArrayList<>();
        
        // Query menggunakan INNER JOIN agar bisa mencari berdasarkan Merk atau No Polisi Kendaraan
        String sql = "SELECT t.* FROM tarif t "
                + "INNER JOIN kendaraan k ON t.idkendaraan = k.idkendaraan "
                + "WHERE k.idkendaraan LIKE '%" + keyword + "%' "
                + "OR k.no_polisi LIKE '%" + keyword + "%' "
                + "OR k.merk LIKE '%" + keyword + "%'";
                
        ResultSet rs = DBHelper.selectQuery(sql);

        try {
            while (rs.next()) {
                Tarif t = new Tarif();
                t.setIdTarif(rs.getInt("idtarif"));
                t.setKendaraan(Kendaraan.getById(rs.getInt("idkendaraan")));
                t.setTarifHarian(rs.getDouble("tarif_harian"));
                t.setTarifSopir(rs.getDouble("tarif_sopir"));
                t.setDenda(rs.getDouble("denda"));

                ListTarif.add(t);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ListTarif;
    }

    public void save() {
        if (getById(idTarif).getIdTarif() == 0) {
            String SQL = "INSERT INTO tarif (idkendaraan, tarif_harian, tarif_sopir, denda) VALUES("
                    + " '" + this.getKendaraan().getIdKendaraan() + "', "
                    + " '" + this.tarifHarian + "', "
                    + " '" + this.tarifSopir + "', "
                    + " '" + this.denda + "' "
                    + " )";
            this.idTarif = DBHelper.insertQueryGetId(SQL);
        } else {
            String SQL = "UPDATE tarif SET "
                    + " idkendaraan = '" + this.getKendaraan().getIdKendaraan() + "', "
                    + " tarif_harian = '" + this.tarifHarian + "', "
                    + " tarif_sopir = '" + this.tarifSopir + "', "
                    + " denda = '" + this.denda + "' "
                    + " WHERE idtarif = '" + this.idTarif + "'";
            DBHelper.executeQuery(SQL);
        }
    }

    public void delete() {
        String SQL = "DELETE FROM tarif WHERE idtarif = '" + this.idTarif + "'";
        DBHelper.executeQuery(SQL);
    }
}