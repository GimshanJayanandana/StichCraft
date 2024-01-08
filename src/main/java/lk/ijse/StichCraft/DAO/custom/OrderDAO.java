package lk.ijse.StichCraft.DAO.custom;

import lk.ijse.StichCraft.DAO.CrudDAO;
import lk.ijse.StichCraft.DTO.OrderDto;
import lk.ijse.StichCraft.Entity.Order;

import java.sql.SQLException;

public interface OrderDAO extends CrudDAO <Order> {
    String generateNextId() throws SQLException;
}
