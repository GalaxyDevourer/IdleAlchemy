package MainPack.Database.DAO;

import MainPack.Entity.User;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.Properties;

public class UserDAO implements FactoryDAO<User> {

    // метод получения данных игрока по его логину
    @Override
    public User get(String player) throws Exception {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = getConnection();
            stmt = conn.prepareStatement("SELECT * FROM GameUsers WHERE Username=?");
            stmt.setString(1, player);

            ResultSet rs = stmt.executeQuery();
            User u = new User();

            if(rs.next()) {
                u.setID(rs.getInt("ID"));
                u.setUsername(rs.getString("Username"));
                u.setPassword(rs.getString("Password"));

                return u;
            }
            else return null;

        }
        finally {
            closeSt(stmt);
            closeCon(conn);
        }

    }

    // метод сохранения данных в БД
    @Override
    public int insert(User u) throws Exception{
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = getConnection();
            stmt = conn.prepareStatement("INSERT INTO GameUsers(Username, Password) VALUES(?, ?)", Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, u.getUsername());
            stmt.setString(2, u.getPassword());

            int result = stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();

            if(rs.next()) u.setID(rs.getInt(1));

            return 0;
        }
        finally {
            closeSt(stmt);
            closeCon(conn);
        }

    }

    // метод обновления данных пользователя в БД
    @Override
    public int update(User u) throws Exception{
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = getConnection();
            stmt = conn.prepareStatement("UPDATE GameUsers SET Username=? Password=? WHERE ID=?");
            stmt.setString(1, u.getUsername());
            stmt.setString(2, u.getPassword());
            stmt.setInt(3, u.getID());

            return stmt.executeUpdate();
        } catch (SQLException e) {
            // e.printStackTrace();
            throw new RuntimeException(e);
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