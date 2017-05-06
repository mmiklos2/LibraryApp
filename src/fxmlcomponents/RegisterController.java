package fxmlcomponents;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
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
public class RegisterController implements Initializable, ControlledScreen {
    ScreensController myController;

    @FXML
    CheckBox libCheck;

    @FXML
    TextField libCode;

    @FXML
    TextField fName, lName, email, username;

    @FXML
    PasswordField password;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public void setScreenParent(ScreensController screenParent) {
        myController = screenParent;
    }

    public void goToLogin(ActionEvent actionEvent) {
        myController.setScreen(Main.LOGIN_SCREEN);
    }

    public void triggerRegistration(ActionEvent actionEvent) {
        performRegistration();
    }

    public void enableAdminCode(ActionEvent actionEvent) {
        boolean selected = libCheck.isSelected();
        if (selected) {
            libCode.setDisable(false);
        } else {
            libCode.setDisable(true);
        }
    }

    public void enterRegister(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            performRegistration();
        }
    }

    private void performRegistration() {
        // WRITE NEW USER TO DB
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
            if (libCode.getText().equals("hujak")) {
                con.regLib(fName.getText(), lName.getText(), email.getText(), username.getText(), password.getText());
            } else {
                con.regUser(fName.getText(), lName.getText(), email.getText(), username.getText(), password.getText());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        myController.setScreen(Main.LOGIN_SCREEN);
    }
}
