/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Rizki
 */
public class Pelanggan {

    private int idPelanggan;
    private String kodePelanggan;
    private String namaPelanggan;
    private String noKtp;
    private String noSim;
    private String noTelp;
    private String alamat;

    // Constructor
    public Pelanggan() {
    }

    public Pelanggan(int idPelanggan, String kodePelanggan,
            String namaPelanggan, String noKtp,
            String noSim, String noTelp, String alamat) {

        this.idPelanggan = idPelanggan;
        this.kodePelanggan = kodePelanggan;
        this.namaPelanggan = namaPelanggan;
        this.noKtp = noKtp;
        this.noSim = noSim;
        this.noTelp = noTelp;
        this.alamat = alamat;
    }

    public int getIdPelanggan() {
        return idPelanggan;
    }

    public void setIdPelanggan(int idPelanggan) {
        this.idPelanggan = idPelanggan;
    }

    public String getKodePelanggan() {
        return kodePelanggan;
    }

    public void setKodePelanggan(String kodePelanggan) {
        this.kodePelanggan = kodePelanggan;
    }

    public String getNamaPelanggan() {
        return namaPelanggan;
    }

    public void setNamaPelanggan(String namaPelanggan) {
        this.namaPelanggan = namaPelanggan;
    }

    public String getNoKtp() {
        return noKtp;
    }

    public void setNoKtp(String noKtp) {
        this.noKtp = noKtp;
    }

    public String getNoSim() {
        return noSim;
    }

    public void setNoSim(String noSim) {
        this.noSim = noSim;
    }

    public String getNoTelp() {
        return noTelp;
    }

    public void setNoTelp(String noTelp) {
        this.noTelp = noTelp;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public static ArrayList<Pelanggan> getAll() {
        ArrayList<Pelanggan> list = new ArrayList<>();

        ResultSet rs = DBHelper.selectQuery("SELECT * FROM pelanggan");

        try {
        
            while(rs.next()){
            
                Pelanggan p = new Pelanggan();

                p.setIdPelanggan(rs.getInt("id_pelanggan"));
                p.setKodePelanggan(rs.getString("kode_pelanggan"));
                p.setNamaPelanggan(rs.getString("nama_pelanggan"));
                p.setNoKtp(rs.getString("no_ktp"));
                p.setNoSim(rs.getString("no_sim"));
                p.setNoTelp(rs.getString("no_telp"));
                p.setAlamat(rs.getString("alamat"));

                list.add(p);

            }

        } catch(Exception e){
        
            System.out.println(e);

        }

        return list;

    }
    
        public static ArrayList<Pelanggan> search(String keyword) {
    
        ArrayList<Pelanggan> list = new ArrayList<>();
    
        String query =
            "SELECT * FROM pelanggan WHERE " +
            "kode_pelanggan LIKE '%" + keyword + "%' OR " +
            "nama_pelanggan LIKE '%" + keyword + "%' OR " +
            "no_ktp LIKE '%" + keyword + "%' OR " +
            "no_sim LIKE '%" + keyword + "%' OR " +
            "no_telp LIKE '%" + keyword + "%'";
    
        ResultSet rs = DBHelper.selectQuery(query);
    
        try {
        
            while(rs.next()){
            
                Pelanggan p = new Pelanggan();
    
                p.setIdPelanggan(rs.getInt("id_pelanggan"));
                p.setKodePelanggan(rs.getString("kode_pelanggan"));
                p.setNamaPelanggan(rs.getString("nama_pelanggan"));
                p.setNoKtp(rs.getString("no_ktp"));
                p.setNoSim(rs.getString("no_sim"));
                p.setNoTelp(rs.getString("no_telp"));
                p.setAlamat(rs.getString("alamat"));
    
                list.add(p);
    
            }
    
        } catch(SQLException e){
        
            System.out.println(e);
    
        }
    
        return list;
    
    }
    
    public void save() {

        String query;

        if (this.idPelanggan == 0) {

            query = "INSERT INTO pelanggan(kode_pelanggan,nama_pelanggan,no_ktp,no_sim,no_telp,alamat)"
                    + " VALUES("
                    + "'" + kodePelanggan + "',"
                    + "'" + namaPelanggan + "',"
                    + "'" + noKtp + "',"
                    + "'" + noSim + "',"
                    + "'" + noTelp + "',"
                    + "'" + alamat + "')";

            this.idPelanggan = DBHelper.insertQueryGetId(query);

        } else {

            query = "UPDATE pelanggan SET "
                    + "kode_pelanggan='" + kodePelanggan + "',"
                    + "nama_pelanggan='" + namaPelanggan + "',"
                    + "no_ktp='" + noKtp + "',"
                    + "no_sim='" + noSim + "',"
                    + "no_telp='" + noTelp + "',"
                    + "alamat='" + alamat + "' "
                    + "WHERE id_pelanggan='" + idPelanggan + "'";

            DBHelper.executeQuery(query);

        }
    }
    
        public void delete() {
    
        String query = "DELETE FROM pelanggan WHERE id_pelanggan='" + idPelanggan + "'";
    
        DBHelper.executeQuery(query);
    
    }
    
    public static Pelanggan getById(int id) {
    
        Pelanggan p = new Pelanggan();
    
        ResultSet rs = DBHelper.selectQuery(
                "SELECT * FROM pelanggan WHERE id_pelanggan=" + id);
    
        try {
        
            if (rs.next()) {
            
                p.setIdPelanggan(rs.getInt("id_pelanggan"));
                p.setKodePelanggan(rs.getString("kode_pelanggan"));
                p.setNamaPelanggan(rs.getString("nama_pelanggan"));
                p.setNoKtp(rs.getString("no_ktp"));
                p.setNoSim(rs.getString("no_sim"));
                p.setNoTelp(rs.getString("no_telp"));
                p.setAlamat(rs.getString("alamat"));
    
            }
    
        } catch (SQLException e) {
        
            System.out.println(e);
    
        }
    
        return p;
    
    }
    public static boolean cekKTP(String noKtp, int idPelanggan) {
        String query = "SELECT * FROM pelanggan "
                + "WHERE no_ktp='" + noKtp + "' "
                + "AND id_pelanggan<>" + idPelanggan;
    
        ResultSet rs = DBHelper.selectQuery(query);
    
        try {
        
            if (rs.next()) {
                return true;
            }
    
        } catch (SQLException e) {
            System.out.println(e);
        }
    
        return false;
    }
}