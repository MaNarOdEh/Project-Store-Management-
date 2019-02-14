package desgin2;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import javax.activation.MimetypesFileTypeMap;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.JOptionPane;
import org.controlsfx.control.Notifications;

public class CommonMethods {

    public void mainAdmin() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Admin.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void welcomePage() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FirstWelcomeToAll.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            Desgin2.st = stage;
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void logInPage() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("LogIn.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void mainEmployee() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Employee.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static boolean isEmail(String email) {

        Pattern pattern = Pattern.compile("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}");
        Matcher mat = pattern.matcher(email);
        if (mat.matches()) {
            return true;
        } else {
            return false;
        }
    }

    public void ForgetPassword() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ForgetPassword.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void AdminOrEmployee() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Chooser.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void EditEmployee() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("EditEmployee.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void ShowAndEditEmployee() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ShowInformationEmployee.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void InvoicesAdminPage() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("admin_Invoices.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void SuppliersAdminPage() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("admin_Suplliers.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void goToTheEmployeeAdmin() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("admin_Employee.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void goToTheCategories() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Categories.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void goToTheSetting() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("setting.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void goToTheNotification() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("notification.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void goToTheAboutUs() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("aboutUs.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void goToTheAddNewEmployee() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("admin_EmployeeOperation.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean check(String to) {
        if (to.isEmpty()) {
            return false;
        } else if (isEmail(to)) {
            return true;
        }
        return false;
    }

    public String newPassword() {
        String password = "";
        Random r = new Random();
        for (int i = 0; i < 4; i++) {
            password += (r.nextInt(10) + 1);
        }
        password += ((char) r.nextInt(90) + 65) + ((char) r.nextInt(90) + 65);

        return password;
    }

    public void changeCursorAndDisable(ImageView img, boolean fOrT) {
        if (fOrT) {
            img.setDisable(false);
            img.setCursor(Cursor.DEFAULT);
        } else {
            img.setDisable(true);
            img.setCursor(Cursor.HAND);
        }
    }

    public boolean isImage(String PathForImage) {
        File f = new File(PathForImage);
        String mimetype = new MimetypesFileTypeMap().getContentType(f);
        String type = mimetype.split("/")[0];//splite to array and return the first value in it
        if (type.equals("image")) {
            return true;
        }
        return false;
    }

    public LocalDate isDate(String dateSt) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-YYYY");
            //formatter = formatter.withLocale(putAppropriateLocaleHere);  // Locale specifies human language for translating, and cultural norms for lowercase/uppercase and abbreviations and such. Example: Locale.US or Locale.CANADA_FRENCH
            LocalDate date = LocalDate.parse(dateSt, formatter);
            return date;

        } catch (Exception ex) {
            return null;
        }
    }

    public void sendMessgeAdmin() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SendMessage.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(CommonMethods.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void sendEmailToEmployee(String toEmployee, String subject, String messageText) {
        try {
            String to = toEmployee;
            System.out.println(toEmployee + "okkkkkkkk");
            if (Desgin2.commonMethods.check(to)) {
                String host = "smtp.live.com";
                String user = "manar-_odeh@hotmail.com";
                String pass = "lkhv123456789";
                String from = "manar-_odeh@hotmail.com";
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
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }

    }

    public void fullArrayList(ArrayList<String> list, String type) {

        if (type.equals("employee")) {
            Desgin2.connectionToDataBase.getAllEmployeeName(list);

        } else if (type.equals("email")) {
            Desgin2.connectionToDataBase.getAllEmployeeEmail(list);

        } else if (type.equals("supllier")) {
            Desgin2.connectionToDataBase.getAllSupllierName(list);
        } else if (type.equals("supllierEmail")) {
            Desgin2.connectionToDataBase.getAllSupplierEmail(list);
        } else if (type.equals("categories")) {
            Desgin2.connectionToDataBase.getAllCategoriesName(list, "0");
        } else if (type.equals("categories1")) {
            Desgin2.connectionToDataBase.getAllCategoriesName(list, "1");

        } else if (type.equals("Date")) {
            Desgin2.connectionToDataBase.giveAllDataInvoices(list);

        } else if (type.equals("ID")) {
            Desgin2.connectionToDataBase.giveAllIDInvoices(list);

        } else if (type.equals("NameCustomer")) {
            Desgin2.connectionToDataBase.giveAllNameCustomerInvoices(list);
        } else if (type.equals("Type Name")) {
            Desgin2.connectionToDataBase.giveAllNameTypeInvoices(list);
        } else if (type.equals("NameEmployee")) {
            Desgin2.connectionToDataBase.giveEmployeeName(list);
        }

    }

    public void goToTheSettirngPassword() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SettingPassword.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(CommonMethods.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void goToTheSettirngEmail() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SettingEmailAccount.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(CommonMethods.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void goToTheSettingUserName() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SettingUserName.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(CommonMethods.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void goToTheInoicesSecondPage() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("admin_Invoices0.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(CommonMethods.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void IntroducingSalce() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("IntroducingTheSalce.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(CommonMethods.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void ReviewOfTheLost() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ViewOwner.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(CommonMethods.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void getNotification(String text, String title, String type) {
        Image img = null;
        if (type.equals("error")) {
            img = new Image(("img/can.png"));
        } else if (type.equals("correct")) {
            img = new Image(("img/check-mark-button.png"));
        }
        Notifications notification = Notifications.create()
                .title(title)
                .text(text)
                .graphic(new ImageView(img))
                .hideAfter(Duration.seconds(5))
                .position(Pos.BOTTOM_RIGHT)
                .onAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                    }
                });
        notification.darkStyle();
        Platform.runLater(() -> {
            if (type.equals("error")) {
                notification.show();
            } else if (type.equals("correct")) {
                notification.show();
            }
        });

    }

    public String increaseDateBySpecificNumberOfDay(String date, int count) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Calendar c = Calendar.getInstance();
            if (date != null) {
                c.setTime(sdf.parse(date));
                c.add(Calendar.DATE, count);
                // number of days to add
                return sdf.format(c.getTime());
            }
        } catch (ParseException ex) {
            Logger.getLogger(CommonMethods.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;

    }

    public void makeInvoices(String count, String type, String date, String phone, String nameC, String invoiceType) {
        String Price = (Integer.parseInt(count)
                * Desgin2.connectionToDataBase.giveCategoriesPrice(type)) + "";
        Invoices_Maker in = new Invoices_Maker();
        in.setDate(date);
        in.setNumberOfAdminOrEmployee(phone);
        //in.setPrice();
        in.setemployeNameOrAdmin(nameC);
        in.setNumberOfCustomerOrSupplier("");
        in.setTypeInvoices("Sold");
        in.setTypeOfCategories(invoiceType);
        in.setQuntity(count);
        in.createParagraphOrTitleHeading();
        in.createDetailsInvoicesParagraph();
        in.createGoodsDetailsParagraph();
        in.doInvoices();
        getNotification("Your invoices now is store in doc file you can Print It", "Sucssefully", "correct");
    }

}
