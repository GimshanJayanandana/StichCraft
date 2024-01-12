package lk.ijse.StichCraft.BO.Custom.Impl;

import lk.ijse.StichCraft.BO.Custom.EmployeeBO;
import lk.ijse.StichCraft.DAO.DAOFactory;
import lk.ijse.StichCraft.DAO.custom.EmployeeDAO;
import lk.ijse.StichCraft.DTO.EmployeeDto;
import lk.ijse.StichCraft.Entity.Employee;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeBOImpl implements EmployeeBO {

    EmployeeDAO employeeDAO = (EmployeeDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.EMPLOYEE);

    public String generateNextEmployeeId() throws SQLException {
        return employeeDAO.generateNextId();
    }
    public boolean saveEmployee(EmployeeDto dto) throws SQLException {
        return employeeDAO.save(new Employee(dto.getEmployee_id(),dto.getName(),dto.getAddress(),dto.getContact()));
    }
    public List<EmployeeDto> getAllEmployee() throws SQLException {
        ArrayList<EmployeeDto> employeeDto = new ArrayList<>();
        List<Employee> employee = employeeDAO.getAll();

        for (Employee employeeDto1 : employee) {
            employeeDto.add(new EmployeeDto(employeeDto1.getEmployee_id(), employeeDto1.getName(), employeeDto1.getAddress(), employeeDto1.getContact()));
        }
        return employeeDto;
    }
    public boolean updateEmployee(EmployeeDto dto) throws SQLException {
        return employeeDAO.update(new Employee(dto.getEmployee_id(),dto.getName(),dto.getAddress(),dto.getContact()));
    }

    public EmployeeDto searchEmployeeByPhoneNumber(String phoneNumber) throws SQLException {
        Employee employeeDto = employeeDAO.search(phoneNumber);
        if (employeeDto != null){
            return new EmployeeDto(employeeDto.getEmployee_id(),employeeDto.getName(),employeeDto.getAddress(),employeeDto.getContact());
        }else {
            return null;
        }
    }
    public boolean deleteEmployee(String id) throws SQLException {
        return employeeDAO.delete(id);
    }
    public EmployeeDto searchId(String searchInput) throws SQLException {
        Employee employeeDto = employeeDAO.searchId(searchInput);
        if (employeeDto != null) {
            return new EmployeeDto(employeeDto.getEmployee_id(), employeeDto.getName(), employeeDto.getAddress(), employeeDto.getContact());
        } else {
            return null;
        }
    }
}
