
package desgin2;

import com.jfoenix.controls.JFXCheckBox;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

public class CategoriesADDController implements Initializable {

    @FXML
    private TextField nameCategories;
    @FXML
    private TextField typeCategories;
    @FXML
    private TextField priceCategories;
    @FXML
    private Label color;
    @FXML
    private ColorPicker colorChooser;
    @FXML
    private JFXCheckBox male;
    @FXML
    private JFXCheckBox female;
    @FXML
    private TextField countCategories;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void onlyNumber(KeyEvent event) {
    }

    @FXML
    private void addNewCategories(ActionEvent event) {
    }
    
}
