package lk.ijse.StichCraft.DAO.custom;
import lk.ijse.StichCraft.DTO.CustomerDto;
import java.sql.SQLException;
import java.util.List;

public interface CustomerDAO {

    String generateNextCustomer() throws SQLException;
    boolean save(CustomerDto dto) throws SQLException;
     List<CustomerDto> getAllCustomer() throws SQLException;
     boolean updateCustomer(CustomerDto dto) throws SQLException;
     boolean deleteCustomer(String id) throws SQLException;
     CustomerDto searchCustomerByPhoneNumber(String phoneNumber) throws SQLException;
    CustomerDto searchCustomer(String searchId) throws SQLException;
}
