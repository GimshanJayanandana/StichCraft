package lk.ijse.StichCraft.model;

import lk.ijse.StichCraft.DBConnection.DBConnection;
import lk.ijse.StichCraft.DTO.SupplierDto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SuppllierModel {
    public String splitSupplier(String currentSupplierID) {
        if (currentSupplierID != null) {
            String[] split = currentSupplierID.split("00");

            int id = Integer.parseInt(split[1]);
            id++;
            return "S00" + id;
        } else {
            return "E001";
        }
    }

    public String generateNextSupplier() throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "SELECT supplier_id FROM supplier ORDER BY supplier_id DESC LIMIT 1";
        PreparedStatement ptsm = connection.prepareStatement(sql);
        ResultSet resultSet = ptsm.executeQuery();
        if (resultSet.next()) {
            return splitSupplier(resultSet.getString(1));
        }
        return splitSupplier(null);
    }

    public static boolean save(SupplierDto dto) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "INSERT INTO supplier VALUES (?,?,?)";
        PreparedStatement ptsm = connection.prepareStatement(sql);

        ptsm.setString(1, dto.getSupplier_id());
        ptsm.setString(2, dto.getName());
        ptsm.setString(3, dto.getContact());

        return ptsm.executeUpdate() > 0;
    }

    public List<SupplierDto> getAllsupplier() throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "SELECT * FROM supplier";
        PreparedStatement ptsm = connection.prepareStatement(sql);
        ResultSet resultSet = ptsm.executeQuery();

        ArrayList<SupplierDto> dtoList = new ArrayList<>();

        while (resultSet.next()) {
            dtoList.add(
                    new SupplierDto(
                            resultSet.getString(1),
                            resultSet.getString(2),
                            resultSet.getString(3)
                    )
            );
        }
        return dtoList;
    }

    public SupplierDto searchSupplierByPhoneNumber(String phoneNumber) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "SELECT * FROM supplier WHERE contact = ?";
        PreparedStatement ptsm = connection.prepareStatement(sql);
        ptsm.setString(1,phoneNumber);
        ResultSet resultSet = ptsm.executeQuery();

        SupplierDto dto = null;
        if (resultSet.next()){
            String supplier_id = resultSet.getString(1);
            String name = resultSet.getString(2);
            String contact = resultSet.getString(3);

            dto = new SupplierDto(supplier_id,name,contact);
        }
        return null;
    }

    public SupplierDto searchSupplier(String searchId) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "SELECT * FROM supplier WHERE supplier_id = ?";
        PreparedStatement ptsm = connection.prepareStatement(sql);
        ptsm.setString(1,searchId);
        ResultSet resultSet = ptsm.executeQuery();

        SupplierDto dto = null;
        if (resultSet.next()){
            String supplier_id = resultSet.getString(1);
            String name = resultSet.getString(2);
            String contact = resultSet.getString(3);

            dto = new SupplierDto(supplier_id,name,contact);
        }
        return dto;
    }

    public boolean updateSupplier(SupplierDto dto) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "UPDATE supplier SET name = ?,contact = ? WHERE supplier_id = ?";
        PreparedStatement ptsm = connection.prepareStatement(sql);

        ptsm.setString(1,dto.getName());
        ptsm.setString(2,dto.getContact());
        ptsm.setString(3,dto.getSupplier_id());

        return ptsm.executeUpdate() > 0;
    }

    public boolean deleteSupplier(String id) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "DELETE FROM supplier WHERE supplier_id = ?";
        PreparedStatement ptsm = connection.prepareStatement(sql);
        ptsm.setString(1,id);
        return ptsm.executeUpdate() > 0;
    }
}

