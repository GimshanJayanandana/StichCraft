package lk.ijse.StichCraft.DAO.custom.impl;

import lk.ijse.StichCraft.DAO.SQLUtil;
import lk.ijse.StichCraft.DAO.custom.SalaryDAO;
import lk.ijse.StichCraft.DBConnection.DBConnection;
import lk.ijse.StichCraft.DTO.SalaryDto;
import lk.ijse.StichCraft.Entity.Salary;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SalaryDAOimpl implements SalaryDAO {
    private String splitSupplierID(String currentSalaryID) {
        if (currentSalaryID != null) {
            String[] split = currentSalaryID.split("00");

            int id = Integer.parseInt(split[1]);
            id++;
            return "SA00" + id;
        } else {
            return "SA001";
        }
    }
    public String generateNextId() throws SQLException {
        ResultSet resultSet = SQLUtil.execute("SELECT salary_id FROM salary ORDER BY salary_id DESC Limit 1");
        if (resultSet.next()) {
            return splitSupplierID(resultSet.getString(1));
        }
        return splitSupplierID(null);
    }

    public boolean save(Salary dto) throws SQLException {
        return SQLUtil.execute("INSERT INTO salary VALUES (?,?,?,?,?)",
                dto.getSalary_id(),dto.getAmount(),dto.getDate(),dto.getEmployee_id(),dto.getName());
    }

    public List<Salary> getAll() throws SQLException {
        ResultSet resultSet = SQLUtil.execute("SELECT * FROM salary");

        ArrayList<Salary> dtoList = new ArrayList<>();

        while (resultSet.next()){
            dtoList.add(
                    new Salary(
                            resultSet.getString(1),
                            resultSet.getDouble(2),
                            resultSet.getDate(3).toLocalDate(),
                            resultSet.getString(4),
                            resultSet.getString(5)
                    )
            );
        }
        return dtoList;
    }

    public boolean update(Salary dto) throws SQLException {
        return SQLUtil.execute("UPDATE salary SET amount = ?,date = ?,employee_id = ?, name = ? WHERE salary_id = ?",
                dto.getAmount(),dto.getDate(),dto.getEmployee_id(),dto.getName(),dto.getSalary_id());
    }

    public Salary searchId(String id) throws SQLException {
        ResultSet resultSet = SQLUtil.execute("SELECT * FROM salary WHERE salary_id = ?",id);

        Salary dto = null;
        if (resultSet.next()){
            String salary_id = resultSet.getString(1);
            double amount = Double.parseDouble(resultSet.getString(2));
            LocalDate date = LocalDate.parse(resultSet.getString(3));
            String employee_id = resultSet.getString(4);
            String name = resultSet.getString(5);

            dto = new Salary(salary_id,amount,date,employee_id,name);
        }
        return dto;
    }

    public boolean delete(String id) throws SQLException {
        return SQLUtil.execute("DELETE FROM salary WHERE salary_id = ?",id);


    }

    @Override
    public Salary search(String phoneNumber) throws SQLException {
        return null;
    }
}
