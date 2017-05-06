package fxmlcomponents;

import dataobjects.DetailedBook;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import utilities.ConcreteSearcher;
import utilities.PDFBuilder;
import utilities.TableBuilder;

import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class UserController implements Initializable, ControlledScreen {

    ScreensController myController;
    //Clipboard c = Clipboard.getSystemClipboard();
    //private String user = c.getString();
    private List<DetailedBook> list = null;
    private TableView<DetailedBook> table = null;
    private final static int rowsPerPage = 10;
    private final static int dataSize = 10_023;
    PDFBuilder pdfb;

    @FXML
    private TextField searchText;

    @FXML
    private RadioButton allRadio;

    @FXML
    private RadioButton rentedRadio;

    @FXML
    private ComboBox comboBox;

    @FXML
    private Pane placeholder;

    private boolean counter = true;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        comboBox.getItems().addAll("Authors", "Titles", "Publishers", "Genres");
    }

    public void setScreenParent(ScreensController screenParent) {
        myController = screenParent;
    }

    public void buildSearch(ActionEvent actionEvent) {
        TableBuilder tb = new TableBuilder();
        boolean rented = false;
        String textValue;
        String comboValue;
        Alert alert = new Alert(Alert.AlertType.WARNING);
        Alert alert_loan = new Alert(Alert.AlertType.WARNING);

        if (comboBox.getValue() != null) {
            textValue = searchText.getText();
            comboValue = comboBox.getValue().toString();
        } else {

            alert.setTitle("Warning");
            alert.setHeaderText(null);
            alert.setContentText("Please select area!");
            alert.showAndWait();
            return;
        }


        if (rentedRadio.isSelected()) {
            rented = true;
        } else {
            rented = false;
        }


        ConcreteSearcher cs = new ConcreteSearcher(Main.getDbConn());
        list = cs.search(textValue, comboValue, rented, this.myController.getUsername());
        pdfb = new PDFBuilder(list);
        table = tb.createTable();
        Pagination pagination = new Pagination((list.size() / rowsPerPage + 1), 0);
        pagination.setPageFactory(this::createPage);
        placeholder.getChildren().add(pagination);


        if (rented == true || allRadio.isSelected() == true) {

            ArrayList<String> values = new ArrayList<>();
            Date db_Date = null;
            ArrayList<ArrayList<String>> results1 = null;
            ArrayList<String> wurf = new ArrayList<>();
            //////////

            wurf.add(this.myController.getUsername());
            String user_id = "SELECT user_id FROM user where user_username=?";
            results1 = Main.getDbConn().getData(user_id, wurf, false);
            values.add(results1.get(1).get(0));
            //////////////

            Date today = Calendar.getInstance().getTime();
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            /////

            String tod = df.format(today) + " 20:00:00";
            Date today_date = null;
            try {
                today_date = (Date) df1.parse(tod);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            String date = "SELECT date_due FROM books_on_loan WHERE user_id=?";
            ///////////
            ArrayList<ArrayList<String>> results = Main.getDbConn().getData(date, values, false);
            for (int i = 1; i < results.size(); i++) {
                String dbDate = results.get(i).get(0).substring(0, results.get(i).get(0).indexOf("."));

                ///////////
                try {
                    db_Date = (Date) df1.parse(dbDate);

                } catch (ParseException e) {
                    e.printStackTrace();
                }

                if (db_Date.compareTo(today_date) > 0) {
                } else if (db_Date.compareTo(today_date) < 0) {
                    if (counter) {
                        counter = false;
                        alert_loan.setTitle("Rented Books");
                        alert_loan.setContentText("You have Late Loans, Please check your books!!");
                        alert_loan.showAndWait();
                        return;
                    }
                } else {
                    if (counter) {
                        counter = false;
                        alert_loan.setTitle("Rented Books");
                        alert_loan.setContentText("You have to return book today!!");
                        alert_loan.showAndWait();
                        return;
                    }
                }
            }

        }

    }

    private Node createPage(int pageIndex) {
        int fromIndex = pageIndex * rowsPerPage;
        int toIndex = Math.min(fromIndex + rowsPerPage, list.size());
        table.setItems(FXCollections.observableArrayList(list.subList(fromIndex, toIndex)));

        return new Pane(table);
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
