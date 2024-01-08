package lk.ijse.StichCraft.DAO.custom;

import lk.ijse.StichCraft.DAO.CrudDAO;
import lk.ijse.StichCraft.DTO.OrderDto;
import java.sql.SQLException;

public interface OrderDAO extends CrudDAO <OrderDto> {

    boolean placeOrder(OrderDto orderDto) throws SQLException;
    String generateNextId() throws SQLException;
}
