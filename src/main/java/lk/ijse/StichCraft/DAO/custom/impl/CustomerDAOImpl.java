package lk.ijse.StichCraft.DAO.custom.impl;

import lk.ijse.StichCraft.DAO.SQLUtil;
import lk.ijse.StichCraft.DAO.custom.CustomerDAO;
import lk.ijse.StichCraft.DBConnection.DBConnection;
import lk.ijse.StichCraft.DTO.CustomerDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAOImpl implements CustomerDAO {

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
    public String generateNextId() throws SQLException {
        ResultSet resultSet = SQLUtil.execute("SELECT customer_id FROM customer ORDER BY customer_id DESC LIMIT 1");
        if (resultSet.next()) {
            return splitCustomerID(resultSet.getString(1));
        }
        return splitCustomerID(null);
    }

    public boolean save(CustomerDto dto) throws SQLException{
        return SQLUtil.execute("INSERT INTO customer VALUES (?,?,?,?)",dto.getCustomer_id(),dto.getName(),
                dto.getAddress(),dto.getContact());
    }

    public List<CustomerDto> getAll() throws SQLException {
        ResultSet resultSet = SQLUtil.execute("SELECT * FROM customer");

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

    public boolean update(CustomerDto dto) throws SQLException {
        return SQLUtil.execute("UPDATE customer SET name = ?, address = ?,contact = ? WHERE customer_id = ?",
                dto.getName(),dto.getAddress(),dto.getContact(),dto.getCustomer_id());
    }
    public boolean delete(String id) throws SQLException {
        return SQLUtil.execute("DELETE FROM customer WHERE customer_id = ?",id);
    }
    public CustomerDto search(String phoneNumber) throws SQLException {
        ResultSet resultSet = SQLUtil.execute("SELECT * FROM customer WHERE contact = ?",phoneNumber);

        CustomerDto dto = null;
        if (resultSet.next()) {
            String customer_id = resultSet.getString(1);
            String name = resultSet.getString(2);
            String address = resultSet.getString(3);
            String contact = resultSet.getString(4);

            dto = new CustomerDto(customer_id, name, address, contact);
        }
        return dto;
    }

    public  CustomerDto searchId(String searchId) throws SQLException {
        ResultSet resultSet = SQLUtil.execute("SELECT * FROM customer WHERE customer_id = ?",searchId);

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
