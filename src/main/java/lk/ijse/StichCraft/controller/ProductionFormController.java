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
    private TableView<ProductionTm> tblProduction;

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
        try {
            List<ProductionDto> dtoList = model.getAllProduction();
            for (ProductionDto dto : dtoList){
                oblist.add(
                        new ProductionTm(
                                dto.getProduction_id(),
                                dto.getProduction_type(),
                                dto.getStartDate(),
                                dto.getEndDate(),
                                dto.getDescription()
                        )
                );
            }
            tblProduction.setItems(oblist);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String id = lblProductionId.getText();
        String proType = txtProType.getText();
        LocalDate startDate = txtStartDatePicker.getValue();
        LocalDate endDate = txtEndDatePicker.getValue();
        String descripion = txtAreaDescription.getText();

        var dto = new ProductionDto(id,proType,startDate,endDate,descripion);
        try {
            boolean isSaved = ProductionModel.save(dto);
            if (isSaved){
                new Alert(Alert.AlertType.CONFIRMATION,"Production Is Save").show();
                clearFields();
                generateNextProduction();
                loadAllProduction();
            }else {
                new Alert(Alert.AlertType.ERROR,"Production Is Not Save").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }
    @FXML
    void btnDeleteOnAction(ActionEvent event) {

    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {

    }

    @FXML
    void txtProTypeOnAction(ActionEvent event) {

    }

    @FXML
    void txtProductionSearchOnAction(ActionEvent event) {

    }

}
