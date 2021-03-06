package fxmlcomponents;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import utilities.MySQLDatabase;

public class Main extends Application {

    public static final String LOGIN_SCREEN = "login";
    public static final String LOGIN_SCREEN_FXML = "loginScreen.fxml";
    public static final String REGISTER_SCREEN = "register";
    public static final String REGISTER_SCREEN_FXML = "registerScreen.fxml";
    public static final String USER_SCREEN = "user";
    public static final String USER_SCREEN_FXML = "userScreen.fxml";
    public static final String LIBRARIAN_SCREEN = "librarian";
    public static final String LIBRARIAN_SCREEN_FXML = "librarianScreen.fxml";
    public static final String ADDING_SCREEN = "adding";
    public static final String ADDING_SCREEN_FXML = "addingScreen.fxml";
    public static final String CHECKING_SCREEN = "checking";
    public static final String CHECKING_SCREEN_FXML = "checkingScreen.fxml";

    private static MySQLDatabase dbConn;

    public static MySQLDatabase getDbConn() {
        return Main.dbConn;
    }

    public static void setDbConn(MySQLDatabase dbConn) {
        Main.dbConn = dbConn;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        ScreensController mainContainer = new ScreensController();
        mainContainer.loadScreen(Main.LOGIN_SCREEN, Main.LOGIN_SCREEN_FXML);
        mainContainer.loadScreen(Main.REGISTER_SCREEN, Main.REGISTER_SCREEN_FXML);
        mainContainer.loadScreen(Main.USER_SCREEN, Main.USER_SCREEN_FXML);
        mainContainer.loadScreen(Main.LIBRARIAN_SCREEN, Main.LIBRARIAN_SCREEN_FXML);
        mainContainer.loadScreen(Main.ADDING_SCREEN, Main.ADDING_SCREEN_FXML);
        mainContainer.loadScreen(Main.CHECKING_SCREEN, Main.CHECKING_SCREEN_FXML);
        mainContainer.setScreen(Main.LOGIN_SCREEN);
        Group root = new Group();
        root.getChildren().addAll(mainContainer);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
        primaryStage.setOnCloseRequest(we -> {
            if (Main.getDbConn() != null) {
                if (Main.getDbConn().closeConnection()) {
                    System.out.println("Connection closed!");
                } else {
                    System.out.println("Connection closing failed!");
                }
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
