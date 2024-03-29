package lk.ijse.StichCraft.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.StichCraft.BO.BOFactory;
import lk.ijse.StichCraft.BO.Custom.CustomerBO;
import lk.ijse.StichCraft.DBConnection.DBConnection;
import lk.ijse.StichCraft.DTO.CustomerDto;
import lk.ijse.StichCraft.DTO.tm.CustomerTm;
import lk.ijse.StichCraft.RegExPatterns.RegExPatterns;
import lk.ijse.StichCraft.DAO.custom.impl.CustomerDAOImpl;
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

public class CustomersFormController {

    @FXML
    private TextField txtCustomerAddress;

    @FXML
    private TextField txtCustomerContact;

    @FXML
    private Label lblCustomerId;

    @FXML
    private Label lblTotalCust;

    @FXML
    private TextField txtCustomerName;

    @FXML
    private TextField txtCustomerSearch;

    @FXML
    private TableColumn<?, ?> colCustomerAddress;

    @FXML
    private TableColumn<?, ?> colCustomerContact;

    @FXML
    private TableColumn<?, ?> colCustomerId;

    @FXML
    private TableColumn<?, ?> colCustomerName;

    @FXML
    private TableView<CustomerTm> tblCustomer;

    CustomerBO customerModel = (CustomerBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.CUSTOMER);

    public void initialize() throws SQLException {//abstraction
        setCellValueFactory();
        generateNextCustomer();
        loadAllCustomer();
        countCustomer();
    }

    private void countCustomer() throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        Statement statement = connection.createStatement();

        String sql = "SELECT count(*) FROM customer";
        ResultSet resultSet = statement.executeQuery(sql);

