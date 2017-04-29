package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * Created by lukacrnjakovic on 4/27/17.
 */
public class RegisterController implements Initializable, ControlledScreen{
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
    public void initialize(URL url, ResourceBundle rb){

    }

    public void setScreenParent(ScreensController screenParent) {
        myController = screenParent;
    }

    public void goToLogin(ActionEvent actionEvent) {
        myController.setScreen(Main.LOGIN_SCREEN);
    }

    public void triggerRegistration(ActionEvent actionEvent) {
        // WRITE NEW USER TO DB
        Connection conect=null;
        MySQLDatabase con= new MySQLDatabase("root","password","localhost","3306", "mydb");
        if(con.connect(conect)){
            System.out.println("Connected!");
        }
        else{
            System.out.println("Not connected");

        }
        try{
            if(libCode.getText().equals("15")){
                con.regLib(fName.getText(), lName.getText(), email.getText(), username.getText(), password.getText());
            }
            else{
                con.regUser(fName.getText(), lName.getText(), email.getText(), username.getText(), password.getText());
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        if(con.closeConnection()){
            System.out.println("connect closed ");
        }else{
            System.out.println("connect did not closed ");
        }
    }

    public void enableAdminCode(ActionEvent actionEvent) {
        boolean selected = libCheck.isSelected();
        if(selected){
            libCode.setDisable(false);
        }
        else{
            libCode.setDisable(true);
        }
    }
}
