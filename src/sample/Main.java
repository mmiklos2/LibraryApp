package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static final String LOGIN_SCREEN = "login";
    public static final String LOGIN_SCREEN_FXML = "loginScreen.fxml";
    public static final String REGISTER_SCREEN = "register";
    public static final String REGISTER_SCREEN_FXML = "registerScreen.fxml";
    public static final String USER_SCREEN = "user";
    public static final String USER_SCREEN_FXML = "userScreen.fxml";

    @Override
    public void start(Stage primaryStage) throws Exception{
        ScreensController mainContainer = new ScreensController();
        mainContainer.loadScreen(Main.LOGIN_SCREEN, Main.LOGIN_SCREEN_FXML);
        mainContainer.loadScreen(Main.REGISTER_SCREEN, Main.REGISTER_SCREEN_FXML);
        mainContainer.loadScreen(Main.USER_SCREEN, Main.USER_SCREEN_FXML);
        mainContainer.setScreen(Main.LOGIN_SCREEN);
        Group root = new Group();
        root.getChildren().addAll(mainContainer);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
