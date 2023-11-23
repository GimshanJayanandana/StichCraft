package lk.ijse.StichCraft.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.StichCraft.DTO.ProductionDto;
import lk.ijse.StichCraft.DTO.tm.ProductionTm;
import lk.ijse.StichCraft.model.ProductionModel;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.AbstractList;
import java.util.List;

public class ProductionFormController {

    @FXML
    private TableColumn<?, ?> colDescription;

    @FXML
    private TableColumn<?, ?> colEndDate;

    @FXML
    private TableColumn<?, ?> colProductionId;

    @FXML
    private TableColumn<?, ?> colStartDate;

    @FXML
    private TableColumn<?, ?> colType;

    @FXML
    private Label lblProductionId;

    @FXML
    private TableView<?> tblProduction;

    @FXML
    private TextArea txtAreaDescription;

    @FXML
    private DatePicker txtEndDatePicker;

    @FXML
    private TextField txtSearchProduction;

    @FXML
    private DatePicker txtStartDatePicker;

    @FXML
    private TextField txtProType;

    private ProductionModel productionModel = new ProductionModel();
    public void initialize(){
        generateNextProduction();
        setCellValueFactory();
        loadAllProduction();
    }

    public void generateNextProduction(){
        try {
            String previousProductionId = lblProductionId.getText();
            String productionID = productionModel.generateNextProduction();
            lblProductionId.setText(productionID);
            clearFields();
            if (btnClearPressed){
                lblProductionId.setText(previousProductionId);
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    private boolean btnClearPressed = false;

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();
        generateNextProduction();
    }

    private void clearFields(){
        txtAreaDescription.setText("");
        txtProType.setText("");
        txtStartDatePicker.setValue(null);
        txtEndDatePicker.setValue(null);
        txtSearchProduction.setText("");
    }

    private void setCellValueFactory(){
        colProductionId.setCellValueFactory(new PropertyValueFactory<>("production_id"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colType.setCellValueFactory(new PropertyValueFactory<>("production_type"));
        colStartDate.setCellValueFactory(new PropertyValueFactory<>("StartDate"));
        colEndDate.setCellValueFactory(new PropertyValueFactory<>("EndDate"));
    }

    private void loadAllProduction(){
        var model = new ProductionModel();

        ObservableList<ProductionTm> oblist = FXCollections.observableArrayList();

    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {

    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {

    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {

    }

    @FXML
    void txtProTypeOnAction(ActionEvent event) {

    }

    @FXML
    void txtSearchProductionSearchOnAction(ActionEvent event) {

    }

}
