package desgin2;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javax.swing.JFileChooser;
import org.controlsfx.control.textfield.TextFields;

public class admin_SuplliersController implements Initializable {

    @FXML
    private TextField searchSuppliers;

    @FXML
    private TableView<Suplliers> tabelToShowInformationOfSupllier;

    @FXML
    private ImageView imgSearch;

    @FXML
    private TextField employeeFirstName;

    @FXML
    private TextField employeePhoneNumber;

    @FXML
    private TextField emailEmployee;

    @FXML
    private ImageView photoOfEmployee;
    @FXML
    private Label st;

    @FXML
    private Label st1;

    @FXML
    private Label st2;

    @FXML
    private Button btn;

    final ObservableList<Suplliers> data = FXCollections.observableArrayList();
    final ObservableList<Suplliers> data1 = FXCollections.observableArrayList();
    JFileChooser fileChooser;
    String path = "img/images.png";
    boolean firstClick;
    String name;
    String phone;
    String email;
    int id;
    int selected;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ArrayList<String> list = new ArrayList<>();
        Desgin2.commonMethods.fullArrayList(list, "supllier");
        TextFields.bindAutoCompletion(searchSuppliers, list);

        TableColumn IDCoulmn = new TableColumn("ID");
        IDCoulmn.setMinWidth(20);
        IDCoulmn.setCellValueFactory(new PropertyValueFactory<>("ID"));

        TableColumn NameCoulmn = new TableColumn("Name");
        NameCoulmn.setMinWidth(70);
        NameCoulmn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn PhoneCoulmn = new TableColumn("Phone");
        PhoneCoulmn.setMinWidth(70);
        PhoneCoulmn.setCellValueFactory(new PropertyValueFactory<>("phone"));

        TableColumn EmailCoulmn = new TableColumn("Email");
        EmailCoulmn.setMinWidth(170);
        EmailCoulmn.setCellValueFactory(new PropertyValueFactory<>("email"));

