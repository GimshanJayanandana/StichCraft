package lk.ijse.StichCraft.DAO.custom;

import lk.ijse.StichCraft.DTO.SalaryDto;
import java.sql.SQLException;
import java.util.List;

public interface SalaryDAO {

    String generateNextSalary() throws SQLException;

    boolean save(SalaryDto dto) throws SQLException;

    List<SalaryDto> getAllSalary() throws SQLException;

    boolean updateSalary(SalaryDto dto) throws SQLException;

    SalaryDto searchSalaryById(String id) throws SQLException;
    boolean deleteSalary(String id) throws SQLException;
}
