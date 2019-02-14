package desgin2;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javax.swing.JOptionPane;

public class settingController implements Initializable {

    @FXML
    private JFXButton password;

    @FXML
    private JFXButton userName;

    @FXML
    private JFXButton emailAccount;

    @FXML
    private JFXButton componyCapital;
    @FXML
    private JFXButton savePaswwrod;
    @FXML
    private JFXCheckBox showPassword;

    @FXML
    private TextField newPassword;

    @FXML
    private TextField confirmNewPassword;

    @FXML
    private TextField oldPassword;

    @FXML
    private PasswordField oldPasswordO;

    @FXML
    private PasswordField newPasswordO;

    @FXML
    private PasswordField confirmPasswordO;

    @FXML
    private Label forgetPassword;
//currentPasswordO
    String newPasswordStr;
    String confrirmPassrwodStr;
    String oldPassordStr;
    String passwordStr;
    String newEmailStr;
    String name;

    //for emailSetting
    @FXML
    private JFXButton saveEmail;

    @FXML
    private JFXCheckBox showPasswordEmail;

    @FXML
    private TextField newEmail;

    @FXML
    private TextField currentPassword;

    @FXML
    private PasswordField currentPasswordO;

    //user name
    @FXML
    private TextField userNametext;

    @FXML
    private JFXButton s;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (componyCapital != null && !Desgin2.isAdmin) {
            componyCapital.setVisible(false);
        }
        if (userNametext != null) {
            final BooleanProperty firstTime = new SimpleBooleanProperty(true); // Variable to store the focus on stage load

            userNametext.focusedProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue && firstTime.get()) {
                    s.requestFocus(); // Delegate the focus to container
                    firstTime.setValue(false); // Variable value changed for future references
                }
            });
        }

    }

    @FXML
    void goToAnoutherPage(ActionEvent event) {
        if (event.getSource() == password) {
            Desgin2.commonMethods.goToTheSettirngPassword();
            ((Node) (event.getSource())).getScene().getWindow().hide();

        } else if (event.getSource() == userName) {
            Desgin2.commonMethods.goToTheSettingUserName();
            ((Node) (event.getSource())).getScene().getWindow().hide();
        } else if (event.getSource() == emailAccount) {
            Desgin2.commonMethods.goToTheSettirngEmail();
            ((Node) (event.getSource())).getScene().getWindow().hide();
        } else if (event.getSource() == componyCapital) {
            boolean isCorrect = false;
            String newMoney = "";
            while (!isCorrect) {
                newMoney = JOptionPane.showInputDialog(null, "Input your new Money...");
                isCorrect = check(newMoney);
            }
            Desgin2.connectionToDataBase.updateAdminMoney(newMoney);
        }

    }

    public boolean check(String text) {
        if (text == null) {
            return false;
        }
        for (int i = 0; i < text.length(); i++) {
            if (Character.isLetter(text.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    @FXML
    void close(MouseEvent event) {
        if (Desgin2.isAdmin) {
            Desgin2.commonMethods.mainAdmin();
        } else {
            Desgin2.commonMethods.mainEmployee();
        }
        ((Node) (event.getSource())).getScene().getWindow().hide();

    }

    @FXML
    void back(MouseEvent event) {
        goBack();
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }

    @FXML
    void cancel(ActionEvent event) {
        goBack();
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }

    public void setValueEmail() {
        passwordStr = currentPasswordO.getText().isEmpty() ? currentPassword.getText() : currentPasswordO.getText();
        newEmailStr = newEmail.getText();
    }

    public void empty() {
        currentPassword.setText("");
        currentPasswordO.setText("");
        newEmail.setText("");
    }

    public void setValueUserName() {
        passwordStr = currentPasswordO.getText().isEmpty() ? currentPassword.getText() : currentPasswordO.getText();
        name = userNametext.getText();
    }

    public void setClearUserName() {
        currentPassword.setText("");
        currentPasswordO.setText("");
        userNametext.setText("");
    }

    @FXML
    void save(ActionEvent event) {
        if (userNametext != null) {
            setValueUserName();
            if (passwordStr.isEmpty() || name.isEmpty()) {
                Desgin2.commonMethods.getNotification("Input all requied field!", "Wrong", "error");
            } else if (!passwordStr.equals(Desgin2.passwordForAdminOrEmployee)) {
                forgetPassword.setVisible(true);
            } else if (Desgin2.isAdmin) {
                Desgin2.connectionToDataBase.updateAdminUserName(name, Desgin2.nameOrEmail);
                Desgin2.commonMethods.getNotification("Your user Name was Updated", "Sucssefully", "correct");
                setClearUserName();

            } else {
                Desgin2.connectionToDataBase.updateEmployeeUserName(name, Desgin2.nameOrEmail);
                Desgin2.commonMethods.getNotification("Your user Name was Updated", "Sucssefully", "correct");
                setClearUserName();
            }
        } else if (saveEmail != null) {
            setValueEmail();
            if (newEmailStr.isEmpty() || passwordStr.isEmpty()) {
                Desgin2.commonMethods.getNotification("Input all requied field!", "Wrong", "error");
            } else {
                if (!Desgin2.commonMethods.isEmail(newEmailStr)) {
                    Desgin2.commonMethods.getNotification("Wrong Email!", "Wrong", "error");
                }
                if (!passwordStr.equals(Desgin2.passwordForAdminOrEmployee)) {
                    forgetPassword.setVisible(true);
                } else {
                     if (Desgin2.isAdmin) {
                    Desgin2.connectionToDataBase.updateAdminEmail(newEmailStr, Desgin2.nameOrEmail);
                    Desgin2.nameOrEmail = newEmailStr;
                    Desgin2.commonMethods.getNotification("Your Email was  Updated", "Sucssefully", "correct");
                    empty();
                } else {
                    Desgin2.connectionToDataBase.updateEmplyeeEmail(newEmailStr, Desgin2.nameOrEmail);
                    Desgin2.nameOrEmail = newEmailStr;
                    Desgin2.commonMethods.getNotification("Your Email was  Updated", "Sucssefully", "correct");
                    empty();
                }
                }

            }
        } else {
            setValue();
            boolean check = true;
            if (Desgin2.isAdmin) {
                String oldPassword = Desgin2.connectionToDataBase.giveAdminPassword();
                System.out.print(oldPassword + "     ");
                if (!oldPassordStr.isEmpty() && !oldPassword.equals(this.oldPassordStr)) {
                    forgetPassword.setVisible(true);
                    check = false;
                } else {
                    check = true;
                }
            } else {

                String oldPassword = Desgin2.connectionToDataBase.giveEmployeePassword(Desgin2.nameOrEmail);

                System.out.println(oldPassword + "K");

                if (!oldPassordStr.isEmpty() && !oldPassword.equals(this.oldPassordStr)) {
                    forgetPassword.setVisible(true);
                    check = false;
                } else {
                    check = true;
                }
            }
            if (checkPassword()) {
                String name = Desgin2.nameOrEmail;
                if (Desgin2.isAdmin) {
                    if (check) {
                        Desgin2.connectionToDataBase.updateAdminPassword(newPasswordStr, name);
                        forgetPassword.setVisible(false);
                        Desgin2.commonMethods.getNotification("Your Password Updated!", "Sucssefully", "correct");
                        Desgin2.passwordForAdminOrEmployee = newPasswordStr;
                        emptyField();
                    }
                } else {
                    if (check) {
                        Desgin2.connectionToDataBase.updateEmployeePassword(newPasswordStr, name);
                        forgetPassword.setVisible(false);
                        Desgin2.passwordForAdminOrEmployee = newPasswordStr;
                        Desgin2.commonMethods.getNotification("Your Password Updated!", "Sucssefully", "correct");
                        emptyField();
                    }

                }
            }
        }
    }

    public void emptyField() {
        if (oldPasswordO.isVisible()) {
            oldPasswordO.setText("");
            newPasswordO.setText("");
            confirmPasswordO.setText("");
        } else {
            oldPassword.setText("");
            newPassword.setText("");
            confirmNewPassword.setText("");
        }

    }

    @FXML
    void clean(KeyEvent event) {
        if (saveEmail != null) {
            forgetPassword.setVisible(false);
        } else if (oldPasswordO != null || oldPassword != null) {
            if (event.getSource() == oldPasswordO || event.getSource() == oldPassword) {
                forgetPassword.setVisible(false);
            }
        }
    }

    @FXML
    void rememmberPassword(MouseEvent event) {
        Desgin2.commonMethods.ForgetPassword();

    }

    private void setValue() {
        oldPassordStr = oldPasswordO.isVisible() ? oldPasswordO.getText() : oldPassword.getText();
        newPasswordStr = newPasswordO.isVisible() ? newPasswordO.getText() : newPassword.getText();
        confrirmPassrwodStr = confirmPasswordO.isVisible() ? confirmPasswordO.getText() : confirmNewPassword.getText();
    }

    private boolean checkPassword() {
        System.out.println(oldPassordStr + "  " + newPasswordStr + "  " + confrirmPassrwodStr + "  ");
        if (newPasswordStr.isEmpty() || newPasswordStr.isEmpty() || confrirmPassrwodStr.isEmpty()) {
            Desgin2.commonMethods.getNotification("Input All Field", "Wrong", "error");
            return false;
        }
        if (newPasswordStr.length() < 5 || newPasswordStr.length() > 10) {
            Desgin2.commonMethods.getNotification("Your Password must be at least 5 digit and less than 9",
                    "Wrong", "error");
            return false;
        } else if (!newPasswordStr.equals(confrirmPassrwodStr)) {
            Desgin2.commonMethods.getNotification("Your Password Not Equal!",
                    "Wrong", "error");
            return false;
        }
        return true;

    }

    @FXML
    void showPass(ActionEvent event) {
        if (userNametext != null) {
            if (showPasswordEmail.isSelected()) {
                currentPasswordO.setVisible(false);
                currentPassword.setText(currentPasswordO.getText());

            } else {
                currentPasswordO.setVisible(true);
                currentPasswordO.setText(currentPassword.getText());
            }
        }
        if (showPassword != null) {
            if (showPassword.isSelected()) {
                oldPasswordO.setVisible(false);
                newPasswordO.setVisible(false);
                confirmPasswordO.setVisible(false);
                oldPassword.setText(oldPasswordO.getText());
                newPassword.setText(newPasswordO.getText());
                confirmNewPassword.setText(confirmPasswordO.getText());

            } else {
                oldPasswordO.setVisible(true);
                newPasswordO.setVisible(true);
                confirmPasswordO.setVisible(true);
                oldPasswordO.setText(oldPassword.getText());
                newPasswordO.setText(newPassword.getText());
                confirmPasswordO.setText(confirmNewPassword.getText());

            }
        } else if (showPasswordEmail != null) {
            if (showPasswordEmail.isSelected()) {
                currentPasswordO.setVisible(false);
                currentPassword.setText(currentPasswordO.getText());

            } else {
                currentPasswordO.setVisible(true);
                currentPasswordO.setText(currentPassword.getText());
            }

        }
    }

    public void goBack() {
        Desgin2.commonMethods.goToTheSetting();

    }

}
