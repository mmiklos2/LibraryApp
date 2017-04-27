package sample;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import sample.ControlledScreen;
import sample.ScreensController;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by lukacrnjakovic on 4/27/17.
 */
public class LoginController implements Initializable, ControlledScreen{
    ScreensController myController;

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

    }
}
