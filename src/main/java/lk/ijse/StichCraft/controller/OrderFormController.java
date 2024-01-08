package lk.ijse.StichCraft.controller;

import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.StichCraft.BO.BOFactory;
import lk.ijse.StichCraft.BO.Custom.CustomerBO;
import lk.ijse.StichCraft.BO.Custom.OrderBO;
import lk.ijse.StichCraft.BO.Custom.ProductionBO;
import lk.ijse.StichCraft.DBConnection.DBConnection;
import lk.ijse.StichCraft.DTO.CustomerDto;
import lk.ijse.StichCraft.DTO.OrderDto;
import lk.ijse.StichCraft.DTO.ProductionDto;
import lk.ijse.StichCraft.DTO.tm.OrderTm;
import lk.ijse.StichCraft.DAO.custom.impl.CustomerDAOImpl;
import lk.ijse.StichCraft.DAO.custom.impl.OrderDAOimpl;
import lk.ijse.StichCraft.DAO.custom.impl.ProductionDAOimpl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
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
    private Label lblTotalOr;

    @FXML
    private TextField txtQuantity;

    CustomerBO customerModel = (CustomerBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.CUSTOMER);
    ProductionBO productionBO = (ProductionBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.PRODUCTION);
    OrderBO orderModel = (OrderBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ORDER);
    private ObservableList<OrderTm> obList = FXCollections.observableArrayList();



    public void initialize() throws SQLException {
        generateNextOrder();
        setDate();
        loadAllCustomers();
        loadAllProduction();
        setCellValueFactory();
        countOrders();
    }

    private void countOrders() throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        Statement statement = connection.createStatement();

        String sql = "SELECT count(*) FROM orders";
        ResultSet resultSet = statement.executeQuery(sql);

        resultSet.next();
        int count = resultSet.getInt(1);
        lblTotalOr.setText(String.valueOf(count));
    }

    private void generateNextOrder() {
        try {
            String previousOrderId = lblOrderId.getText();
            String orderID = orderModel.generateNextId();
            lblOrderId.setText(orderID);
            if (orderID != null){
                lblOrderId.setText(orderID);
            }
//            clearFields();
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

    private void setCellValueFactory(){
        colItemCode.setCellValueFactory(new PropertyValueFactory<>("productId"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        tblOrders.setId("my-table");
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
            List<ProductionDto> dtoList = productionBO.getAllProduction();

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
        String itemCode = cmbItemCode.getValue();
        String description = lblDescription.getText();
        int quantity = Integer.parseInt(txtQuantity.getText());
        double unitPrice = Double.parseDouble(lblUnitPrice.getText());
        double total = unitPrice * quantity;

        if (txtQuantity.getText().isEmpty()){
            new Alert(Alert.AlertType.ERROR,"Quantity Is Empty").show();
        }

        if (!obList.isEmpty()){
            for (int i = 0; i < tblOrders.getItems().size(); i++) {
                if (colItemCode.getCellData(i).equals(itemCode)){
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
        var orderTm = new OrderTm(itemCode, description, quantity, unitPrice, total);

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
        generateNextOrder();
    }
    @FXML
    void btnPlaceTheOrderOnAction(ActionEvent event) {
        String order_id = lblOrderId.getText();
        LocalDate order_date = LocalDate.parse(lblOrderDate.getText());
        String customer_id = cmbCustomerId.getValue();

        List<OrderTm> orderTmList = new ArrayList<>();
        for (int i = 0; i < tblOrders.getItems().size(); i++) {
            OrderTm orderTm = obList.get(i);

            orderTmList.add(orderTm);
        }
       var orderDto =  new OrderDto(order_id,order_date,customer_id,orderTmList);
        try {
            boolean isSuccess = orderModel.placeOrder(orderDto);
            if (isSuccess){
                new Alert(Alert.AlertType.CONFIRMATION,"Order Is Success!").show();
                String productId = cmbItemCode.getValue();
                ProductionDto updateProduct = productionBO.searchProduction(productId);
                generateNextOrder();
                countOrders();
                if (updateProduct != null) {
                    lblQtyOnHand.setText(String.valueOf(updateProduct.getQuantityOnHand()));
                }
                obList.clear();
                tblOrders.refresh();
                calculateTotal();
                generateNextOrder();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }
    @FXML
    void cmbCustomerIdOnAction(ActionEvent event) {
        String id = cmbCustomerId.getValue();

        try {
            CustomerDto dto = customerModel.searchCustomer(id);
            lblCustName.setText(dto.getName());
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    @FXML
    void cmbItemCodeOnAction(ActionEvent event) {
        String id = cmbItemCode.getValue();

        try {
            ProductionDto dto = productionBO.searchProduction(id);
            lblDescription.setText(dto.getDescription());
            lblUnitPrice.setText(String.valueOf(dto.getUnitPrice()));
            lblQtyOnHand.setText(String.valueOf(dto.getQuantityOnHand()));
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }
    @FXML
    void txtQuantityOnAction(ActionEvent event) {

    }
}
