package lk.ijse.StichCraft.DAO;

import lk.ijse.StichCraft.DTO.CustomerDto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface CrudDAO <T> extends SuperDAO {

     String generateNextId() throws SQLException;
     boolean save(T dto) throws SQLException;
     List<T> getAll() throws SQLException;
     boolean update(T dto) throws SQLException;
     boolean delete(String id) throws SQLException;
     T search(String phoneNumber) throws SQLException;
     T searchId(String searchId) throws SQLException;
}
