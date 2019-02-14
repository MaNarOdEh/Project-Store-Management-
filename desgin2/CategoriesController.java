package desgin2;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
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
import javafx.scene.control.ColorPicker;
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

public class CategoriesController implements Initializable {

    @FXML
    private TableView<Categories> tabelShowCategories;

    @FXML
    private ImageView imagesearch;

    @FXML
    private TextField nameCategories;

    @FXML
    private TextField priceCategories;

    @FXML
    private TextField typeCategories;

    @FXML
    private Label color;

    @FXML
    private ColorPicker colorChooser;

    @FXML
    private TextField countCategories;

    @FXML
    private ImageView photoOfEmployee;

    @FXML

    private TextField searchCategories;
    @FXML
    private JFXCheckBox male;

    @FXML
    private JFXCheckBox female;

    final ObservableList<Categories> data = FXCollections.observableArrayList();
    final ObservableList<Categories> data1 = FXCollections.observableArrayList();

    JFileChooser fileChooser;
    @FXML
    private Label st;

    @FXML
    private Label st1;

    @FXML
    private Label st2;

    @FXML
    private Label st3;
    @FXML
    private JFXButton ed;
    @FXML
    private JFXButton de;
    @FXML
    private JFXButton ad;

    String path = "img/img.jpg";
    String nameOfCategories;
    String typeOfCategories;
    String priceOfCategories;
    String colorOfCategories;
    String genderOfCategories;
    String countOfCategories;
    boolean firstClick;

    int id;
    int selected;
    ArrayList<String> list = new ArrayList<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (!Desgin2.isAdmin) {
            ad.setVisible(false);
            ed.setVisible(false);

        }
        Desgin2.commonMethods.fullArrayList(list, "categories");
        TextFields.bindAutoCompletion(searchCategories, list);

        TableColumn IDCoulmn = new TableColumn("ID");
        IDCoulmn.setMinWidth(20);
        IDCoulmn.setCellValueFactory(new PropertyValueFactory<>("ID"));

        TableColumn NameCoulmn = new TableColumn("Name");
        NameCoulmn.setMinWidth(70);
        NameCoulmn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn TypeCoulmn = new TableColumn("Type");
        TypeCoulmn.setMinWidth(70);
        TypeCoulmn.setCellValueFactory(new PropertyValueFactory<>("Type"));

        TableColumn PriceCoulmn = new TableColumn("Price");
        PriceCoulmn.setMinWidth(40);
        PriceCoulmn.setCellValueFactory(new PropertyValueFactory<>("price"));

        TableColumn CountCoulmn = new TableColumn("Count");
        CountCoulmn.setMinWidth(40);
        CountCoulmn.setCellValueFactory(new PropertyValueFactory<>("count"));

        TableColumn ColorCoulmn = new TableColumn("color");
        ColorCoulmn.setMinWidth(40);
        ColorCoulmn.setCellValueFactory(new PropertyValueFactory<>("color"));

        TableColumn GenderCoulmn = new TableColumn("Gender");
        GenderCoulmn.setMinWidth(40);
        GenderCoulmn.setCellValueFactory(new PropertyValueFactory<>("gender"));

        tabelShowCategories.getColumns().addAll(IDCoulmn, NameCoulmn, TypeCoulmn, PriceCoulmn, CountCoulmn, ColorCoulmn, GenderCoulmn);
        ResultSet giveSupplierInformation = null;
        if (!Desgin2.isAdmin) {
            giveSupplierInformation = Desgin2.connectionToDataBase.giveAllCategoriesInformation();
        } else {
            giveSupplierInformation = Desgin2.connectionToDataBase.giveAllCategoriesInformationForAdmin();
        }
        addDataToObservableList(giveSupplierInformation, data, false);
        tabelShowCategories.setItems(data);

