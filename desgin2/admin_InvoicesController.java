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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import org.controlsfx.control.textfield.TextFields;

public class admin_InvoicesController implements Initializable {
    @FXML
    private TableView<Invoices> tableViewInvoices;

    @FXML
    private ImageView imageSearch;

    @FXML
    private TextField textSearch;

    @FXML
    private ComboBox<String> possibilitySearch;
    private ImageView nextimage;   
    final ObservableList<Invoices> data = FXCollections.observableArrayList();
    String selected = "";
    boolean firstClick;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ArrayList<String> listDate = new ArrayList<>();
        Desgin2.commonMethods.fullArrayList(listDate, "Date");
        ArrayList<String> listID = new ArrayList<>();
        ArrayList<String> listNameCustomer = new ArrayList<>();
        ArrayList<String> listTypeName = new ArrayList<>();
        Desgin2.commonMethods.fullArrayList(listTypeName, "Type Name");
        ArrayList<String> listNameEmployee = new ArrayList<>();
        if (Desgin2.isAdmin) {
            selected = "NameCustomer";
            Desgin2.commonMethods.fullArrayList(listID, "ID");
            Desgin2.commonMethods.fullArrayList(listNameCustomer, "NameCustomer");
            textSearch.setPromptText("NameCustomer");
            setCompleteTextField(listNameCustomer);
            possibilitySearch.getItems().addAll("Date", "ID", "NameCustomer", "Type Invoices", "Type Name");
        } else {
            selected = "NameEmployee";
            Desgin2.commonMethods.fullArrayList(listNameEmployee, "NameEmployee");
            textSearch.setPromptText("NameEmployee");
            setCompleteTextField(listNameEmployee);
            possibilitySearch.getItems().addAll("Date", "NameEmployee", "Type Name");
        }

        possibilitySearch.setOnAction(e -> {
            selected = (String) possibilitySearch.getSelectionModel().getSelectedItem();
            possibilitySearch.setPromptText(selected);
            textSearch.setPromptText(selected);

            switch (selected) {
                case "Type Invoices":
                    TextFields.bindAutoCompletion(textSearch, "Imported", "Sold");
                    break;
                case "Date":
                    setCompleteTextField(listDate);
                    break;
                case "NameCustomer":
                    setCompleteTextField(listNameCustomer);
                    break;
                case "Type Name":
                    setCompleteTextField(listTypeName);
                    break;
                case "ID":
                    setCompleteTextField(listID);
                    break;
                case "NameEmployee":
                    setCompleteTextField(listNameEmployee);
                    break;
                default:
                    break;
            }

        });

        TableColumn IDCoulmn = new TableColumn("ID");
        IDCoulmn.setMinWidth(20);
        IDCoulmn.setCellValueFactory(new PropertyValueFactory<>("ID"));

        TableColumn TypeInvoicesCoulmn = new TableColumn("Type Invoices");
        TypeInvoicesCoulmn.setMinWidth(20);
        TypeInvoicesCoulmn.setCellValueFactory(new PropertyValueFactory<>("typeInvoices"));

        TableColumn NameCoulmn = new TableColumn(!Desgin2.isAdmin ? "Name Employee" : "Name Customer");
        NameCoulmn.setMinWidth(70);
        NameCoulmn.setCellValueFactory(new PropertyValueFactory<>("nameCustomer"));

        TableColumn TypeCoulmn = new TableColumn("TypeName");
        TypeCoulmn.setMinWidth(70);
        TypeCoulmn.setCellValueFactory(new PropertyValueFactory<>("nameType"));

        TableColumn DateCoulmn = new TableColumn("Date");
        DateCoulmn.setMinWidth(70);
        DateCoulmn.setCellValueFactory(new PropertyValueFactory<>("date"));

        TableColumn QuntityCoulmn = new TableColumn("quntity");
        QuntityCoulmn.setMinWidth(60);
        QuntityCoulmn.setCellValueFactory(new PropertyValueFactory<>("quntity"));

        TableColumn PriceCoulmn = new TableColumn("price");
        PriceCoulmn.setMinWidth(50);
        PriceCoulmn.setCellValueFactory(new PropertyValueFactory<>("price"));

        TableColumn PhoneCoulmn = new TableColumn("Phone");
        PhoneCoulmn.setMinWidth(70);
        PhoneCoulmn.setCellValueFactory(new PropertyValueFactory<>("phoneOfSupllier"));

        tableViewInvoices.getColumns().addAll(IDCoulmn, TypeInvoicesCoulmn, NameCoulmn, TypeCoulmn, DateCoulmn, QuntityCoulmn, PriceCoulmn, PhoneCoulmn);
        ResultSet giveAllInvoicesInformation = Desgin2.connectionToDataBase.giveAllInvoicesInformation();
        addDataInvoicesToObservableList(giveAllInvoicesInformation, data);
        tableViewInvoices.setItems(data);

