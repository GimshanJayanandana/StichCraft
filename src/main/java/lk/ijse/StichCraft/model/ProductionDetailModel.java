package lk.ijse.StichCraft.model;

import lk.ijse.StichCraft.DBConnection.DBConnection;
import lk.ijse.StichCraft.DTO.tm.OrderTm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class ProductionDetailModel {
    public boolean saveOrderDetails(String orderId, List<OrderTm> orderTmList) throws SQLException {
        for (OrderTm tm : orderTmList){
            if (!saveOrderDetails(orderId, tm)){
                return false;
            }
        }
        return true;
    }

    private boolean saveOrderDetails(String orderId, OrderTm tm) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "INSERT INTO ProductionDetails VALUES (?,?)";
        PreparedStatement ptsm = connection.prepareStatement(sql);

        ptsm.setString(1,orderId);
        ptsm.setString(2,tm.getProductId());

        return ptsm.executeUpdate() > 0;
    }
}

