package lk.ijse.StichCraft.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.StichCraft.DBConnection.DBConnection;
import lk.ijse.StichCraft.DTO.SupplierDto;
import lk.ijse.StichCraft.DTO.tm.SupplierTm;
import lk.ijse.StichCraft.RegExPatterns.RegExPatterns;
import lk.ijse.StichCraft.model.SuppllierModel;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class SupplierFormController {

//    @FXML
//    private TableColumn<?, ?> colSupplierAddress;

    @FXML
    private TableColumn<?, ?> colSupplierContact;

    @FXML
    private TableColumn<?, ?> colSupplierId;

    @FXML
    private TableColumn<?, ?> colSupplierName;

    @FXML
    private TableView<SupplierTm> tblSupplier;

    @FXML
    private TextField txtSupplierSearch;

    @FXML
    private Label lblSupplierId;

    @FXML
    private Label lblTotalSup;

    @FXML
    private TextField txtSupplierName;

    @FXML
    private TextField txtSupplierPhoneNumber;

    private SuppllierModel suppllierModel = new SuppllierModel();
    
    public void initialize() throws SQLException {

        generateNextSupplier();
        setCellValueFactory();
        loadAllSupplier();
        countSuppliers();
    }

    private void countSuppliers() throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        Statement statement = connection.createStatement();

        String sql = "SELECT count(*) FROM supplier";
        ResultSet resultSet = statement.executeQuery(sql);

        resultSet.next();
        int count = resultSet.getInt(1);
        lblTotalSup.setText(String.valueOf(count));
    }

    private void generateNextSupplier() {
        try {
            String supplierID = suppllierModel.generateNextSupplier();
            lblSupplierId.setText(supplierID);
        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    @FXML
    void btnSupClearOnAction(ActionEvent event) {
        cleareFiels();
        generateNextSupplier();

    }

    private void cleareFiels(){
        lblSupplierId.setText("");
        txtSupplierName.setText("");
        txtSupplierPhoneNumber.setText("");
    }

    private void setCellValueFactory(){
        colSupplierId.setCellValueFactory(new PropertyValueFactory<>("supplier_id"));
        colSupplierName.setCellValueFactory(new PropertyValueFactory<>("name"));
       // colSupplierAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colSupplierContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        tblSupplier.setId("my-table");
    }

    private void loadAllSupplier(){
        var model= new SuppllierModel();

        ObservableList<SupplierTm> oblist = FXCollections.observableArrayList();
        try {
            List<SupplierDto> dtoList = model.getAllsupplier();
            for (SupplierDto dto : dtoList){
                oblist.add(
                        new SupplierTm(
                                dto.getSupplier_id(),
                                dto.getName(),
                                dto.getContact()
                        )
                );
            }
            tblSupplier.setItems(oblist);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();


        }
    }
    @FXML
    void BtnSupSaveOnAction(ActionEvent event) {
        String id = lblSupplierId.getText();
        String name = txtSupplierName.getText();
        String contact = txtSupplierPhoneNumber.getText();

        boolean isValidName = RegExPatterns.validName.matcher(name).matches();
        boolean isValidPhoneNumber = RegExPatterns.validPhoneNumber.matcher(contact).matches();

        if (!isValidName) {
            new Alert(Alert.AlertType.ERROR, "Can Not Save Supplier.Name Is Empty").showAndWait();
            return;
        }
        if (!isValidPhoneNumber) {
            new Alert(Alert.AlertType.ERROR, "Can Not Save Supplier.Phone Number Is Empty").show();
        } else {
            var dto = new SupplierDto(id, name, contact);
            try {
                boolean isSaved = SuppllierModel.save(dto);
                if (isSaved){
                    new Alert(Alert.AlertType.CONFIRMATION,"Supplier Is Saved").show();
                    cleareFiels();
                    generateNextSupplier();
                    loadAllSupplier();
                }else {
                    new Alert(Alert.AlertType.ERROR, "Supplier Is Not Saved").show();
                }
            }catch (SQLException e){
                new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
            }
        }
    }
    @FXML
    void txtSupplierSearchOnAction(ActionEvent event) {
        String searchInput = txtSupplierSearch.getText();

        try {
            SupplierDto supplierDto;

            if (searchInput.matches("\\d")){
               supplierDto = suppllierModel.searchSupplierByPhoneNumber(searchInput);
            }else {
                supplierDto = suppllierModel.searchSupplier(searchInput);
            }
            if (supplierDto != null) {
                lblSupplierId.setText(supplierDto.getSupplier_id());
                txtSupplierName.setText(supplierDto.getName());
                txtSupplierPhoneNumber.setText(supplierDto.getContact());
            }else {
                lblSupplierId.setText("");
                generateNextSupplier();
                new Alert(Alert.AlertType.INFORMATION,"Supplier Is Not Found").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }
    @FXML
    void BtnSupUpdateOnAction(ActionEvent event) {
        String id = lblSupplierId.getText();
        String name = txtSupplierName.getText();
        String contact = txtSupplierPhoneNumber.getText();


        boolean isValidName = RegExPatterns.validName.matcher(name).matches();
        boolean isValidPhoneNumber = RegExPatterns.validPhoneNumber.matcher(contact).matches();

        if (!isValidName) {
            new Alert(Alert.AlertType.ERROR, "Can Not Update Supplier.Name Is Empty").showAndWait();
            return;
        }
        if (!isValidPhoneNumber) {
            new Alert(Alert.AlertType.ERROR, "Can Not Update Supplier.Phone Number Is Empty").show();
        } else {
            SupplierDto dto = new SupplierDto(id, name, contact);
            try {
                try {
                    boolean isUpdated = suppllierModel.updateSupplier(dto);
                    if (isUpdated) {
                        new Alert(Alert.AlertType.CONFIRMATION, "Supplier Is Updated").show();
                        loadAllSupplier();
                        cleareFiels();
                        generateNextSupplier();
                    } else {
                        new Alert(Alert.AlertType.ERROR, "Customer Is Note Upadated").show();
                    }
                } catch (SQLException e) {
                    new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
                }
            } catch (NumberFormatException e) {
                new Alert(Alert.AlertType.ERROR, "Invalid Phone Number").show();
            }
        }
    }

    @FXML
    void btnSupDeleteOnAction(ActionEvent event) {
        String id = lblSupplierId.getText();
        String name = txtSupplierName.getText();
        String contact = txtSupplierPhoneNumber.getText();

        boolean isValidName = RegExPatterns.validName.matcher(name).matches();
        boolean isValidPhoneNumber = RegExPatterns.validPhoneNumber.matcher(contact).matches();

        if (!isValidName) {
            new Alert(Alert.AlertType.ERROR, "Can Not Delete Supplier.Name Is Empty").showAndWait();
            return;
        }
        if (!isValidPhoneNumber) {
            new Alert(Alert.AlertType.ERROR, "Can Not Delete Supplier.Phone Number Is Empty").show();
        } else {
            try {
                boolean isDelete = suppllierModel.deleteSupplier(id);
                if (isDelete){
                    new Alert(Alert.AlertType.CONFIRMATION,"Supplier Is Deleted").show();
                    loadAllSupplier();
                    cleareFiels();
                    generateNextSupplier();
                }else {
                    new Alert(Alert.AlertType.INFORMATION,"Supplier Is Not Deleted").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
            }
        }
    }
    @FXML
    void txtGoToSupplierPhoneNumberOnAction(ActionEvent event) {
        txtSupplierPhoneNumber.requestFocus();

    }

}
