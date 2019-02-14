package desgin2;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
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
import org.controlsfx.control.textfield.TextFields;
import javafx.scene.input.MouseEvent;

public class admin_EmployeeController implements Initializable {

    @FXML
    private TextField searchEmployee;
    @FXML
    private ImageView imgSearch;
    @FXML
    private TableView<Employee> tabelToShowInformationOfEmployee;
    final ObservableList<Employee> data = FXCollections.observableArrayList();
    ArrayList<String> list = new ArrayList<>();
    boolean firstClick;
    final ObservableList<Employee> data1 = FXCollections.observableArrayList();
    int selected;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Desgin2.isEmployee = true;
        Desgin2.commonMethods.fullArrayList(list, "employee");
        TextFields.bindAutoCompletion(searchEmployee, list);

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

        TableColumn SalaryCoulmn = new TableColumn("Salary");
        SalaryCoulmn.setMinWidth(40);
        SalaryCoulmn.setCellValueFactory(new PropertyValueFactory<>("salary"));

        TableColumn HourCoulmn = new TableColumn("Hour");
        HourCoulmn.setMinWidth(30);
        HourCoulmn.setCellValueFactory(new PropertyValueFactory<>("hour"));

        TableColumn StartDateCoulmn = new TableColumn("StartDate");
        StartDateCoulmn.setMinWidth(50);
        StartDateCoulmn.setCellValueFactory(new PropertyValueFactory<>("dateStart"));

        TableColumn EndCoulmn = new TableColumn("EndDate");
        EndCoulmn.setMinWidth(50);
        EndCoulmn.setCellValueFactory(new PropertyValueFactory<>("dateEnd"));

