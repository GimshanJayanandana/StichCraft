package lk.ijse.StichCraft.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.StichCraft.BO.BOFactory;
import lk.ijse.StichCraft.BO.Custom.EmployeeBO;
import lk.ijse.StichCraft.DBConnection.DBConnection;
import lk.ijse.StichCraft.DTO.EmployeeDto;
import lk.ijse.StichCraft.DTO.tm.EmployeeTm;
import lk.ijse.StichCraft.RegExPatterns.RegExPatterns;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class EmployeeFormController {

    @FXML
    private TableColumn<?, ?> colEmployeeAddress;

    @FXML
    private TableColumn<?, ?> colEmployeeContact;

    @FXML
    private TableColumn<?, ?> colEmployeeId;

    @FXML
    private TableColumn<?, ?> colEmployeeName;

    @FXML
    private Label lblEmployeeId;

    @FXML
    private Label lblTotalEmp;

    @FXML
    private TableView<EmployeeTm> tblEmployee;

    @FXML
    private TextField txtEmployeeAddress;

    @FXML
    private TextField txtEmployeeName;

    @FXML
    private TextField txtEmployeePhoneNumber;

    @FXML
    private TextField txtEmployeeSearch;
    EmployeeBO employeeModel = (EmployeeBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.EMPLOYEE);

    public void initialize() throws SQLException {
        setCellValueFactory();
        generateNextEmployee();
        loadAllEmployee();
        countEmployee();
    }

    private void countEmployee() throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        Statement statement = connection.createStatement();

        String sql = "SELECT count(*) FROM employee";
        ResultSet resultSet = statement.executeQuery(sql);

        resultSet.next();
        int count = resultSet.getInt(1);
        lblTotalEmp.setText(String.valueOf(count));
    }

    private void generateNextEmployee() {
        try {
            String previousEmployeeID = lblEmployeeId.getText();
            String employeeID = employeeModel.generateNextEmployeeId();
            lblEmployeeId.setText(employeeID);
            clearFields();
            if (btnClearPressd) {
                lblEmployeeId.setText(previousEmployeeID);
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }
    private boolean btnClearPressd = false;

    @FXML
    void btnEmpClearOnAction(ActionEvent event) {
        clearFields();
        generateNextEmployee();
    }

    private void clearFields() {
        txtEmployeeName.setText("");
        txtEmployeeAddress.setText("");
        txtEmployeePhoneNumber.setText("");
        txtEmployeeSearch.setText("");
    }

    @FXML
    void btnEmpSaveOnAction(ActionEvent event) {
        String id = lblEmployeeId.getText();
        String name = txtEmployeeName.getText();
        String address = txtEmployeeAddress.getText();
        String contact = txtEmployeePhoneNumber.getText();

        boolean isValidName = RegExPatterns.validName.matcher(name).matches();
        boolean isValidAddress = RegExPatterns.validAddress.matcher(address).matches();
        boolean isValidPhoneNumber = RegExPatterns.validPhoneNumber.matcher(contact).matches();

        if (!isValidName){
            new Alert(Alert.AlertType.ERROR,"Can Not Save Employee.Name IS Empty").showAndWait();
            return;
        }
        if (!isValidAddress){
            new Alert(Alert.AlertType.ERROR,"Can Not Save Employee.Address Is Empty").showAndWait();
            return;
        }
        if (!isValidPhoneNumber){
            new Alert(Alert.AlertType.ERROR,"Can Not Save Employee.Phone Number Is Empty").showAndWait();
        }else {
            var dto = new EmployeeDto(id, name, address, contact);
            try {
                boolean isSaved = employeeModel.saveEmployee(dto);
                if (isSaved) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Employee is Saved").show();
                    clearFields();
                    generateNextEmployee();
                    loadAllEmployee();
                    countEmployee();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Employee is Not Save").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        }
    }

    private void setCellValueFactory() {
        colEmployeeId.setCellValueFactory(new PropertyValueFactory<>("employee_id"));
        colEmployeeName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colEmployeeAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colEmployeeContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        tblEmployee.setId("my-table");

    }

    private void loadAllEmployee() {
        ObservableList<EmployeeTm> oblist = FXCollections.observableArrayList();
        try {
            List<EmployeeDto> dtoList = employeeModel.getAllEmployee();
            for (EmployeeDto dto : dtoList) {
                oblist.add(
                        new EmployeeTm(
                                dto.getEmployee_id(),
                                dto.getName(),
                                dto.getAddress(),
                                dto.getContact()
                        )
                );
            }
            tblEmployee.setItems(oblist);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnEmpUpdateOnAction(ActionEvent event) {
        String id = lblEmployeeId.getText();
        String name = txtEmployeeName.getText();
        String address = txtEmployeeAddress.getText();
        String contact = txtEmployeePhoneNumber.getText();

        boolean isValidName = RegExPatterns.validName.matcher(name).matches();
        boolean isValidAddress = RegExPatterns.validAddress.matcher(address).matches();
        boolean isValidPhoneNumber = RegExPatterns.validPhoneNumber.matcher(contact).matches();

        if (!isValidName){
            new Alert(Alert.AlertType.ERROR,"Can Not Update Employee.Name Is Empty").showAndWait();
            return;
        }
        if (!isValidAddress){
            new Alert(Alert.AlertType.ERROR,"Can Not Update Employee.Address Is Empty").showAndWait();
            return;
        }
        if (!isValidPhoneNumber){
            new Alert(Alert.AlertType.ERROR,"Can Not Update Employee.Phone Number Is Empty").showAndWait();
        }else {
            try {
                EmployeeDto dto = new EmployeeDto(id, name, address, contact);
                try {
                    boolean isUpdated = employeeModel.updateEmployee(dto);
                    if (isUpdated) {
                        new Alert(Alert.AlertType.CONFIRMATION, "Employee is updated").show();
                        loadAllEmployee();
                        clearFields();
                        generateNextEmployee();
                    }
                } catch (SQLException e) {
                    new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
                }
            } catch (NumberFormatException e) {
                new Alert(Alert.AlertType.ERROR, "Invalid Phone Number Format").showAndWait();
            }
        }
    }

    @FXML
    void btnEmpDeleteOnAction(ActionEvent event) {
        String id = lblEmployeeId.getText();
        String name = txtEmployeeName.getText();
        String address = txtEmployeeAddress.getText();
        String contact = txtEmployeePhoneNumber.getText();

        boolean isValidName = RegExPatterns.validName.matcher(name).matches();
        boolean isValidAddress = RegExPatterns.validAddress.matcher(address).matches();
        boolean isValidPhoneNumber = RegExPatterns.validAddress.matcher(contact).matches();

        if (!isValidName) {
            new Alert(Alert.AlertType.ERROR, "Can Not Delete Employee,Name Is Empty").showAndWait();
            return;
        }
        if (!isValidAddress){
            new Alert(Alert.AlertType.ERROR,"Can Not Delete Employee.Address Is Empty").showAndWait();
            return;
        }
        if (!isValidPhoneNumber){
            new Alert(Alert.AlertType.ERROR,"Can Not Delete Employee.Phone Number Is Empty").showAndWait();
        }else {
            try {
                boolean isDelete = employeeModel.deleteEmployee(id);
                if (isDelete){
                    new Alert(Alert.AlertType.CONFIRMATION,"Employee Is Deleted").show();
                    loadAllEmployee();
                    clearFields();
                    generateNextEmployee();
                }else {
                    new Alert(Alert.AlertType.INFORMATION,"Employee Is Not Delete").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
            }
        }
    }
    @FXML
    void txtEmployeeSearchOnAction(ActionEvent event) {
        String searchInput = txtEmployeeSearch.getText();

        try {
            EmployeeDto employeeDto;

            if (searchInput.matches("\\d")){
                employeeDto = employeeModel.searchEmployeeByPhoneNumber(searchInput);
            }else {
                employeeDto = employeeModel.searchId(searchInput);
            }
            if (employeeDto != null){
                lblEmployeeId.setText(employeeDto.getEmployee_id());
                txtEmployeeName.setText(employeeDto.getName());
                txtEmployeeAddress.setText(employeeDto.getAddress());
                txtEmployeePhoneNumber.setText(employeeDto.getContact());
            }else {
                lblEmployeeId.setText("");
                generateNextEmployee();
                new Alert(Alert.AlertType.INFORMATION,"Employee Is Not Found").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }
    @FXML
    void txtGoToEmployeePhoneNumberOnAction(ActionEvent event) {
        txtEmployeePhoneNumber.requestFocus();

    }

    @FXML

    void txtGotoEmployeeAddressOnAction(ActionEvent event) {
        txtEmployeeAddress.requestFocus();


    }
    @FXML
    void btnReportsOnAction(ActionEvent event) throws SQLException, JRException {
        InputStream resourceAsStream = getClass().getResourceAsStream("/reports/Employee_A4.jrxml");
        JasperDesign load = JRXmlLoader.load(resourceAsStream);
        JasperReport jasperReport = JasperCompileManager.compileReport(load);
        JasperPrint jasperPrint = JasperFillManager.fillReport(
                jasperReport,
                null,
                DBConnection.getInstance().getConnection()
        );
        JasperViewer.viewReport(jasperPrint,false);
    }
}

