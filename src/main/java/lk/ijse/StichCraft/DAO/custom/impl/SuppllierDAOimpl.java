package lk.ijse.StichCraft.DAO.custom.impl;

import lk.ijse.StichCraft.DAO.SQLUtil;
import lk.ijse.StichCraft.DAO.custom.SalaryDAO;
import lk.ijse.StichCraft.DAO.custom.SupplierDAO;
import lk.ijse.StichCraft.DBConnection.DBConnection;
import lk.ijse.StichCraft.DTO.SupplierDto;
import lk.ijse.StichCraft.Entity.Supplier;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SuppllierDAOimpl implements SupplierDAO {
    private String splitSupplier(String currentSupplierID) {
        if (currentSupplierID != null) {
            String[] split = currentSupplierID.split("00");

            int id = Integer.parseInt(split[1]);
            id++;
            return "S00" + id;
        } else {
            return "S001";
        }
    }

    public String generateNextId() throws SQLException {
        ResultSet resultSet = SQLUtil.execute("SELECT supplier_id FROM supplier ORDER BY supplier_id DESC LIMIT 1");
        if (resultSet.next()) {
            return splitSupplier(resultSet.getString(1));
        }
        return splitSupplier(null);
    }

    public boolean save(Supplier dto) throws SQLException {
        return SQLUtil.execute("INSERT INTO supplier VALUES (?,?,?)",
                dto.getSupplier_id(),dto.getName(),dto.getContact());
    }

    public List<Supplier> getAll() throws SQLException {
        ResultSet resultSet = SQLUtil.execute("SELECT * FROM supplier");

        ArrayList<Supplier> dtoList = new ArrayList<>();

        while (resultSet.next()) {
            dtoList.add(
                    new Supplier(
                            resultSet.getString(1),
                            resultSet.getString(2),
                            resultSet.getString(3)
                    )
            );
        }
        return dtoList;
    }

    public Supplier search(String phoneNumber) throws SQLException {
        ResultSet resultSet = SQLUtil.execute("SELECT * FROM supplier WHERE contact = ?",phoneNumber);

        Supplier dto = null;
        if (resultSet.next()){
            String supplier_id = resultSet.getString(1);
            String name = resultSet.getString(2);
            String contact = resultSet.getString(3);

            dto = new Supplier(supplier_id,name,contact);
        }
        return dto;
    }

    public Supplier searchId(String searchId) throws SQLException {
        ResultSet resultSet = SQLUtil.execute("SELECT * FROM supplier WHERE supplier_id = ?",searchId);

        Supplier dto = null;
        if (resultSet.next()){
            String supplier_id = resultSet.getString(1);
            String name = resultSet.getString(2);
            String contact = resultSet.getString(3);

            dto = new Supplier(supplier_id,name,contact);
        }
        return dto;
    }

    public boolean update(Supplier dto) throws SQLException {
        return SQLUtil.execute("UPDATE supplier SET name = ?,contact = ? WHERE supplier_id = ?",
                dto.getName(),dto.getContact(),dto.getSupplier_id());
    }

    public boolean delete(String id) throws SQLException {
        return SQLUtil.execute("DELETE FROM supplier WHERE supplier_id = ?",id);
    }
}