        tabelToShowInformationOfSupllier.getColumns().addAll(IDCoulmn, NameCoulmn, PhoneCoulmn, EmailCoulmn);
        ResultSet giveSupplierInformation = Desgin2.connectionToDataBase.giveSllSuplierInformation();
        addDataToObservableList(giveSupplierInformation, data);
        tabelToShowInformationOfSupllier.setItems(data);
        tabelToShowInformationOfSupllier.setOnMouseClicked(e -> {

            ObservableList<Suplliers> productSelected;
            productSelected = tabelToShowInformationOfSupllier.getSelectionModel().getSelectedItems();
            int selectedIndex = tabelToShowInformationOfSupllier.getSelectionModel().getSelectedIndex();
            if (selectedIndex >= 0) {
                if (data.size() != 0) {
                    setValue(selectedIndex, data);
                } else if (!data1.isEmpty()) {
                    setValue(selectedIndex, data1);
                }
            }
            tabelToShowInformationOfSupllier.refresh();

        });
    }

    @FXML
    void Delete(ActionEvent event) {
        ObservableList<Suplliers> productSelected;
        productSelected = tabelToShowInformationOfSupllier.getSelectionModel().getSelectedItems();
        int selectedIndex = tabelToShowInformationOfSupllier.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            if (!data.isEmpty()) {
                Desgin2.connectionToDataBase.deleteSupplierWithId(Integer.parseInt(data.get(selectedIndex).getID()));
                data.removeAll(productSelected);
            } else if (!data1.isEmpty()) {
                Desgin2.connectionToDataBase.deleteSupplierWithId(Integer.parseInt(data1.get(selectedIndex).getID()));
                data1.removeAll(productSelected);
            }
            Desgin2.commonMethods.getNotification("Deleted Supplier Sucssefully", "Sucssefully", "correct");
            tabelToShowInformationOfSupllier.refresh();
            EmptyAllField();
        } else {
            Desgin2.commonMethods.getNotification("choose Somthing To Deleted", "Wrong", "error");
        }
    }

    @FXML
    void Edit(ActionEvent event) {
        int selectedIndex = tabelToShowInformationOfSupllier.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            if (!data.isEmpty()) {
                name = employeeFirstName.getText();
                phone = employeePhoneNumber.getText();
                email = emailEmployee.getText();
                selected = selectedIndex;
                id = Integer.parseInt(data.get(selectedIndex).getID());
                edit(data);
            } else if (!data1.isEmpty()) {
                name = employeeFirstName.getText();
                phone = employeePhoneNumber.getText();
                email = emailEmployee.getText();
                selected = selectedIndex;
                id = Integer.parseInt(data1.get(selectedIndex).getID());
                edit(data1);
            }
        }
    }

    public void edit(ObservableList<Suplliers> data) {
        boolean isChange = false;
        Image image = Desgin2.connectionToDataBase.getSupplierImage(id);
        Image newImage = photoOfEmployee.getImage();
        if (!name.equals(data.get(selected).getName())) {
            isChange = true;
            data.get(selected).setName(name);
            Desgin2.connectionToDataBase.changgeSupplierName(id, name);
        }
        if (!phone.equals(data.get(selected).getPhone())) {
            isChange = true;
            Desgin2.connectionToDataBase.changgeSupplierPhone(id, phone);
            data.get(selected).setPhone(phone);
        }
        if (!email.equals(data.get(selected).getEmail())) {
            if (Desgin2.commonMethods.isEmail(email)) {
                isChange = true;
                data.get(selected).setEmail(email);
                Desgin2.connectionToDataBase.changgeSupplierEmail(id, email);
            } else {
                Desgin2.commonMethods.getNotification("Your Email Is Wrong", "Wrong", "error");
            }
        }
        if (image.getWidth() != newImage.getWidth() || image.getHeight() != newImage.getHeight()) {
            isChange = true;
            Desgin2.connectionToDataBase.updateImagePath(id, path);
        }
        if (isChange) {
            Desgin2.commonMethods.getNotification("Your Changge is Updated", "Sucssefully", "correct");
            ArrayList<String> list = new ArrayList<>();
            Desgin2.commonMethods.fullArrayList(list, "supllier");
            TextFields.bindAutoCompletion(searchSuppliers, list);
        } else {
            Desgin2.commonMethods.getNotification("You Don't Changge any field", "Wrong", "error");
        }
        tabelToShowInformationOfSupllier.refresh();

    }

    @FXML
    void addSupppliers(ActionEvent event) {
        if (checkWhenAddOrEdit()) {
            boolean isemail = Desgin2.commonMethods.isEmail(email);
            if (!isemail) {
                Desgin2.commonMethods.getNotification("Your Email Is Wrong", "Wrong", "error");

            } else if (isemail) {
                boolean result = Desgin2.connectionToDataBase.addNewSupllier(name, phone, email, path);
                if (result) {
                    Desgin2.commonMethods.getNotification("Your Supplier Was Add\n"
                            + " You will Recive notification about your new Supplier!!", "Succsefully", "correct");
                    data.add(new Suplliers(data.size() + "", name, phone, email));
                }
                EmptyAllField();
                tabelToShowInformationOfSupllier.refresh();

            }
        } else {
            Desgin2.commonMethods.getNotification("Please Input All Requied Input..", "Wrong", "error");
        }

    }

    @FXML
    void back(MouseEvent event) {
        Desgin2.commonMethods.mainAdmin();
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }

    @FXML
    void search(MouseEvent event) {
        String name = searchSuppliers.getText();
        searchSuppliers.setText("");
        data1.clear();
        if (!name.isEmpty() && !firstClick) {
            ResultSet giveSupplierInformation = Desgin2.connectionToDataBase.giveSupllierInformation(name);
            for (int i = 0; i < tabelToShowInformationOfSupllier.getItems().size(); i++) {
                tabelToShowInformationOfSupllier.getItems().clear();
            }
            addDataToObservableList(giveSupplierInformation, data1);
            imgSearch.setImage(new Image("img/arrows.png"));
            tabelToShowInformationOfSupllier.setItems(data1);
            firstClick = true;
        } else if (firstClick) {
            firstClick = false;
            imgSearch.setImage(new Image("img/refreshTwo32.png"));
            ResultSet giveEmployeeInformation = Desgin2.connectionToDataBase.giveSllSuplierInformation();
            addDataToObservableList(giveEmployeeInformation, data);
            tabelToShowInformationOfSupllier.setItems(data);

        }
    }

    @FXML
    void sendMessage(ActionEvent event) {
        int selectedIndex = tabelToShowInformationOfSupllier.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            if (!data.isEmpty()) {
                email = data.get(selectedIndex).getEmail();
            } else if (!data1.isEmpty()) {
                email = data1.get(selectedIndex).getEmail();
            }
            SendMessageController.toEm = email;
            Desgin2.commonMethods.sendMessgeAdmin();
            selected = selectedIndex;

        }
    }

    @FXML
    void imageSupplier(MouseEvent event
    ) {
        fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            try {
                File file = fileChooser.getSelectedFile();
                path = file.getAbsolutePath();
                URL url = file.toURI().toURL();
                Image image = new Image(url.toExternalForm());
                photoOfEmployee.setImage(image);

            } catch (MalformedURLException ex) {
                Desgin2.commonMethods.getNotification("This Is Not Image", "Wrong", "error");
            }

        }
    }

    @FXML
    void numberOnly(KeyEvent event
    ) {

        try {
            char ch = event.getCharacter().charAt(0);
            if (!Character.isDigit(ch)) {
                if (Character.isLetter(ch)) {
                    //JOptionPane.showMessageDialog(null, "Please Input Number Only!!");
                    Desgin2.commonMethods.getNotification("Input Number Only", "Wrong", "error");
                }
                event.consume();

            }
            if (event.getSource() == employeePhoneNumber) {
                if (employeePhoneNumber.getText().length() >= 10) {
                    event.consume();
                    //JOptionPane.showMessageDialog(null, "Your Input must 9 digit or less!!");
                    Desgin2.commonMethods.getNotification("your Input must 9 digit or less!!", "Wrong", "error");

                }
            }
        } catch (Exception e) {
            event.consume();
        }

    }

    @FXML
    void removeStar(MouseEvent event
    ) {

        if (event.getSource() == employeeFirstName) {
            st.setVisible(false);
        } else if (event.getSource() == employeePhoneNumber) {
            st1.setVisible(false);
        } else if (event.getSource() == emailEmployee) {
            st2.setVisible(false);
        }
    }

    @FXML
    void clear(ActionEvent event
    ) {
        if (btn.getText().equals("Empty")) {
            EmptyAllField();

        }
    }

    public void addDataToObservableList(ResultSet resultSet, ObservableList<Suplliers> data) {
        try {
            while (resultSet.next()) {
                data.add(new Suplliers(resultSet.getString("ID"), resultSet.getString("NAME"),
                        resultSet.getString("PHONE"), resultSet.getString("EMAIL")));

            }
        } catch (SQLException ex) {
            Logger.getLogger(CommonMethods.class
                    .getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                resultSet.close();

            } catch (SQLException ex) {
                Logger.getLogger(admin_EmployeeController.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public boolean checkWhenAddOrEdit() {
        name = employeeFirstName.getText();
        phone = employeePhoneNumber.getText();
        email = emailEmployee.getText();
        Image img = photoOfEmployee.getImage();
        if (name.isEmpty()) {
            st.setVisible(true);
        }

        if (phone.isEmpty()) {
            st1.setVisible(true);
        }
        if (email.isEmpty()) {
            st2.setVisible(true);
        }
        return !(name.isEmpty() || phone.isEmpty() || email.isEmpty());

    }

    public void EmptyAllField() {
        employeeFirstName.setText("");
        emailEmployee.setText("");
        employeePhoneNumber.setText("");
//        photoOfEmployee.setImage(new Image(path));
    }

    public void setValue(int selectedIndex, ObservableList<Suplliers> data) {
        employeeFirstName.setText(data.get(selectedIndex).getName());
        employeePhoneNumber.setText(data.get(selectedIndex).getPhone());
        emailEmployee.setText(data.get(selectedIndex).getEmail());
        photoOfEmployee.setImage(Desgin2.connectionToDataBase.getSupplierImage(Integer.parseInt(data.get(selectedIndex).getID())));

    }

}
