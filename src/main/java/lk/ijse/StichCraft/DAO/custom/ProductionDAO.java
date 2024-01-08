package lk.ijse.StichCraft.DAO.custom;

import lk.ijse.StichCraft.DAO.CrudDAO;
import lk.ijse.StichCraft.DTO.ProductionDto;
import lk.ijse.StichCraft.DTO.tm.OrderTm;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface ProductionDAO extends CrudDAO<ProductionDto> {

     boolean save(ProductionDto dto) throws SQLException;
     ProductionDto searchId(String SearchId) throws SQLException;
    String generateNextId() throws SQLException;
    ArrayList<ProductionDto> getAll() throws SQLException;
    boolean delete(String productionId) throws SQLException;
    boolean update(ProductionDto dto) throws SQLException;
    boolean updateProductions(List<OrderTm> orderTmList) throws SQLException;
}
