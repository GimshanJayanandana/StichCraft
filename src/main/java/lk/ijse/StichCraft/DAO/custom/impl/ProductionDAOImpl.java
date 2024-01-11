package lk.ijse.StichCraft.DAO.custom.impl;

import lk.ijse.StichCraft.DAO.SQLUtil;
import lk.ijse.StichCraft.DAO.custom.ProductionDAO;
import lk.ijse.StichCraft.DBConnection.DBConnection;
import lk.ijse.StichCraft.DTO.tm.OrderTm;
import lk.ijse.StichCraft.Entity.Production;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ProductionDAOImpl implements ProductionDAO {

    public boolean save(Production dto) throws SQLException {
        return SQLUtil.execute("INSERT INTO production VALUES (?,?,?,?,?,?,?)",dto.getProduction_id(),
                dto.getProduction_type(),dto.getStartDate(),dto.getEndDate(),dto.getDescription(),dto.getUnitPrice(),
                dto.getQuantityOnHand());
    }

    public Production searchId(String SearchId) throws SQLException {
        ResultSet resultSet = SQLUtil.execute("SELECT * FROM production WHERE production_id = ?",SearchId);

        Production dto = null;
        if (resultSet.next()) {
            String production_id = resultSet.getString(1);
            String production_type = resultSet.getString(2);
            LocalDate StarDate = LocalDate.parse(resultSet.getString(3));
            LocalDate EndDate = LocalDate.parse(resultSet.getString(4));
            String Description = resultSet.getString(5);
            double unitPrice = resultSet.getDouble(6);
            int quantityOnHand = resultSet.getInt(7);

            dto = new Production(production_id, production_type, StarDate, EndDate, Description,unitPrice,quantityOnHand);
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
    public String generateNextId() throws SQLException {
        ResultSet resultSet = SQLUtil.execute("SELECT production_id FROM production ORDER BY production_id DESC LIMIT 1");
        if (resultSet.next()){
            return splitProductionID(resultSet.getString(1));
        }
        return splitProductionID(null);
    }

    public ArrayList<Production> getAll() throws SQLException {
        ResultSet resultSet = SQLUtil.execute("SELECT * FROM production");

        ArrayList<Production> dtoList = new ArrayList<>();

        while (resultSet.next()) {
            dtoList.add(
                    new Production(
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
    public boolean delete(String productionId) throws SQLException {
        return SQLUtil.execute("DELETE FROM production WHERE production_id = ?",productionId);
    }

    @Override
    public Production search(String phoneNumber) throws SQLException {
        return null;
    }

    public boolean update(Production dto) throws SQLException {
        return SQLUtil.execute("UPDATE production SET production_type = ?,StartDate = ?,EndDate = ?," +
                "Description = ?,unitPrice = ?,quantityOnHand = ? WHERE production_id = ?",
                dto.getProduction_type(),dto.getStartDate(),dto.getEndDate(),dto.getDescription(),dto.getUnitPrice(),
                dto.getQuantityOnHand(),dto.getProduction_id());
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
        return SQLUtil.execute("UPDATE production SET quantityOnHand = quantityOnHand - ? WHERE production_id = ?",
                productId,quantity);
    }
}
