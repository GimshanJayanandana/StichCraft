package lk.ijse.StichCraft.BO.Custom;

import lk.ijse.StichCraft.BO.SuperBO;
import lk.ijse.StichCraft.DBConnection.DBConnection;
import lk.ijse.StichCraft.DTO.OrderDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public interface OrderBO extends SuperBO {
    String generateNextId() throws SQLException;
    boolean placeOrder(OrderDto orderDto) throws SQLException;
}
