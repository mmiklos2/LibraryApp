package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * Created by lukacrnjakovic on 4/28/17.
 */
public class AddingController implements Initializable, ControlledScreen{
    ScreensController myController;

    @FXML
    private TextField title, isbn, year, copies, location, pubName, pubCity, genre, fName, lName;

    @Override
    public void initialize(URL url, ResourceBundle rb){

    }

    public void setScreenParent(ScreensController screenParent){
        myController = screenParent;
    }

    public void goBack(ActionEvent actionEvent) {
        myController.setScreen(Main.LIBRARIAN_SCREEN);
    }

    public void addBook(ActionEvent actionEvent) {

        try{
            this.myController.getDbConnObject().postP(title.getText(), isbn.getText(), year.getText(), copies.getText(), location.getText(), pubName.getText(), pubCity.getText(), genre.getText(), fName.getText(), lName.getText());
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        myController.setScreen(Main.LIBRARIAN_SCREEN);

    }
}
