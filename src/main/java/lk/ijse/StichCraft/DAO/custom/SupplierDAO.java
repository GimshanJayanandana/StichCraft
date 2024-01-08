package lk.ijse.StichCraft.DAO.custom;

import lk.ijse.StichCraft.DAO.CrudDAO;
import lk.ijse.StichCraft.DTO.SupplierDto;
import java.sql.SQLException;
import java.util.List;

public interface SupplierDAO extends CrudDAO<SupplierDto> {

     String generateNextId() throws SQLException;
     boolean save(SupplierDto dto) throws SQLException;
     List<SupplierDto> getAll() throws SQLException;
     SupplierDto search(String phoneNumber) throws SQLException;
     SupplierDto searchId(String searchId) throws SQLException;
     boolean update(SupplierDto dto) throws SQLException;
     boolean delete(String id) throws SQLException;
}
