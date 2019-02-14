package desgin2;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.StringTokenizer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

public class notificationController implements Initializable {
    
    @FXML
    private Label labelName;
    @FXML
    private JFXListView<?> listViewNotification;
    @FXML
    private JFXButton clickMe;
    @FXML
    private Label labelMasseage;
    @FXML
    private Label labelCategories;
    @FXML
    private Label labelGoods;
    
    @FXML
    private Label labelOwner;
    @FXML
    private Label labMasseg;
    
    String notification;
    StringTokenizer st;
    ArrayList<String> notMasseg = new ArrayList<>();
    ArrayList<String> notOwner = new ArrayList<>();
    ArrayList<String> notGoods = new ArrayList<>();
    ArrayList<String> notCate = new ArrayList<>();
    int notMa = 0;
    int notOw = 0;
    int notCa = 0;
    int notG = 0;
    String space = "                    ";
    
    public void setNewNotification(ArrayList<String> list) {
        for (int i = 0; i < list.size(); i++) {
            notification += list.get(i) + "^^";
        }
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        if (Desgin2.isAdmin) {
            labMasseg.setText("          Add New Employee");
            notification = Desgin2.connectionToDataBase.notificationOfAdmin();
            setNotificationAdmin();
        } else {
            notification = Desgin2.connectionToDataBase.getNotificationEmployee(Desgin2.nameOrEmail);
            setNotificationAdmin();
        }
        
    }
    
    public void setNotificationAdmin() {
        if (notification.isEmpty()) {
            setVisible(false);
        } else {
            setVisible(true);
            st = new StringTokenizer(notification, "^^");
            while (st.hasMoreTokens()) {
                String token = st.nextToken();
                if (token.contains("Owner")) {
                    if (notOw == 0) {
                        labelOwner.setText(space + token);
                    }
                    notOwner.add(token);
                    notOw++;
                    
                } else if (token.contains("Categories")) {
                    if (notCa == 0) {
                        labelCategories.setText(space + token);
                    }
                    notCate.add(token);
                    notCa++;
                } else if (token.contains("Salce")) {
                    if (notG == 0) {
                        labelGoods.setText(space + token);
                    }
                    notGoods.add(token);
                    notG++;
                } else if (token.contains("UserName")) {
                    if (notMa == 0) {
                        labelMasseage.setText(space + token);
                    }
                    notMasseg.add(token);
                    notMa++;
                } else if (token.contains("Admin") || token.equals("Message")) {
                    if (notMa == 0) {
                        labelMasseage.setText(space + token);
                    }
                    notMasseg.add(token);
                    notMa++;
                    
                }
            }
        }
    }
    
    public void setVisible(boolean visible) {
        labelMasseage.setVisible(visible);
        labelCategories.setVisible(visible);
        labelGoods.setVisible(visible);
        labelOwner.setVisible(visible);
    }
    
    @FXML
    void back(ActionEvent event) {
        notification = "";
        setNewNotification(notCate);
        setNewNotification(notMasseg);
        setNewNotification(notGoods);
        setNewNotification(notOwner);
        if (Desgin2.isAdmin) {
            Desgin2.connectionToDataBase.changgeTheNotificationOfAdmin(notification, false);
            Desgin2.commonMethods.mainAdmin();
        } else {
            Desgin2.connectionToDataBase.changgeTheNotificationOfEmployee(notification, Desgin2.nameOrEmail, false);
            Desgin2.commonMethods.mainEmployee();
        }
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }
    
    @FXML
    void deleteC(MouseEvent event) {
        if (notCa > 0) {
            int del = notCate.size() - notCa;
            labelCategories.setText(space + notCate.get(del));
            notCate.remove(del);
            notCa--;
        } else {
            notCate.clear();
            labelCategories.setText("");
            
        }
    }
    
    @FXML
    void deleteG(MouseEvent event) {
        if (notG > 0) {
            int del = notGoods.size() - notG;
            labelGoods.setText(space + notGoods.get(del));
            notGoods.remove(del);
            notG--;
        } else {
            notGoods.clear();
            labelGoods.setText("");
            
        }
        
    }
    
    @FXML
    void deleteM(MouseEvent event) {
        if (notMa > 0) {
            int del = notMasseg.size() - notMa;
            labelMasseage.setText(space + notMasseg.get(del));
            notMasseg.remove(del);
            notMa--;
        } else {
            labelMasseage.setText("");
            notMasseg.clear();
        }
        
    }
    
    @FXML
    void deleteO(MouseEvent event) {
        if (notOw > 0) {
            int del = notOwner.size() - notOw;
            labelOwner.setText(space + notOwner.get(del));
            notOwner.remove(del);
            notOw--;
        } else {
            notOwner.clear();
            labelOwner.setText("");
        }
        
    }
    
}
