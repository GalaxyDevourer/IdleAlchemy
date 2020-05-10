package MainPack.Database.DAO;

import MainPack.Entity.Altar;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.Properties;

public class AltarDAO implements FactoryDAO<Altar>{
    @Override
    public Altar get(String player) throws Exception {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = getConnection();
            stmt = conn.prepareStatement("SELECT * FROM Altar WHERE Username=?");
            stmt.setString(1, player);

            ResultSet rs = stmt.executeQuery();
            Altar a = new Altar();

            if(rs.next()) {
                a.setUsername(rs.getString("Username"));
                a.setCostReduceLevel(rs.getInt("CostReduceLevel"));
                a.setAlchSpeedLevel(rs.getInt("AlchSpeedLevel"));
                a.setAlchGetEssenceLevel(rs.getInt("AlchGetEssenceLevel"));
                a.setWeaponSpeedLevel(rs.getInt("WeaponSpeedLevel"));
                a.setWeaponDamageLevel(rs.getInt("WeaponDamageLevel"));

                return a;
            }
            else return null;

        }
        finally {
            closeSt(stmt);
            closeCon(conn);
        }
    }

    @Override
    public int insert(Altar a) throws Exception {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = getConnection();
            stmt = conn.prepareStatement("INSERT INTO Altar(Username, CostReduceLevel, AlchSpeedLevel, AlchGetEssenceLevel," +
                    " WeaponSpeedLevel, WeaponDamageLevel) VALUES(?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, a.getUsername());
            stmt.setInt(2, a.getCostReduceLevel());
            stmt.setInt(3, a.getAlchSpeedLevel());
            stmt.setInt(4, a.getAlchGetEssenceLevel());
            stmt.setInt(5, a.getWeaponSpeedLevel());
            stmt.setInt(6, a.getWeaponDamageLevel());

            int result = stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();

            if(rs.next()) a.setUsername(rs.getString(1));

            return 0;
        }
        finally {
            closeSt(stmt);
            closeCon(conn);
        }
    }

    @Override
    public int update(Altar a) throws Exception {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = getConnection();
            stmt = conn.prepareStatement("UPDATE Altar SET CostReduceLevel=?, AlchSpeedLevel=?, AlchGetEssenceLevel=?, " +
                    "WeaponSpeedLevel=?, WeaponDamageLevel=? WHERE Username=?");
            stmt.setInt(1, a.getCostReduceLevel());
            stmt.setInt(2, a.getAlchSpeedLevel());
            stmt.setInt(3, a.getAlchGetEssenceLevel());
            stmt.setInt(4, a.getWeaponSpeedLevel());
            stmt.setInt(5, a.getWeaponDamageLevel());
            stmt.setString(6, a.getUsername());

            int result = stmt.executeUpdate();

            return 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return 1;
        } finally {
            closeSt(stmt);
            closeCon(conn);
        }
    }

    private Connection getConnection() throws ClassNotFoundException, IOException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");

        Properties props = new Properties();
        try(InputStream in = Files.newInputStream(Paths.get(
                System.getProperty("user.dir") + "\\database.properties"))){
            props.load(in);
        }
        String url = props.getProperty("url");
        String username = props.getProperty("username");
        String password = props.getProperty("password");

        return DriverManager.getConnection(url, username, password);
    }

    private static void closeCon(Connection con) {
        if (con != null) {
            try {
                con.close();
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private static void closeSt(Statement st) {
        if (st != null) {
            try {
                st.close();
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
