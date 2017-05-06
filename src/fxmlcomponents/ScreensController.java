package fxmlcomponents;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import utilities.MySQLDatabase;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Properties;

/**
 * Created by lukacrnjakovic on 4/27/17.
 */
public class ScreensController extends StackPane {

    private HashMap<String, Node> screens = new HashMap<>();
    private String username = "";
    private String isbn = "";

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void addScreen(String name, Node screen) {
        screens.put(name, screen);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public MySQLDatabase setConnectionProperties() {
        Properties dbInfo = new Properties();
        try {
            InputStream infoStream = new FileInputStream("src/dbconnection/dbinfo.properties");
            dbInfo.load(infoStream);
        } catch (FileNotFoundException e) {
            System.out.println("Property file not found!");
            System.exit(0);
        } catch (IOException e) {
            System.out.println("Error while reading the property file");
            System.exit(0);
        }

        return new MySQLDatabase(dbInfo.getProperty("username"), dbInfo.getProperty("password"), dbInfo.getProperty("dbms"));
    }

    public boolean loadScreen(String name, String resource) {
        try {
            FXMLLoader myLoader = new
                    FXMLLoader(getClass().getResource(resource));
            Parent loadScreen = (Parent) myLoader.load();
            ControlledScreen myScreenControler =
                    ((ControlledScreen) myLoader.getController());
            myScreenControler.setScreenParent(this);
            addScreen(name, loadScreen);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean setScreen(final String name) {
        if (screens.get(name) != null) {
            final DoubleProperty opacity = opacityProperty();
            if (!getChildren().isEmpty()) {
                Timeline fade = new Timeline(
                        new KeyFrame(Duration.ZERO, new KeyValue(opacity, 1.0)),
                        new KeyFrame(new Duration(500),
                                new EventHandler<ActionEvent>() {
                                    @Override
                                    public void handle(ActionEvent event) {
                                        getChildren().remove(0);
                                        //add new screen
                                        getChildren().add(0, screens.get(name));
                                        Timeline fadeIn = new Timeline(
                                                new KeyFrame(Duration.ZERO,
                                                        new KeyValue(opacity, 0.0)),
                                                new KeyFrame(new Duration(800),
                                                        new KeyValue(opacity, 1.0)));
                                        fadeIn.play();
                                    }
                                }, new KeyValue(opacity, 0.0))
                );
                fade.play();
            } else {
                setOpacity(0.0);
                getChildren().add(screens.get(name));
                Timeline fadein = new Timeline(
                        new KeyFrame(Duration.ZERO, new KeyValue(opacity, 0.0)),
                        new KeyFrame(new Duration(1000), new KeyValue(opacity, 1.0))
                );
                fadein.play();
            }
            return true;
        } else {
            System.out.println("Screen hasn't been loaded!");
            return false;
        }
    }

}
