package lk.ijse.StichCraft.DAO.custom;

import lk.ijse.StichCraft.DTO.ProductionDto;
import lk.ijse.StichCraft.DTO.tm.OrderTm;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface ProductionDAO {

     boolean save(ProductionDto dto) throws SQLException;
     ProductionDto searchProduction(String SearchId) throws SQLException;
    String generateNextProduction() throws SQLException;
    ArrayList<ProductionDto> getAllProduction() throws SQLException;
    boolean deleteProduction(String productionId) throws SQLException;
    boolean updateProduction(ProductionDto dto) throws SQLException;
    boolean updateProductions(List<OrderTm> orderTmList) throws SQLException;
}
