package lk.ijse.StichCraft.DAO.custom;

import lk.ijse.StichCraft.DTO.OrderDto;
import java.sql.SQLException;

public interface OrderDAO {

    boolean placeOrder(OrderDto orderDto) throws SQLException;
    String generateNextOrder() throws SQLException;
}
