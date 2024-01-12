package lk.ijse.StichCraft.BO.Custom.Impl;

import lk.ijse.StichCraft.BO.Custom.OrderBO;
import lk.ijse.StichCraft.DAO.DAOFactory;
import lk.ijse.StichCraft.DAO.custom.OrderDAO;
import lk.ijse.StichCraft.DAO.custom.ProductionDAO;
import lk.ijse.StichCraft.DAO.custom.ProductionDetailDAO;
import lk.ijse.StichCraft.DAO.custom.impl.ProductionDetailDAOimpl;
import lk.ijse.StichCraft.DBConnection.DBConnection;
import lk.ijse.StichCraft.DTO.OrderDto;
import lk.ijse.StichCraft.Entity.Order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class OrderBOImpl implements OrderBO {

    OrderDAO orderDAO = (OrderDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDER);
    ProductionDAO productionDAO = (ProductionDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.PRODUCTION);
    ProductionDetailDAO productionDetailDAO = (ProductionDetailDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.PRODUCTIONDETAIL);
    public String generateNextId() throws SQLException {
        return orderDAO.generateNextId();
    }
    public boolean placeOrder(OrderDto orderDto) throws SQLException {

        String order_id = orderDto.getOrder_id();
        LocalDate date = orderDto.getOrder_date();
        String customer_id = orderDto.getCustomer_id();

        Connection connection = null;
        try {
            connection = DBConnection.getInstance().getConnection();
            connection.setAutoCommit(false);

            boolean isOrderSave = orderDAO.save(new Order(order_id,date,customer_id));
            if (isOrderSave){
                boolean isUpdated = productionDAO.updateProductions(orderDto.getOrderTmList());
                if (isUpdated){
                    boolean isOrderDeatilsSaved = productionDetailDAO.save(orderDto.getOrder_id(),orderDto.getOrderTmList());
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
}
