package backend;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author vyn
 */
public class Sopir {

    private int idSopir;
    private String kodeSopir;
    private String namaSopir;
    private String noSim;
    private String noTelp;
    private String status;

    public Sopir() {
    }

    public int getIdSopir() {
        return idSopir;
    }

    public void setIdSopir(int idSopir) {
        this.idSopir = idSopir;
    }

    public String getKodeSopir() {
        return kodeSopir;
    }

    public void setKodeSopir(String kodeSopir) {
        this.kodeSopir = kodeSopir;
    }

    public String getNamaSopir() {
        return namaSopir;
    }

    public void setNamaSopir(String namaSopir) {
        this.namaSopir = namaSopir;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // ==========================================
    // LOGIKA AUTO GENERATE KODE SOPIR (SPR-00X)
    // ==========================================
    public static String generateKodeOtomatis() {
        String kodeBaru = "SPR-001"; // Default menggunakan huruf besar
        String query = "SELECT kode_sopir FROM sopir ORDER BY id_sopir DESC LIMIT 1";
        ResultSet rs = DBHelper.selectQuery(query);
        
        try {
            if (rs.next()) {
                String kodeTerakhir = rs.getString("kode_sopir");
                if (kodeTerakhir != null && kodeTerakhir.contains("-")) {
                    String[] parts = kodeTerakhir.split("-");
                    int angka = Integer.parseInt(parts[1]); 
                    angka++; 
                    kodeBaru = String.format("SPR-%03d", angka); // Format huruf besar
                }
            }
        } catch (Exception e) {
            System.out.println("Error generate kode: " + e.getMessage());
        }
        return kodeBaru;
    }

    public static ArrayList<Sopir> getAll() {
        ArrayList<Sopir> list = new ArrayList<>();
        ResultSet rs = DBHelper.selectQuery("SELECT * FROM sopir ORDER BY id_sopir ASC");

        try {
            while (rs.next()) {
                Sopir s = new Sopir();
                s.setIdSopir(rs.getInt("id_sopir"));
                s.setKodeSopir(rs.getString("kode_sopir"));
                s.setNamaSopir(rs.getString("nama_sopir"));
                s.setNoSim(rs.getString("no_sim"));
                s.setNoTelp(rs.getString("no_telp"));
                s.setStatus(rs.getString("status"));
                list.add(s);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return list;
    }

    public static ArrayList<Sopir> search(String keyword) {
        ArrayList<Sopir> list = new ArrayList<>();
        String query = "SELECT * FROM sopir WHERE "
                + "kode_sopir LIKE '%" + keyword + "%' OR "
                + "nama_sopir LIKE '%" + keyword + "%' OR "
                + "no_sim LIKE '%" + keyword + "%' OR "
                + "no_telp LIKE '%" + keyword + "%' OR "
                + "status LIKE '%" + keyword + "%' "
                + "ORDER BY id_sopir ASC";

        ResultSet rs = DBHelper.selectQuery(query);

        try {
            while (rs.next()) {
                Sopir s = new Sopir();
                s.setIdSopir(rs.getInt("id_sopir"));
                s.setKodeSopir(rs.getString("kode_sopir"));
                s.setNamaSopir(rs.getString("nama_sopir"));
                s.setNoSim(rs.getString("no_sim"));
                s.setNoTelp(rs.getString("no_telp"));
                s.setStatus(rs.getString("status"));
                list.add(s);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return list;
    }

    public void save() {
        String query;

        if (this.idSopir == 0) {
            int idBaru = 1;
            ResultSet rs = DBHelper.selectQuery("SELECT id_sopir FROM sopir ORDER BY id_sopir ASC");
            try {
                while (rs.next()) {
                    if (rs.getInt("id_sopir") == idBaru) {
                        idBaru++;
                    } else {
                        break;
                    }
                }
            } catch (Exception e) {
                System.out.println(e);
            }
            
            this.idSopir = idBaru;

            query = "INSERT INTO sopir(id_sopir, kode_sopir, nama_sopir, no_sim, no_telp, status) "
                    + "VALUES("
                    + "'" + this.idSopir + "',"
                    + "'" + this.kodeSopir + "',"
                    + "'" + this.namaSopir + "',"
                    + "'" + this.noSim + "',"
                    + "'" + this.noTelp + "',"
                    + "'" + this.status + "')";

            DBHelper.executeQuery(query);
        } else {
            query = "UPDATE sopir SET "
                    + "kode_sopir='" + this.kodeSopir + "',"
                    + "nama_sopir='" + this.namaSopir + "',"
                    + "no_sim='" + this.noSim + "',"
                    + "no_telp='" + this.noTelp + "',"
                    + "status='" + this.status + "' "
                    + "WHERE id_sopir='" + this.idSopir + "'";

            DBHelper.executeQuery(query);
        }
    }

    public void delete() {
        String query = "DELETE FROM sopir WHERE id_sopir='" + this.idSopir + "'";
        DBHelper.executeQuery(query);
    }

    public static Sopir getById(int id) {
        Sopir s = new Sopir();
        ResultSet rs = DBHelper.selectQuery("SELECT * FROM sopir WHERE id_sopir=" + id);

        try {
            if (rs.next()) {
                s.setIdSopir(rs.getInt("id_sopir"));
                s.setKodeSopir(rs.getString("kode_sopir"));
                s.setNamaSopir(rs.getString("nama_sopir"));
                s.setNoSim(rs.getString("no_sim"));
                s.setNoTelp(rs.getString("no_telp"));
                s.setStatus(rs.getString("status"));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return s;
    }
    
    public static boolean cekSIM(String noSim, int idSopir) {
        String query = "SELECT * FROM sopir WHERE no_sim='" + noSim + "' AND id_sopir<>" + idSopir;
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