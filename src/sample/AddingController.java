package sample;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by lukacrnjakovic on 4/28/17.
 */
public class AddingController implements Initializable, ControlledScreen{
    ScreensController myController;

    @Override
    public void initialize(URL url, ResourceBundle rb){

    }

    public void setScreenParent(ScreensController screenParent){
        myController = screenParent;
    }

    public void goBack(ActionEvent actionEvent) {
        myController.setScreen(Main.LIBRARIAN_SCREEN);
    }
}
