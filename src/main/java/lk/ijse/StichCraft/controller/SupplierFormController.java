package lk.ijse.StichCraft.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import lk.ijse.StichCraft.DTO.SupplierDto;
import lk.ijse.StichCraft.model.EmployeeModel;
import lk.ijse.StichCraft.model.SuppllierModel;
import org.checkerframework.checker.units.qual.A;

import java.sql.SQLException;

public class SupplierFormController {

    @FXML
    private Label txtSupplierId;

    @FXML
    private TextField txtSupplierName;

    @FXML
    private TextField txtSupplierPhoneNumber;

    private SuppllierModel suppllierModel = new SuppllierModel();
    
    public void initialize(){
        generateNextSupplier();
    }

    private void generateNextSupplier() {
        try {
            String supplierID = suppllierModel.generateNextSupplier();
            txtSupplierId.setText(supplierID);
        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }
    private void cleareFiels(){
        txtSupplierId.setText("");
        txtSupplierName.setText("");
        txtSupplierPhoneNumber.setText("");
    }

    @FXML
    void BtnSupSaveOnAction(ActionEvent event) {
        String id = txtSupplierId.getText();
        String name = txtSupplierName.getText();
        String contact = txtSupplierPhoneNumber.getText();

        var dto = new SupplierDto(id, name, contact);
        try {
            boolean isSaved = SuppllierModel.save(dto);
            if (isSaved){
                new Alert(Alert.AlertType.CONFIRMATION,"Supplier Is Saved").show();
                cleareFiels();
                generateNextSupplier();
            }else {
                new Alert(Alert.AlertType.ERROR, "Supplier Is Not Saved").show();
            }
        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    @FXML
    void BtnSupUpdateOnAction(ActionEvent event) {

    }

    @FXML
    void btnSupDeleteOnAction(ActionEvent event) {

    }
    @FXML
    void txtGoToSupplierPhoneNumberOnAction(ActionEvent event) {
        txtSupplierPhoneNumber.requestFocus();

    }

}
