package lk.ijse.StichCraft.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.StichCraft.DTO.EmployeeDto;
import lk.ijse.StichCraft.DTO.tm.EmployeeTm;
import lk.ijse.StichCraft.model.EmployeeModel;
import org.checkerframework.checker.units.qual.A;

import java.sql.SQLException;
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
    private TableView<EmployeeTm> tblEmployee;

    @FXML
    private TextField txtEmployeeAddress;

    @FXML
    private TextField txtEmployeeName;

    @FXML
    private TextField txtEmployeePhoneNumber;

    @FXML
    private TextField txtEmployeeSearch;
    private EmployeeModel employeeModel = new EmployeeModel();

    public void initialize() {
        setCellValueFactory();
        generateNextEmployee();
        loadAllEmployee();
    }

    private void generateNextEmployee() {
        try {
            String previousEmployeeID = lblEmployeeId.getText();
            String employeeID = employeeModel.generateNextEmployee();
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

    private void setCellValueFactory() {
        colEmployeeId.setCellValueFactory(new PropertyValueFactory<>("employee_id"));
        colEmployeeName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colEmployeeAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colEmployeeContact.setCellValueFactory(new PropertyValueFactory<>("contact"));

    }

    private void loadAllEmployee() {
        var model = new EmployeeModel();

        ObservableList<EmployeeTm> oblist = FXCollections.observableArrayList();
        try {
            List<EmployeeDto> dtoList = model.getAllEmployee();
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
    void btnEmpSaveOnAction(ActionEvent event) {
        String id = lblEmployeeId.getText();
        String name = txtEmployeeName.getText();
        String address = txtEmployeeAddress.getText();
        String contact = txtEmployeePhoneNumber.getText();

        var dto = new EmployeeDto(id, name, address, contact);
        try {
            boolean isSaved = EmployeeModel.save(dto);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Employee is Saved").show();
                clearFields();
                generateNextEmployee();
                loadAllEmployee();
            } else {
                new Alert(Alert.AlertType.ERROR, "Employee is Not Save").show();
            }
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

    @FXML
    void btnEmpDeleteOnAction(ActionEvent event) {
        String id = lblEmployeeId.getText();
        String name = txtEmployeeName.getText();
        String address = txtEmployeeAddress.getText();
        String contact = txtEmployeePhoneNumber.getText();

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
    @FXML
    void txtEmployeeSearchOnAction(ActionEvent event) {
        String searchInput = txtEmployeeSearch.getText();

        try {
            EmployeeDto employeeDto;

            if (searchInput.matches("\\d")){
                employeeDto = employeeModel.searchEmployeeByPhoneNumber(searchInput);
            }else {
                employeeDto = employeeModel.searchEmployee(searchInput);
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
}
