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
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class UserController implements Initializable, ControlledScreen {

    ScreensController myController;
    private List<Publisher> list = null;
    private TableView<Publisher> table = null;
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
        list = createList();
        table = createTable();
        Pagination pagination = new Pagination((list.size() / rowsPerPage + 1), 0);
        pagination.setPageFactory(this::createPage);
        placeholder.getChildren().add(pagination);
    }

    private ArrayList<Publisher> createList(){
        ArrayList<Publisher> rak = new ArrayList<>();
        for(int i = 0; i<=100; i++){
            Publisher p = new Publisher(i, "Name " + i, "City " + i);
            rak.add(p);
        }

        return rak;
    }

    private TableView<Publisher> createTable(){
        TableView<Publisher> table = new TableView<>();
        TableColumn<Publisher, Integer> column1 = new TableColumn<>("Id");
        column1.setCellValueFactory(param -> param.getValue().publisher_id);
        column1.setPrefWidth(150);

        TableColumn<Publisher, String> column2 = new TableColumn<>("Foo");
        column2.setCellValueFactory(param -> param.getValue().publisher_name);
        column2.setPrefWidth(250);

        TableColumn<Publisher, String> column3 = new TableColumn<>("Bar");
        column3.setCellValueFactory(param -> param.getValue().publisher_city);
        column3.setPrefWidth(250);

        table.getColumns().addAll(column1, column2, column3);

        table.setFixedCellSize(25);
        table.prefHeightProperty().bind(table.fixedCellSizeProperty().multiply(Bindings.size(table.getItems()).add(11.1)));
        table.minHeightProperty().bind(table.prefHeightProperty());
        table.maxHeightProperty().bind(table.prefHeightProperty());

        System.out.println(list.size());
        return table;
    }

    private Node createPage(int pageIndex){
        int fromIndex = pageIndex * rowsPerPage;
        int toIndex = Math.min(fromIndex + rowsPerPage, list.size());
        table.setItems(FXCollections.observableArrayList(list.subList(fromIndex, toIndex)));

        return new Pane(table);
    }



}
