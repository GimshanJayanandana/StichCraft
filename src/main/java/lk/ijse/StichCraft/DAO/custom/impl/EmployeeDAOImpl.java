package lk.ijse.StichCraft.DAO.custom.impl;

import lk.ijse.StichCraft.DAO.SQLUtil;
import lk.ijse.StichCraft.DAO.custom.EmployeeDAO;
import lk.ijse.StichCraft.DBConnection.DBConnection;
import lk.ijse.StichCraft.Entity.Employee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAOImpl implements EmployeeDAO {

    private String splitCustomer(String currentEmployeeID) {
        if (currentEmployeeID != null) {
            String[] split = currentEmployeeID.split("00");

            int id = Integer.parseInt(split[1]);
            id++;
            return "E00" + id;
        } else {
            return "E001";
        }
    }

    public String generateNextId() throws SQLException {
        ResultSet resultSet = SQLUtil.execute("SELECT employee_id FROM employee ORDER BY employee_id DESC LIMIT 1 ");
        if (resultSet.next()) {
            return splitCustomer(resultSet.getString(1));
        }
        return splitCustomer(null);
    }

    public boolean save(Employee dto) throws SQLException {
        return SQLUtil.execute("INSERT INTO employee VALUES (?,?,?,?)",dto.getEmployee_id(),dto.getName(),
                dto.getAddress(),dto.getContact());
    }

    public List<Employee> getAll() throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "SELECT * FROM employee";
        PreparedStatement ptsm = connection.prepareStatement(sql);
        ResultSet resultSet = ptsm.executeQuery();

        ArrayList<Employee> dtoList = new ArrayList<>();

        while (resultSet.next()) {
            dtoList.add(
                    new Employee(
                            resultSet.getString(1),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getString(4)
                    )
            );
        }
        return dtoList;
    }

    public boolean update(Employee dto) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "UPDATE employee SET name = ?, address = ?,contact = ? WHERE employee_id = ?";
        PreparedStatement ptsm = connection.prepareStatement(sql);

        ptsm.setString(1, dto.getName());
        ptsm.setString(2, dto.getAddress());
        ptsm.setString(3, dto.getContact());
        ptsm.setString(4, dto.getEmployee_id());

        return ptsm.executeUpdate() > 0;
    }

    public Employee search(String phoneNumber) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "SELECT * FROM employee WHERE contact = ?";
        PreparedStatement ptsm = connection.prepareStatement(sql);
        ptsm.setString(1,phoneNumber);
        ResultSet resultSet = ptsm.executeQuery();

        Employee dto = null;
        if (resultSet.next()){
            String employee_id = resultSet.getString(1);
            String name = resultSet.getString(2);
            String address = resultSet.getString(3);
            String contact = resultSet.getString(4);

            dto = new Employee(employee_id,name,address,contact);
        }
        return dto;

    }

    public boolean delete(String id) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "DELETE FROM employee WHERE employee_id = ?";
        PreparedStatement ptsm = connection.prepareStatement(sql);
        ptsm.setString(1,id);
        return ptsm.executeUpdate() > 0;
    }

    public Employee searchId(String searchInput) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "SELECT * FROM employee WHERE employee_id = ?";
        PreparedStatement ptsm = connection.prepareStatement(sql);
        ptsm.setString(1,searchInput);
        ResultSet resultSet = ptsm.executeQuery();

        Employee dto = null;
        if (resultSet.next()){
            String employee_id = resultSet.getString(1);
            String name = resultSet.getString(2);
            String address = resultSet.getString(3);
            String contact = resultSet.getString(4);

            dto = new Employee(employee_id,name,address,contact);
        }
        return dto;
    }
}

