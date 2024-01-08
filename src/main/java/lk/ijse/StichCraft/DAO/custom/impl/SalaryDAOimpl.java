package lk.ijse.StichCraft.DAO.custom.impl;

import lk.ijse.StichCraft.DAO.custom.SalaryDAO;
import lk.ijse.StichCraft.DBConnection.DBConnection;
import lk.ijse.StichCraft.DTO.SalaryDto;

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
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "SELECT salary_id FROM salary ORDER BY salary_id DESC Limit 1";
        PreparedStatement ptsm = connection.prepareStatement(sql);
        ResultSet resultSet = ptsm.executeQuery();
        if (resultSet.next()) {
            return splitSupplierID(resultSet.getString(1));
        }
        return splitSupplierID(null);
    }

    public boolean save(SalaryDto dto) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "INSERT INTO salary VALUES (?,?,?,?,?)";
        PreparedStatement ptsm = connection.prepareStatement(sql);

        ptsm.setString(1, dto.getSalary_id());
        ptsm.setString(2, String.valueOf(dto.getAmount()));
        ptsm.setString(3, String.valueOf(dto.getDate()));
        ptsm.setString(4, dto.getEmployee_id());
        ptsm.setString(5,dto.getName());

        return ptsm.executeUpdate() > 0;
    }

    public List<SalaryDto> getAll() throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "SELECT * FROM salary";
        PreparedStatement ptsm = connection.prepareStatement(sql);
        ResultSet resultSet = ptsm.executeQuery();

        ArrayList<SalaryDto> dtoList = new ArrayList<>();

        while (resultSet.next()){
            dtoList.add(
                    new SalaryDto(
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

    public boolean update(SalaryDto dto) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "UPDATE salary SET amount = ?,date = ?,employee_id = ?, name = ? WHERE salary_id = ?";
        PreparedStatement ptsm = connection.prepareStatement(sql);

        ptsm.setString(1, String.valueOf(dto.getAmount()));
        ptsm.setString(2, String.valueOf(dto.getDate()));
        ptsm.setString(3,dto.getEmployee_id());
        ptsm.setString(4, dto.getName());
        ptsm.setString(5,dto.getSalary_id());

        return ptsm.executeUpdate() > 0;
    }

    public SalaryDto searchId(String id) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "SELECT * FROM salary WHERE salary_id = ?";
        PreparedStatement ptsm = connection.prepareStatement(sql);
        ptsm.setString(1,id);
        ResultSet resultSet = ptsm.executeQuery();

        SalaryDto dto = null;
        if (resultSet.next()){
            String salary_id = resultSet.getString(1);
            double amount = Double.parseDouble(resultSet.getString(2));
            LocalDate date = LocalDate.parse(resultSet.getString(3));
            String employee_id = resultSet.getString(4);
            String name = resultSet.getString(5);

            dto = new SalaryDto(salary_id,amount,date,employee_id,name);
        }
        return dto;
    }

    public boolean delete(String id) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "DELETE FROM salary WHERE salary_id = ?";
        PreparedStatement ptsm = connection.prepareStatement(sql);
        ptsm.setString(1,id);
        return ptsm.executeUpdate() > 0;


    }

    @Override
    public SalaryDto search(String phoneNumber) throws SQLException {
        return null;
    }
}
