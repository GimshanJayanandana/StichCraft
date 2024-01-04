package lk.ijse.StichCraft.DAO;

import lk.ijse.StichCraft.DBConnection.DBConnection;
import lk.ijse.StichCraft.DTO.ProductionDto;
import lk.ijse.StichCraft.DTO.tm.OrderTm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ProductionModel {

    public static boolean save(ProductionDto dto) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "INSERT INTO production VALUES (?,?,?,?,?,?,?)";
        PreparedStatement ptsm = connection.prepareStatement(sql);

        ptsm.setString(1, dto.getProduction_id());
        ptsm.setString(2, dto.getProduction_type());
        ptsm.setString(3, String.valueOf(dto.getStartDate()));
        ptsm.setString(4, String.valueOf(dto.getEndDate()));
        ptsm.setString(5, dto.getDescription());
        ptsm.setString(6, String.valueOf(dto.getUnitPrice()));
        ptsm.setString(7, String.valueOf(dto.getQuantityOnHand()));

        return ptsm.executeUpdate() > 0;
    }

    public ProductionDto searchProduction(String SearchId) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "SELECT * FROM production WHERE production_id = ?";
        PreparedStatement ptsm = connection.prepareStatement(sql);
        ptsm.setString(1, SearchId);
        ResultSet resultSet = ptsm.executeQuery();

        ProductionDto dto = null;
        if (resultSet.next()) {
            String production_id = resultSet.getString(1);
            String production_type = resultSet.getString(2);
            LocalDate StarDate = LocalDate.parse(resultSet.getString(3));
            LocalDate EndDate = LocalDate.parse(resultSet.getString(4));
            String Description = resultSet.getString(5);
            double unitPrice = resultSet.getDouble(6);
            int quantityOnHand = resultSet.getInt(7);

            dto = new ProductionDto(production_id, production_type, StarDate, EndDate, Description,unitPrice,quantityOnHand);
        }
        return dto;
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
                            resultSet.getString(5),
                            resultSet.getDouble(6),
                            resultSet.getInt(7)
                    )
            );
        }
        return dtoList;
    }
    public boolean deleteProduction(String productionId) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "DELETE FROM production WHERE production_id = ?";
        PreparedStatement ptsm = connection.prepareStatement(sql);
        ptsm.setString(1,productionId);
        return ptsm.executeUpdate() > 0;
    }

    public boolean updateProduction(ProductionDto dto) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "UPDATE production SET production_type = ?,StartDate = ?,EndDate = ?,Description = ?,unitPrice = ?,quantityOnHand = ? WHERE production_id = ?";
        PreparedStatement ptsm = connection.prepareStatement(sql);

        ptsm.setString(1,dto.getProduction_type());
        ptsm.setString(2, String.valueOf(dto.getStartDate()));
        ptsm.setString(3, String.valueOf(dto.getEndDate()));
        ptsm.setString(4,dto.getDescription());
        ptsm.setString(5, String.valueOf(dto.getUnitPrice()));
        ptsm.setString(6, String.valueOf(dto.getQuantityOnHand()));
        ptsm.setString(7,dto.getProduction_id());

        return ptsm.executeUpdate() > 0;
    }

    public boolean updateProductions(List<OrderTm> orderTmList) throws SQLException {
        for (OrderTm tm : orderTmList){
            if (!updateQuantity(tm.getProductId(), tm.getQuantity())) {
                return false;
            }
        }
        return true;
    }
    private boolean updateQuantity(String productId, int quantity) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "UPDATE production SET quantityOnHand = quantityOnHand - ? WHERE production_id = ?";
        PreparedStatement ptsm = connection.prepareStatement(sql);

        ptsm.setInt(1,quantity);
        ptsm.setString(2,productId);

        return ptsm.executeUpdate() > 0;
    }
}
