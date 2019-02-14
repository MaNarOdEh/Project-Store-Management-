package desgin2;

import com.jfoenix.controls.JFXCheckBox;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import org.controlsfx.control.textfield.TextFields;

public class Admin_Invoices0Controller implements Initializable {

    @FXML
    private TextField customerName;

    @FXML
    private TextField PhoneNumber;

    @FXML
    private DatePicker Date;

    @FXML
    private ComboBox<String> ItemType;

    @FXML
    private TextField goodCount;

    @FXML
    private JFXCheckBox imported;

    @FXML
    private JFXCheckBox sold;

    @FXML
    private Label st;

    @FXML
    private Label st3;

    @FXML
    private Label st4;

    @FXML
    private Label st2;

    @FXML
    private Label st1;

    @FXML
    private Label st5;

    @FXML
    private Button don;

    String customerNameSt;
    String PhoneNumberStr;
    String DateStr;
    String goodCountStr;
    String invoicesType;
    String itemType;
    ArrayList<String> list1 = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        final BooleanProperty firstTime = new SimpleBooleanProperty(true); // Variable to store the focus on stage load
        // TODO
        Desgin2.commonMethods.fullArrayList(list1, "supllier");
        isSelected(list1);
        ArrayList<String> list = Desgin2.connectionToDataBase.giveCategoriesType();
        ItemType.getItems().addAll(list);
        ItemType.setOnAction(e -> {
            itemType = (String) ItemType.getSelectionModel().getSelectedItem();
        });
        customerName.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue && firstTime.get()) {
                don.requestFocus(); // Delegate the focus to container
                firstTime.setValue(false); // Variable value changed for future references
            }
        });
        customerName.setOnMouseMoved((event) -> {
            customerNameSt = customerName.getText();
            if (list1.contains(customerNameSt)) {
                PhoneNumber.setText(Desgin2.connectionToDataBase.getSupplierPhone(customerNameSt));
            } else {
                PhoneNumber.setText("");
            }
        });

    }

    @FXML
    void clear(MouseEvent event) {
        if (event.getSource() == customerName) {
            st.setVisible(false);
        }
        if (event.getSource() == PhoneNumber) {
            st1.setVisible(false);
        }
        if (event.getSource() == goodCount) {
            st2.setVisible(false);
        }
        if (event.getSource() == Date) {
            st3.setVisible(false);
        }
        if (event.getSource() == ItemType) {
            st4.setVisible(false);
        }

    }

    public void isSelected(ArrayList<String> list) {
        TextFields.bindAutoCompletion(customerName, list);
    }

    @FXML
    void back(MouseEvent event) {
        Desgin2.commonMethods.InvoicesAdminPage();
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }

    @FXML
    void done(ActionEvent event) {
        setValue();
        if (check()) {
            Desgin2.connectionToDataBase.addInvoices(invoicesType, customerNameSt, itemType, DateStr, goodCountStr, PhoneNumberStr);
            int oldCount = Integer.parseInt(Desgin2.connectionToDataBase.CategoriesNameCountLess(itemType));
            Desgin2.connectionToDataBase.updateCategoriesCount(itemType, (oldCount + Integer.parseInt(goodCountStr)) + "");
            Desgin2.commonMethods.getNotification("you added invoices successfully", "Successfully", "correct");
            if (!list1.contains(customerNameSt)) {
                Desgin2.connectionToDataBase.addNewSupllier(customerNameSt, PhoneNumberStr, "", "");
            }
            emptyValue();
        } else {
            Desgin2.commonMethods.getNotification("Please Input All Requied Field", "Wrong", "error");
        }

    }

    @FXML
    void other(ActionEvent event) {

    }

    @FXML
    void print(ActionEvent event) {
        setValue();
        if (check()) {
            Desgin2.commonMethods.makeInvoices(goodCountStr, itemType, DateStr, PhoneNumberStr, customerNameSt, invoicesType);
        }
        Desgin2.commonMethods.getNotification("Please Input All Requied Field", "Wrong", "error");

    }

    @FXML
    void onlyNumber(KeyEvent event) {
        try {
            char ch = event.getCharacter().charAt(0);
            if (!Character.isDigit(ch)) {
                if (Character.isLetter(ch)) {
                    Desgin2.commonMethods.getNotification("Input only Number! ", "Wrong Input", "error");
                    // JOptionPane.showMessageDialog(null, "Please Input Number Only!!");
                }
                event.consume();
            }
        } catch (Exception e) {
            event.consume();
        }
    }

    public boolean check() {
        boolean check = true;
        if (customerNameSt.isEmpty()) {
            st.setVisible(true);
            check = false;
        }
        if (PhoneNumberStr.isEmpty()) {
            st1.setVisible(true);
            check = false;
        }
        if (DateStr == null) {
            st3.setVisible(true);
            check = false;
        }
        if (goodCountStr.isEmpty()) {
            st2.setVisible(true);
            check = false;
        } else {

        }
        if (invoicesType == null) {
            st5.setVisible(true);
            check = false;
        }
        if (itemType == null || itemType.equals("Item Type")) {
            st4.setVisible(true);
            check = false;

        }
        return check;
    }

    public void setValue() {
        customerNameSt = customerName.getText();
        PhoneNumberStr = PhoneNumber.getText();
        DateStr = (Date.getValue() == null) ? null : Date.getValue() + "";
        goodCountStr = goodCount.getText();
        invoicesType = imported.isSelected() ? "1" : sold.isSelected() ? "0" : null;

    }

    public void emptyValue() {
        customerName.setText("");
        PhoneNumber.setText("");
        Date.setValue(null);
        goodCount.setText("");
        imported.setSelected(true);
        sold.setSelected(false);
        ItemType.setValue("Item Type");
        PhoneNumber.setEditable(true);
    }

    @FXML
    void sel(ActionEvent event) {
        if (imported.isSelected()) {
            sold.setSelected(false);
            //importedisSelected();
        }/* else if (!imported.isSelected()) {
            //notImportedisSelected();
            sold.setSelected(true);
        }*/
    }

}
