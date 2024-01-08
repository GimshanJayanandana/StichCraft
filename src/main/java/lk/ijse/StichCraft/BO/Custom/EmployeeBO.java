package lk.ijse.StichCraft.BO.Custom;

import lk.ijse.StichCraft.BO.SuperBO;
import lk.ijse.StichCraft.DTO.EmployeeDto;

import java.sql.SQLException;
import java.util.List;

public interface EmployeeBO extends SuperBO {
     String generateNextEmployeeId() throws SQLException;
     boolean saveEmployee(EmployeeDto dto) throws SQLException;
     List<EmployeeDto> getAllEmployee() throws SQLException;
     boolean updateEmployee(EmployeeDto dto) throws SQLException;
     EmployeeDto searchEmployeeByPhoneNumber(String phoneNumber) throws SQLException;
     boolean deleteEmployee(String id) throws SQLException;
     EmployeeDto searchId(String searchInput) throws SQLException;
}
