package lk.ijse.StichCraft.DAO.custom.impl;

import lk.ijse.StichCraft.DBConnection.DBConnection;
import lk.ijse.StichCraft.DTO.EmployeeDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeModel {
    public String splitCustomer(String currentEmployeeID) {
        if (currentEmployeeID != null) {
            String[] split = currentEmployeeID.split("00");

            int id = Integer.parseInt(split[1]);
            id++;
            return "E00" + id;
        } else {
            return "E001";
        }
    }

    public String generateNextEmployee() throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "SELECT employee_id FROM employee ORDER BY employee_id DESC LIMIT 1 ";
        PreparedStatement ptsm = connection.prepareStatement(sql);
        ResultSet resultSet = ptsm.executeQuery();
        if (resultSet.next()) {
            return splitCustomer(resultSet.getString(1));
        }
        return splitCustomer(null);
    }

    public static boolean save(EmployeeDto dto) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "INSERT INTO employee VALUES (?,?,?,?)";
        PreparedStatement ptsm = connection.prepareStatement(sql);

        ptsm.setString(1, dto.getEmployee_id());
        ptsm.setString(2, dto.getName());
        ptsm.setString(3, dto.getAddress());
        ptsm.setString(4, String.valueOf(dto.getContact()));

        return ptsm.executeUpdate() > 0;
    }

    public List<EmployeeDto> getAllEmployee() throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "SELECT * FROM employee";
        PreparedStatement ptsm = connection.prepareStatement(sql);
        ResultSet resultSet = ptsm.executeQuery();

        ArrayList<EmployeeDto> dtoList = new ArrayList<>();

        while (resultSet.next()) {
            dtoList.add(
                    new EmployeeDto(
                            resultSet.getString(1),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getString(4)
                    )
            );
        }
        return dtoList;
    }

    public boolean updateEmployee(EmployeeDto dto) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "UPDATE employee SET name = ?, address = ?,contact = ? WHERE employee_id = ?";
        PreparedStatement ptsm = connection.prepareStatement(sql);

        ptsm.setString(1, dto.getName());
        ptsm.setString(2, dto.getAddress());
        ptsm.setString(3, dto.getContact());
        ptsm.setString(4, dto.getEmployee_id());

        return ptsm.executeUpdate() > 0;
    }

    public EmployeeDto searchEmployeeByPhoneNumber(String phoneNumber) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "SELECT * FROM employee WHERE contact = ?";
        PreparedStatement ptsm = connection.prepareStatement(sql);
        ptsm.setString(1,phoneNumber);
        ResultSet resultSet = ptsm.executeQuery();

        EmployeeDto dto = null;
        if (resultSet.next()){
            String employee_id = resultSet.getString(1);
            String name = resultSet.getString(2);
            String address = resultSet.getString(3);
            String contact = resultSet.getString(4);

            dto = new EmployeeDto(employee_id,name,address,contact);
        }
        return dto;

    }

    public boolean deleteEmployee(String id) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "DELETE FROM employee WHERE employee_id = ?";
        PreparedStatement ptsm = connection.prepareStatement(sql);
        ptsm.setString(1,id);
        return ptsm.executeUpdate() > 0;
    }

    public EmployeeDto searchEmployee(String searchInput) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "SELECT * FROM employee WHERE employee_id = ?";
        PreparedStatement ptsm = connection.prepareStatement(sql);
        ptsm.setString(1,searchInput);
        ResultSet resultSet = ptsm.executeQuery();

        EmployeeDto dto = null;
        if (resultSet.next()){
            String employee_id = resultSet.getString(1);
            String name = resultSet.getString(2);
            String address = resultSet.getString(3);
            String contact = resultSet.getString(4);

            dto = new EmployeeDto(employee_id,name,address,contact);
        }
        return dto;
    }
}

