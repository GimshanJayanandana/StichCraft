package lk.ijse.StichCraft.BO.Custom.Impl;

import lk.ijse.StichCraft.BO.Custom.SalaryBO;
import lk.ijse.StichCraft.DAO.DAOFactory;
import lk.ijse.StichCraft.DAO.custom.SalaryDAO;
import lk.ijse.StichCraft.DTO.SalaryDto;
import lk.ijse.StichCraft.Entity.Salary;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SalaryBOImpl implements SalaryBO {

    SalaryDAO salaryDAO = (SalaryDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.SALARY);

    public String generateNextSalaryId() throws SQLException {
        return salaryDAO.generateNextId();
    }
    public boolean saveSalary(SalaryDto dto) throws SQLException {
        return salaryDAO.save(new Salary(dto.getSalary_id(),dto.getAmount(),dto.getDate(),dto.getEmployee_id(),
                dto.getName()));
    }
    public List<SalaryDto> getAllSalary() throws SQLException {
        ArrayList<SalaryDto> salaryDto = new ArrayList<>();;
        List<Salary> salary = salaryDAO.getAll();

        for (Salary salaryDto1 : salary){
            salaryDto.add(new SalaryDto(salaryDto1.getSalary_id(),salaryDto1.getAmount(),salaryDto1.getDate(),
                    salaryDto1.getEmployee_id(),salaryDto1.getName()));
        }
        return salaryDto;
    }
    public boolean updateSalary(SalaryDto dto) throws SQLException {
        return salaryDAO.update(new Salary(dto.getSalary_id(),dto.getAmount(),dto.getDate(),dto.getEmployee_id(),
                dto.getName()));
    }

    public SalaryDto searchSalary(String id) throws SQLException {
        Salary salaryDto = salaryDAO.searchId(id);
        if (salaryDto != null){
            return new SalaryDto(salaryDto.getSalary_id(),salaryDto.getAmount(),salaryDto.getDate(),
                    salaryDto.getEmployee_id(),salaryDto.getName());
        }else {
            return null;
        }
    }
    public boolean delete(String id) throws SQLException {
        return salaryDAO.delete(id);
    }
}