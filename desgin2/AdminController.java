package desgin2;

import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class AdminController implements Initializable {

    @FXML
    private JFXHamburger hamburger;
    @FXML
    private ImageView img1;

    @FXML
    private ImageView img2;

    @FXML
    private ImageView img3;

    @FXML
    private ImageView img4;

    @FXML
    private ImageView img5;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        HamburgerBackArrowBasicTransition arrowBasicTransition = new HamburgerBackArrowBasicTransition(hamburger);
        arrowBasicTransition.setRate(-1);
        hamburger.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> {
            arrowBasicTransition.setRate(arrowBasicTransition.getRate() * -1);
            arrowBasicTransition.play();
            if (!img1.isVisible()) {
                img1.setVisible(true);
                img2.setVisible(true);
                img3.setVisible(true);
                img4.setVisible(true);
                img5.setVisible(true);
            } else {
                img1.setVisible(false);
                img2.setVisible(false);
                img3.setVisible(false);
                img4.setVisible(false);
                img5.setVisible(false);
            }
        });

    }

    @FXML
    void Close(MouseEvent event) {
        Desgin2.commonMethods.welcomePage();
        ((Node) (event.getSource())).getScene().getWindow().hide();

    }

    @FXML
    void goToTheEmployees(ActionEvent event) {
        Desgin2.commonMethods.goToTheEmployeeAdmin();
        ((Node) (event.getSource())).getScene().getWindow().hide();

    }

    @FXML
    void goToTheInvoices(ActionEvent event) {
        Desgin2.commonMethods.InvoicesAdminPage();
        ((Node) (event.getSource())).getScene().getWindow().hide();

    }

    @FXML
    void goToTheSuppliers(ActionEvent event) {
        Desgin2.commonMethods.SuppliersAdminPage();
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }

    @FXML
    void goToTheategories(ActionEvent event) {
        Desgin2.commonMethods.goToTheCategories();
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }

    @FXML
    void moveToAnoutherPage(MouseEvent event) {
        ImageView element = (ImageView) event.getSource();
        if (element == img1) {
            Desgin2.commonMethods.mainAdmin();
            ((Node) (event.getSource())).getScene().getWindow().hide();

        } else if (element == img2) {
            Desgin2.commonMethods.goToTheSetting();
            ((Node) (event.getSource())).getScene().getWindow().hide();

        } else if (element == img3) {
            Desgin2.commonMethods.goToTheNotification();
            ((Node) (event.getSource())).getScene().getWindow().hide();

        } else if (element == img4) {
            /*
            Desgin2.commonMethods.goToTheAboutUs();
            ((Node) (event.getSource())).getScene().getWindow().hide();*/

        } else if (element == img5) {
            Desgin2.commonMethods.ReviewOfTheLost();
            ((Node) (event.getSource())).getScene().getWindow().hide();

        }
    }
}
