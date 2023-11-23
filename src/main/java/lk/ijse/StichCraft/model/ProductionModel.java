package lk.ijse.StichCraft.model;

import lk.ijse.StichCraft.DBConnection.DBConnection;
import lk.ijse.StichCraft.DTO.ProductionDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ProductionModel {

    private String splitProductionID(String currentProductionID){
        if (currentProductionID != null){
            String[] split = currentProductionID.split("00");

            int id = Integer.parseInt(split[1]);
            id++;
            return "P00" + id;
        }else {
            return "P001";
        }
    }
    public String generateNextProduction() throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "SELECT production_id FROM production ORDER BY production_id DESC LIMIT 1";
        PreparedStatement ptsm = connection.prepareStatement(sql);
        ResultSet resultSet = ptsm.executeQuery();
        if (resultSet.next()){
            return splitProductionID(resultSet.getString(1));
        }
        return splitProductionID(null);
    }
}
