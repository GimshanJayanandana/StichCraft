package lk.ijse.StichCraft.BO.Custom.Impl;

import lk.ijse.StichCraft.BO.Custom.CustomerBO;
import lk.ijse.StichCraft.DAO.DAOFactory;
import lk.ijse.StichCraft.DAO.SQLUtil;
import lk.ijse.StichCraft.DAO.custom.CustomerDAO;
import lk.ijse.StichCraft.DTO.CustomerDto;
import lk.ijse.StichCraft.Entity.Customer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerBOImpl implements CustomerBO {

    CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.CUSTOMER);

    public String generateNextCustomerId() throws SQLException {
        return customerDAO.generateNextId();
    }

    public boolean saveCustomer(CustomerDto dto) throws SQLException{
        return customerDAO.save(new Customer(dto.getCustomer_id(),dto.getName(),dto.getAddress(), dto.getContact()));
    }

    public List<CustomerDto> getAllCustomer() throws SQLException {
        ArrayList<CustomerDto> customerDto = new ArrayList<>();
        List<Customer> customer = customerDAO.getAll();

        for (Customer customerDto1 : customer) {
            customerDto.add(new CustomerDto(customerDto1.getCustomer_id(), customerDto1.getName(), customerDto1.getAddress(), customerDto1.getContact()));
        }
        return customerDto;
    }

    public boolean updateCustomer(CustomerDto dto) throws SQLException {
        return customerDAO.update(new Customer(dto.getCustomer_id(),dto.getName(),dto.getAddress(), dto.getContact()));
    }
    public boolean deleteCustomer(String id) throws SQLException {
        return customerDAO.delete(id);
    }
    public CustomerDto searchCustomerByPhoneNumber(String phoneNumber) throws SQLException {
        Customer customerDto = customerDAO.search(phoneNumber);
        if (customerDto != null) {
            return new CustomerDto(customerDto.getCustomer_id(),customerDto.getName(),customerDto.getAddress(),customerDto.getContact());
        }else {
            return null;
        }
    }
    public  CustomerDto searchCustomer(String searchId) throws SQLException {
        Customer customerDto = customerDAO.searchId(searchId);
        if (customerDto != null) {
            return new CustomerDto(customerDto.getCustomer_id(), customerDto.getName(), customerDto.getAddress(), customerDto.getContact());
        } else {
            return null;
        }
    }
}
