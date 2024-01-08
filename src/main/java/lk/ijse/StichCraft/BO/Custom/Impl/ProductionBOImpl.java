package lk.ijse.StichCraft.BO.Custom.Impl;

import lk.ijse.StichCraft.BO.Custom.ProductionBO;
import lk.ijse.StichCraft.DAO.DAOFactory;
import lk.ijse.StichCraft.DAO.custom.ProductionDAO;
import lk.ijse.StichCraft.DBConnection.DBConnection;
import lk.ijse.StichCraft.DTO.ProductionDto;
import lk.ijse.StichCraft.DTO.tm.OrderTm;
import lk.ijse.StichCraft.Entity.Production;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ProductionBOImpl implements ProductionBO {

    ProductionDAO productionDAO = (ProductionDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.PRODUCTION);

    @Override
    public boolean saveProduction(ProductionDto dto) throws SQLException {
        return productionDAO.save(new Production(dto.getProduction_id(),dto.getProduction_type(),dto.getStartDate(),
                dto.getEndDate(),dto.getDescription(),dto.getUnitPrice(),dto.getQuantityOnHand()));
    }

    public ProductionDto searchProduction(String SearchId) throws SQLException {
        Production production = productionDAO.searchId(SearchId);
        if (production != null){
            return new ProductionDto(production.getProduction_id(),production.getProduction_type(),
                    production.getStartDate(),production.getEndDate(),production.getDescription(),
                    production.getUnitPrice(),production.getQuantityOnHand());
        }else {
            return null;
        }
    }
    public String generateNextProductionId() throws SQLException {
        return productionDAO.generateNextId();
    }

    public ArrayList<ProductionDto> getAllProduction() throws SQLException {
        ArrayList<ProductionDto> productionDto = new ArrayList<>();
        List<Production> production = productionDAO.getAll();

        for (Production productionDto1 : production) {
            productionDto.add(new ProductionDto(productionDto1.getProduction_id(),productionDto1.getProduction_type(),
                    productionDto1.getStartDate(),productionDto1.getEndDate(),productionDto1.getDescription(),
                    productionDto1.getUnitPrice(),productionDto1.getQuantityOnHand()));
        }
        return productionDto;
    }
    public boolean deleteProduction(String productionId) throws SQLException {
        return productionDAO.delete(productionId);
    }
    public boolean updateProduction(ProductionDto dto) throws SQLException {
        return productionDAO.update(new Production(dto.getProduction_id(),dto.getProduction_type(),dto.getStartDate(),
                dto.getEndDate(),dto.getDescription(),dto.getUnitPrice(),dto.getQuantityOnHand()));
    }

    public boolean updateProductions(List<OrderTm> orderTmList) throws SQLException {
        return productionDAO.updateProductions(orderTmList);
    }
}
