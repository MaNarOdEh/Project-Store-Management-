package desgin2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Desgin2 extends Application {

    public static ConnectionToDataBase connectionToDataBase = new ConnectionToDataBase();
    public static CommonMethods commonMethods = new CommonMethods();
    public static boolean isAdmin;
    public static String nameOrEmail;
    public static String passwordForAdminOrEmployee;
    public static Stage st;
    public static boolean isEmployee;

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FirstWelcomeToAll.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        st=stage;
        stage.initStyle(StageStyle.UTILITY);
        stage.requestFocus();
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
