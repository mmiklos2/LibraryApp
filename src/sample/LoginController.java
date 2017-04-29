package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import sample.ControlledScreen;
import sample.ScreensController;

import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * Created by lukacrnjakovic on 4/27/17.
 */
public class LoginController implements Initializable, ControlledScreen{
    ScreensController myController;

    @FXML
    TextField username;

    @FXML
    PasswordField password;

    @Override
    public void initialize(URL url, ResourceBundle rb){

    }

    public void setScreenParent(ScreensController screenParent) {
        myController = screenParent;
    }

    public void goToRegister(ActionEvent actionEvent) {
        myController.setScreen(Main.REGISTER_SCREEN);
    }

    public void triggerLogin(ActionEvent actionEvent) {
        // PERFORM AUTHENTICATION
        Connection conect=null;
        String role;
        MySQLDatabase con= new MySQLDatabase("root","password","localhost","3306", "mydb");
        if(con.connect(conect)){
            System.out.println("Connected!");
        }
        else{
            System.out.println("Not connected");

        }
        try{
            role = con.login(username.getText(), password.getText());
            if(role.equals("U")) {
                final Clipboard c = Clipboard.getSystemClipboard();
                final ClipboardContent cc = new ClipboardContent();
                cc.putString(username.getText());
                c.setContent(cc);
                myController.setScreen(Main.USER_SCREEN);
            }
            else if(role.equals("L"))
                myController.setScreen(Main.LIBRARIAN_SCREEN);
            else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setHeaderText(null);
                alert.setContentText("Username or password incorrect!");
                alert.showAndWait();

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
}
