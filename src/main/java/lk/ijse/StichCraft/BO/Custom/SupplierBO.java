package lk.ijse.StichCraft.BO.Custom;

import lk.ijse.StichCraft.BO.SuperBO;
import lk.ijse.StichCraft.DTO.SupplierDto;

import java.sql.SQLException;
import java.util.List;

public interface SupplierBO extends SuperBO {
     String generateNextSupplierId() throws SQLException;
     boolean saveSupplier(SupplierDto dto) throws SQLException;
     List<SupplierDto> getAllSupplier() throws SQLException;
     SupplierDto searchSupplierByPhoneNumber(String phoneNumber) throws SQLException;
     SupplierDto searchSupplier(String searchId) throws SQLException;
     boolean update(SupplierDto dto) throws SQLException;
     boolean delete(String id) throws SQLException;
}