        tabelToShowInformationOfEmployee.getColumns().addAll(IDCoulmn, NameCoulmn, PhoneCoulmn, EmailCoulmn, SalaryCoulmn, HourCoulmn, StartDateCoulmn, EndCoulmn);
        ResultSet giveEmployeeInformation = Desgin2.connectionToDataBase.giveEmployeeInformation();
        addDataToObservableList(giveEmployeeInformation, data);
        tabelToShowInformationOfEmployee.setItems(data);
        tabelToShowInformationOfEmployee.setEditable(true);
    }

    @FXML
    void AddNewEmployee(ActionEvent event) {
        Desgin2.commonMethods.goToTheAddNewEmployee();
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }

    @FXML
    void DeleteEmployee(ActionEvent event) {
        int selectedIndex = tabelToShowInformationOfEmployee.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            selected = selectedIndex;
            if (!data.isEmpty()) {
                remove(list, data);
            } else if (!data1.isEmpty()) {
                remove(list, data1);
            }
            TextFields.bindAutoCompletion(searchEmployee, list);
        }
        tabelToShowInformationOfEmployee.refresh();
    }

    public void remove(ArrayList<String> list, ObservableList<Employee> data) {
        Desgin2.connectionToDataBase.deleteEmployeeName(Integer.parseInt(data.get(selected).getID()));
        list.remove(data.get(selected).getName());
        data.remove(selected);
    }

    private void setEdit(ObservableList<Employee> data) {
        EditEmployeeController.count = Desgin2.connectionToDataBase.countOfEmployee(data.get(selected).getID() + "");
        EditEmployeeController.date = data.get(selected).getDateStart();
        EditEmployeeController.hour = data.get(selected).getHour();
        EditEmployeeController.salary = data.get(selected).getSalary();
        EditEmployeeController.id = Integer.parseInt(data.get(selected).getID());
        data.remove(data.get(selected));        
}

    @FXML
    void EditEmployee(ActionEvent event) {
        int selectedIndex = tabelToShowInformationOfEmployee.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            selected = selectedIndex;
            if (!data.isEmpty()) {
                setEdit(data);
            } else if (!data1.isEmpty()) {
                setEdit(data1);
            }
            Desgin2.commonMethods.EditEmployee();
            tabelToShowInformationOfEmployee.refresh();
        } else {
            Desgin2.commonMethods.getNotification("Select Employee to Updated!", "Wrong", "error");
        }

        /*
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog with Custom Actions");
            alert.setContentText("Choose what you want to edit..");

            ButtonType buttonTypeSalary = new ButtonType("salary");
            ButtonType buttonTypeHour = new ButtonType("hour");
            ButtonType buttonTypeCount = new ButtonType("Count Of Day");
            ButtonType buttonTypeDateStart = new ButtonType("DateStart");
            ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

            alert.getButtonTypes().setAll(buttonTypeSalary, buttonTypeHour, buttonTypeCount, buttonTypeDateStart, buttonTypeCancel);
            Optional<ButtonType> result = alert.showAndWait();

            int id = Integer.parseInt(data.get(selectedIndex).getID());

            if (result.get() == buttonTypeSalary) {
                String input = JOptionPane.showInputDialog("Input new Salary..");
                if (input != null && (!input.isEmpty())) {
                    int newSalary = Integer.parseInt(input);
                    int salary = Integer.parseInt(data.get(selectedIndex).getSalary());
                    System.out.println(newSalary + "   " + salary);
                    if (salary != newSalary) {
                        Desgin2.connectionToDataBase.changgeSalary(id, newSalary);
                        JOptionPane.showMessageDialog(null, "Updated Sussefully..");

                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Wrong Input!");
                }

            } else if (result.get() == buttonTypeHour) {
                String input = JOptionPane.showInputDialog(null, "Input new Hour..");
                if (input != null && !input.isEmpty()) {

                    int hour = Integer.parseInt(input);
                    if (hour <= 12 && hour != Integer.parseInt(data.get(selectedIndex).getHour())) {
                        Desgin2.connectionToDataBase.changgeHour(id, hour);
                        JOptionPane.showMessageDialog(null, "Updated Sussefully..");

                    } else {
                        JOptionPane.showMessageDialog(null, "Wrong Input!");

                    }
                }
            } else if (result.get() == buttonTypeCount) {
                String input = JOptionPane.showInputDialog("Input new Count..");
                if (input != null && !input.isEmpty()) {
                    int count = Integer.parseInt(input);
                    if (count <= 30) {
                        Desgin2.connectionToDataBase.changgeCount(id, count);
                        JOptionPane.showMessageDialog(null, "Updated Sussefully..");

                    } else {
                        JOptionPane.showMessageDialog(null, "Wrong Input!");
                    }
                }
            } else if (result.get() == buttonTypeDateStart) {
                String input = JOptionPane.showInputDialog("Input new Date");
                if (input != null && !input.isEmpty()) {
                    LocalDate date = Desgin2.commonMethods.isDate(input);
                    Desgin2.connectionToDataBase.changgeDate(id, date + "");
                    JOptionPane.showMessageDialog(null, "Updated Sussefully..");

                } else {
                    JOptionPane.showMessageDialog(null, "Wrong Input!");

                }
            }
        }*/
    }

    @FXML
    void SendMasseage(ActionEvent event
    ) {
        int selectedIndex = tabelToShowInformationOfEmployee.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            String email = null;
            if (!data.isEmpty()) {
                email = data.get(selectedIndex).getEmail();
            } else if (!data1.isEmpty()) {
                email = data1.get(selectedIndex).getEmail();
            }
            SendMessageController.toEm = email;
        }
        Desgin2.commonMethods.sendMessgeAdmin();
    }

    @FXML

    void goToTheMainAdmin(MouseEvent event
    ) {
        Desgin2.commonMethods.mainAdmin();
        ((Node) (event.getSource())).getScene().getWindow().hide();

    }

    @FXML

    public void addDataToObservableList(ResultSet resultSet, ObservableList<Employee> data
    ) {
        try {
            while (resultSet.next()) {
                String date1 = resultSet.getString("DATEOFSTART");
                int count = Integer.parseInt(resultSet.getString("COUNTOFDAY"));
                if(date1==null||date1.equals("null")){
                   date1=new Date()+""; 
                }
                String date2 = Desgin2.commonMethods.increaseDateBySpecificNumberOfDay(date1, count);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date currentDate = new Date();
                int salary = Integer.parseInt(resultSet.getString("SALARY"));
                if ((currentDate + "").compareTo(date2) >= 0) {
                    date1 = date2 + "";
                    date2 = Desgin2.commonMethods.increaseDateBySpecificNumberOfDay(date1, count);
                    Desgin2.connectionToDataBase.updateAdminMoney((Double.parseDouble(Desgin2.connectionToDataBase.getAdminMoney()) - salary) + "");
                }
                data.add(new Employee(resultSet.getString("ID"),
                        resultSet.getString("NAME"),
                        resultSet.getString("PHONENUMBER"),
                        resultSet.getString("EMAIL"),
                        salary + "",
                        resultSet.getString("HOUROFWORK"),
                        date1,
                        date2));
            }

        } catch (SQLException ex) {
        } finally {
            try {
                resultSet.close();

            } catch (SQLException ex) {

            }
        }
    }

    @FXML
    void back(MouseEvent event
    ) {
        Desgin2.isEmployee = false;
        Desgin2.commonMethods.mainAdmin();
        ((Node) (event.getSource())).getScene().getWindow().hide();

    }

    @FXML
    void onMouseClicked(MouseEvent event
    ) {
        int selectedIndex = tabelToShowInformationOfEmployee.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            if (!data.isEmpty()) {
                data.get(selectedIndex).getID();
            } else if (!data1.isEmpty()) {
                data1.get(selectedIndex).getID();
            }
            selected = selectedIndex;
        }
    }

    @FXML
    void search(MouseEvent event
    ) {
        String name = searchEmployee.getText();
        searchEmployee.setText("");
        data1.clear();
        if (!name.isEmpty() && !firstClick) {
            ResultSet giveEmployeeInformation = Desgin2.connectionToDataBase.giveEmployeeInformation(name);
            for (int i = 0; i < tabelToShowInformationOfEmployee.getItems().size(); i++) {
                tabelToShowInformationOfEmployee.getItems().clear();
            }
            addDataToObservableList(giveEmployeeInformation, data1);
            imgSearch.setImage(new Image("img/arrows.png"));
            tabelToShowInformationOfEmployee.setItems(data1);
            firstClick = true;
        } else if (firstClick) {
            firstClick = false;
            imgSearch.setImage(new Image("img/refreshTwo32.png"));
            ResultSet giveEmployeeInformation = Desgin2.connectionToDataBase.giveEmployeeInformation();
            addDataToObservableList(giveEmployeeInformation, data);
            tabelToShowInformationOfEmployee.setItems(data);

        }

    }
}
