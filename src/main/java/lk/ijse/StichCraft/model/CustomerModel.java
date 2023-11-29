package lk.ijse.StichCraft.model;

import lk.ijse.StichCraft.DBConnection.DBConnection;
import lk.ijse.StichCraft.DTO.CustomerDto;
import lk.ijse.StichCraft.DTO.EmployeeDto;
import org.checkerframework.checker.units.qual.C;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerModel {

    private String splitCustomerID(String currentCustomerID){
        if (currentCustomerID != null) {
            String[] split = currentCustomerID.split("00");

            int id = Integer.parseInt(split[1]);
            id++;
            return "C00" + id;
        }else {
            return "C001";
        }
    }
    public String generateNextCustomer() throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "SELECT customer_id FROM customer ORDER BY customer_id DESC LIMIT 1";
        PreparedStatement ptsm = connection.prepareStatement(sql);
        ResultSet resultSet = ptsm.executeQuery();
        if (resultSet.next()) {
            return splitCustomerID(resultSet.getString(1));
        }
        return splitCustomerID(null);
    }

    public boolean save(CustomerDto dto) throws SQLException{
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "INSERT INTO customer VALUES (?,?,?,?)";
        PreparedStatement ptsm = connection.prepareStatement(sql);

        ptsm.setString(1,dto.getCustomer_id());
        ptsm.setString(2,dto.getName());
        ptsm.setString(3,dto.getAddress());
        ptsm.setString(4, String.valueOf(dto.getContact()));

        return ptsm.executeUpdate() > 0;
    }

    public List<CustomerDto> getAllCustomer() throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "SELECT * FROM customer";
        PreparedStatement ptsm = connection.prepareStatement(sql);
        ResultSet resultSet = ptsm.executeQuery();

        ArrayList<CustomerDto> dtoList = new ArrayList<>();

        while (resultSet.next()){
            dtoList.add(
                    new CustomerDto(
                            resultSet.getString(1),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getString(4)
                    )
            );
        }
        return dtoList;
    }

    public boolean updateCustomer(CustomerDto dto) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "UPDATE customer SET name = ?, address = ?,contact = ? WHERE customer_id = ?";
        PreparedStatement ptsm = connection.prepareStatement(sql);

        ptsm.setString(1,dto.getName());
        ptsm.setString(2,dto.getAddress());
        ptsm.setString(3,dto.getContact());
        ptsm.setString(4,dto.getCustomer_id());

        return ptsm.executeUpdate() > 0;

    }

    public boolean deleteCustomer(String id) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "DELETE FROM customer WHERE customer_id = ?";
        PreparedStatement ptsm = connection.prepareStatement(sql);
        ptsm.setString(1,id);
        return ptsm.executeUpdate() > 0;
    }

    public CustomerDto searchCustomerByPhoneNumber(String phoneNumber) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "SELECT * FROM customer WHERE contact = ?";
        PreparedStatement ptsm = connection.prepareStatement(sql);
        ptsm.setString(1, phoneNumber);
        ResultSet resultSet = ptsm.executeQuery();

        CustomerDto dto = null;
        if (resultSet.next()) {
            String customer_id = resultSet.getString(1);
            String name = resultSet.getString(2);
            String address = resultSet.getString(3);
            String contact = resultSet.getString(4);

            dto = new CustomerDto(customer_id, name, address, contact);
        }
        return null;
    }

    public static CustomerDto searchCustomer(String searchId) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "SELECT * FROM customer WHERE customer_id = ?";
        PreparedStatement ptsm = connection.prepareStatement(sql);
        ptsm.setString(1,searchId);
        ResultSet resultSet = ptsm.executeQuery();

        CustomerDto dto = null;
        if (resultSet.next()){
            String customer_id = resultSet.getString(1);
            String name = resultSet.getString(2);
            String address = resultSet.getString(3);
            String contact = resultSet.getString(4);

            dto = new CustomerDto(customer_id,name,address,contact);
        }
        return dto;

    }
}
