package lk.ijse.StichCraft.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.StichCraft.BO.BOFactory;
import lk.ijse.StichCraft.BO.Custom.EmployeeBO;
import lk.ijse.StichCraft.BO.Custom.SalaryBO;
import lk.ijse.StichCraft.DTO.EmployeeDto;
import lk.ijse.StichCraft.DTO.SalaryDto;
import lk.ijse.StichCraft.DTO.tm.SalaryTm;
import lk.ijse.StichCraft.RegExPatterns.RegExPatterns;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class SalaryFormController {

    @FXML
    private TableColumn<?, ?> colAmount;

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colEmployeeName;

    @FXML
    private TableColumn<?, ?> colSalaryId;


    @FXML
    private TableView<SalaryTm> tblSalary;

    @FXML
    private DatePicker txtDatePicker;

    @FXML
    private Label lblEmployeeName;

    @FXML
    private TextField txtSalaryIdToSearch;

    @FXML
    private TextField txtSalaryAmount;

    @FXML
    private TextField txtSearchEmployee;

    @FXML
    private Label lblEmployeeId;

    @FXML
    private Label lblSalaryId;

    SalaryBO salaryBO = (SalaryBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.SALARY);

    EmployeeBO employeeModel = (EmployeeBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.EMPLOYEE);

    public void initialize() {
        setCellValueFactory();
        generateNextSalary();
        clearFields();
        loadAllSalary();
    }

    private void generateNextSalary() {
        try {
            String previousSalaryId = lblSalaryId.getText();
            String salaryID = salaryBO.generateNextSalaryId();
            lblSalaryId.setText(salaryID);
            clearFields();
            if (btnClearPressed) {
                lblSalaryId.setText(previousSalaryId);
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private boolean btnClearPressed = false;

    private void clearFields() {
        lblEmployeeId.setText("");
        txtSearchEmployee.setText("");
        txtSalaryAmount.setText("");
        txtSalaryIdToSearch.setText("");
        lblEmployeeName.setText("");
        txtDatePicker.setValue(null);
    }

    private void setCellValueFactory() {
        colSalaryId.setCellValueFactory(new PropertyValueFactory<>("salary_id"));
        colEmployeeName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        tblSalary.setId("my-table");
    }

    private void loadAllSalary() {
        ObservableList<SalaryTm> oblist = FXCollections.observableArrayList();
        try {
            List<SalaryDto> dtoList = salaryBO.getAllSalary();
            for (SalaryDto dto : dtoList) {
                oblist.add(
                        new SalaryTm(
                                dto.getSalary_id(),
                                dto.getAmount(),
                                dto.getDate(),
                                dto.getEmployee_id(),
                                dto.getName()
                        )
                );
            }
            tblSalary.setItems(oblist);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();
        generateNextSalary();
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String id = lblSalaryId.getText();
        String amountText = txtSalaryAmount.getText();
        LocalDate date = txtDatePicker.getValue();
        String empID = lblEmployeeId.getText();
        String name = lblEmployeeName.getText();

        boolean isValidAmount = RegExPatterns.validDouble.matcher(amountText).matches();
        if (!isValidAmount){
            new Alert(Alert.AlertType.ERROR,"Can Not Save Salary.Amount Is Empty").showAndWait();
        }else {
            try {
                boolean isDelete = salaryBO.delete(id);
                if (isDelete) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Salary Is Deleted").show();
                    loadAllSalary();
                    clearFields();
                    generateNextSalary();
                } else {
                    new Alert(Alert.AlertType.INFORMATION, "Salary Is Not Delete").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        }
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String id = lblSalaryId.getText();
        String amountText = txtSalaryAmount.getText();
        LocalDate date = txtDatePicker.getValue();
        String empID = lblEmployeeId.getText();
        String name = lblEmployeeName.getText();

        boolean isValidAmount = RegExPatterns.validDouble.matcher(amountText).matches();

        if (!isValidAmount){
            new Alert(Alert.AlertType.ERROR,"Can Not Save Salary.Amount Is Empty").showAndWait();
        }else {
            try {
                double amount = Double.parseDouble(txtSalaryAmount.getText());
                var dto = new SalaryDto(id, amount, date, empID,name);
                try {
                    boolean isSaved = salaryBO.saveSalary(dto);
                    if (isSaved) {
                        new Alert(Alert.AlertType.CONFIRMATION, "Salary Is Saved").show();
                        clearFields();
                        generateNextSalary();
                        loadAllSalary();

                    } else {
                        new Alert(Alert.AlertType.ERROR, "Salary Is Not Saved").show();
                    }
                } catch (SQLException e) {
                    new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
                }
            } catch (NumberFormatException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String id = lblSalaryId.getText();
        String amountText = txtSalaryAmount.getText();
        LocalDate date = txtDatePicker.getValue();
        String empID = lblEmployeeId.getText();
        String name = lblEmployeeName.getText();

        boolean isValidAmount = RegExPatterns.validDouble.matcher(amountText).matches();

        if (!isValidAmount) {
            new Alert(Alert.AlertType.ERROR, "Can Not Update Salary.Amount Is Empty").showAndWait();
        } else {
            try {
                try {
                    double amount = Double.parseDouble(txtSalaryAmount.getText());
                    SalaryDto dto = new SalaryDto(id, amount, date, empID,name);
                    boolean isUpdate = salaryBO.updateSalary(dto);
                    System.out.println(isUpdate);
                    if (isUpdate) {
                        new Alert(Alert.AlertType.CONFIRMATION, "Salary Is Updated").show();
                        clearFields();
                        generateNextSalary();
                        loadAllSalary();
                    } else {
                        new Alert(Alert.AlertType.ERROR, "Salary Is Not Updatetd").show();
                    }
                } catch (SQLException e) {
                    new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
                }
            } catch (NumberFormatException e) {
                new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
            }
        }
    }

    @FXML
    void txtAmountOnAction(ActionEvent event) {

    }

    @FXML
    void txtSalaryIdSearchOnAction(ActionEvent event) {
        String id = txtSalaryIdToSearch.getText();

        try {
            SalaryDto salaryDto;
                salaryDto = salaryBO.searchSalary(id);
            if (salaryDto != null){
                lblSalaryId.setText(salaryDto.getSalary_id());
                txtSalaryAmount.setText(String.valueOf(salaryDto.getAmount()));
                txtDatePicker.setValue(salaryDto.getDate());
                lblEmployeeName.setText(salaryDto.getName());
                lblEmployeeId.setText(salaryDto.getEmployee_id());
            }else {
                lblSalaryId.setText("");
                generateNextSalary();
                new Alert(Alert.AlertType.INFORMATION,"Salary Is Not Found").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();

        }
    }

    @FXML
    void txtSearchEmployeeOnAction(ActionEvent event) {
        String searchInput = txtSearchEmployee.getText();

        try {
            EmployeeDto employeeDto;

            if (searchInput.matches("\\d")){
                employeeDto = employeeModel.searchEmployeeByPhoneNumber(searchInput);
            }else {
                employeeDto = employeeModel.searchId(searchInput);
            }
            if (employeeDto != null){
                lblEmployeeId.setText(employeeDto.getEmployee_id());
                lblEmployeeName.setText(employeeDto.getName());
            }else {
                lblEmployeeId.setText("");
                new Alert(Alert.AlertType.INFORMATION,"Employee Is Not Found").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

}
