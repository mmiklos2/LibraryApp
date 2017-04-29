package sample;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;

import java.util.List;

/**
 * Created by lukacrnjakovic on 4/28/17.
 */
public class TableBuilder {

    public TableView<DetailedBook> createTable(){
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
        column4.setPrefWidth(40);

        TableColumn<DetailedBook, String> column5 = new TableColumn<>("Copies");
        column5.setCellValueFactory(param -> param.getValue().getBook_copies());
        column5.setPrefWidth(70);

        TableColumn<DetailedBook, String> column6 = new TableColumn<>("Location");
        column6.setCellValueFactory(param -> param.getValue().getBook_location());
        column6.setPrefWidth(70);

        TableColumn<DetailedBook, String> column7 = new TableColumn<>("Genre");
        column7.setCellValueFactory(param -> param.getValue().getBook_genre());
        column7.setPrefWidth(70);

        TableColumn<DetailedBook, String> column8 = new TableColumn<>("Publisher");
        column8.setCellValueFactory(param -> param.getValue().getPublisher_name());
        column8.setPrefWidth(75);

        TableColumn<DetailedBook, String> column9 = new TableColumn<>("Date due");
        column9.setCellValueFactory(param -> param.getValue().getDateDue());
        column9.setPrefWidth(75);

        table.getColumns().addAll(column1, column2, column3, column4, column5, column6, column7, column8, column9);

        table.setFixedCellSize(25);
        table.prefHeightProperty().bind(table.fixedCellSizeProperty().multiply(Bindings.size(table.getItems()).add(11.1)));
        table.minHeightProperty().bind(table.prefHeightProperty());
        table.maxHeightProperty().bind(table.prefHeightProperty());
        return table;
    }

}
