package desgin2;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;

public class FXMLDocumentController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @FXML
    void Quide(ActionEvent event) {
    }

    @FXML
    void cancel(ActionEvent event) {
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }

    @FXML
    void logIn(ActionEvent event) {
        Desgin2.commonMethods.AdminOrEmployee();
        //((Node) (event.getSource())).getScene().getWindow().hide();
    

    }

    @FXML
    void closeMainInterface(ActionEvent event) {//this methods for make account..
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }

}

/* 
    @FXML
    void Close(MouseEvent event) {//close The Program Whill go to The FirstPage
        try {

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FirstWelcomeToAll.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    void goToTheEmployees(ActionEvent event) {
        try {
            admin_anchor.setVisible(false);
            // admin_anchor
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("admin_Employee.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (IOException ex) {
            System.out.println(":");
        }
    }

    @FXML
    void goToTheInvoices(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("admin_Invoices.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (IOException ex) {
            System.out.println(":");
        }
    }

    @FXML
    void goToTheSuppliers(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("admin_Suplliers.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (IOException ex) {
            System.out.println(":");
        }
    }

    @FXML
    void goToTheategories(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Categories.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (IOException ex) {
            System.out.println(":");
        }
    }

    @FXML
    void AddNewEmployee(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("admin_EmployeeOperation.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (IOException ex) {
            System.out.println(":");
        }
    }

    @FXML
    void DeleteEmployee(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("admin_EmployeeOperation.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (IOException ex) {
            System.out.println(":");
        }
    }

    @FXML
    void EditEmployee(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("admin_EmployeeOperation.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (IOException ex) {
            System.out.println(":");
        }
    }

    @FXML
    void SendMasseage(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SendMessage.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (IOException ex) {
            System.out.println(":");
        }
    }
 */
 /*
background-image: linear-gradient(to right, #D1913C 0%, #FFD194 51%, #D1913C 100%)}
.btn-grad:hover { background-position: right center;
 */
