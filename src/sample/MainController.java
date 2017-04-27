package sample;

import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable, ControlledScreen {

    ScreensController myController;

    @Override
    public void initialize(URL url, ResourceBundle rb){

    }

    public void setScreenParent(ScreensController screenParent){
        myController = screenParent;
    }
}
