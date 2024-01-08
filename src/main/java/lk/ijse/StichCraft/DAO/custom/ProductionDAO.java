package lk.ijse.StichCraft.DAO.custom;

import lk.ijse.StichCraft.DAO.CrudDAO;
import lk.ijse.StichCraft.DTO.ProductionDto;
import lk.ijse.StichCraft.DTO.tm.OrderTm;
import lk.ijse.StichCraft.Entity.Production;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface ProductionDAO extends CrudDAO<Production> {

     boolean save(Production dto) throws SQLException;
     Production searchId(String SearchId) throws SQLException;
    String generateNextId() throws SQLException;
    ArrayList<Production> getAll() throws SQLException;
    boolean delete(String productionId) throws SQLException;
    boolean update(Production dto) throws SQLException;
    boolean updateProductions(List<OrderTm> orderTmList) throws SQLException;
}
