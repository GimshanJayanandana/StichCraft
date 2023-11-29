package lk.ijse.StichCraft.model;

import lk.ijse.StichCraft.DBConnection.DBConnection;
import lk.ijse.StichCraft.DTO.ProductionDto;
import lombok.Data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProductionModel {

    public static boolean save(ProductionDto dto) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "INSERT INTO production VALUES (?,?,?,?,?)";
        PreparedStatement ptsm = connection.prepareStatement(sql);

        ptsm.setString(1,dto.getProduction_id());
        ptsm.setString(2,dto.getProduction_type());
        ptsm.setString(3, String.valueOf(dto.getStartDate()));
        ptsm.setString(4, String.valueOf(dto.getEndDate()));
        ptsm.setString(5,dto.getDescription());

        return ptsm.executeUpdate() > 0;
    }
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

    public ArrayList<ProductionDto> getAllProduction() throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "SELECT * FROM production";
        PreparedStatement ptsm = connection.prepareStatement(sql);
        ResultSet resultSet = ptsm.executeQuery();

        ArrayList<ProductionDto> dtoList = new ArrayList<>();

        while (resultSet.next()) {
            dtoList.add(
                    new ProductionDto(
                            resultSet.getString(1),
                            resultSet.getString(2),
                            resultSet.getDate(3).toLocalDate(),
                            resultSet.getDate(4).toLocalDate(),
                            resultSet.getString(5)
                    )
            );
        }
        return dtoList;
    }
}
