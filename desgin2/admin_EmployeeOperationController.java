package desgin2;

import com.jfoenix.controls.JFXCheckBox;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javax.swing.JFileChooser;

public class admin_EmployeeOperationController implements Initializable {

    @FXML
    private TextField employeeFirstName;

    @FXML
    private TextField salaryEmployee;

    @FXML
    private TextField employeeLastName;

    @FXML
    private TextField employeePhoneNumber;

    @FXML
    private TextField PasswordT;

    @FXML
    private TextArea noteEmployee;

    @FXML
    private TextField emailEmployee;

    @FXML
    private JFXCheckBox male;

    @FXML
    private JFXCheckBox female;

    @FXML
    private PasswordField Password;

    @FXML
    private TextField hourOfWorkEmployee;

    @FXML
    private DatePicker DateOfStartWork;

    @FXML
    private TextField CountOfDayToDeliverySalary;

    @FXML
    private ImageView photoOfEmployee;
    @FXML
    private Label image;

    JFileChooser fileChooser;
    String path = "img/images.png";
    @FXML
    private Label st;

    @FXML
    private Label st0;

    @FXML
    private Label st1;

    @FXML
    private Label st2;

    @FXML
    private Label st5;

    @FXML
    private Label st4;

    @FXML
    private Label st6;

    @FXML
    private Label st3;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    void ShowPassword(ActionEvent event) {
        if (Password.isVisible()) {
            Password.setVisible(false);
            PasswordT.setText(Password.getText());
        } else {
            Password.setVisible(true);
            Password.setText(PasswordT.getText());

        }
    }

    @FXML
    void back(MouseEvent event) {
        Desgin2.commonMethods.goToTheEmployeeAdmin();
        ((Node) (event.getSource())).getScene().getWindow().hide();

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
        st3.setVisible(false);

    }

    @FXML
    void addImage(ActionEvent event) {
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
                Desgin2.commonMethods.getNotification("This Is Not Image!", "Wrong", "error");
            }

        }
    }

    @FXML
    void addNewCategories(ActionEvent event) {
        String name = employeeFirstName.getText() + " " + employeeLastName.getText();
        int salary = 0;
        String phone = employeePhoneNumber.getText();
        String password = PasswordT.getText();
        String email = emailEmployee.getText();
        String note = noteEmployee.getText();
        int countOfDaybeforeDeliverySalary = 0;
        int isMale = male.isSelected() ? 1 : female.isSelected() ? 1 : -1;
        int hourOfWork = 0;
        String dateStart = DateOfStartWork.getValue() + "";
        System.out.println(dateStart);
        if (dateStart == null) {
            dateStart = new Date() + "";
        }
        boolean check = false;
        if (null == dateStart) {
            check = true;
            st2.setVisible(true);
        }
        if (name.equals(" ")) {
            st.setVisible(true);
            check = true;
        }
        if (salaryEmployee.getText().isEmpty()) {
            check = true;
            st4.setVisible(true);
        }
        if (phone.isEmpty()) {
            check = true;
            st0.setVisible(true);
        }
        if (email.isEmpty()) {
            check = true;
            st6.setVisible(true);
        }
        if (hourOfWorkEmployee.getText().isEmpty()) {
            check = true;
            st1.setVisible(true);
        }
        if (CountOfDayToDeliverySalary.getText().isEmpty()) {
            check = true;
            st5.setVisible(true);

        }
        if (isMale == -1) {
            check = true;
            st3.setVisible(true);
        }
        if (!check) {
            if (email.equals("") && !Desgin2.commonMethods.isEmail(email)) {
                Desgin2.commonMethods.getNotification("Your Email Is Wrong!", "Wrong", "error");
            } else {
                salary = Integer.parseInt(salaryEmployee.getText());
                hourOfWork = Integer.parseInt(hourOfWorkEmployee.getText());
                countOfDaybeforeDeliverySalary = Integer.parseInt(CountOfDayToDeliverySalary.getText());
                password = password.isEmpty() ? Desgin2.commonMethods.newPassword() : password;
                Desgin2.connectionToDataBase.addNewEmployee(name, password, email, salary, phone, hourOfWork, path, dateStart,
                        countOfDaybeforeDeliverySalary, note);
                Desgin2.commonMethods.getNotification("Your EMployee Was Added Sucssefuuly/n"
                        + " You will Recive notification about employee name and her password!!", "Sucssefully", "correct");
                emptyAllField();
            }
        }
        if (check) {
            Desgin2.commonMethods.getNotification("Please Input all requied field!", "Wrong", "error");
        }
    }

    public void emptyAllField() {
        noteEmployee.setText("");
        emailEmployee.setText("");
        employeeFirstName.setText("");
        employeeLastName.setText("");
        employeePhoneNumber.setText("");
        hourOfWorkEmployee.setText("");
        DateOfStartWork.setValue(null);
        CountOfDayToDeliverySalary.setText("");
        Password.setText("");
        PasswordT.setText("");
       // path = path == null ? "img/images.png" : path;
//        photoOfEmployee.setImage(new Image(path));
        salaryEmployee.setText("");

    }

    @FXML
    void removeStar(MouseEvent event) {

        if (event.getSource() == employeeFirstName || event.getSource() == employeeLastName) {
            st.setVisible(false);
        } else if (event.getSource() == CountOfDayToDeliverySalary) {
            st5.setVisible(false);
        } else if (event.getSource() == male || event.getSource() == female) {
            st3.setVisible(false);
        } else if (event.getSource() == employeePhoneNumber) {
            st0.setVisible(false);
        } else if (event.getSource() == emailEmployee) {
            st6.setVisible(false);
        } else if (event.getSource() == salaryEmployee) {
            st4.setVisible(false);
        } else if (event.getSource() == DateOfStartWork) {
            st2.setVisible(false);
        } else if (event.getSource() == hourOfWorkEmployee) {
            st1.setVisible(false);
        }

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

            }
            if (event.getSource() == employeePhoneNumber) {
                if (employeePhoneNumber.getText().length() >= 10) {
                    event.consume();
                    Desgin2.commonMethods.getNotification("Your Input must Be Less Than 9 Digit!", "Wrong", "error");
                }
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

}
