package lk.ijse.StichCraft.BO.Custom.Impl;

import lk.ijse.StichCraft.BO.Custom.SupplierBO;
import lk.ijse.StichCraft.DAO.DAOFactory;
import lk.ijse.StichCraft.DAO.custom.SupplierDAO;
import lk.ijse.StichCraft.DTO.SupplierDto;
import lk.ijse.StichCraft.Entity.Supplier;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SupplierBOImpl implements SupplierBO {

    SupplierDAO supplierDAO = (SupplierDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.SUPPLIER);


    public String generateNextSupplierId() throws SQLException {
        return supplierDAO.generateNextId();
    }
    public boolean saveSupplier(SupplierDto dto) throws SQLException {
        return supplierDAO.save(new Supplier(dto.getSupplier_id(),dto.getName(),dto.getContact()));
    }
    public List<SupplierDto> getAllSupplier() throws SQLException {
        ArrayList<SupplierDto> supplierDto = new ArrayList<>();
        List<Supplier> supplier = supplierDAO.getAll();

        for (Supplier supplierDto1 : supplier) {
            supplierDto.add(new SupplierDto(supplierDto1.getSupplier_id(), supplierDto1.getName(),
                    supplierDto1.getContact()));
        }
        return supplierDto;
    }
    public SupplierDto searchSupplierByPhoneNumber(String phoneNumber) throws SQLException {
        Supplier supplierDto = supplierDAO.search(phoneNumber);
        if (supplierDto != null){
            return new SupplierDto(supplierDto.getSupplier_id(),supplierDto.getName(),supplierDto.getContact());
        }else {
            return null;
        }
    }
    public SupplierDto searchSupplier(String searchId) throws SQLException {
        Supplier supplierDto = supplierDAO.searchId(searchId);
        if (supplierDto != null){
            return new SupplierDto(supplierDto.getSupplier_id(),supplierDto.getName(),supplierDto.getContact());
        }else {
            return null;
        }
    }
    public boolean update(SupplierDto dto) throws SQLException {
        return supplierDAO.update(new Supplier(dto.getSupplier_id(),dto.getName(),dto.getContact()));
    }
    public boolean delete(String id) throws SQLException {
        return supplierDAO.delete(id);
    }
}