        tableViewInvoices.setOnMouseClicked(e -> {
            ObservableList<Invoices> productSelected;
            productSelected = tableViewInvoices.getSelectionModel().getSelectedItems();
            int selectedIndex = tableViewInvoices.getSelectionModel().getSelectedIndex();
            if (selectedIndex >= 0) {
                setValue(selectedIndex);
            }
            tableViewInvoices.refresh();

        });
    }

    public void setCompleteTextField(ArrayList<String> list) {
        TextFields.bindAutoCompletion(textSearch, list);
    }

    @FXML
    void ModeifiedSelected(ActionEvent event) {

    }

    @FXML
    void PrintSelected(ActionEvent event) {
        int selectedIndex = tableViewInvoices.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            Desgin2.commonMethods.makeInvoices(data.get(selectedIndex).getQuntity(),
                    data.get(selectedIndex).getNameType(), data.get(selectedIndex).getDate(), data.get(selectedIndex).getPhoneOfSupllier(),
                    data.get(selectedIndex).getNameCustomer(), data.get(selectedIndex).getTypeInvoices());

            tableViewInvoices.refresh();
        }

    }

    @FXML
    void back(MouseEvent event) {
        if (Desgin2.isAdmin) {
            Desgin2.commonMethods.mainAdmin();
            ((Node) (event.getSource())).getScene().getWindow().hide();
        } else {
            Desgin2.commonMethods.mainEmployee();
            ((Node) (event.getSource())).getScene().getWindow().hide();

        }
    }

    @FXML
    void deleteSelected(ActionEvent event) {
        ObservableList<Invoices> productSelected;
        productSelected = tableViewInvoices.getSelectionModel().getSelectedItems();
        int selectedIndex = tableViewInvoices.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            Desgin2.connectionToDataBase.deleteInvoicesWithId(Integer.parseInt(data.get(selectedIndex).getID()));
            data.removeAll(productSelected);
            tableViewInvoices.refresh();
        } else {
            Desgin2.commonMethods.getNotification("Select Thing To Delete","Wrong","error");
           // JOptionPane.showMessageDialog(null, "select thing to delete!!");
        }

    }

    @FXML
    void next(MouseEvent event) {
        if (Desgin2.isAdmin) {
            Desgin2.commonMethods.goToTheInoicesSecondPage();
            ((Node) (event.getSource())).getScene().getWindow().hide();
        }

    }

    @FXML
    void search(MouseEvent event) {
        String name = textSearch.getText();
        System.out.println(name);
        final ObservableList<Invoices> data1 = FXCollections.observableArrayList();

        if (!name.isEmpty() && !firstClick) {
            ResultSet giveSupplierInformation = giveresultSet(name);
            for (int i = 0; i < tableViewInvoices.getItems().size(); i++) {
                tableViewInvoices.getItems().clear();
            }
            addDataInvoicesToObservableList(giveSupplierInformation, data1);
            imageSearch.setImage(new Image("img/arrows.png"));
            tableViewInvoices.setItems(data1);
            textSearch.setText("");
            firstClick = true;
        } else if (firstClick) {
            firstClick = false;
            imageSearch.setImage(new Image("img/refreshTwo32.png"));
            ResultSet giveEmployeeInformation = Desgin2.connectionToDataBase.giveAllInvoicesInformation();
            addDataInvoicesToObservableList(giveEmployeeInformation, data);
            tableViewInvoices.setItems(data);
        }
    }

    public ResultSet giveresultSet(String name) {
        ResultSet resultSet = null;
        if (selected.equals("NameCustomer")) {
            resultSet = Desgin2.connectionToDataBase.giveInvoicesrInformation(name);
        } else if (selected.equals("Date")) {
            resultSet = Desgin2.connectionToDataBase.giveInvoicesInformation(name);
        } else if (selected.equals("ID")) {
            resultSet = Desgin2.connectionToDataBase.giveInvoicesInformationWithId(name);
        } else if (selected.equals("Type Invoices")) {
            resultSet = Desgin2.connectionToDataBase.giveInvoicesInformationFromTypeInvoices(name);

        } else if (selected.equals("Type Name")) {
            resultSet = Desgin2.connectionToDataBase.giveInvoicesInformationFromTypeName(name);
        }
        return resultSet;

    }

    public void addDataInvoicesToObservableList(ResultSet resultSet, ObservableList<Invoices> data) {
        try {
            if (resultSet == null) {
                return;
            }
            while (resultSet.next()) {
                if (!Desgin2.isAdmin) {
                    if (resultSet.getString("TYPEINVOICES").equals("0")) {
                        String Price = (Integer.parseInt(resultSet.getString("QUNTITY"))
                                * Desgin2.connectionToDataBase.giveCategoriesPrice(resultSet.getString("NAMETYPE"))) + "";

                        data.add(new Invoices(resultSet.getString("ID"),
                                resultSet.getString("NAMECUSTOMER"),
                                resultSet.getString("NAMETYPE"),
                                resultSet.getString("DATE"),
                                resultSet.getString("QUNTITY"),
                                Price,
                                resultSet.getString("PHONEOFSUPLLIER"),
                                "Sold"));

                    }
                } else {

                    String Price = (Integer.parseInt(resultSet.getString("QUNTITY"))
                            * Desgin2.connectionToDataBase.giveCategoriesPrice(resultSet.getString("NAMETYPE"))) + "";

                    data.add(new Invoices(resultSet.getString("ID"),
                            resultSet.getString("NAMECUSTOMER"),
                            resultSet.getString("NAMETYPE"),
                            resultSet.getString("DATE"),
                            resultSet.getString("QUNTITY"),
                            Price,
                            resultSet.getString("PHONEOFSUPLLIER"),
                            resultSet.getString("TYPEINVOICES").equals("0") ? "Sold" : "Imported"));

                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(CommonMethods.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }

            } catch (SQLException ex) {
                Logger.getLogger(admin_EmployeeController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void setValue(int selectedIndex) {

    }

}
