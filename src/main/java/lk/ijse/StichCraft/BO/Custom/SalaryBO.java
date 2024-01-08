package lk.ijse.StichCraft.BO.Custom;

import lk.ijse.StichCraft.BO.SuperBO;
import lk.ijse.StichCraft.DTO.SalaryDto;

import java.sql.SQLException;
import java.util.List;

public interface SalaryBO extends SuperBO {
     String generateNextSalaryId() throws SQLException;
     boolean saveSalary(SalaryDto dto) throws SQLException;
     List<SalaryDto> getAllSalary() throws SQLException;
     boolean updateSalary(SalaryDto dto) throws SQLException;
     SalaryDto searchSalary(String id) throws SQLException;
     boolean delete(String id) throws SQLException;
}
