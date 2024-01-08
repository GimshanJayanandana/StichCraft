package lk.ijse.StichCraft.DAO.custom;

import lk.ijse.StichCraft.DTO.EmployeeDto;
import java.sql.SQLException;
import java.util.List;

public interface EmployeeDAO {

     String generateNextId() throws SQLException;
     boolean save(EmployeeDto dto) throws SQLException;
     List<EmployeeDto> getAll() throws SQLException;
     boolean update(EmployeeDto dto) throws SQLException;
     EmployeeDto search(String phoneNumber) throws SQLException;
     boolean delete(String id) throws SQLException;
     EmployeeDto searchId(String searchInput) throws SQLException;
}
