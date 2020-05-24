package MainPack.Database.DAO;

import java.util.List;

public interface FactoryDAO<T> {
    T get(String username) throws Exception;
    List<T> getAll() throws Exception;
    int insert(T user) throws Exception;
    int update(T user) throws Exception;
}
