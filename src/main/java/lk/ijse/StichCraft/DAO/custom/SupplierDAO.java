package lk.ijse.StichCraft.DAO.custom;

import lk.ijse.StichCraft.DTO.SupplierDto;
import java.sql.SQLException;
import java.util.List;

public interface SupplierDAO {

     String generateNextSupplier() throws SQLException;
     boolean save(SupplierDto dto) throws SQLException;
     List<SupplierDto> getAllsupplier() throws SQLException;
     SupplierDto searchSupplierByPhoneNumber(String phoneNumber) throws SQLException;
     SupplierDto searchSupplier(String searchId) throws SQLException;
     boolean updateSupplier(SupplierDto dto) throws SQLException;
     boolean deleteSupplier(String id) throws SQLException;
}
