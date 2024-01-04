package lk.ijse.StichCraft.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.StichCraft.DTO.RegistrationDto;
import lk.ijse.StichCraft.DAO.custom.impl.RegistationDAOimpl;

import java.io.IOException;
import java.sql.SQLException;

public class RegisterFormController {

    @FXML
    private AnchorPane RegisterPane;

    @FXML
    private TextField txtNewPasswordId;

    @FXML
    private TextField txtPasswordId;

    @FXML
    private TextField txtUserName;

    private RegistationDAOimpl registationModel = new RegistationDAOimpl();

    public void initialize(){
        clearFields();
    }

    private void clearFields() {
        txtUserName.setText("");
        txtPasswordId.setText("");
        txtNewPasswordId.setText("");
    }

    @FXML
    void btnBackOnAction(MouseEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(this.getClass().getResource("/view/login_form.fxml"));
        Scene scene = new Scene(anchorPane);
        Stage stage = (Stage) this.RegisterPane.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Login form");
        stage.centerOnScreen();
    }

    @FXML
    void btnRegisterOnAction(ActionEvent event) {
        String UserName = txtUserName.getText();
        String pw = txtPasswordId.getText();
        String newPw = txtNewPasswordId.getText();

        if (!newPw.equals(pw) || UserName.isEmpty() || pw.isEmpty()){
            new Alert(Alert.AlertType.ERROR,"Password Did Not Matched").showAndWait();
            return;
        }

        var dto = new RegistrationDto(UserName,pw);
        try {
            boolean checkDuplicate = registationModel.check(UserName,pw);
            if (checkDuplicate){
                new Alert(Alert.AlertType.ERROR,"Duplicate Entry").showAndWait();
                return;
            }

            boolean isRegistered = registationModel.SaveUser(dto);
            if (isRegistered){
                new Alert(Alert.AlertType.CONFIRMATION,"Your Account Is Create").show();
                clearFields();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    @FXML
    void txtGoToPasswordOnAction(ActionEvent event) {
        txtPasswordId.requestFocus();
    }

    @FXML
    void txtGoToNewPasswordOnAction(ActionEvent event) {
        txtNewPasswordId.requestFocus();
    }
    @FXML
    void txtRegisterOnAction(ActionEvent event) {
        btnRegisterOnAction(new ActionEvent());
    }
}
