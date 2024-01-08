package lk.ijse.StichCraft.DAO.custom;

import lk.ijse.StichCraft.DAO.CrudDAO;
import lk.ijse.StichCraft.DTO.SalaryDto;
import lk.ijse.StichCraft.Entity.Salary;

import java.sql.SQLException;
import java.util.List;

public interface SalaryDAO extends CrudDAO <Salary> {

    String generateNextId() throws SQLException;

    boolean save(Salary dto) throws SQLException;

    List<Salary> getAll() throws SQLException;

    boolean update(Salary dto) throws SQLException;

    Salary searchId(String id) throws SQLException;
    boolean delete(String id) throws SQLException;
}
