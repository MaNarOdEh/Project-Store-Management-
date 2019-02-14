package desgin2;

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
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import org.controlsfx.control.textfield.TextFields;

public class IntroducingTheSalceController implements Initializable {

    @FXML
    private ComboBox<String> ItemType;

    @FXML
    private DatePicker date;

    @FXML
    private TextField count;

    @FXML
    private TextField name;

    @FXML
    private TableView<Invoices> tabelSalce;
    final ObservableList<Invoices> data = FXCollections.observableArrayList();
    String selectedType;
    String nameEmployee;
    int countS;
    String countStr;
    String dateStr;
    String phone;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ArrayList<String> list = Desgin2.connectionToDataBase.giveCategoriesType();
        ItemType.getItems().addAll(list);
        ItemType.setOnAction(e -> {
            selectedType = (String) ItemType.getSelectionModel().getSelectedItem();

        });
        if (!Desgin2.commonMethods.isEmail(Desgin2.nameOrEmail)) {
            name.setText(Desgin2.nameOrEmail);
        } else {
            name.setText(Desgin2.connectionToDataBase.getEmployeeName(Desgin2.nameOrEmail));
        }

        ArrayList<String> list1 = new ArrayList<>();

        Desgin2.commonMethods.fullArrayList(list1,
                "NameEmployee");
        TextFields.bindAutoCompletion(name, list1);
        TableColumn IDCoulmn = new TableColumn("ID");

        IDCoulmn.setMinWidth(
                20);
        IDCoulmn.setCellValueFactory(
                new PropertyValueFactory<>("ID"));

        TableColumn NameCoulmn = new TableColumn("Name Employee");

        NameCoulmn.setMinWidth(
                70);
        NameCoulmn.setCellValueFactory(
                new PropertyValueFactory<>("nameCustomer"));

        TableColumn TypeCoulmn = new TableColumn("Type Name");

        TypeCoulmn.setMinWidth(
                70);
        TypeCoulmn.setCellValueFactory(
                new PropertyValueFactory<>("nameType"));

        TableColumn DateCoulmn = new TableColumn("Date");

        DateCoulmn.setMinWidth(
                70);
        DateCoulmn.setCellValueFactory(
                new PropertyValueFactory<>("date"));

        TableColumn QuntityCoulmn = new TableColumn("quntity");

        QuntityCoulmn.setMinWidth(
                60);
        QuntityCoulmn.setCellValueFactory(
                new PropertyValueFactory<>("quntity"));

        TableColumn PriceCoulmn = new TableColumn("price");

        PriceCoulmn.setMinWidth(
                50);
        PriceCoulmn.setCellValueFactory(
                new PropertyValueFactory<>("price"));

        TableColumn PhoneCoulmn = new TableColumn("Phone");

        PhoneCoulmn.setMinWidth(
                70);
        PhoneCoulmn.setCellValueFactory(
                new PropertyValueFactory<>("phoneOfSupllier"));

        tabelSalce.getColumns()
                .addAll(IDCoulmn, NameCoulmn, PhoneCoulmn, TypeCoulmn, QuntityCoulmn, PriceCoulmn, DateCoulmn);
        ResultSet giveAllInvoicesInformation = Desgin2.connectionToDataBase.giveAllSalceInformation();

        addDataInvoicesToObservableList(giveAllInvoicesInformation, data);

