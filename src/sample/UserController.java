package sample;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class UserController implements Initializable, ControlledScreen {

    ScreensController myController;
    private List<DetailedBook> list = null;
    private TableView<DetailedBook> table = null;
    private final static int rowsPerPage = 10;
    private final static int dataSize = 10_023;

    @FXML
    private ComboBox comboBox;

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

        Connection conect=null;
        MySQLDatabase con= new MySQLDatabase("root","password","localhost","3306", "mydb");
        if(con.connect(conect)){
            System.out.println("Connected!");
        }
        else{
            System.out.println("Not connected");

        }
        ConcreteSearcher cs=new ConcreteSearcher(con);
        list = cs.search("dible", "publisher");
        System.out.println("Size : " + list.size());
        table = createTable();
        Pagination pagination = new Pagination((list.size() / rowsPerPage + 1), 0);
        pagination.setPageFactory(this::createPage);
        placeholder.getChildren().add(pagination);
    }

    private TableView<DetailedBook> createTable(){
        TableView<DetailedBook> table = new TableView<>();

        TableColumn<DetailedBook, String> column1 = new TableColumn<>("ISBN");
        column1.setCellValueFactory(param -> param.getValue().getBook_isbn());
        column1.setPrefWidth(125);

        TableColumn<DetailedBook, String> column2 = new TableColumn<>("Title");
        column2.setCellValueFactory(param -> param.getValue().getBook_title());
        column2.setPrefWidth(100);

        TableColumn<DetailedBook, String> column3 = new TableColumn<>("Author(s)");
        column3.setCellValueFactory(param -> param.getValue().getAuthor());
        column3.setPrefWidth(100);

        TableColumn<DetailedBook, String> column4 = new TableColumn<>("Year");
        column4.setCellValueFactory(param -> param.getValue().getBook_publisher_year());
        column4.setPrefWidth(50);

        TableColumn<DetailedBook, String> column5 = new TableColumn<>("Copies");
        column5.setCellValueFactory(param -> param.getValue().getBook_copies());
        column5.setPrefWidth(75);

        TableColumn<DetailedBook, String> column6 = new TableColumn<>("Location");
        column6.setCellValueFactory(param -> param.getValue().getBook_location());
        column6.setPrefWidth(100);

        TableColumn<DetailedBook, String> column7 = new TableColumn<>("Genre");
        column7.setCellValueFactory(param -> param.getValue().getBook_genre());
        column7.setPrefWidth(100);

        TableColumn<DetailedBook, String> column8 = new TableColumn<>("Publisher");
        column8.setCellValueFactory(param -> param.getValue().getPublisher_name());
        column8.setPrefWidth(100);

        table.getColumns().addAll(column1, column2, column3, column4, column5, column6, column7, column8);

        table.setFixedCellSize(25);
        table.prefHeightProperty().bind(table.fixedCellSizeProperty().multiply(Bindings.size(table.getItems()).add(11.1)));
        table.minHeightProperty().bind(table.prefHeightProperty());
        table.maxHeightProperty().bind(table.prefHeightProperty());
        return table;
    }

    private Node createPage(int pageIndex){
        int fromIndex = pageIndex * rowsPerPage;
        int toIndex = Math.min(fromIndex + rowsPerPage, list.size());
        table.setItems(FXCollections.observableArrayList(list.subList(fromIndex, toIndex)));

        return new Pane(table);
    }


}
