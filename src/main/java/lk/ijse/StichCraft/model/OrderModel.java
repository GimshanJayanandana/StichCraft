package lk.ijse.StichCraft.model;

import lk.ijse.StichCraft.DBConnection.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderModel {
    public String generateNextOrder() throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "SELECT order_id FROM orders ORDER BY order_id DESC LIMIT 1";
        PreparedStatement ptsm = connection.prepareStatement(sql);
        ResultSet resultSet = ptsm.executeQuery();
        if (resultSet.next()){
            return splitOrderID(resultSet.getString(1));
        }
        return splitOrderID(null);
    }
    private String splitOrderID(String currentOrderID) {
        if (currentOrderID != null){
            String[] split = currentOrderID.split("00");

            int id = Integer.parseInt(split[1]);
            id++;
            return "OR00" + id;
        }else {
            return "OR001";
        }
    }
}
