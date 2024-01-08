package lk.ijse.StichCraft.DAO.custom;

import lk.ijse.StichCraft.DAO.CrudDAO;
import lk.ijse.StichCraft.DTO.EmployeeDto;
import lk.ijse.StichCraft.Entity.Employee;

import java.sql.SQLException;
import java.util.List;

public interface EmployeeDAO extends CrudDAO<Employee> {

     String generateNextId() throws SQLException;
     boolean save(Employee dto) throws SQLException;
     List<Employee> getAll() throws SQLException;
     boolean update(Employee dto) throws SQLException;
     Employee search(String phoneNumber) throws SQLException;
     boolean delete(String id) throws SQLException;
     Employee searchId(String searchInput) throws SQLException;
}
