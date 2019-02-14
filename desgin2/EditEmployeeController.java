package desgin2;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

public class EditEmployeeController implements Initializable {

    @FXML
    private TextField salaryEmployee;

    @FXML
    private TextField hourOfWorkEmployee;

    @FXML
    private DatePicker DateOfStartWork;

    @FXML
    private TextField CountOfDayToDeliverySalary;

    @FXML
    private Button btnClose;

    public static String salary;
    public static String hour;
    public static String date;
    public static String count;
    public static int id;
    public static ObservableList<Employee> data;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        CountOfDayToDeliverySalary.setText(count);
        hourOfWorkEmployee.setText(hour);
        salaryEmployee.setText(salary);

        /*DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-mm-dd");
        //convert String to LocalDate
        LocalDate localDate = LocalDate.parse(date, formatter);
        DateOfStartWork.setValue(localDate);*/
    }

    @FXML
    void cancel(ActionEvent event) {
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }

    @FXML
    void numberOnly(KeyEvent event) {
        try {
            char ch = event.getCharacter().charAt(0);
            if (!Character.isDigit(ch)) {
                if (Character.isLetter(ch)) {
                    Desgin2.commonMethods.getNotification("Please Input Number Only!", "Wrong", "error");
                }
                event.consume();

            } else if (event.getSource() == hourOfWorkEmployee) {
                if ((hourOfWorkEmployee.getText().isEmpty() ? Integer.parseInt(ch + "")
                        : Integer.parseInt(hourOfWorkEmployee.getText() + ch)) >= 13) {
                    Desgin2.commonMethods.getNotification("Your hour of work must be less than or equal 12", "Wrong", "error");
                    event.consume();
                }
            } else if (event.getSource() == CountOfDayToDeliverySalary) {
                if ((CountOfDayToDeliverySalary.getText().isEmpty() ? Integer.parseInt(ch + "")
                        : Integer.parseInt(CountOfDayToDeliverySalary.getText() + ch)) > 30) {
                    Desgin2.commonMethods.getNotification("Your hour of work must be less than or equal 30", "Wrong", "error");
                    event.consume();
                }
            }
        } catch (Exception e) {
            event.consume();
        }

    }

    @FXML
    void update(ActionEvent event) {

        boolean check = false;
        String hourE = hourOfWorkEmployee.getText();
        String salaryE = salaryEmployee.getText();
        String dateSt = DateOfStartWork.getValue() + "";
        String counE = CountOfDayToDeliverySalary.getText();

        if (!hourE.equals(hour)) {
            Desgin2.connectionToDataBase.changgeHour(id, Integer.parseInt(hourE));
            Desgin2.connectionToDataBase.changgeTheNotificationOfEmployee("your Admin was change The hour of work from: " + hour + " to " + hourE+"^^", id, true);
            check = true;
        }
        if (!salaryE.equals(salary)) {
            Desgin2.connectionToDataBase.changgeSalary(id, Integer.parseInt(salaryE));
            Desgin2.connectionToDataBase.changgeTheNotificationOfEmployee("your Admin was change The salary of work to " + salaryE+"^^", id, true);
            check = true;

        }
        if (!dateSt.equals("null") && !dateSt.equals(date)) {
            Desgin2.connectionToDataBase.changgeDate(id, dateSt);
            Desgin2.connectionToDataBase.changgeTheNotificationOfEmployee("your Admin was change The Date Start of work to " + dateSt+"^^", id, true);
            check = true;
        }
        if (!counE.equals(count)) {
            Desgin2.connectionToDataBase.changgeCount(id, Integer.parseInt(counE));
            Desgin2.connectionToDataBase.changgeTheNotificationOfEmployee("your admin was change The count of day to delivery the salary to "+counE + "^^", id, true);

            check = true;
        }
        if (check) {
            Desgin2.commonMethods.getNotification("Updated Sucssefully", "Sucssefully", "correct");
        } else {
            Desgin2.commonMethods.getNotification("Changge thing To Sace", "Wrong", "error");
        }
    }
}
