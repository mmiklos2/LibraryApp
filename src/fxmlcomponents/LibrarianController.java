package fxmlcomponents;

import dataobjects.DetailedBook;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import utilities.ConcreteSearcher;
import utilities.PDFBuilder;
import utilities.TableBuilder;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by lukacrnjakovic on 4/28/17.
 */
public class LibrarianController implements Initializable, ControlledScreen {
    ScreensController myController;
    private List<DetailedBook> list = null;
    private TableView<DetailedBook> table = null;
    private final static int rowsPerPage = 10;
    private final static int dataSize = 10_023;
    PDFBuilder pdfb;

    @FXML
    private ComboBox comboBox;

    @FXML
    private TextField searchText;

    @FXML
    private Pane placeholder;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        comboBox.getItems().addAll("Authors", "Titles", "Publishers", "Genres");
    }

    public void setScreenParent(ScreensController screenParent) {
        myController = screenParent;
    }

    public void buildSearch(ActionEvent actionEvent) {
        TableBuilder tb = new TableBuilder();
        String textValue;
        String comboValue;
        if (comboBox.getValue() != null) {
            textValue = searchText.getText();
            comboValue = comboBox.getValue().toString();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText(null);
            alert.setContentText("Please select area!");
            alert.showAndWait();
            return;
        }

        ConcreteSearcher cs = new ConcreteSearcher(Main.getDbConn());
        list = cs.search(textValue, comboValue, false, "");
        pdfb = new PDFBuilder(list);
        table = tb.createTable();
        table.setRowFactory(tv -> {
            TableRow<DetailedBook> db = new TableRow<>();
            db.setOnMouseClicked(event -> {
                if (!db.isEmpty() && event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
                    myController.setIsbn(db.getItem().getBook_isbn().getValue());
                }
            });
            return db;
        });
        Pagination pagination = new Pagination((list.size() / rowsPerPage + 1), 0);
        pagination.setPageFactory(this::createPage);
        placeholder.getChildren().add(pagination);

    }

    private Node createPage(int pageIndex) {
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

    public void exportPDF(ActionEvent actionEvent) {
        if (list == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText(null);
            alert.setContentText("You must populate the table first!");
            alert.showAndWait();
        } else {
            pdfb.populatePDF();
        }
    }
}
