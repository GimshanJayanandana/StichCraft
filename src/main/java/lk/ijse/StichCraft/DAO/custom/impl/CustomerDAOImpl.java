package lk.ijse.StichCraft.DAO.custom.impl;

import lk.ijse.StichCraft.DAO.SQLUtil;
import lk.ijse.StichCraft.DAO.custom.CustomerDAO;
import lk.ijse.StichCraft.DBConnection.DBConnection;
import lk.ijse.StichCraft.DTO.CustomerDto;
import lk.ijse.StichCraft.Entity.Customer;

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

    public boolean save(Customer entity) throws SQLException{
        return SQLUtil.execute("INSERT INTO customer VALUES (?,?,?,?)",entity.getCustomer_id(),entity.getName(),
                entity.getAddress(),entity.getContact());
    }

    public List<Customer> getAll() throws SQLException {
        ResultSet resultSet = SQLUtil.execute("SELECT * FROM customer");

        ArrayList<Customer> entityList = new ArrayList<>();

        while (resultSet.next()){
            entityList.add(
                    new Customer(
                            resultSet.getString(1),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getString(4)
                    )
            );
        }
        return entityList;
    }

    public boolean update(Customer dto) throws SQLException {
        return SQLUtil.execute("UPDATE customer SET name = ?, address = ?,contact = ? WHERE customer_id = ?",
                dto.getName(),dto.getAddress(),dto.getContact(),dto.getCustomer_id());
    }
    public boolean delete(String id) throws SQLException {
        return SQLUtil.execute("DELETE FROM customer WHERE customer_id = ?",id);
    }
    public Customer search(String phoneNumber) throws SQLException {
        ResultSet resultSet = SQLUtil.execute("SELECT * FROM customer WHERE contact = ?",phoneNumber);

        Customer dto = null;
        if (resultSet.next()) {
            String customer_id = resultSet.getString(1);
            String name = resultSet.getString(2);
            String address = resultSet.getString(3);
            String contact = resultSet.getString(4);

            dto = new Customer(customer_id, name, address, contact);
        }
        return dto;
    }

    public  Customer searchId(String searchId) throws SQLException {
        ResultSet resultSet = SQLUtil.execute("SELECT * FROM customer WHERE customer_id = ?",searchId);

        Customer dto = null;
        if (resultSet.next()){
            String customer_id = resultSet.getString(1);
            String name = resultSet.getString(2);
            String address = resultSet.getString(3);
            String contact = resultSet.getString(4);

            dto = new Customer(customer_id,name,address,contact);
        }
        return dto;

    }
}
