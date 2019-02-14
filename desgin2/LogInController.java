package desgin2;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class LogInController implements Initializable {

    @FXML
    private TextField UserNameOrEmail;

    @FXML
    private TextField Password;
    @FXML
    private TextField PasswordT;

    @FXML
    private Label forgetPassword;
    @FXML
    private JFXButton btn;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        final BooleanProperty firstTime = new SimpleBooleanProperty(true); // Variable to store the focus on stage load
        PasswordT.setVisible(false);
        UserNameOrEmail.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue && firstTime.get()) {
                btn.requestFocus(); // Delegate the focus to container
                firstTime.setValue(false); // Variable value changed for future references
            }
        });
    }

    @FXML
    void goToTheWelcomePage(MouseEvent event) {
        ((Node) (event.getSource())).getScene().getWindow().hide();
        Desgin2.commonMethods.welcomePage();
    }

    @FXML
    void GoToTheMainPage(ActionEvent event) {
        String nameOrEmail = UserNameOrEmail.getText();
        String password = Password.getText();
        if (password.isEmpty()) {
            password = PasswordT.getText();
        }

        if (!nameOrEmail.isEmpty() && !password.isEmpty()) {
            if (Desgin2.isAdmin) {
                if (Desgin2.connectionToDataBase.giveAdminNameAndUserName(nameOrEmail, password)) {
                    Desgin2.nameOrEmail = nameOrEmail;
                    Desgin2.passwordForAdminOrEmployee = password;
                    ((Node) (event.getSource())).getScene().getWindow().hide();
                    Desgin2.commonMethods.mainAdmin();
                } else {
                    forgetPassword.setVisible(true);
                    PasswordT.setText("");
                    Password.setText("");
                    UserNameOrEmail.setText("");

                }
            } else if (Desgin2.connectionToDataBase.giveEmployeeNameAndUserName(nameOrEmail, password)) {
                Desgin2.nameOrEmail = nameOrEmail;
                Desgin2.passwordForAdminOrEmployee = password;
                ((Node) (event.getSource())).getScene().getWindow().hide();
                Desgin2.commonMethods.mainEmployee();
            } else {
                forgetPassword.setVisible(true);
                PasswordT.setText("");
                Password.setText("");
                UserNameOrEmail.setText("");
            }
        }
    }

    @FXML
    void LogInWithFacebook(ActionEvent event) {

    }

    @FXML
    void ShowPassword(ActionEvent event) {
        if (!PasswordT.isVisible()) {
            PasswordT.setVisible(true);
            PasswordT.setText(Password.getText());
        } else {
            Password.setText(PasswordT.getText());
            PasswordT.setVisible(false);

        }
    }

    @FXML
    void forgetPassword(MouseEvent event) {
        Desgin2.commonMethods.ForgetPassword();

    }

    @FXML
    void hide(MouseEvent event) {
        forgetPassword.setVisible(false);
    }

}
