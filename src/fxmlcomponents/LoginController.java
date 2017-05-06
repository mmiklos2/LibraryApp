package fxmlcomponents;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import utilities.MySQLDatabase;

import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * Created by lukacrnjakovic on 4/27/17.
 */
public class LoginController implements Initializable, ControlledScreen {
    ScreensController myController;

    @FXML
    TextField username;

    @FXML
    PasswordField password;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public void setScreenParent(ScreensController screenParent) {
        myController = screenParent;
    }

    public void goToRegister(ActionEvent actionEvent) {
        myController.setScreen(Main.REGISTER_SCREEN);
    }

    public void triggerLogin(ActionEvent actionEvent) {
        performLogin();
    }

    public void enterSignIn(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            performLogin();
        }
    }

    private void performLogin() {
        // PERFORM AUTHENTICATION
        String role;
        MySQLDatabase con;
        if (Main.getDbConn() == null) {
            Connection dbConn = null;
            con = myController.setConnectionProperties();
            if (con.connect(dbConn)) {
                System.out.println("Connected!");
                Main.setDbConn(con);
            } else {
                System.out.println("Not connected");

            }
        } else {
            con = Main.getDbConn();
        }

        try {
            role = con.login(username.getText(), password.getText());
            if (role.equals("U")) {
                myController.setUsername(username.getText());
                myController.setScreen(Main.USER_SCREEN);
            } else if (role.equals("L"))
                myController.setScreen(Main.LIBRARIAN_SCREEN);
            else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setHeaderText(null);
                alert.setContentText("Username or password incorrect!");
                alert.showAndWait();

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
