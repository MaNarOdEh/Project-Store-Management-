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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import org.controlsfx.control.textfield.TextFields;

public class ViewOwnerController implements Initializable {

    @FXML
    private TableView<Categories> tabelOwner;
    @FXML
    private TextField textSearch;
    final ObservableList<Categories> data1 = FXCollections.observableArrayList();
    final ObservableList<Categories> data = FXCollections.observableArrayList();
    ArrayList<String> list = new ArrayList<>();

    @FXML
    private ImageView imageSearch;

    boolean firstClick;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Desgin2.commonMethods.fullArrayList(list, "categories1");
        TextFields.bindAutoCompletion(textSearch, list);

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

        tabelOwner.getColumns().addAll(IDCoulmn, NameCoulmn, TypeCoulmn, PriceCoulmn, CountCoulmn, ColorCoulmn, GenderCoulmn);
        ResultSet giveSupplierInformation = Desgin2.connectionToDataBase.giveAllCategoriesDeleted();
        addDataToObservableList(giveSupplierInformation, data, false);
        tabelOwner.setItems(data);

    }

    public void addDataToObservableList(ResultSet resultSet, ObservableList<Categories> data, boolean bool) {
        try {
            if (resultSet == null) {
                return;
            }
            while (resultSet.next()) {
                if (bool && (resultSet.getString("ISDELETED") + "").equals("1")) {
                    data.add(new Categories(resultSet.getString("ID"), resultSet.getString("NAME"),
                            resultSet.getString("TYPE"), resultSet.getString("PRICE"), resultSet.getString("COUNT"), resultSet.getString("COLOR"),
                            resultSet.getString("GENDER")));
                } else if (!bool) {
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

    @FXML
    private void back(MouseEvent event) {
        if (Desgin2.isAdmin) {
            Desgin2.commonMethods.mainAdmin();
        } else {
            Desgin2.commonMethods.mainEmployee();
        }
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }

    @FXML
    private void imageSearch(MouseEvent event) {
        String name = textSearch.getText();
        textSearch.setText("");
        data1.clear();
        if (!name.isEmpty() && !firstClick) {
            ResultSet giveCategoriesNameInformation = Desgin2.connectionToDataBase.giveCategoriesInformation(name);
            for (int i = 0; i < tabelOwner.getItems().size(); i++) {
                tabelOwner.getItems().clear();
            }
            addDataToObservableList(giveCategoriesNameInformation, data1, true);
            imageSearch.setImage(new Image("img/arrows.png"));
            tabelOwner.setItems(data1);
            firstClick = true;
        } else if (firstClick) {
            for (int i = 0; i < tabelOwner.getItems().size(); i++) {
                tabelOwner.getItems().clear();
            }
            firstClick = false;
            imageSearch.setImage(new Image("img/refreshTwo32.png"));
            ResultSet giveEmployeeInformation = Desgin2.connectionToDataBase.giveAllCategoriesDeleted();
            addDataToObservableList(giveEmployeeInformation, data, false);
            tabelOwner.setItems(data);

        }
    }

    @FXML
    private void restore(ActionEvent event) {

        int selectedIndex = tabelOwner.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            String type;
            int id;
            if (!data.isEmpty()) {
                type = data.get(selectedIndex).getType();
                id = Integer.parseInt(data.get(selectedIndex).getID());
                list.remove(data.get(selectedIndex).getName());
                data.remove(selectedIndex);
            } else {
                type = data1.get(selectedIndex).getType();
                id = Integer.parseInt(data1.get(selectedIndex).getID());
                list.remove(data1.get(selectedIndex).getName());
                data1.remove(selectedIndex);
            }
            tabelOwner.refresh();
            Desgin2.commonMethods.getNotification("You Categories Restore You Can Check The Categories To See It..", "Sucssefully", "correct");
            Desgin2.connectionToDataBase.deleteCategoriesWithId(id, "0");
            /* double newValue = (Double.parseDouble(Desgin2.connectionToDataBase.getAdminMoney())
                    + Desgin2.connectionToDataBase.giveCategoriesPrice(type));
            Desgin2.connectionToDataBase.updateAdminMoney(newValue + "");*/
            Desgin2.connectionToDataBase.changgeTheNotificationOfAdmin("Restore categories with Name: " + type + "Is Scussefully", true);
            Desgin2.connectionToDataBase.changgeTheNotificationOfEmployee("Restore categories with Name: " + type + "Is Scussefully" + "^^", "all", true);
        } else {
            Desgin2.commonMethods.getNotification("Choose Categories to Restore", "Wrong", "error");
        }
    }

    @FXML
    private void delete(ActionEvent event) {
        int selectedIndex = tabelOwner.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            int id;
            if (!data.isEmpty()) {
                id = Integer.parseInt(data.get(selectedIndex).getID());
                list.remove(data.get(selectedIndex).getName());
                data.remove(selectedIndex);
            } else {
                id = Integer.parseInt(data1.get(selectedIndex).getID());
                list.remove(data1.get(selectedIndex).getName());
                data1.remove(selectedIndex);
            }
            Desgin2.connectionToDataBase.deleteCategoriesWithId(id);
            Desgin2.commonMethods.getNotification("You Categories was deleted", "Sucssefully", "correct");

            tabelOwner.refresh();
        } else {
            Desgin2.commonMethods.getNotification("Choose Categories to delete", "Wrong", "error");
        }
    }

}
