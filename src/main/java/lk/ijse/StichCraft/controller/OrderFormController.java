package lk.ijse.StichCraft.controller;

import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.StichCraft.DTO.CustomerDto;
import lk.ijse.StichCraft.DTO.ProductionDto;
import lk.ijse.StichCraft.DTO.tm.OrderTm;
import lk.ijse.StichCraft.model.CustomerModel;
import lk.ijse.StichCraft.model.OrderModel;
import lk.ijse.StichCraft.model.ProductionModel;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class OrderFormController {

    @FXML
    private JFXComboBox<String> cmbCustomerId;

    @FXML
    private JFXComboBox<String> cmbItemCode;

    @FXML
    private TableColumn<?, ?> colDescription;

    @FXML
    private TableColumn<?, ?> colItemCode;

    @FXML
    private TableColumn<?, ?> colOrderId;

    @FXML
    private TableColumn<?, ?> colQuantity;

    @FXML
    private TableColumn<?, ?> colUnitPrice;

    @FXML
    private TableColumn<?, ?> colTotal;


    @FXML
    private Label lblCustName;

    @FXML
    private Label lblDescription;

    @FXML
    private Label lblOrderDate;

    @FXML
    private Label lblOrderId;

    @FXML
    private Label lblQtyOnHand;

    @FXML
    private Label lblUnitPrice;

    @FXML
    private TableView<OrderTm> tblOrders;

    @FXML
    private Label lblNetTotal;


    @FXML
    private TextField txtQuantity;

    private CustomerModel customerModel = new CustomerModel();
    private ProductionModel productionModel = new ProductionModel();
    private OrderModel orderModel = new OrderModel();


    private ObservableList<OrderTm> obList = FXCollections.observableArrayList();



    public void initialize() {
        generateNextOrder();
        setDate();
        loadAllCustomers();
        loadAllProduction();
        setCellValueFactory();
    }

    private void generateNextOrder() {
        try {
            String previousOrderId = lblOrderId.getText();
            String orderID = orderModel.generateNextOrder();
            lblOrderId.setText(orderID);
            clearFields();
            if (btnClearPressed){
                lblOrderId.setText(previousOrderId);
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    private void setDate(){
        lblOrderDate.setText(String.valueOf(LocalDate.now()));
    }
    private boolean btnClearPressed = false;

    private void clearFields(){
        cmbCustomerId.setValue(null);
        lblCustName.setText("");
        cmbItemCode.setValue(null);
        lblDescription.setText("");
        lblUnitPrice.setText("");
        lblQtyOnHand.setText("");
        txtQuantity.setText("");
    }

    private void loadAllCustomers(){
        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            List<CustomerDto> dtoList = customerModel.getAllCustomer();

            for (CustomerDto dto : dtoList){
                obList.add(dto.getCustomer_id());
            }
            cmbCustomerId.setItems(obList);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }
    private void loadAllProduction(){
        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            List<ProductionDto> dtoList = productionModel.getAllProduction();

            for (ProductionDto dto : dtoList){
                obList.add(dto.getProduction_id());
            }
            cmbItemCode.setItems(obList);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();

        }

    }
    @FXML
    void btnAddToCartOnAction(ActionEvent event) {
        String order_id = lblOrderId.getText();
        String itemCode = cmbItemCode.getValue();
        String description = lblDescription.getText();
        int quantity = Integer.parseInt(txtQuantity.getText());
        double unitPrice = Double.parseDouble(lblUnitPrice.getText());
        double total = unitPrice * quantity;

        if (!obList.isEmpty()){
            for (int i = 0; i < tblOrders.getItems().size(); i++) {
                if (colOrderId.getCellData(i).equals(order_id)){
                    int col_qty = (int) colQuantity.getCellData(i);
                    quantity += col_qty;
                    total = unitPrice * quantity;

                    obList.get(i).setQuantity(quantity);
                    obList.get(i).setTotal(total);

                    calculateTotal();
                    tblOrders.refresh();
                    return;

                }
            }
        }
        var orderTm = new OrderTm(order_id, itemCode, description, quantity, unitPrice, total);

        obList.add(orderTm);

        tblOrders.setItems(obList);
        calculateTotal();
    }

    private void calculateTotal(){
        double total = 0;
        for (int i = 0; i <tblOrders.getItems().size(); i++) {
            total += (double) colTotal.getCellData(i);
        }
        lblNetTotal.setText(String.valueOf(total));
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();

    }

    @FXML
    void btnPlaceTheOrderOnAction(ActionEvent event) {

    }

    @FXML
    void cmbCustomerIdOnAction(ActionEvent event) {
        String id = cmbCustomerId.getValue();

        try {
            CustomerDto dto = CustomerModel.searchCustomer(id);
            lblCustName.setText(dto.getName());
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }
    @FXML
    void cmbItemCodeOnAction(ActionEvent event) {
        String id = cmbItemCode.getValue();

        try {
            ProductionDto dto = productionModel.searchProduction(id);
            lblDescription.setText(dto.getDescription());
            lblUnitPrice.setText(String.valueOf(dto.getUnitPrice()));
            lblQtyOnHand.setText(String.valueOf(dto.getQuantityOnHand()));
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    private void setCellValueFactory(){
        colOrderId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        colItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));

    }
    @FXML
    void txtQuantityOnAction(ActionEvent event) {

    }
}
