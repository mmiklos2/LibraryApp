package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * Created by lukacrnjakovic on 4/28/17.
 */
public class CheckingController implements Initializable, ControlledScreen{
    ScreensController myController;

    @FXML
    private TextField username, isbn;

    @Override
    public void initialize(URL url, ResourceBundle rb){

    }

    public void setScreenParent(ScreensController screenParent){
        myController = screenParent;
    }

    private void setIsbnText(){
        isbn.setText(this.myController.getIsbn());
    }
    public void goBack(ActionEvent actionEvent) {
        myController.setScreen(Main.LIBRARIAN_SCREEN);
        username.clear();
        isbn.clear();
    }

    public void returnBook(ActionEvent actionEvent) {
        Connection conect=null;
        MySQLDatabase con= new MySQLDatabase("root","student","localhost","3306", "mydb");
        if(con.connect(conect)){
            System.out.println("Connected!");
        }
        else{
            System.out.println("Not connected");

        }
        try{
            con.deleteP(username.getText(), isbn.getText());
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        if(con.closeConnection()){
            System.out.println("connect closed ");
        }else{
            System.out.println("connect did not closed ");
        }
        myController.setScreen(Main.LIBRARIAN_SCREEN);
    }

    public void loanBook(ActionEvent actionEvent) {
        Connection conect=null;
        MySQLDatabase con= new MySQLDatabase("root","student","localhost","3306", "mydb");
        if(con.connect(conect)){
            System.out.println("Connected!");
        }
        else{
            System.out.println("Not connected");

        }
        try{
            con.setBook_On_Loan(username.getText(), isbn.getText());
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        if(con.closeConnection()){
            System.out.println("connect closed ");
        }else{
            System.out.println("connect did not closed ");
        }
        myController.setScreen(Main.LIBRARIAN_SCREEN);
    }

    public void loadIsbn(MouseEvent mouseEvent) {
        this.setIsbnText();
    }
}