        tabelSalce.setItems(data);
    }

    @FXML
    void back(MouseEvent event) {
        Desgin2.commonMethods.mainEmployee();
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }

    public void addDataInvoicesToObservableList(ResultSet resultSet, ObservableList<Invoices> data) {
        try {
            while (resultSet.next()) {
                String Price = (Integer.parseInt(resultSet.getString("COUNT"))
                        * Desgin2.connectionToDataBase.giveCategoriesPrice(resultSet.getString("NAMETYPE"))) + "";

                data.add(new Invoices(
                        resultSet.getString("ID"),
                        resultSet.getString("EMPLOYNAME"),
                        resultSet.getString("NAMETYPE"),
                        resultSet.getString("DATE"),
                        resultSet.getString("COUNT"),
                        Price,
                        resultSet.getString("PHONENUMBEROFEMPLOYEE")
                ));

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

    @FXML
    void deleteSelected(ActionEvent event) {
        ObservableList<Invoices> productSelected;
        productSelected = tabelSalce.getSelectionModel().getSelectedItems();
        int selectedIndex = tabelSalce.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            Desgin2.connectionToDataBase.deleteSalceWithId(Integer.parseInt(data.get(selectedIndex).getID()));
            int price = Integer.parseInt(data.get(selectedIndex).getPrice());
            double oldmoney = Double.parseDouble(Desgin2.connectionToDataBase.getAdminMoney());
            Desgin2.connectionToDataBase.updateAdminMoney((oldmoney - price) + "");
            data.removeAll(productSelected);
            tabelSalce.refresh();
        } else {
            Desgin2.commonMethods.getNotification("select salce to delete!!", "Wrong", "error");

        }
    }

    @FXML
    void done(ActionEvent event) {
        setValue();
        if (check()) {
            if (checkValue()) {
                Desgin2.connectionToDataBase.addSalce(nameEmployee, selectedType, dateStr, countStr, Desgin2.connectionToDataBase.getEmployeePhone(nameEmployee));
                Desgin2.connectionToDataBase.updateCategoriesCount(selectedType, (countS - Integer.parseInt(countStr) + ""));
                String Price = (Integer.parseInt(countStr)
                        * Desgin2.connectionToDataBase.giveCategoriesPrice(selectedType)) + "";

                double oldmoney = Double.parseDouble(Desgin2.connectionToDataBase.getAdminMoney());
                Desgin2.connectionToDataBase.updateAdminMoney((oldmoney + Price) + "");

                data.add(new Invoices(data.size() + "", nameEmployee, selectedType, dateStr, countStr, Price, phone));
                tabelSalce.refresh();
                Desgin2.commonMethods.getNotification("You Salce Was Added!!", "Scussefully", "correct");

            }
        } else {
            Desgin2.commonMethods.getNotification("Input All Requied Field", "Wrong", "error");

        }

    }

    public void setValue() {
        nameEmployee = name.getText();
        countStr = count.getText();
        dateStr = date.getValue() == null ? null : date.getValue() + "";
    }

    public boolean check() {
        boolean check = true;
        if (nameEmployee.isEmpty()) {
            check = false;
        } else if (countStr.isEmpty()) {
            check = false;
        } else if (dateStr == null) {
            check = false;
        } else if (ItemType == null) {
            check = false;
        }
        return check;
    }

    public boolean checkValue() {

        boolean isCorrectValue = true;
        if (!Desgin2.connectionToDataBase.isEmployeeNameFound(nameEmployee)) {
            Desgin2.commonMethods.getNotification("The Name Of employee Not Found!", "Wrong", "error");
            isCorrectValue = false;
        } else {
            phone = Desgin2.connectionToDataBase.getEmployeePhone(nameEmployee);
        }
        countS = Integer.parseInt(Desgin2.connectionToDataBase.CategoriesNameCountLess(selectedType));
        int countst = Integer.parseInt(countStr);
        if (countst > countS) {
            Desgin2.commonMethods.getNotification("Your type " + selectedType + " have count equal" + countS + "  \n That is less that you input"
                    + "you can tell your admin about that \n by sending him a letter", "Wrong", "error");

            isCorrectValue = false;
        }
        return isCorrectValue;
    }

    @FXML
    void makeInvoices(ActionEvent event) {

        int selectedIndex = tabelSalce.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            if (!data.isEmpty()) {
                Desgin2.connectionToDataBase.addInvoices("0", data.get(selectedIndex).getNameCustomer(),
                        data.get(selectedIndex).getNameType(),
                        data.get(selectedIndex).getDate(),
                        data.get(selectedIndex).getQuntity(), data.get(selectedIndex).getPhoneOfSupllier());
                int oldCount = Integer.parseInt(Desgin2.connectionToDataBase.CategoriesNameCountLess(data.get(selectedIndex).getNameType()));
                Desgin2.connectionToDataBase.updateCategoriesCount(data.get(selectedIndex).getNameType(), (oldCount + Integer.parseInt(data.get(selectedIndex).getQuntity())) + "");
                Desgin2.commonMethods.getNotification("You add invoices succesfully!", "Succesfully", "correct");
            }
        } else {
            Desgin2.commonMethods.getNotification("Choose Salce To Make Invoices", "Wrong", "error");

        }
    }

    @FXML
    void print(ActionEvent event) {
        in();
    }

    public void in() {
        Desgin2.commonMethods.makeInvoices(countStr, selectedType, dateStr, phone, nameEmployee, "Sold");
    }

    @FXML
    void onlyNumber(KeyEvent event) {
        try {
            char ch = event.getCharacter().charAt(0);
            if (!Character.isDigit(ch)) {
                if (Character.isLetter(ch)) {
                    Desgin2.commonMethods.getNotification("Please Input Number Only", "Wrong", "error");
                }
                event.consume();

            }
        } catch (Exception e) {
            event.consume();
        }
    }

}
