package lk.ijse.StichCraft.DAO.custom;

import lk.ijse.StichCraft.DAO.CrudDAO;
import lk.ijse.StichCraft.DTO.SalaryDto;
import java.sql.SQLException;
import java.util.List;

public interface SalaryDAO extends CrudDAO <SalaryDto> {

    String generateNextId() throws SQLException;

    boolean save(SalaryDto dto) throws SQLException;

    List<SalaryDto> getAll() throws SQLException;

    boolean update(SalaryDto dto) throws SQLException;

    SalaryDto searchId(String id) throws SQLException;
    boolean delete(String id) throws SQLException;
}
