package lk.ijse.StichCraft.DAO.custom;

import lk.ijse.StichCraft.DAO.CrudDAO;
import lk.ijse.StichCraft.DTO.SupplierDto;
import lk.ijse.StichCraft.Entity.Supplier;

import java.sql.SQLException;
import java.util.List;

public interface SupplierDAO extends CrudDAO<Supplier> {

     String generateNextId() throws SQLException;
     boolean save(Supplier dto) throws SQLException;
     List<Supplier> getAll() throws SQLException;
     Supplier search(String phoneNumber) throws SQLException;
     Supplier searchId(String searchId) throws SQLException;
     boolean update(Supplier dto) throws SQLException;
     boolean delete(String id) throws SQLException;
}
