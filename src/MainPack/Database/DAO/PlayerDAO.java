package MainPack.Database.DAO;

import MainPack.Entity.Player;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class PlayerDAO implements FactoryDAO<Player> {

    @Override
    public Player get(String player) throws Exception {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = getConnection();
            stmt = conn.prepareStatement("SELECT * FROM PlayerStats WHERE Username=?");
            stmt.setString(1, player);

            ResultSet rs = stmt.executeQuery();
            Player p = new Player();

            if(rs.next()) {
                p.setUsername(rs.getString("Username"));
                p.setStage(rs.getInt("Stage"));
                p.setAlchSpeed(rs.getInt("AlchSpeed"));
                p.setAlchProd(rs.getInt("AlchProd"));
                p.setWeaponDamage(rs.getInt("WeaponDamage"));
                p.setWeaponSpeed(rs.getDouble("WeaponSpeed"));

                return p;
            }
            else return null;

        }
        finally {
            closeSt(stmt);
            closeCon(conn);
        }

    }

    @Override
    public List<Player> getAll() throws Exception {
        Connection conn = null;
        PreparedStatement stmt = null;
        List<Player> list = new ArrayList<>();

        try {
            conn = getConnection();
            stmt = conn.prepareStatement("SELECT * FROM PlayerStats ORDER BY Stage");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Player player = new Player();

                player.setUsername(rs.getString("Username"));
                player.setStage(rs.getInt("Stage"));
                player.setAlchSpeed(rs.getInt("AlchSpeed"));
                player.setAlchProd(rs.getInt("AlchProd"));
                player.setWeaponDamage(rs.getInt("WeaponDamage"));
                player.setWeaponSpeed(rs.getDouble("WeaponSpeed"));

                list.add(player);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeSt(stmt);
            closeCon(conn);
        }

        return list;
    }

    @Override
    public int insert(Player p) throws Exception {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = getConnection();
            stmt = conn.prepareStatement("INSERT INTO PlayerStats(Username, Stage, AlchSpeed, AlchProd, WeaponDamage, WeaponSpeed)" +
                    " VALUES(?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, p.getUsername());
            stmt.setInt(2, p.getStage());
            stmt.setInt(3, p.getAlchSpeed());
            stmt.setInt(4, p.getAlchProd());
            stmt.setInt(5, p.getWeaponDamage());
            stmt.setDouble(6, p.getWeaponSpeed());

            int result = stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();

            if(rs.next()) p.setUsername(rs.getString(1));

            return 0;
        }
        finally {
            closeSt(stmt);
            closeCon(conn);
        }

    }

    @Override
    public int update(Player p) throws Exception {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = getConnection();
            stmt = conn.prepareStatement("UPDATE PlayerStats SET Stage=?, AlchSpeed=?, AlchProd=?, " +
                    "WeaponDamage=?, WeaponSpeed=? WHERE Username=?");
            stmt.setInt(1, p.getStage());
            stmt.setInt(2, p.getAlchSpeed());
            stmt.setInt(3, p.getAlchProd());
            stmt.setInt(4, p.getWeaponDamage());
            stmt.setDouble(5, p.getWeaponSpeed());
            stmt.setString(6, p.getUsername());

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
