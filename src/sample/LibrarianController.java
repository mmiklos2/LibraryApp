package sample;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.sql.Connection;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by lukacrnjakovic on 4/28/17.
 */
public class LibrarianController implements Initializable, ControlledScreen{
    ScreensController myController;
    private List<DetailedBook> list = null;
    private TableView<DetailedBook> table = null;
    private final static int rowsPerPage = 10;
    private final static int dataSize = 10_023;

    @FXML
    private ComboBox comboBox;

    @FXML
    private TextField searchText;

    @FXML
    private Pane placeholder;

    @Override
    public void initialize(URL url, ResourceBundle rb){
        comboBox.getItems().addAll("Authors", "Titles", "Publishers", "Genres");
    }

    public void setScreenParent(ScreensController screenParent){
        myController = screenParent;
    }

    public void buildSearch(ActionEvent actionEvent) {
        TableBuilder tb = new TableBuilder();
        String textValue;
        String comboValue;
        if(comboBox.getValue() != null){
            textValue = searchText.getText();
            comboValue = comboBox.getValue().toString();
        }
        else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText(null);
            alert.setContentText("Please select area!");
            alert.showAndWait();
            return;
        }

        Connection conect=null;
        MySQLDatabase con= new MySQLDatabase("root","password","localhost","3306", "mydb");
        if(con.connect(conect)){
            System.out.println("Connected!");
        }
        else{
            System.out.println("Not connected");

        }
        ConcreteSearcher cs=new ConcreteSearcher(con);
        System.out.println(textValue + ", " + comboValue);
        list = cs.search(textValue, comboValue, false, "");
        System.out.println("Size : " + list.size());
        table = tb.createTable();
        Pagination pagination = new Pagination((list.size() / rowsPerPage + 1), 0);
        pagination.setPageFactory(this::createPage);
        placeholder.getChildren().add(pagination);
        if(con.closeConnection()){
            System.out.println("connect closed ");
        }else{
            System.out.println("connect did not closed ");
        }

    }

    private Node createPage(int pageIndex){
        int fromIndex = pageIndex * rowsPerPage;
        int toIndex = Math.min(fromIndex + rowsPerPage, list.size());
        table.setItems(FXCollections.observableArrayList(list.subList(fromIndex, toIndex)));

        return new Pane(table);
    }

    public void addBook(ActionEvent actionEvent) {
        myController.setScreen(Main.ADDING_SCREEN);
    }

    public void performCheckOut(ActionEvent actionEvent) {
        myController.setScreen(Main.CHECKING_SCREEN);
    }
}
