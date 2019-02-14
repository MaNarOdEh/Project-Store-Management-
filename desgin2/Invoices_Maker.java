package desgin2;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import java.io.File;
import java.io.FileOutputStream;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

public class Invoices_Maker {

    private String supplierNameOrCustromer;
    private String employeNameOrAdmin;
    private String typeOfCategories;
    private String price;
    private String date;
    private String typeInvoices;
    private String quntity;
    private String numberOfAdminOrEmployee;
    private String numberOfCustomerOrSupplier;
    private XWPFDocument resultDocument = new XWPFDocument();
    private int headFontSize = 20;
    private int fontSize = 15;
    String path_txt;
    public static Stage st;
    //private XWPFDocument document;

    public void createParagraphOrTitleHeading() {
        XWPFParagraph headding = resultDocument.createParagraph();
        XWPFRun run = headding.createRun();
        headding.setAlignment(ParagraphAlignment.CENTER);
        run.setBold(true);
        run.setFontSize(headFontSize);
        run.setText("invoices".toUpperCase());
        run.addBreak();
        run.setText(typeInvoices);
        run.addBreak();

    }

    public void createDetailsInvoicesParagraph() {
        XWPFParagraph personal = resultDocument.createParagraph();
        XWPFRun run = personal.createRun();
        personal.setAlignment(ParagraphAlignment.LEFT);

        run.setFontSize(fontSize);

        run.setText("Invoices:");
        run.addBreak();

        run.setText("Phone No Of Employee : " + numberOfAdminOrEmployee + "                   Email No of Admin: " + employeNameOrAdmin);
        run.addBreak();

        run.setText("Phone No Of Customer : " + numberOfCustomerOrSupplier + "                 Email No of Customer:   " + numberOfCustomerOrSupplier);
        run.addBreak();

    }

    public void createGoodsDetailsParagraph() {
        XWPFParagraph headding = resultDocument.createParagraph();
        XWPFRun run = headding.createRun();
        headding.setAlignment(ParagraphAlignment.LEFT);

        run.setFontSize(fontSize);

        run.setText("Goods is: " + typeOfCategories + "                Guntity of goods is " + quntity + "           Price of goods is " + price);
        run.addBreak();

        run.setText("Price of goods is " + price);
        run.addBreak();
        run.setText("Signature of Customer is:  " + "Signature of Employee is:  ");
        run.addBreak();

    }

    public void setemployeNameOrAdmin(String employeNameOrAdmin) {
        this.employeNameOrAdmin = employeNameOrAdmin;
    }

    public void setSupplierNameOrCustromer(String supplierNameOrCustromer) {
        this.supplierNameOrCustromer = supplierNameOrCustromer;
    }

    public void setTypeOfCategories(String typeOfCategories) {
        this.typeOfCategories = typeOfCategories;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTypeInvoices(String typeInvoices) {
        this.typeInvoices = typeInvoices;
    }

    public void setQuntity(String quntity) {
        this.quntity = quntity;
    }

    public void setNumberOfAdminOrEmployee(String numberOfAdminOrEmployee) {
        this.numberOfAdminOrEmployee = numberOfAdminOrEmployee;
    }

    public void setNumberOfCustomerOrSupplier(String numberOfCustomerOrSupplier) {
        this.numberOfCustomerOrSupplier = numberOfCustomerOrSupplier;
    }

    public XWPFDocument getResultDocument() {
        return resultDocument;
    }

    public void path() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedDirectory = directoryChooser.showDialog(st);
        if (selectedDirectory != null) {
            path_txt = selectedDirectory.getAbsolutePath();
        }

    }

    public void doInvoices() {
        path();
        if (path_txt != null) {
            try {
                FileOutputStream output = new FileOutputStream(path_txt + "/" + "Invoices" + ".doc");
                resultDocument.write(output);
                output.close();
                //showMessage("Done successfully !", "Congrats ! your invoices is created and saved at the path you choose :)\nDont forget to print it");
            } catch (Exception e) {
                //  showMessage("Error :(", "An error occured while creating your invoices\nDetailes: " + e.getMessage());
            }
        }

    }

    public void showMessage(String title, String message) {
        JFXDialogLayout layout = new JFXDialogLayout();
        layout.setHeading(new Text(title));
        layout.setBody(new Text(message));
        JFXButton bt = new JFXButton("Ok");
        layout.setActions(bt);

        JFXDialog dialog = new JFXDialog(null, layout, JFXDialog.DialogTransition.CENTER);
        bt.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                dialog.close();
            }
        });

        dialog.show();

    }

}
