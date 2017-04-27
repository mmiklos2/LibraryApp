package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

import java.net.URL;
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
    }

    public void enableAdminCode(ActionEvent actionEvent) {
        boolean selected = libCheck.isSelected();
        if(selected){
            libCode.setDisable(false);
        }
    }
}
