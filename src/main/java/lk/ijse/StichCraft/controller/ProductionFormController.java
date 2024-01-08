package lk.ijse.StichCraft.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.StichCraft.BO.BOFactory;
import lk.ijse.StichCraft.BO.Custom.ProductionBO;
import lk.ijse.StichCraft.DTO.ProductionDto;
import lk.ijse.StichCraft.DTO.tm.ProductionTm;
import lk.ijse.StichCraft.RegExPatterns.RegExPatterns;
import lk.ijse.StichCraft.DAO.custom.impl.ProductionDAOimpl;

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
    private TableColumn<?, ?> colUnitPrice;

    @FXML
    private TableColumn<?, ?> colQtyOnHand;

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

    @FXML
    private TextField txtQtyOnHand;

    @FXML
    private TextField txtUnitPrice;


    ProductionBO productionModel = (ProductionBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.PRODUCTION);

    public void initialize() {
        generateNextProduction();
        setCellValueFactory();
        loadAllProduction();
    }

    public void generateNextProduction() {
        try {
            String previousProductionId = lblProductionId.getText();
            String productionID = productionModel.generateNextProductionId();
            lblProductionId.setText(productionID);
            clearFields();
            if (btnClearPressed) {
                lblProductionId.setText(previousProductionId);
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private boolean btnClearPressed = false;

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();
        generateNextProduction();
    }

    private void clearFields() {
        txtAreaDescription.setText("");
        txtProType.setText("");
        txtStartDatePicker.setValue(null);
        txtEndDatePicker.setValue(null);
        txtSearchProduction.setText("");
        txtUnitPrice.setText("");
        txtQtyOnHand.setText("");
    }

    private void setCellValueFactory() {
        colProductionId.setCellValueFactory(new PropertyValueFactory<>("production_id"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colType.setCellValueFactory(new PropertyValueFactory<>("production_type"));
        colStartDate.setCellValueFactory(new PropertyValueFactory<>("StartDate"));
        colEndDate.setCellValueFactory(new PropertyValueFactory<>("EndDate"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("UnitPrice"));
        colQtyOnHand.setCellValueFactory(new PropertyValueFactory<>("quantityOnHand"));
        tblProduction.setId("my-table");
    }

    private void loadAllProduction() {
        ObservableList<ProductionTm> oblist = FXCollections.observableArrayList();
        try {
            List<ProductionDto> dtoList = productionModel.getAllProduction();
            for (ProductionDto dto : dtoList) {
                oblist.add(
                        new ProductionTm(
                                dto.getProduction_id(),
                                dto.getProduction_type(),
                                dto.getStartDate(),
                                dto.getEndDate(),
                                dto.getDescription(),
                                dto.getUnitPrice(),
                                dto.getQuantityOnHand()
                        )
                );
            }
            tblProduction.setItems(oblist);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String id = lblProductionId.getText();
        String proType = txtProType.getText();
        LocalDate startDate = txtStartDatePicker.getValue();
        LocalDate endDate = txtEndDatePicker.getValue();
        String descripion = txtAreaDescription.getText();
        String unitPrice = txtUnitPrice.getText();
        String quantityOnHand = txtQtyOnHand.getText();

        boolean isValidDescription = RegExPatterns.validDescription.matcher(descripion).matches();
        boolean isValidProductionType = RegExPatterns.validDescription.matcher(proType).matches();
        boolean isValidUnitPrice = RegExPatterns.validDouble.matcher(unitPrice).matches();
        boolean qtyOnHand = RegExPatterns.validDouble.matcher(quantityOnHand).matches();

        if (!isValidDescription){
            new Alert(Alert.AlertType.ERROR,"Can Not Save Production,Description Is Empty").showAndWait();
            return;
        }if (!isValidProductionType){
            new Alert(Alert.AlertType.ERROR,"Can Not Save Product,Production Type Is Empty").showAndWait();
        }if (!isValidUnitPrice){
            new Alert(Alert.AlertType.ERROR,"Can Not Save Product,Unit Pricce Is Empty").showAndWait();
        }if (!qtyOnHand){
            new Alert(Alert.AlertType.ERROR,"Can Not Save Product,Quantity On Hand Is Empty").showAndWait();
        }else {
            try {
                double price = Double.parseDouble(unitPrice);
                double qty = Double.parseDouble(quantityOnHand);

                var dto = new ProductionDto(id, proType, startDate, endDate, descripion,price, (int) qty);
                try {
                    boolean isSaved = productionModel.saveProduction(dto);
                    if (isSaved) {
                        new Alert(Alert.AlertType.CONFIRMATION, "Production Is Save").show();
                        clearFields();
                        generateNextProduction();
                        loadAllProduction();
                    } else {
                        new Alert(Alert.AlertType.ERROR, "Production Is Not Save").show();
                    }
                } catch (SQLException e) {
                    new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
                }
            }catch (NumberFormatException e){
                new Alert(Alert.AlertType.ERROR,"Invalid quantity or Price Format").showAndWait();
            }
        }

    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String production_id = lblProductionId.getText();
        String production_type = txtProType.getText();
        LocalDate StartDate = txtStartDatePicker.getValue();
        LocalDate EndDate = txtEndDatePicker.getValue();
        String Description = txtAreaDescription.getText();
        String unitPrice = txtUnitPrice.getText();
        String quantityOnHand = txtQtyOnHand.getText();

        boolean isValidDescription = RegExPatterns.validDescription.matcher(Description).matches();
        boolean isValidProductionType = RegExPatterns.validDescription.matcher(production_type).matches();
        boolean isValidUnitPrice = RegExPatterns.validDouble.matcher(unitPrice).matches();
        boolean qtyOnHand = RegExPatterns.validDouble.matcher(quantityOnHand).matches();

        if (!isValidDescription) {
            new Alert(Alert.AlertType.ERROR, "Can Not Save Production,Description Is Empty").showAndWait();
            return;
        }
        if (!isValidProductionType) {
            new Alert(Alert.AlertType.ERROR, "Can Not Save Product,Production Type Is Empty").showAndWait();
        }
        if (!isValidUnitPrice) {
            new Alert(Alert.AlertType.ERROR, "Can Not Save Product,Unit Pricce Is Empty").showAndWait();
        }
        if (!qtyOnHand) {
            new Alert(Alert.AlertType.ERROR, "Can Not Save Product,Quantity On Hand Is Empty").showAndWait();
        } else {
            try {
                double price = Double.parseDouble(unitPrice);
                double qty = Double.parseDouble(quantityOnHand);

                var dto = new ProductionDto(production_id,production_type,StartDate,EndDate,Description,price, (int) qty);
                boolean idDelete = productionModel.deleteProduction(String.valueOf(dto));
                if (idDelete) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Production Is Deleted").show();
                    loadAllProduction();
                    clearFields();
                    generateNextProduction();
                } else {
                    new Alert(Alert.AlertType.INFORMATION, "Production Is Not Deleted").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String production_id = lblProductionId.getText();
        String production_type = txtProType.getText();
        LocalDate StartDate = txtStartDatePicker.getValue();
        LocalDate EndDate = txtEndDatePicker.getValue();
        String Description = txtAreaDescription.getText();
        String unitPrice = txtUnitPrice.getText();
        String quantityOnHand = txtQtyOnHand.getText();

        boolean isValidDescription = RegExPatterns.validDescription.matcher(Description).matches();
        boolean isValidProductionType = RegExPatterns.validDescription.matcher(production_type).matches();
        boolean isValidUnitPrice = RegExPatterns.validDouble.matcher(unitPrice).matches();
        boolean qtyOnHand = RegExPatterns.validDouble.matcher(quantityOnHand).matches();

        if (!isValidDescription) {
            new Alert(Alert.AlertType.ERROR, "Can Not Update Production,Description Is Empty").showAndWait();
            return;
        }
        if (!isValidProductionType) {
            new Alert(Alert.AlertType.ERROR, "Can Not Update Product,Production Type Is Empty").showAndWait();
        }
        if (!isValidUnitPrice) {
            new Alert(Alert.AlertType.ERROR, "Can Not Update Product,Unit Pricce Is Empty").showAndWait();
        }
        if (!qtyOnHand) {
            new Alert(Alert.AlertType.ERROR, "Can Not Update Product,Quantity On Hand Is Empty").showAndWait();
        } else {
            try {
                double price = Double.parseDouble(unitPrice);
                double qty = Double.parseDouble(quantityOnHand);

                var dto = new ProductionDto(production_id,production_type,StartDate,EndDate,Description,price, (int) qty);
                boolean isUpdate = productionModel.updateProduction(dto);
                if (isUpdate) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Production IS Updated").show();
                    loadAllProduction();
                    clearFields();
                    generateNextProduction();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Production IS Not Updated").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        }
    }
    @FXML
    void txtProTypeOnAction(ActionEvent event) {

    }

    @FXML
    void txtProductionSearchOnAction(ActionEvent event) {
        String searchInput = txtSearchProduction.getText();

        try {
            ProductionDto productionDto;

            if (searchInput.matches("\\d")){
                productionDto = productionModel.searchProduction(searchInput);
            }else {
                productionDto = productionModel.searchProduction(searchInput);
            }
            if (productionDto != null){
                lblProductionId.setText(productionDto.getProduction_id());
                txtProType.setText(productionDto.getProduction_type());
                txtUnitPrice.setText(String.valueOf(productionDto.getUnitPrice()));
                txtAreaDescription.setText(productionDto.getDescription());
                txtQtyOnHand.setText(String.valueOf(productionDto.getQuantityOnHand()));
                txtStartDatePicker.setValue(productionDto.getStartDate());
                txtEndDatePicker.setValue(productionDto.getEndDate());
            }else {
                lblProductionId.setText("");
                generateNextProduction();
                new Alert(Alert.AlertType.INFORMATION,"Production Is Not Found").show();
            }

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }
    @FXML
    void txtQtyOnHandOnAction(ActionEvent event) {

    }

    @FXML
    void txtUnitPriceOnAction(ActionEvent event) {

    }
}
