package MainPack.Database.DAO;

import MainPack.Entity.Stock;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.Properties;

public class StockDAO implements FactoryDAO<Stock>{

    @Override
    public Stock get(String player) throws Exception {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = getConnection();
            stmt = conn.prepareStatement("SELECT * FROM Stock WHERE Username=?");
            stmt.setString(1, player);

            ResultSet rs = stmt.executeQuery();
            Stock s = new Stock();

            if(rs.next()) {
                s.setUsername(rs.getString("Username"));
                s.setSunEssenceAmount(rs.getInt("SunEss"));
                s.setStormEssenceAmount(rs.getInt("StormEss"));
                s.setEarthEssenceAmount(rs.getInt("EarthEss"));
                s.setVoidEssenceAmount(rs.getInt("VoidEss"));
                s.setEnergyEssenceAmount(rs.getInt("EnergyEss"));

                return s;
            }
            else return null;

        }
        finally {
            closeSt(stmt);
            closeCon(conn);
        }
    }

    @Override
    public int insert(Stock s) throws Exception {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = getConnection();
            stmt = conn.prepareStatement("INSERT INTO Stock(Username, SunEss, StormEss, EarthEss," +
                    " VoidEss, EnergyEss) VALUES(?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, s.getUsername());
            stmt.setInt(2, s.getSunEssenceAmount());
            stmt.setInt(3, s.getStormEssenceAmount());
            stmt.setInt(4, s.getEarthEssenceAmount());
            stmt.setInt(5, s.getVoidEssenceAmount());
            stmt.setDouble(6, s.getEnergyEssenceAmount());

            int result = stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();

            if(rs.next()) s.setUsername(rs.getString(1));

            return 0;
        }
        finally {
            closeSt(stmt);
            closeCon(conn);
        }

    }

    @Override
    public int update(Stock s) throws Exception {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = getConnection();
            stmt = conn.prepareStatement("UPDATE Stock SET SunEss=?, StormEss=?, EarthEss=?, " +
                    "VoidEss=?, EnergyEss=? WHERE Username=?");
            stmt.setInt(1, s.getSunEssenceAmount());
            stmt.setInt(2, s.getStormEssenceAmount());
            stmt.setInt(3, s.getEarthEssenceAmount());
            stmt.setInt(4, s.getVoidEssenceAmount());
            stmt.setDouble(5, s.getEnergyEssenceAmount());
            stmt.setString(6, s.getUsername());

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
