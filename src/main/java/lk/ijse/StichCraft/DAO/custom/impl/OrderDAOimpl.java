package lk.ijse.StichCraft.DAO.custom.impl;

import lk.ijse.StichCraft.DAO.custom.OrderDAO;
import lk.ijse.StichCraft.DBConnection.DBConnection;
import lk.ijse.StichCraft.DTO.OrderDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class OrderDAOimpl implements OrderDAO {

//    private OrderModel orderModel = new OrderModel();
    private ProductionDAOimpl productionModel = new ProductionDAOimpl();
    private ProductionDetailDAOimpl productionDetailModel = new ProductionDetailDAOimpl();


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
            String [] split = currentOrderID.split("[O]");

            int id = Integer.parseInt(split[1]);
            id++;
            return String.format("O%03d",id);
        }else {
            return "O001";
        }
    }

    public boolean placeOrder(OrderDto orderDto) throws SQLException {

        String order_id = orderDto.getOrder_id();
        LocalDate date = orderDto.getOrder_date();
        String customer_id = orderDto.getCustomer_id();

        Connection connection = null;
        try {
            connection = DBConnection.getInstance().getConnection();
            connection.setAutoCommit(false);

            boolean isOrderSave = saveOrder(order_id,date,customer_id);
            if (isOrderSave){
                boolean isUpdated = productionModel.updateProductions(orderDto.getOrderTmList());
                if (isUpdated){
                    boolean isOrderDeatilsSaved = productionDetailModel.saveOrderDetails(orderDto.getOrder_id(),orderDto.getOrderTmList());
                    if (isOrderDeatilsSaved){
                        connection.commit();
                    }
                }
            }
            connection.rollback();
        } finally {
            connection.setAutoCommit(true);
        }
        return true;
    }

    private boolean saveOrder(String orderId, LocalDate date, String customerId) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "INSERT INTO orders VALUES (?,?,?)";
        PreparedStatement ptsm = connection.prepareStatement(sql);

        ptsm.setString(1,orderId);
        ptsm.setString(2, String.valueOf(date));
        ptsm.setString(3,customerId);

        return ptsm.executeUpdate() > 0;
    }
}
