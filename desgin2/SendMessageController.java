package desgin2;

import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.JOptionPane;
import org.controlsfx.control.textfield.TextFields;

public class SendMessageController implements Initializable {

    @FXML
    private JFXTextField from;

    @FXML
    private JFXTextField to;

    @FXML
    private JFXTextField subject;

    @FXML
    private TextArea YourMasseage;
    public static String toEm;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        final BooleanProperty firstTime = new SimpleBooleanProperty(true); // Variable to store the focus on stage load
        to.setText(toEm);
        to.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue && firstTime.get()) {
                subject.requestFocus(); // Delegate the focus to container
                firstTime.setValue(false); // Variable value changed for future references
            }
        });
        ArrayList<String> list = new ArrayList<>();
        if (!Desgin2.isAdmin) {
            Desgin2.commonMethods.fullArrayList(list, "email");
        } else {
            Desgin2.commonMethods.fullArrayList(list, "supllierEmail");
        }
        TextFields.bindAutoCompletion(to, list);
        from.setText(Desgin2.connectionToDataBase.emialAdmin);
        from.setDisable(true);
    }

    @FXML
    void cancel(ActionEvent event) {
        if (Desgin2.isEmployee) {
            Desgin2.commonMethods.goToTheEmployeeAdmin();
        } else {
            Desgin2.commonMethods.SuppliersAdminPage();
        }
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }

    @FXML
    void sendMessage(ActionEvent event) {
        String messageText = YourMasseage.getText();
        String subject = this.subject.getText();
        String toEmployee;// = to.getText();
        toEmployee = "manar-_1997@hotmail.com";

        if (messageText.isEmpty()) {
            Desgin2.commonMethods.getNotification("Please Input a masseage To send!!", "Wrong", "error");
        } else if (subject.isEmpty()) {
            Desgin2.commonMethods.getNotification("Please Input a subject For your masseage!!", "Wrong", "error");
        } else if (toEmployee.isEmpty()) {
            Desgin2.commonMethods.getNotification("Please Choose email To Send it A masseage!!!!", "Wrong", "error");
        } else {

            if (Desgin2.commonMethods.check(toEmployee)) {
                try {
                    String host = "smtp.live.com";
                    String user = "************";
                    String pass = "***********";
                    String from = user;

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
                    InternetAddress[] address = {new InternetAddress(toEmployee)};
                    msg.setRecipients(Message.RecipientType.TO, address);
                    msg.setSubject(subject);
                    msg.setSentDate(new Date());
                    msg.setText(messageText);

                    Transport transport = mailSession.getTransport("smtp");
                    transport.connect(host, user, pass);
                    transport.sendMessage(msg, msg.getAllRecipients());
                    transport.close();
                    System.out.println("message send successfully");
                    Desgin2.commonMethods.getNotification("message send successfully", "Successfully", "correct");
                    Desgin2.connectionToDataBase.changgeTheNotificationOfEmployee("You Admin Send you a Message Check your Email!" + "^^", toEmployee, true);

                    /*  
                String host = "smtp.live.com";
            String user = "";
            String pass = "******************";
            String to = "*******************";
            String from = "";
            String subject = "This is confirmation number for your expertprogramming account. Please insert this number to activate your account.";
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
            System.out.println("message send successfully");
        
                     */
                } catch (MessagingException ex) {
                    Logger.getLogger(SendMessageController.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    ((Node) (event.getSource())).getScene().getWindow().hide();
                    /* if (!Desgin2.isAdmin) {
                        Desgin2.commonMethods.goToTheEmployeeAdmin();
                    } else {
                        Desgin2.commonMethods.SuppliersAdminPage();
                    }*/
                }
            }
        }
    }

}
