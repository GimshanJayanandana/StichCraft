package lk.ijse.StichCraft.controller;



import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.StichCraft.model.RegistationModel;
import java.io.IOException;
import java.sql.SQLException;

public class LoginFromController {
    @FXML
    private AnchorPane rootNode;
    @FXML
    private TextField txtPassword;
    @FXML
    private TextField txtUserName;

    private RegistationModel registationModel = new RegistationModel();

    private void Login() throws IOException{
        rootNode.getScene().getWindow().hide();
        Stage stage = new Stage();
        stage.setScene(new Scene(FXMLLoader.load(this.getClass().getResource("/view/mainboard_form.fxml"))));
        stage.centerOnScreen();
        stage.show();
    }

    @FXML
    void btnLoginOnAction(ActionEvent event) throws IOException {
        String UserName = txtUserName.getText();
        String pw = txtPassword.getText();
        try {
            boolean isValid = registationModel.ValidUser(UserName,pw);
            if (isValid){
                Login();
            }else {
                new Alert(Alert.AlertType.ERROR,"UserName or Password Did Not Mached").showAndWait();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }
    @FXML
    void  btnRegisterOnAction(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(this.getClass().getResource("/view/register_form.fxml"));
        Scene scene = new Scene(anchorPane);
        Stage stage = (Stage) this.rootNode.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("Register");
    }

    @FXML
    void txtGoToPassword(ActionEvent event) {
        txtPassword.requestFocus();
    }

    @FXML
    void txtLoginOnAction(ActionEvent event) throws IOException {
        btnLoginOnAction(new ActionEvent());
    }

}
