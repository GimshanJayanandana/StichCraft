package lk.ijse.StichCraft.DAO.custom.impl;

import lk.ijse.StichCraft.DAO.custom.ProductionDetailDAO;
import lk.ijse.StichCraft.DBConnection.DBConnection;
import lk.ijse.StichCraft.DTO.ProductionDto;
import lk.ijse.StichCraft.DTO.tm.OrderTm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class ProductionDetailDAOimpl implements ProductionDetailDAO {
    public boolean save(String orderId, List<OrderTm> orderTmList) throws SQLException {
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

    @Override
    public String generateNextId() throws SQLException {
        return null;
    }

    @Override
    public boolean save(ProductionDto dto) throws SQLException {
        return false;
    }

    @Override
    public List<ProductionDto> getAll() throws SQLException {
        return null;
    }

    @Override
    public boolean update(ProductionDto dto) throws SQLException {
        return false;
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return false;
    }

    @Override
    public ProductionDto search(String phoneNumber) throws SQLException {
        return null;
    }

    @Override
    public ProductionDto searchId(String searchId) throws SQLException {
        return null;
    }
}

