package desgin2;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

public class EmployeeController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    void Close(MouseEvent event) {
        Desgin2.commonMethods.welcomePage();
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }

    @FXML
    void InsetItems(ActionEvent event) {
        Desgin2.commonMethods.goToTheCategories();
        ((Node) (event.getSource())).getScene().getWindow().hide();

    }

    @FXML
    void Invoices(ActionEvent event) {
        Desgin2.commonMethods.InvoicesAdminPage();
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }

    @FXML
    void introducingSalce(ActionEvent event) {
        Desgin2.commonMethods.IntroducingSalce();
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }

    @FXML
    void reviewOfTheLost(ActionEvent event) {
        Desgin2.commonMethods.ReviewOfTheLost();
        ((Node) (event.getSource())).getScene().getWindow().hide();

    }

    @FXML
    void notification(MouseEvent event) {
        Desgin2.commonMethods.goToTheNotification();
        ((Node) (event.getSource())).getScene().getWindow().hide();

    }

    @FXML
    void settings(MouseEvent event) {
        Desgin2.commonMethods.goToTheSetting();
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }


}