        resultSet.next();
        int count = resultSet.getInt(1);
        lblTotalCust.setText(String.valueOf(count));
    }

    private void generateNextCustomer() {
        try {
            String previousCustomerID = lblCustomerId.getText();
            String customerID = customerModel.generateNextCustomerId();
            lblCustomerId.setText(customerID);
            clearFields();
            if (btnClearPressed) {
                lblCustomerId.setText(previousCustomerID);
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private boolean btnClearPressed = false;

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();
        generateNextCustomer();

    }

    private void clearFields() {
        txtCustomerName.setText("");
        txtCustomerAddress.setText("");
        txtCustomerContact.setText("");
        txtCustomerSearch.setText("");
    }

    private void setCellValueFactory() {
        colCustomerId.setCellValueFactory(new PropertyValueFactory<>("customer_id"));
        colCustomerName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colCustomerAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colCustomerContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        tblCustomer.setId("my-table");

    }

    private void loadAllCustomer() {
        ObservableList<CustomerTm> oblist = FXCollections.observableArrayList();
        try {
            List<CustomerDto> dtoList = customerModel.getAllCustomer();
            for (CustomerDto dto : dtoList) {
                oblist.add(
                        new CustomerTm(
                                dto.getCustomer_id(),
                                dto.getName(),
                                dto.getAddress(),
                                dto.getContact()
                        )
                );
            }
            tblCustomer.setItems(oblist);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String id = lblCustomerId.getText();
        String name = txtCustomerName.getText();
        String address = txtCustomerAddress.getText();
        String contact = txtCustomerContact.getText();

        boolean isValidName = RegExPatterns.validName.matcher(name).matches();
        boolean isValidAddress = RegExPatterns.validAddress.matcher(address).matches();
        boolean isValidPhoneNumber = RegExPatterns.validPhoneNumber.matcher(contact).matches();

        if (!isValidName) {
            new Alert(Alert.AlertType.ERROR, "Can Not Save Customer.Name Is Empty").showAndWait();
            return;
        }
        if (!isValidAddress) {
            new Alert(Alert.AlertType.ERROR, "Can Not Save Customer.Address Is Empty").showAndWait();
            return;
        }
        if (!isValidPhoneNumber) {
            new Alert(Alert.AlertType.ERROR, "Can Not Save Customer.Phone Number Is Empty").show();
        } else {
            var dto = new CustomerDto(id, name, address, contact);
            try {
                try {
                    boolean isSaved = customerModel.saveCustomer(dto);
                    if (isSaved) {
                        new Alert(Alert.AlertType.CONFIRMATION, "Customer is Saved").show();
                        clearFields();
                        generateNextCustomer();
                        loadAllCustomer();
                        countCustomer();
                    } else {
                        new Alert(Alert.AlertType.ERROR, "Customer is not save").show();
                    }
                } catch (SQLException e) {
                    new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
                }
            } catch (NumberFormatException e) {
                new Alert(Alert.AlertType.ERROR, "Invalid Phone Number").showAndWait();
            }
        }
    }


    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String id = lblCustomerId.getText();
        String name = txtCustomerName.getText();
        String address = txtCustomerAddress.getText();
        String contact = txtCustomerContact.getText();

        boolean isValidName = RegExPatterns.validName.matcher(name).matches();
        boolean isValidAddress = RegExPatterns.validAddress.matcher(address).matches();
        boolean isValidPhoneNumber = RegExPatterns.validPhoneNumber.matcher(contact).matches();

        if (!isValidName) {
            new Alert(Alert.AlertType.ERROR, "Can Not Update Customer.Name Is Empty").showAndWait();
            return;
        }
        if (!isValidAddress) {
            new Alert(Alert.AlertType.ERROR, "Can Not Update Customer.Address Is Empty").showAndWait();
            return;
        }
        if (!isValidPhoneNumber) {
            new Alert(Alert.AlertType.ERROR, "Can Not Update Customer.Phone Number Is Empty").showAndWait();
        } else {
            try {
                var dto = new CustomerDto(id, name, address, contact);
                try {
                    boolean isUpdated = customerModel.updateCustomer(dto);
                    if (isUpdated) {
                        new Alert(Alert.AlertType.CONFIRMATION, "Customer Is Updated").show();
                        loadAllCustomer();
                        clearFields();
                        generateNextCustomer();
                    } else {
                        new Alert(Alert.AlertType.ERROR, "Customer Is Not Updated").show();
                    }
                } catch (SQLException e) {
                    new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
                }
            } catch (NumberFormatException e) {
                new Alert(Alert.AlertType.ERROR, "In Valid Phone Number Format").showAndWait();
            }
        }
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String id = lblCustomerId.getText();
        String name = txtCustomerName.getText();
        String address = txtCustomerAddress.getText();
        String contact = txtCustomerContact.getText();

        boolean isValidName = RegExPatterns.validName.matcher(name).matches();
        boolean isValidAddress = RegExPatterns.validAddress.matcher(address).matches();
        boolean isValidPhoneNumber = RegExPatterns.validAddress.matcher(contact).matches();

        if (!isValidName) {
            new Alert(Alert.AlertType.ERROR, "Can Not Delete Customer,Name Is Empty").showAndWait();
            return;
        }
        if (!isValidAddress){
            new Alert(Alert.AlertType.ERROR,"Can Not Delete Customer.Address Is Empty").showAndWait();
            return;
        }
        if (!isValidPhoneNumber){
            new Alert(Alert.AlertType.ERROR,"Can Not Delete Customer.Phone Number Is Empty").showAndWait();
        }else {
            try {
                boolean isDelete = customerModel.deleteCustomer(id);
                if (isDelete) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Customer Is Deleted").show();
                    loadAllCustomer();
                    clearFields();
                    generateNextCustomer();
                } else {
                    new Alert(Alert.AlertType.INFORMATION, "Customer Is Not Deleted").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        }
    }


    @FXML
    void txtCustomerSearchOnAction(ActionEvent event) {
        String searchInput = txtCustomerSearch.getText();

        try {
            CustomerDto customerDto;

            if (searchInput.matches("\\d")) {
                customerDto = customerModel.searchCustomerByPhoneNumber(searchInput);
            } else {
                customerDto = customerModel.searchCustomer(searchInput);
            }
            if (customerDto != null) {
                lblCustomerId.setText(customerDto.getCustomer_id());
                txtCustomerName.setText(customerDto.getName());
                txtCustomerAddress.setText(customerDto.getAddress());
                txtCustomerContact.setText(customerDto.getContact());
            } else {
                lblCustomerId.setText("");
                generateNextCustomer();
                new Alert(Alert.AlertType.INFORMATION, "Customer Is Not Found").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }


    @FXML
    void btnReportOnAction(ActionEvent event) throws JRException, SQLException {
        InputStream resourceAsStream = getClass().getResourceAsStream("/reports/Customer.jrxml");
        JasperDesign load = JRXmlLoader.load(resourceAsStream);
        JasperReport jasperReport = JasperCompileManager.compileReport(load);
        JasperPrint jasperPrint = JasperFillManager.fillReport(
                jasperReport,
                null,
                DBConnection.getInstance().getConnection()
        );
        JasperViewer.viewReport(jasperPrint,false);
    }

    @FXML
    void txtGoToCustomerPhoneNumberOnAction(ActionEvent event) {
        txtCustomerContact.requestFocus();
    }

    @FXML
    void txtGoToCustomerAddressOnAction(ActionEvent event) {
        txtCustomerAddress.requestFocus();
    }
}