package lk.ijse.StichCraft.DAO.custom;
import lk.ijse.StichCraft.DAO.CrudDAO;
import lk.ijse.StichCraft.DTO.CustomerDto;
import java.sql.SQLException;
import java.util.List;

public interface CustomerDAO extends CrudDAO<CustomerDto> {

    String generateNextId() throws SQLException;
    boolean save(CustomerDto dto) throws SQLException;
     List<CustomerDto> getAll() throws SQLException;
     boolean update(CustomerDto dto) throws SQLException;
     boolean delete(String id) throws SQLException;
     CustomerDto search(String phoneNumber) throws SQLException;
    CustomerDto searchId(String searchId) throws SQLException;
}
