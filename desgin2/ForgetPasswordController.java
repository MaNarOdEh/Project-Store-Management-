package desgin2;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.Date;
import java.util.Properties;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class ForgetPasswordController implements Initializable {

    @FXML
    private TextField email;
    @FXML
    private JFXButton employee;

    @FXML
    private JFXButton admin;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    void isAdminOrEmployee(ActionEvent event) {
        if (event.getSource() == admin) {
            Desgin2.isAdmin = true;
        } else {
            Desgin2.isAdmin = false;
        }
        Desgin2.commonMethods.logInPage();
        ((Node) (event.getSource())).getScene().getWindow().hide();
        Desgin2.st.close();
    }

    @FXML
    void cancel(ActionEvent event) {
        ((Node) (event.getSource())).getScene().getWindow().hide();

    }

    @FXML
    void submit(ActionEvent event) {
        try {
            //String to = email.getText();
            String to = "manar-_1997@hotmail.com";
            if (Desgin2.commonMethods.check(to)) {
                String host = "smtp.live.com";
                String user = "**********";
                String pass = "**********";
                String from = user;
                String newPassword = Desgin2.commonMethods.newPassword();

                String subject = "Thank you for contact Us you recive a new Password\n "
                        + "Your New Password is:\n " + newPassword + "\n";
                String messageText = "Your Is Test Email :";
                boolean sessionDebug = false;

                Properties props = System.getProperties();

                props.put("mail.smtp.starttls.enable", "true");
                props.put("mail.smtp.host", host);
                props.put("mail.smtp.port", "587");
                props.put("mail.smtp.auth", "true");
                props.put("mail.smtp.starttls.required", "true");

                java.security.Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
                Session mailSession = Session.getDefaultInstance(props, null);
                mailSession.setDebug(sessionDebug);
                Message msg = new MimeMessage(mailSession);
                msg.setFrom(new InternetAddress(from));
                InternetAddress[] address = {new InternetAddress(to)};
                msg.setRecipients(Message.RecipientType.TO, address);
                msg.setSubject(subject);
                msg.setSentDate(new Date());
                msg.setText(messageText);

                Transport transport = mailSession.getTransport("smtp");
                transport.connect(host, user, pass);
                transport.sendMessage(msg, msg.getAllRecipients());
                transport.close();
                Desgin2.commonMethods.getNotification("message send successfully", "Successfully", "error");
                Desgin2.connectionToDataBase.updateEmployeePassword(newPassword,Desgin2.nameOrEmail);
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }

    }

}
