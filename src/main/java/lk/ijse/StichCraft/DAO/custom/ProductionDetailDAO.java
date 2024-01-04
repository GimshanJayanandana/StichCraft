package lk.ijse.StichCraft.DAO.custom;

import lk.ijse.StichCraft.DTO.tm.OrderTm;

import java.sql.SQLException;
import java.util.List;

public interface ProductionDetailDAO {
    boolean saveOrderDetails(String orderId, List<OrderTm> orderTmList) throws SQLException;
}
