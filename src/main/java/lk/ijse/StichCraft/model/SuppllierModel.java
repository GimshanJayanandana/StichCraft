package lk.ijse.StichCraft.model;

import lk.ijse.StichCraft.DBConnection.DBConnection;
import lk.ijse.StichCraft.DTO.SupplierDto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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

        ptsm.setString(1,dto.getId());
        ptsm.setString(2,dto.getName());
        ptsm.setString(3, dto.getContact());

        return ptsm.executeUpdate() > 0;
    }
}