        tabelShowCategories.setOnMouseClicked(e -> {

            ObservableList<Categories> productSelected;
            productSelected = tabelShowCategories.getSelectionModel().getSelectedItems();
            int selectedIndex = tabelShowCategories.getSelectionModel().getSelectedIndex();
            if (selectedIndex >= 0) {
                if (!data.isEmpty()) {
                    setValue(selectedIndex, data);
                } else if (!data1.isEmpty()) {
                    setValue(selectedIndex, data1);
                }
            }
            tabelShowCategories.refresh();

        });

    }

    public void setValue() {
        nameOfCategories = nameCategories.getText();
        typeOfCategories = typeCategories.getText();
        priceOfCategories = priceCategories.getText();
        colorOfCategories = colorChooser.getValue() + "";
        countOfCategories = countCategories.getText();
        genderOfCategories = male.isSelected() ? "male" : female.isSelected() ? "female" : "both";

    }

    @FXML
    void addNew(ActionEvent event) {
        setValue();
        if (checkWhenAddOrEdit()) {
            if (Desgin2.connectionToDataBase.isCategoriesTypeFound(typeOfCategories)) {
                Desgin2.commonMethods.getNotification("This good is Found!", "Wrong", "error");

            } else {
                boolean result = Desgin2.connectionToDataBase.addNewCategories(nameOfCategories,
                        typeOfCategories, priceOfCategories, countOfCategories, colorOfCategories, genderOfCategories, path);
                if (result) {
                    list.add(nameOfCategories);
                    TextFields.bindAutoCompletion(searchCategories, list);
                    data.add(new Categories(data.size() + "", nameOfCategories, typeOfCategories, priceOfCategories, countOfCategories, colorOfCategories, genderOfCategories));
                    tabelShowCategories.refresh();
                    Double newValue = (Double.parseDouble(Desgin2.connectionToDataBase.getAdminMoney())
                            - Desgin2.connectionToDataBase.giveCategoriesPrice(typeOfCategories));
                    Desgin2.connectionToDataBase.updateAdminMoney(newValue + "");
                    Desgin2.connectionToDataBase.changgeTheNotificationOfEmployee("You Admin Add New Categories that name is: " + nameOfCategories
                            + "and count if it is " + countOfCategories + "^^", "all", true);
                    Desgin2.commonMethods.getNotification("Sucssefully Add New Categories!!", "Wrong", "correct");
                }
                empty();

            }
        } else {
            Desgin2.commonMethods.getNotification("Input All Reguied Field!!", "Wrong", "error");
        }

    }

    @FXML
    void deleteCategories(ActionEvent event) {
        ObservableList<Categories> productSelected;
        productSelected = tabelShowCategories.getSelectionModel().getSelectedItems();
        int selectedIndex = tabelShowCategories.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            String type = "";
            int id = 0;
            if (!data.isEmpty()) {
                type = data.get(selectedIndex).getType();
                id = Integer.parseInt(data.get(selectedIndex).getID());
                data.removeAll(productSelected);
            } else if (!data1.isEmpty()) {
                type = data1.get(selectedIndex).getType();
                id = Integer.parseInt(data1.get(selectedIndex).getID());
                data1.removeAll(productSelected);
            }
            Desgin2.connectionToDataBase.deleteCategoriesWithId(id, "1");
            /*Double newValue = (Double.parseDouble(Desgin2.connectionToDataBase.getAdminMoney())
                    - Desgin2.connectionToDataBase.giveCategoriesPrice(type));
            Desgin2.connectionToDataBase.updateAdminMoney(newValue + "");*/
            list.remove(nameOfCategories);
            TextFields.bindAutoCompletion(searchCategories, list);
            tabelShowCategories.refresh();
            Desgin2.commonMethods.getNotification("Your Catgories deleted Sucssefuuly You can restore it by visit review Owner!", "Sucssefully", "corrects");
            Desgin2.connectionToDataBase.changgeTheNotificationOfAdmin("deleted categories with Name: " + type + " You can restoe it By going To viewOwner^^", true);
        } else {
            Desgin2.commonMethods.getNotification("Please Choose Categories To deleted ", "Wrong", "error");
        }
    }

    @FXML
    void editCategories(ActionEvent event) {
        int selectedIndex = tabelShowCategories.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            String nameOfCategories1 = nameCategories.getText();
            String typeOfCategories1 = typeCategories.getText();
            String priceOfCategories1 = priceCategories.getText();
            String colorOfCategories1 = colorChooser.getValue() + "";
            String countOfCategories1 = countCategories.getText();
            String genderOfCategories1 = male.isSelected() ? "male" : "female";
            boolean isChange = false;
            if (!nameOfCategories.equals(nameOfCategories1)) {
                list.remove(nameOfCategories);
                list.add(nameOfCategories1);
                TextFields.bindAutoCompletion(searchCategories, list);
                Desgin2.connectionToDataBase.updateCategoriesName(id, nameOfCategories1);
                isChange = true;
            }
            if (!typeOfCategories.equals(typeOfCategories1)) {
                Desgin2.connectionToDataBase.updateCategoriesType(id, typeOfCategories1);
                isChange = true;
            }
            if (!priceOfCategories.equals(priceOfCategories1)) {
                Desgin2.connectionToDataBase.updateCategoriesPrice(id, priceOfCategories1);
                isChange = true;
            }
            if (!colorOfCategories.equals(colorOfCategories1)) {
                Desgin2.connectionToDataBase.updateCategoriesColor(id, colorOfCategories1);
                isChange = true;
            }
            if (!countOfCategories.equals(countOfCategories1)) {
                Desgin2.connectionToDataBase.updateCategoriesCount(id, countOfCategories1);
                isChange = true;
            }
            if (!genderOfCategories.equals(genderOfCategories1)) {
                Desgin2.connectionToDataBase.updateCategoriesGender(id, genderOfCategories1);
                isChange = true;
            }
            if (isChange) {
                Desgin2.commonMethods.getNotification("You Change Was Saved", "Sucssefully", "correct");
                Categories c;
                if (!data.isEmpty()) {
                    c = data.get(selectedIndex);
                    c.setColor(colorOfCategories1);
                    c.setCount(countOfCategories1);
                    c.setName(nameOfCategories1);
                    c.setGender(genderOfCategories1);
                    c.setPrice(priceOfCategories1);
                    c.setType(priceOfCategories1);
                    data.remove(data.get(selectedIndex));
                    data.add(c);
                } else {
                    c = data1.get(selectedIndex);
                    c.setColor(colorOfCategories1);
                    c.setCount(countOfCategories1);
                    c.setName(nameOfCategories1);
                    c.setGender(genderOfCategories1);
                    c.setPrice(priceOfCategories1);
                    c.setType(priceOfCategories1);
                    data1.remove(data1.get(selectedIndex));
                    data1.add(c);
                }

            } else if (!isChange) {
                Desgin2.commonMethods.getNotification("change something to save", "Wrong", "error");

            }
        } else {
            Desgin2.commonMethods.getNotification("Please Choose Categories To Edit ", "Wrong", "error");
        }
    }

    @FXML
    void emptyField(ActionEvent event) {
        empty();
    }

    @FXML
    void imageSupplier(MouseEvent event) {

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
                Desgin2.commonMethods.getNotification("Thid Is Not Image!", "Wrong", "error");
            }

        }

    }

    @FXML
    void removeStar(MouseEvent event
    ) {

        if (event.getSource() == nameCategories) {
            st.setVisible(false);
        } else if (event.getSource() == typeCategories) {
            st1.setVisible(false);
        } else if (event.getSource() == priceCategories) {
            st2.setVisible(false);
        } else if (event.getSource() == countCategories) {
            st3.setVisible(false);
        }
    }

    @FXML
    void onlyNumber(KeyEvent event) {
        try {
            char ch = event.getCharacter().charAt(0);
            if (!Character.isDigit(ch)) {
                if (Character.isLetter(ch)) {
                    Desgin2.commonMethods.getNotification("Please Input Number Only!", "Wrong", "error");
                }
                event.consume();
            }
        } catch (Exception e) {
            event.consume();
        }
    }

    @FXML
    void sel(ActionEvent event) {
        if (male.isSelected()) {
            female.setDisable(true);
        } else if (female.isSelected()) {
            male.setDisable(true);
        } else {
            female.setDisable(false);
            male.setDisable(false);
        }

    }

    public void addDataToObservableList(ResultSet resultSet, ObservableList<Categories> data, boolean bool) {
        try {
            while (resultSet.next()) {
                if (bool && (resultSet.getString("ISDELETED")+"").equals("0")) {
                    data.add(new Categories(resultSet.getString("ID"), resultSet.getString("NAME"),
                            resultSet.getString("TYPE"), resultSet.getString("PRICE"), resultSet.getString("COUNT"), resultSet.getString("COLOR"),
                            resultSet.getString("GENDER")));
                } else if(!bool) {
                    data.add(new Categories(resultSet.getString("ID"), resultSet.getString("NAME"),
                            resultSet.getString("TYPE"), resultSet.getString("PRICE"), resultSet.getString("COUNT"), resultSet.getString("COLOR"),
                            resultSet.getString("GENDER")));
                }

            }
        } catch (SQLException ex) {
            Logger.getLogger(CommonMethods.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                resultSet.close();

            } catch (SQLException ex) {
                Logger.getLogger(admin_EmployeeController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void setValue(int selectedIndex, final ObservableList<Categories> data) {
        id = Integer.parseInt(data.get(selectedIndex).getID());
        nameOfCategories = data.get(selectedIndex).getName();
        typeOfCategories = data.get(selectedIndex).getType();
        priceOfCategories = data.get(selectedIndex).getPrice();
        colorOfCategories = data.get(selectedIndex).getColor() + "";
        countOfCategories = data.get(selectedIndex).getCount();
        genderOfCategories = data.get(selectedIndex).getGender();
        color.setText(colorOfCategories);
        nameCategories.setText(nameOfCategories);
        typeCategories.setText(typeOfCategories);
        priceCategories.setText(priceOfCategories);

        if (genderOfCategories.equals("female")) {
            female.setSelected(true);
            male.setSelected(false);

        } else if (genderOfCategories.equals("both")) {
            male.setSelected(true);
            female.setSelected(true);

        } else if (genderOfCategories.equals("male")) {
            female.setSelected(false);
            male.setSelected(true);
        }
        countCategories.setText(countOfCategories);
        photoOfEmployee.setImage(Desgin2.connectionToDataBase.getCaategoriesImage(Integer.parseInt(data.get(selectedIndex).getID())));

    }

    public boolean checkWhenAddOrEdit() {
        Image img = photoOfEmployee.getImage();
        if (nameOfCategories != null && nameOfCategories.isEmpty()) {
            st.setVisible(true);
        }
        if (typeOfCategories != null && typeOfCategories.isEmpty()) {
            st1.setVisible(true);
        }
        if (priceOfCategories != null && priceOfCategories.isEmpty()) {
            st2.setVisible(true);
        }

        if (!male.isSelected() && !female.isSelected() || (male.isSelected() && female.isSelected())) {
            genderOfCategories = "both";
        }
        if (countOfCategories != null && countOfCategories.isEmpty()) {
            st3.setVisible(true);
        }

        return !(nameOfCategories.isEmpty() || typeOfCategories.isEmpty() || priceOfCategories.isEmpty() || countOfCategories.isEmpty());
    }

    public void empty() {
        nameCategories.setText("");
        typeCategories.setText("");
        priceCategories.setText("");
        countCategories.setText("");
    }

    @FXML
    void back(MouseEvent event) {
        if (Desgin2.isAdmin) {
            Desgin2.commonMethods.mainAdmin();
        } else {
            Desgin2.commonMethods.mainEmployee();
        }
        ((Node) (event.getSource())).getScene().getWindow().hide();

    }

    @FXML
    void searchbutton(MouseEvent event) {
        String name = searchCategories.getText();
        searchCategories.setText("");
        data1.clear();
        if (!name.isEmpty() && !firstClick) {
            ResultSet giveCategoriesNameInformation = Desgin2.connectionToDataBase.giveCategoriesInformation(name);
            for (int i = 0; i < tabelShowCategories.getItems().size(); i++) {
                tabelShowCategories.getItems().clear();
            }
            addDataToObservableList(giveCategoriesNameInformation, data1, true);
            imagesearch.setImage(new Image("img/arrows.png"));
            tabelShowCategories.setItems(data1);
            firstClick = true;
        } else if (firstClick) {
            firstClick = false;
            imagesearch.setImage(new Image("img/refreshTwo32.png"));
            ResultSet giveEmployeeInformation = Desgin2.connectionToDataBase.giveAllCategoriesInformation();
            addDataToObservableList(giveEmployeeInformation, data, false);
            tabelShowCategories.setItems(data);

        }
    }

}
