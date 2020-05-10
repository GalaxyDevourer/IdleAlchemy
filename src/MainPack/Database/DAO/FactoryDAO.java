package MainPack.Database.DAO;

public interface FactoryDAO<T> {
    T get(String username) throws Exception;
    int insert(T user) throws Exception;
    int update(T user) throws Exception;
}
