package lk.ijse.StichCraft.DAO.custom;

import lk.ijse.StichCraft.DTO.EmployeeDto;
import java.sql.SQLException;
import java.util.List;

public interface EmployeeDAO {

     String generateNextEmployee() throws SQLException;
     boolean save(EmployeeDto dto) throws SQLException;
     List<EmployeeDto> getAllEmployee() throws SQLException;
     boolean updateEmployee(EmployeeDto dto) throws SQLException;
     EmployeeDto searchEmployeeByPhoneNumber(String phoneNumber) throws SQLException;
     boolean deleteEmployee(String id) throws SQLException;
     EmployeeDto searchEmployee(String searchInput) throws SQLException;
}
