package lk.ijse.StichCraft.BO.Custom;

import lk.ijse.StichCraft.BO.SuperBO;
import lk.ijse.StichCraft.DTO.CustomerDto;

import java.sql.SQLException;
import java.util.List;

public interface CustomerBO extends SuperBO {

     String generateNextCustomerId() throws SQLException;
     boolean saveCustomer(CustomerDto dto) throws SQLException;
     List<CustomerDto> getAllCustomer() throws SQLException;
     boolean updateCustomer(CustomerDto dto) throws SQLException;
     boolean deleteCustomer(String id) throws SQLException;
     CustomerDto searchCustomerByPhoneNumber(String phoneNumber) throws SQLException;
     CustomerDto searchCustomer(String searchId) throws SQLException;
}
