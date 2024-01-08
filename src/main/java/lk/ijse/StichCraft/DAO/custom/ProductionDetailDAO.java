package lk.ijse.StichCraft.DAO.custom;

import lk.ijse.StichCraft.DAO.CrudDAO;
import lk.ijse.StichCraft.DTO.ProductionDto;
import lk.ijse.StichCraft.DTO.tm.OrderTm;

import java.sql.SQLException;
import java.util.List;

public interface ProductionDetailDAO extends CrudDAO <ProductionDto> {
    boolean save(String orderId, List<OrderTm> orderTmList) throws SQLException;
}
