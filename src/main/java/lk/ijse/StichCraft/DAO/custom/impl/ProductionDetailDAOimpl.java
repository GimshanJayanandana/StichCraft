package lk.ijse.StichCraft.DAO.custom.impl;

import lk.ijse.StichCraft.DAO.SQLUtil;
import lk.ijse.StichCraft.DAO.custom.ProductionDetailDAO;
import lk.ijse.StichCraft.DBConnection.DBConnection;
import lk.ijse.StichCraft.DTO.ProductionDto;
import lk.ijse.StichCraft.DTO.tm.OrderTm;
import lk.ijse.StichCraft.Entity.Production;

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
        return SQLUtil.execute("INSERT INTO ProductionDetails VALUES (?,?)",orderId,tm);
    }

    @Override
    public String generateNextId() throws SQLException {
        return null;
    }

    @Override
    public boolean save(Production dto) throws SQLException {
        return false;
    }

    @Override
    public List<Production> getAll() throws SQLException {
        return null;
    }

    @Override
    public boolean update(Production dto) throws SQLException {
        return false;
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return false;
    }

    @Override
    public Production search(String phoneNumber) throws SQLException {
        return null;
    }

    @Override
    public Production searchId(String searchId) throws SQLException {
        return null;
    }
}

