package lk.ijse.StichCraft.DAO.custom.impl;

import lk.ijse.StichCraft.BO.BOFactory;
import lk.ijse.StichCraft.BO.Custom.ProductionBO;
import lk.ijse.StichCraft.DAO.SQLUtil;
import lk.ijse.StichCraft.DAO.custom.OrderDAO;
import lk.ijse.StichCraft.DBConnection.DBConnection;
import lk.ijse.StichCraft.DTO.OrderDto;
import lk.ijse.StichCraft.Entity.Order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class OrderDAOimpl implements OrderDAO {

    public String generateNextId() throws SQLException {
        ResultSet resultSet = SQLUtil.execute("SELECT order_id FROM orders ORDER BY order_id DESC LIMIT 1");
        if (resultSet.next()){
            return splitOrderID(resultSet.getString(1));
        }
        return splitOrderID(null);
    }

    @Override
    public boolean save(Order dto) throws SQLException {
        return SQLUtil.execute("INSERT INTO orders VALUES (?,?,?)",dto.getOrder_id(),dto.getOrder_date(),dto.getCustomer_id());
    }

    @Override
    public List<Order> getAll() throws SQLException {
        return null;
    }

    @Override
    public boolean update(Order dto) throws SQLException {
        return false;
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return false;
    }

    @Override
    public Order search(String phoneNumber) throws SQLException {
        return null;
    }

    @Override
    public Order searchId(String searchId) throws SQLException {
        return null;
    }

    private String splitOrderID(String currentOrderID) {
        if (currentOrderID != null){
            String [] split = currentOrderID.split("[O]");

            int id = Integer.parseInt(split[1]);
            id++;
            return String.format("O%03d",id);
        }else {
            return "O001";
        }
    }
}
