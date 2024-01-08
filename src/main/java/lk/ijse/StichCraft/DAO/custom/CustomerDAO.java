package lk.ijse.StichCraft.DAO.custom;
import lk.ijse.StichCraft.DAO.CrudDAO;
import lk.ijse.StichCraft.DTO.CustomerDto;
import lk.ijse.StichCraft.Entity.Customer;

import java.sql.SQLException;
import java.util.List;

public interface CustomerDAO extends CrudDAO<Customer> {

    String generateNextId() throws SQLException;
    boolean save(Customer dto) throws SQLException;
     List<Customer> getAll() throws SQLException;
     boolean update(Customer dto) throws SQLException;
     boolean delete(String id) throws SQLException;
     Customer search(String phoneNumber) throws SQLException;
    Customer searchId(String searchId) throws SQLException;
}
