package desgin2;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.image.Image;
import javax.swing.ImageIcon;

public class ConnectionToDataBase {

    public static Connection connection;
    String emialAdmin = "manar-_odeh@hotmail.com";

    public ConnectionToDataBase() {
        connection = getConnection();
    }

    private static Connection getConnection() {
        try {
            Connection connection = DriverManager.getConnection("jdbc:sqlite:StoreKeeper.db");
            return connection;
        } catch (SQLException ex) {
            return null;
        }
    }

    public boolean giveAdminNameAndUserName(String nameOrEmail, String password) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            check();

            if (Desgin2.commonMethods.isEmail(nameOrEmail)) {
                Statement st = connection.createStatement();
                resultSet = st.executeQuery("SELECT PASSWORD,EMAIL FROM AdminInformation");
                if (resultSet.next()) {
                    if (password.equals(resultSet.getString(1))) {
                        if (nameOrEmail.equals(resultSet.getString(2))) {
                            return true;
                        }
                    } else {
                    }
                }
            } else {
                preparedStatement = connection.prepareStatement("SELECT NAME,PASSWORD FROM AdminInformation");
                resultSet = preparedStatement.executeQuery();
                if (nameOrEmail.equals(resultSet.getString(1))) {
                    if (password.equals(resultSet.getString(2))) {
                        return true;
                    }
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(ConnectionToDataBase.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (resultSet != null) {
                    resultSet.close();
                }

            } catch (Exception ex) {
                Logger.getLogger(ConnectionToDataBase.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return false;
    }

    public String giveAdminPassword() {
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;

        try {
            check();
            preparedStatement = connection.prepareStatement("SELECT PASSWORD FROM AdminInformation");
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("PASSWORD");
            }
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionToDataBase.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                resultSet.close();
                preparedStatement.close();
            } catch (Exception ex) {

            }
        }
        return null;
    }

    public String giveEmployeePassword(String name) {
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;

        try {
            check();
            if (Desgin2.commonMethods.isEmail(name)) {
                preparedStatement = connection.prepareStatement("SELECT PASSWORD FROM EmployeeInformation WHERE EMAIL='" + name + "'");
                resultSet = preparedStatement.executeQuery();
            } else {
                preparedStatement = connection.prepareStatement("SELECT PASSWORD FROM EmployeeInformation WHERE NAME='" + name + "'");
                resultSet = preparedStatement.executeQuery();
            }
            if (resultSet.next()) {
                return resultSet.getString("PASSWORD");
            }

        } catch (SQLException ex) {
            Logger.getLogger(ConnectionToDataBase.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                resultSet.close();
                preparedStatement.close();
            } catch (Exception ex) {

            }
        }
        return null;
    }

    public String updateEmployeePassword(String newPassword, String nameOrEmail) {
        PreparedStatement preparedStatement = null;

        try {
            check();
            if (Desgin2.commonMethods.isEmail(nameOrEmail)) {
                preparedStatement = connection.prepareStatement("UPDATE EmployeeInformation SET PASSWORD=?  WHERE EMAIL='" + nameOrEmail + "'");
                preparedStatement.setString(1, newPassword);
                preparedStatement.executeUpdate();
            } else {
                preparedStatement = connection.prepareStatement("UPDATE EmployeeInformation SET PASSWORD=? WHERE NAME='" + nameOrEmail + "'");
                preparedStatement.setString(1, newPassword);
                preparedStatement.executeUpdate();
            }
            Desgin2.passwordForAdminOrEmployee = newPassword;

        } catch (SQLException ex) {
            Logger.getLogger(ConnectionToDataBase.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                preparedStatement.close();
            } catch (Exception ex) {

            }
        }
        return null;
    }

    public String updateAdminEmail(String neweEmail, String name) {
        PreparedStatement preparedStatement = null;

        try {
            check();
            if (Desgin2.commonMethods.isEmail(name)) {
                Desgin2.nameOrEmail = name;
                preparedStatement = connection.prepareStatement("UPDATE AdminInformation SET EMAIL=?  WHERE EMAIL='" + name + "'");
                preparedStatement.setString(1, neweEmail);
                preparedStatement.executeUpdate();
            } else {
                preparedStatement = connection.prepareStatement("UPDATE AdminInformation SET EMAIL=? WHERE NAME='" + name + "'");
                preparedStatement.setString(1, neweEmail);
                preparedStatement.executeUpdate();
            }

        } catch (SQLException ex) {
            Logger.getLogger(ConnectionToDataBase.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                preparedStatement.close();
            } catch (Exception ex) {

            }
        }
        return null;
    }

    public String updateAdminUserName(String newUserName, String name) {
        PreparedStatement preparedStatement = null;

        try {
            check();
            if (Desgin2.commonMethods.isEmail(name)) {
                Desgin2.nameOrEmail = name;
                preparedStatement = connection.prepareStatement("UPDATE AdminInformation SET NAME=?  WHERE EMAIL='" + name + "'");
                preparedStatement.setString(1, newUserName);
                preparedStatement.executeUpdate();
            } else {
                preparedStatement = connection.prepareStatement("UPDATE AdminInformation SET NAME=? WHERE NAME='" + name + "'");
                preparedStatement.setString(1, newUserName);
                preparedStatement.executeUpdate();
            }

        } catch (SQLException ex) {
            Logger.getLogger(ConnectionToDataBase.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                preparedStatement.close();
            } catch (Exception ex) {

            }
        }
        return null;
    }

    public String updateEmployeeUserName(String newUserName, String name) {
        PreparedStatement preparedStatement = null;

        try {
            check();
            if (Desgin2.commonMethods.isEmail(name)) {
                preparedStatement = connection.prepareStatement("UPDATE EmployeeInformation SET NAME=?  WHERE EMAIL='" + name + "'");
                preparedStatement.setString(1, newUserName);
                preparedStatement.executeUpdate();
            } else {
                Desgin2.nameOrEmail = name;
                preparedStatement = connection.prepareStatement("UPDATE EmployeeInformation SET NAME=? WHERE NAME='" + name + "'");
                preparedStatement.setString(1, newUserName);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionToDataBase.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                preparedStatement.close();
            } catch (Exception ex) {

            }
        }
        return null;
    }

    public String updateEmplyeeEmail(String neweEmail, String name) {
        PreparedStatement preparedStatement = null;

        try {
            check();
            if (Desgin2.commonMethods.isEmail(name)) {
                preparedStatement = connection.prepareStatement("UPDATE EmployeeInformation SET EMAIL=?  WHERE EMAIL='" + name + "'");
                preparedStatement.setString(1, neweEmail);
                preparedStatement.executeUpdate();
            } else {
                Desgin2.nameOrEmail = name;
                preparedStatement = connection.prepareStatement("UPDATE EmployeeInformation SET EMAIL=? WHERE NAME='" + name + "'");
                preparedStatement.setString(1, neweEmail);
                preparedStatement.executeUpdate();
            }

        } catch (SQLException ex) {
            Logger.getLogger(ConnectionToDataBase.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                preparedStatement.close();
            } catch (Exception ex) {

            }
        }
        return null;
    }

    public String updateAdminPassword(String newPassword, String name) {
        PreparedStatement preparedStatement = null;

        try {
            check();
            if (Desgin2.commonMethods.isEmail(name)) {
                preparedStatement = connection.prepareStatement("UPDATE AdminInformation SET PASSWORD=?  WHERE EMAIL='" + name + "'");
                preparedStatement.setString(1, newPassword);
                preparedStatement.executeUpdate();
            } else {
                preparedStatement = connection.prepareStatement("UPDATE AdminInformation SET PASSWORD=? WHERE NAME='" + name + "'");
                preparedStatement.setString(1, newPassword);
                preparedStatement.executeUpdate();
            }
            Desgin2.passwordForAdminOrEmployee = newPassword;

        } catch (SQLException ex) {
            Logger.getLogger(ConnectionToDataBase.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                preparedStatement.close();
            } catch (Exception ex) {

            }
        }
        return null;
    }

    public String updateAdminMoney(String newMoney) {
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement("UPDATE ComponyInformation SET THE_COMPONYS_CAPITAL=?");
            preparedStatement.setString(1, newMoney);
            preparedStatement.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(ConnectionToDataBase.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                preparedStatement.close();
            } catch (Exception ex) {

            }
        }
        return null;
    }

    public String getAdminMoney() {
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;

        try {
            check();
            preparedStatement = connection.prepareStatement("SELECT THE_COMPONYS_CAPITAL FROM ComponyInformation");
            rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return rs.getString("THE_COMPONYS_CAPITAL");

            }

        } catch (SQLException ex) {
            Logger.getLogger(ConnectionToDataBase.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                preparedStatement.close();
                rs.close();
            } catch (Exception ex) {

            }
        }
        return null;
    }

    public boolean giveEmployeeNameAndUserName(String nameOrEmail, String password) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            check();

            if (Desgin2.commonMethods.isEmail(nameOrEmail)) {
                preparedStatement = connection.prepareStatement("SELECT PASSWORD,EMAIL FROM EmployeeInformation WHERE EMAIL='" + nameOrEmail + "'");
                resultSet = preparedStatement.executeQuery();
                if (password.equals(resultSet.getString(1))) {
                    if (nameOrEmail.equals(resultSet.getString(2))) {
                        return true;
                    }
                }
            } else {
                preparedStatement = connection.prepareStatement("SELECT NAME,PASSWORD FROM EmployeeInformation WHERE NAME=?");
                preparedStatement.setString(1, nameOrEmail);
                resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    if (nameOrEmail.equals(resultSet.getString(1))) {
                        if (password.equals(resultSet.getString(2))) {
                            return true;
                        }
                    }
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(ConnectionToDataBase.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {

                preparedStatement.close();
                resultSet.close();

            } catch (Exception ex) {
                Logger.getLogger(ConnectionToDataBase.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return false;
    }

    public ResultSet giveEmployeeInformation() {
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;

        try {
            check();
            preparedStatement = connection.prepareStatement("SELECT ID,NAME,EMAIL,SALARY,PHONENUMBER,HOUROFWORK,DATEOFSTART,COUNTOFDAY FROM EmployeeInformation");
            resultSet = preparedStatement.executeQuery();
            return resultSet;
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionToDataBase.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {

                //preparedStatement.close();
            } catch (Exception ex) {
                Logger.getLogger(ConnectionToDataBase.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    public ResultSet giveEmployeeInformation(String name) {
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;

        try {
            check();
            preparedStatement = connection.prepareStatement("SELECT ID,NAME,EMAIL,SALARY,PHONENUMBER,HOUROFWORK,"
                    + "DATEOFSTART,COUNTOFDAY FROM EmployeeInformation WHERE NAME='" + name + "'");
            resultSet = preparedStatement.executeQuery();
            return resultSet;
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionToDataBase.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {

                //preparedStatement.close();
            } catch (Exception ex) {
                Logger.getLogger(ConnectionToDataBase.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    public void giveEmployeeName(ArrayList<String> list) {
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;

        try {
            check();
            preparedStatement = connection.prepareStatement("SELECT NAME FROM EmployeeInformation");
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String name = resultSet.getString("NAME");
                if (!list.contains(name)) {
                    list.add(name);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionToDataBase.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {

                //preparedStatement.close();
            } catch (Exception ex) {
                Logger.getLogger(ConnectionToDataBase.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void addNewEmployee(String name, String password, String email, int salary, String PhoneNumber,
            int hourOfWork, String imagePath, String dateOfStart, int countOfDay, String note) {
        PreparedStatement preparedStatement = null;
        try {
            check();
            preparedStatement = connection.prepareStatement("INSERT  INTO EmployeeInformation VALUES(?,?,?,?,?,?,?,?,?,?,?)");

            preparedStatement.setString(2, name);
            preparedStatement.setString(3, password);
            preparedStatement.setString(4, email);
            preparedStatement.setInt(5, salary);
            preparedStatement.setString(6, PhoneNumber);
            preparedStatement.setInt(7, hourOfWork);
            try {
                //preparedStatement.setBinaryStream(8, toRecordImage(imagePath), toRecordImage(imagePath).available());
                FileInputStream fis = new FileInputStream(imagePath);
                preparedStatement.setBinaryStream(8, fis, fis.available());
            } catch (IOException ex) {
            }
            preparedStatement.setString(9, dateOfStart + "");
            preparedStatement.setInt(10, countOfDay);
            preparedStatement.setString(11, note);
            preparedStatement.executeUpdate();
            changgeTheNotificationOfAdmin("You Add New Employee UserName is: " + name + " and Have Password is: " + password + "^^", true); //preparedStatement.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionToDataBase.class.getName()).log(Level.SEVERE, null, ex);

        } finally {
            try {

                preparedStatement.close();

            } catch (Exception ex) {
                Logger.getLogger(ConnectionToDataBase.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static FileInputStream toRecordImage(String imagePath) {
        if (imagePath != null) {
            try {
                FileInputStream fis = new FileInputStream(imagePath);
                return fis;
            } catch (FileNotFoundException ex) {
                return null;
            }
        }
        return null;

    }

    public void changgeTheNotificationOfAdmin(String newNotification, boolean isAddNew) {
        Statement statement = null;
        try {
            check();
            statement = connection.createStatement();
            statement.execute("UPDATE AdminInformation SET NOTIFICATION='" + newNotification
                    + (isAddNew ? notificationOfAdmin() : "") + "'" + " WHERE EMAIL='" + emialAdmin + "'");

        } catch (SQLException ex) {
            Logger.getLogger(ConnectionToDataBase.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                statement.close();
            } catch (Exception ex) {
                Logger.getLogger(ConnectionToDataBase.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public String notificationOfAdmin() {
        String notification = "";
        PreparedStatement statement = null;
        ResultSet rs = null;

        try {
            check();
            statement = connection.prepareStatement("SELECT NOTIFICATION FROM AdminInformation WHERE EMAIL='" + emialAdmin + "'");
            rs = statement.executeQuery();
            if (rs.next()) {
                notification = rs.getString("NOTIFICATION");
            }
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionToDataBase.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                statement.close();
                rs.close();
            } catch (Exception ex) {
                Logger.getLogger(ConnectionToDataBase.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return notification;
    }

    public void getAllEmployeeName(ArrayList<String> list) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            check();
            st = connection.prepareStatement("SELECT NAME FROM EmployeeInformation");
            rs = st.executeQuery();
            while (rs.next()) {
                list.add(rs.getString("NAME"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionToDataBase.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                st.close();
                rs.close();
            } catch (Exception ex) {
                Logger.getLogger(ConnectionToDataBase.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public void getAllEmployeeEmail(ArrayList<String> list) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {//EMAIL
            check();
            st = connection.prepareStatement("SELECT EMAIL FROM EmployeeInformation");
            rs = st.executeQuery();
            while (rs.next()) {
                list.add(rs.getString("EMAIL"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionToDataBase.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                st.close();
                rs.close();
            } catch (Exception ex) {
                Logger.getLogger(ConnectionToDataBase.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public void close() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException ex) {
                // Logger.getLogger(ConnectionToDataBase.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void check() {
        if (connection == null) {
            getConnection();
        }
    }

    public void deleteEmployeeName(int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM EmployeeInformation WHERE ID='" + id + "'");
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionToDataBase.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getEmployeeEmail(int id) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {//EMAIL
            check();
            st = connection.prepareStatement("SELECT EMAIL FROM EmployeeInformation WHERE ID='" + id + "'");
            rs = st.executeQuery();
            if (rs.next()) {
                return rs.getString("EMAIL");
            }

        } catch (SQLException ex) {
            Logger.getLogger(ConnectionToDataBase.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                st.close();
                rs.close();
            } catch (Exception ex) {
                Logger.getLogger(ConnectionToDataBase.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;

    }

    public int getEmployeeHour(int id) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {//EMAIL
            check();
            st = connection.prepareStatement("SELECT HOUR FROM EmployeeInformation WHERE ID='" + id + "'");
            rs = st.executeQuery();
            if (rs.next()) {
                return rs.getInt("HOUROFWORK");
            }

        } catch (SQLException ex) {
            Logger.getLogger(ConnectionToDataBase.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                st.close();
                rs.close();
            } catch (Exception ex) {
                Logger.getLogger(ConnectionToDataBase.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return 0;

    }

    public void changgeSalary(int id, int salary) {
        Statement statement = null;
        try {
            check();
            statement = connection.createStatement();
            statement.execute("UPDATE EmployeeInformation SET SALARY='" + salary + "' WHERE ID='" + id + "'");
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionToDataBase.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                statement.close();
            } catch (Exception ex) {
                Logger.getLogger(ConnectionToDataBase.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public void changgeHour(int id, int hour) {
        Statement statement = null;
        try {
            check();
            statement = connection.createStatement();
            statement.execute("UPDATE EmployeeInformation SET HOUROFWORK='" + hour + "' WHERE ID='" + id + "'");
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionToDataBase.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                statement.close();
            } catch (Exception ex) {
                Logger.getLogger(ConnectionToDataBase.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public void changgeCount(int id, int count) {
        Statement statement = null;
        try {
            check();
            statement = connection.createStatement();
            statement.execute("UPDATE EmployeeInformation SET COUNTOFDAY='" + count + "' WHERE ID='" + id + "'");
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionToDataBase.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                statement.close();
            } catch (Exception ex) {
                Logger.getLogger(ConnectionToDataBase.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public void changgeDate(int id, String date) {
        if (date == null) {
            return;
        }
        Statement statement = null;
        try {
            check();
            statement = connection.createStatement();
            statement.execute("UPDATE EmployeeInformation SET DATEOFSTART='" + date + "' WHERE ID='" + id + "'");
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionToDataBase.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                statement.close();
            } catch (Exception ex) {
                Logger.getLogger(ConnectionToDataBase.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public ResultSet giveSllSuplierInformation() {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement("SELECT ID,NAME,PHONE,EMAIL FROM SupplierInformation");
            resultSet = preparedStatement.executeQuery();
            return resultSet;
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionToDataBase.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /*
    public ResultSet giveEmployeCount() {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement("SELECT ID,NAME,PHONE,EMAIL FROM SupplierInformation");
            resultSet = preparedStatement.executeQuery();
            return resultSet;
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionToDataBase.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }*/
    public void getAllSupllierName(ArrayList<String> list) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            check();
            st = connection.prepareStatement("SELECT NAME FROM SupplierInformation");
            rs = st.executeQuery();
            while (rs.next()) {
                list.add(rs.getString("NAME"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionToDataBase.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                st.close();
                rs.close();
            } catch (Exception ex) {
                Logger.getLogger(ConnectionToDataBase.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public ResultSet giveSupllierInformation(String name) {
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;

        try {
            check();
            preparedStatement = connection.prepareStatement("SELECT ID,NAME,PHONE,EMAIL FROM SupplierInformation WHERE NAME='" + name + "'");
            resultSet = preparedStatement.executeQuery();
            return resultSet;
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionToDataBase.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {

                //preparedStatement.close();
            } catch (Exception ex) {
                Logger.getLogger(ConnectionToDataBase.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    public boolean addNewSupllier(String name, String password, String email, String imagePath) {
        PreparedStatement preparedStatement = null;
        try {
            check();
            preparedStatement = connection.prepareStatement("INSERT  INTO SupplierInformation VALUES(?,?,?,?,?)");

            preparedStatement.setString(2, name);
            preparedStatement.setString(3, password);
            preparedStatement.setString(4, email);
            try {
                if (imagePath != null && !imagePath.isEmpty()) {
                    FileInputStream fis = new FileInputStream(imagePath);
                    preparedStatement.setBinaryStream(5, fis, fis.available());
                } else {
                    FileInputStream fis = new FileInputStream(imagePath);
                    preparedStatement.setBinaryStream(5, fis, fis.available());

                }
            } catch (IOException ex) {
                System.out.println(ex);
            }
            preparedStatement.executeUpdate();
            changgeTheNotificationOfAdmin("You Add New Supllier UserName is: ", true);
        } catch (SQLException ex) {
            Desgin2.commonMethods.getNotification("you must input value different!!/n this is repeated value", "Wrong", "error");
            return false;
        } finally {
            try {

                preparedStatement.close();

            } catch (Exception ex) {
                Logger.getLogger(ConnectionToDataBase.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return true;
    }

    public void deleteSupplierWithId(int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM SupplierInformation WHERE ID='" + id + "'");
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionToDataBase.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean updateImagePath(int id, String imagePath) {
        try {
            PreparedStatement statement = connection.prepareStatement("UPDATE SupplierInformation SET IMAGE=? WHERE ID='" + id + "'");
            FileInputStream fis = toRecordImage(imagePath);
            try {
                statement.setBinaryStream(1, fis, fis.available());//we deal with photo as big data to store in data base  so i store it as binary and devision it when store..                   prepareStatement.executeUpdate();
            } catch (IOException ex) {
                Logger.getLogger(ConnectionToDataBase.class.getName()).log(Level.SEVERE, null, ex);
                return false;

            }
            return statement.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionToDataBase.class.getName()).log(Level.SEVERE, null, ex);
            return false;

        }
    }

    public Image getSupplierImage(int id) {
        PreparedStatement st = null;
        ResultSet rs = null;
        ImageIcon formatImageIcon = null;

        try {//EMAIL
            check();
            st = connection.prepareStatement("SELECT IMAGE FROM SupplierInformation WHERE ID='" + id + "'");
            rs = st.executeQuery();

            if (rs.next()) {
                //rs.getBinaryStream(emialAdmin)
                //java.sql.Blob blob = rs.getBlob(5);
                InputStream in = rs.getBinaryStream("IMAGE");
                try {
                    OutputStream os = new FileOutputStream(new File("photo.jpg"));
                    byte[] content = new byte[1024];
                    int size = 0;
                    if (in != null) {
                        while ((size = in.read(content)) != -1) {
                            os.write(content, 0, size);
                        }
                    }
                    Image img = new Image("file:photo.jpg", 100, 150, true, true);
                    if (os != null) {
                        os.close();
                    }
                    if (in != null) {
                        in.close();
                    }
                    return img;

                } catch (FileNotFoundException ex) {
                } catch (IOException ex) {
                }

            }
            return null;

        } catch (SQLException ex) {
            Logger.getLogger(ConnectionToDataBase.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {

                st.close();
                rs.close();
            } catch (Exception ex) {
                Logger.getLogger(ConnectionToDataBase.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;

    }

    public void getAllSupplierEmail(ArrayList<String> list) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            check();
            st = connection.prepareStatement("SELECT EMAIL FROM SupplierInformation");
            rs = st.executeQuery();
            while (rs.next()) {
                list.add(rs.getString("EMAIL"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionToDataBase.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                st.close();
                rs.close();
            } catch (Exception ex) {
                Logger.getLogger(ConnectionToDataBase.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public String getSupplierPhone(String name) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            check();
            st = connection.prepareStatement("SELECT PHONE FROM SupplierInformation WHERE NAME='" + name + "'");
            rs = st.executeQuery();
            if (rs.next()) {
                return rs.getString("PHONE");
            }
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionToDataBase.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                st.close();
                rs.close();
            } catch (Exception ex) {
                Logger.getLogger(ConnectionToDataBase.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;

    }

    public void changgeSupplierName(int id, String newName) {
        Statement statement = null;
        try {
            check();
            statement = connection.createStatement();
            statement.execute("UPDATE SupplierInformation SET NAME='" + newName + "' WHERE ID='" + id + "'");
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionToDataBase.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                statement.close();
            } catch (Exception ex) {
                Logger.getLogger(ConnectionToDataBase.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public void changgeSupplierPhone(int id, String newPhone) {
        Statement statement = null;
        try {
            check();
            statement = connection.createStatement();
            statement.execute("UPDATE SupplierInformation SET PHONE='" + newPhone + "' WHERE ID='" + id + "'");
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionToDataBase.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                statement.close();
            } catch (Exception ex) {
                Logger.getLogger(ConnectionToDataBase.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public void changgeSupplierEmail(int id, String newemail) {
        Statement statement = null;
        try {
            check();
            statement = connection.createStatement();
            statement.execute("UPDATE SupplierInformation SET EMAIL='" + newemail + "' WHERE ID='" + id + "'");
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionToDataBase.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                statement.close();
            } catch (Exception ex) {
                Logger.getLogger(ConnectionToDataBase.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public ResultSet giveAllCategoriesInformation() {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement("SELECT ID,NAME,TYPE,PRICE,COUNT,COLOR,GENDER FROM Categories WHERE ISDELETED='0'");
            resultSet = preparedStatement.executeQuery();
            return resultSet;
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionToDataBase.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public ResultSet giveAllCategoriesInformationForAdmin() {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement("SELECT ID,NAME,TYPE,PRICE,COUNT,COLOR,GENDER FROM Categories WHERE ISDELETED='0'");
            resultSet = preparedStatement.executeQuery();
            return resultSet;
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionToDataBase.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public ResultSet giveAllCategoriesDeleted() {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement("SELECT ID,NAME,TYPE,PRICE,COUNT,COLOR,GENDER FROM Categories WHERE ISDELETED='1'");
            resultSet = preparedStatement.executeQuery();
            return resultSet;
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionToDataBase.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void getAllCategoriesName(ArrayList<String> list, String isDeleted) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            check();
            st = connection.prepareStatement("SELECT NAME FROM Categories WHERE ISDELETED='" + isDeleted + "'");
            rs = st.executeQuery();
            while (rs.next()) {
                list.add(rs.getString("NAME"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionToDataBase.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                st.close();
                rs.close();
            } catch (Exception ex) {
                Logger.getLogger(ConnectionToDataBase.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public void getAllCategoriesType(ArrayList<String> list) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            check();
            st = connection.prepareStatement("SELECT TYPE FROM Categories");
            rs = st.executeQuery();
            while (rs.next()) {
                list.add(rs.getString("TYPE"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionToDataBase.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                st.close();
                rs.close();
            } catch (Exception ex) {
                Logger.getLogger(ConnectionToDataBase.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public void getAllCategoriesColor(ArrayList<String> list) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            check();
            st = connection.prepareStatement("SELECT COLOR FROM Categories");
            rs = st.executeQuery();
            while (rs.next()) {
                list.add(rs.getString("COLOR"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionToDataBase.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                st.close();
                rs.close();
            } catch (Exception ex) {
                Logger.getLogger(ConnectionToDataBase.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public void deleteCategoriesWithId(int id, String isdelete) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement("UPDATE Categories SET ISDELETED='" + isdelete + "'" + "WHERE ID='" + id + "'");
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionToDataBase.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(ConnectionToDataBase.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void deleteCategoriesWithId(int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM Categories WHERE ID='" + id + "'");
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionToDataBase.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Image getCaategoriesImage(int id) {
        PreparedStatement st = null;
        ResultSet rs = null;
        ImageIcon formatImageIcon = null;

        try {
            check();
            st = connection.prepareStatement("SELECT IMAGE FROM Categories WHERE ID='" + id + "'");
            rs = st.executeQuery();

            if (rs.next()) {
                //rs.getBinaryStream(emialAdmin)
                //java.sql.Blob blob = rs.getBlob(5);
                InputStream in = rs.getBinaryStream("IMAGE");
                try {
                    OutputStream os = new FileOutputStream(new File("photo.jpg"));
                    byte[] content = new byte[1024];
                    int size = 0;
                    try {
                        while ((size = in.read(content)) != -1) {
                            os.write(content, 0, size);
                        }
                    } catch (Exception ex) {
                        //System.out.println(ex);
                    }
                    Image img = new Image("file:photo.jpg", 100, 150, true, true);
                    if (os != null) {
                        os.close();
                    }
                    if (in != null) {
                        in.close();
                    }
                    return img;

                } catch (FileNotFoundException ex) {
                } catch (IOException ex) {
                }

            }
            return null;

        } catch (SQLException ex) {
            Logger.getLogger(ConnectionToDataBase.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {

                st.close();
                rs.close();
            } catch (Exception ex) {
                Logger.getLogger(ConnectionToDataBase.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;

    }

    public boolean addNewCategories(String name, String type, String price, String count, String color, String gender, String imagePath) {
        PreparedStatement preparedStatement = null;
        try {
            check();
            preparedStatement = connection.prepareStatement("INSERT  INTO Categories VALUES(?,?,?,?,?,?,?,?,?)");
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, type);
            preparedStatement.setString(4, price);
            preparedStatement.setString(5, count);
            preparedStatement.setString(6, color);
            preparedStatement.setString(7, gender);

            try {
                FileInputStream fis = new FileInputStream(imagePath);
                preparedStatement.setBinaryStream(8, fis, fis.available());
            } catch (IOException ex) {
                System.out.println(ex);
            }
            preparedStatement.setString(9, "0");

            preparedStatement.executeUpdate();
            changgeTheNotificationOfAdmin("New Categories was Added sucssfuuly and his type is:" + type + "..^^", true);
        } catch (SQLException ex) {
            Desgin2.commonMethods.getNotification("you must input value different!!"
                    + "this is repeated value May  May be this Type is Deleted restore it or delte from owner review", "Wrong", "error");
            return false;
        } finally {
            try {

                preparedStatement.close();

            } catch (Exception ex) {
                Logger.getLogger(ConnectionToDataBase.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return true;
    }

    public boolean isCategoriesTypeFound(String type) {
        String notification = "";
        PreparedStatement statement = null;
        ResultSet rs = null;

        try {
            check();
            statement = connection.prepareStatement("SELECT * FROM Categories WHERE TYPE='" + type + "'");
            rs = statement.executeQuery();
            if (rs.next() && rs.getString("ISDELETED").equals("0")) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionToDataBase.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                statement.close();
                rs.close();
            } catch (Exception ex) {
                Logger.getLogger(ConnectionToDataBase.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return false;
    }

    public String notificationOfEmplyee(String newNot) {
        String notification = "";
        PreparedStatement statement = null;
        ResultSet rs = null;

        try {
            check();
            statement = connection.prepareStatement("SELECT NAME,NOTIFICATION FROM EmployeeInformation");
            rs = statement.executeQuery();
            if (rs.next()) {
                String name = rs.getString("NAME");
                notification = getNotificationEmployee(name);
                //changgeTheNotificationOfEmployee(notification, newNot, name);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionToDataBase.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                statement.close();
                rs.close();
            } catch (Exception ex) {
                Logger.getLogger(ConnectionToDataBase.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return notification;
    }

    public ResultSet getEmployeeWithId(String id) {
        PreparedStatement statement = null;
        ResultSet rs = null;

        try {
            check();
            statement = connection.prepareStatement("SELECT ID,NAME,COUNTOFDAY,SALARY FROM EmployeeInformation WHERE ID='" + id + "'");
            rs = statement.executeQuery();
            return rs;
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionToDataBase.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                statement.close();
                rs.close();
            } catch (Exception ex) {
                Logger.getLogger(ConnectionToDataBase.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    public String countOfEmployee(String id) {
        PreparedStatement statement = null;
        ResultSet rs = null;

        try {
            check();
            statement = connection.prepareStatement("SELECT COUNTOFDAY FROM EmployeeInformation WHERE ID='" + id + "'");
            rs = statement.executeQuery();
            if (rs.next()) {
                String name = rs.getString("COUNTOFDAY");
                return name;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionToDataBase.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                statement.close();
                rs.close();
            } catch (Exception ex) {
                Logger.getLogger(ConnectionToDataBase.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return "";
    }

    public String getNotificationEmployee(String name) {
        String notification = "";
        PreparedStatement statement = null;
        ResultSet rs = null;

        try {
            check();
            if (Desgin2.commonMethods.isEmail(name)) {
                statement = connection.prepareStatement("SELECT NOTIFICATION FROM EmployeeInformation WHERE EMAIL='" + name + "'");
            } else {
                statement = connection.prepareStatement("SELECT NOTIFICATION FROM EmployeeInformation WHERE NAME='" + name + "'");
            }
            rs = statement.executeQuery();
            if (rs.next()) {
                notification = rs.getString("NOTIFICATION");
            }

        } catch (SQLException ex) {
            Logger.getLogger(ConnectionToDataBase.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                statement.close();
                rs.close();
            } catch (Exception ex) {
                Logger.getLogger(ConnectionToDataBase.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return notification;
    }

    public void changgeTheNotificationOfEmployee(String newNotification, String email, boolean isAddNewNot) {
        Statement statement = null;
        try {
            check();
            statement = connection.createStatement();
            if (email.equals("all")) {
                ArrayList<String> list = new ArrayList<>();
                getAllEmployeeEmail(list);
                while (list.size() > 0) {
                    email = list.get(0);
                    statement.execute("UPDATE EmployeeInformation SET NOTIFICATION='" + newNotification
                            + (isAddNewNot ? getNotificationEmployee(email) : "") + "' WHERE EMAIL='" + email + "'");
                    list.remove(0);
                }

            } else {
                if (Desgin2.commonMethods.isEmail(email)) {
                    statement.execute("UPDATE EmployeeInformation SET NOTIFICATION='" + newNotification
                            + (isAddNewNot ? getNotificationEmployee(email) : "") + "' WHERE EMAIL='" + email + "'");
                } else {
                    statement.execute("UPDATE EmployeeInformation SET NOTIFICATION='" + newNotification
                            + (isAddNewNot ? getNotificationEmployee(email) : "") + "' WHERE NAME='" + email + "'");
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(ConnectionToDataBase.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                statement.close();
            } catch (Exception ex) {
                Logger.getLogger(ConnectionToDataBase.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public void changgeTheNotificationOfEmployee(String newNotification, int id, boolean isAddNewNot) {
        Statement statement = null;
        try {
            check();
            statement = connection.createStatement();
            statement.execute("UPDATE EmployeeInformation SET NOTIFICATION='" + newNotification
                    + (isAddNewNot ? getNotificationEmployee(id) : "") + "' WHERE ID='" + id + "'");
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionToDataBase.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                statement.close();
            } catch (Exception ex) {
                Logger.getLogger(ConnectionToDataBase.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public String getNotificationEmployee(int id) {
        String notification = "";
        PreparedStatement statement = null;
        ResultSet rs = null;

        try {
            check();
            statement = connection.prepareStatement("SELECT NOTIFICATION FROM EmployeeInformation WHERE ID='" + id + "'");
            rs = statement.executeQuery();
            if (rs.next()) {
                notification = rs.getString("NOTIFICATION");
            }

        } catch (SQLException ex) {
            Logger.getLogger(ConnectionToDataBase.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                statement.close();
                rs.close();
            } catch (Exception ex) {
                Logger.getLogger(ConnectionToDataBase.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return notification;
    }

    public void updateCategoriesName(int id, String newName) {
        Statement statement = null;
        try {
            check();
            statement = connection.createStatement();
            statement.execute("UPDATE Categories SET NAME='" + newName + "' WHERE ID='" + id + "'");
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionToDataBase.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                statement.close();
            } catch (Exception ex) {
                Logger.getLogger(ConnectionToDataBase.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public void updateCategoriesType(int id, String newType) {
        Statement statement = null;
        try {
            check();
            statement = connection.createStatement();
            statement.execute("UPDATE Categories SET TYPE='" + newType + "' WHERE ID='" + id + "'");
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionToDataBase.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                statement.close();
            } catch (Exception ex) {
                Logger.getLogger(ConnectionToDataBase.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public void updateCategoriesPrice(int id, String newPrice) {
        Statement statement = null;
        try {
            check();
            statement = connection.createStatement();
            statement.execute("UPDATE Categories SET PRICE='" + newPrice + "' WHERE ID='" + id + "'");
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionToDataBase.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                statement.close();
            } catch (Exception ex) {
                Logger.getLogger(ConnectionToDataBase.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public void updateCategoriesCount(int id, String newCount) {
        Statement statement = null;
        try {
            check();
            statement = connection.createStatement();
            statement.execute("UPDATE Categories SET COUNT='" + newCount + "' WHERE ID='" + id + "'");
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionToDataBase.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                statement.close();
            } catch (Exception ex) {
                Logger.getLogger(ConnectionToDataBase.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public void updateCategoriesCount(String type, String newCount) {
        PreparedStatement statement = null;
        try {
            check();
            statement = connection.prepareStatement("UPDATE Categories SET COUNT='" + newCount + "' WHERE TYPE='" + type + "'");
            statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionToDataBase.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                statement.close();
            } catch (Exception ex) {
                Logger.getLogger(ConnectionToDataBase.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public void updateCategoriesColor(int id, String newColor) {
        Statement statement = null;
        try {
            check();
            statement = connection.createStatement();
            statement.execute("UPDATE Categories SET COLOR='" + newColor + "' WHERE ID='" + id + "'");
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionToDataBase.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                statement.close();
            } catch (Exception ex) {
                Logger.getLogger(ConnectionToDataBase.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public void updateCategoriesGender(int id, String newGender) {
        Statement statement = null;
        try {
            check();
            statement = connection.createStatement();
            statement.execute("UPDATE Categories SET GENDER='" + newGender + "' WHERE ID='" + id + "'");
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionToDataBase.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                statement.close();
            } catch (Exception ex) {
                Logger.getLogger(ConnectionToDataBase.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public boolean updateImagePathForCategories(int id, String imagePath) {
        try {
            PreparedStatement statement = connection.prepareStatement("UPDATE Categories SET IMAGE=? WHERE ID='" + id + "'");
            FileInputStream fis = toRecordImage(imagePath);
            try {
                statement.setBinaryStream(1, fis, fis.available());//we deal with photo as big data to store in data base  so i store it as binary and devision it when store..                   prepareStatement.executeUpdate();
            } catch (IOException ex) {
                Logger.getLogger(ConnectionToDataBase.class.getName()).log(Level.SEVERE, null, ex);
                return false;

            }
            return statement.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionToDataBase.class.getName()).log(Level.SEVERE, null, ex);
            return false;

        }
    }

    public ResultSet giveCategoriesInformation(String name) {
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;

        try {
            check();
            preparedStatement = connection.prepareStatement("SELECT ID,NAME,TYPE,PRICE,COUNT,COLOR,GENDER,ISDELETED FROM Categories WHERE NAME='" + name + "'");
            resultSet = preparedStatement.executeQuery();
            return resultSet;
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionToDataBase.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
            } catch (Exception ex) {
                Logger.getLogger(ConnectionToDataBase.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    public int giveCategoriesPrice(String name) {
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;

        try {
            check();
            preparedStatement = connection.prepareStatement("SELECT PRICE FROM Categories WHERE TYPE='" + name + "'");
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Integer.parseInt(resultSet.getString("PRICE"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionToDataBase.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                resultSet.close();
                preparedStatement.close();
            } catch (Exception ex) {
                Logger.getLogger(ConnectionToDataBase.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return 1;
    }

    public ResultSet giveAllInvoicesInformation() {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM Invoices");
            resultSet = preparedStatement.executeQuery();
            return resultSet;
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionToDataBase.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void giveAllDataInvoices(ArrayList<String> list) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement("SELECT DATE FROM Invoices");
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                list.add(resultSet.getString("DATE"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionToDataBase.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void giveAllIDInvoices(ArrayList<String> list) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement("SELECT ID FROM Invoices");
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                list.add(resultSet.getString("ID"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionToDataBase.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void giveAllNameCustomerInvoices(ArrayList<String> list) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement("SELECT NAMECUSTOMER FROM Invoices");
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String name = resultSet.getString("NAMECUSTOMER");
                if (!list.contains(name)) {
                    list.add(resultSet.getString("NAMECUSTOMER"));
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionToDataBase.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void giveAllNameTypeInvoices(ArrayList<String> list) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement("SELECT NAMETYPE FROM Invoices");
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                list.add(resultSet.getString("NAMETYPE"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionToDataBase.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ResultSet giveInvoicesrInformation(String name) {
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;

        try {
            check();
            preparedStatement = connection.prepareStatement(""
                    + "SELECT ID,NAMECUSTOMER,NAMETYPE,DATE,QUNTITY,PHONEOFSUPLLIER,TYPEINVOICES FROM Invoices WHERE NAMECUSTOMER='" + name + "'");
            resultSet = preparedStatement.executeQuery();
            return resultSet;
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionToDataBase.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
            } catch (Exception ex) {
                Logger.getLogger(ConnectionToDataBase.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    public ResultSet giveInvoicesInformation(String date) {
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;

        try {
            check();
            preparedStatement = connection.prepareStatement("SELECT ID,NAMECUSTOMER,NAMETYPE,DATE,QUNTITY,PHONEOFSUPLLIER,TYPEINVOICES FROM Invoices WHERE DATE='" + date + "'");
            resultSet = preparedStatement.executeQuery();
            return resultSet;
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionToDataBase.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
            } catch (Exception ex) {
                Logger.getLogger(ConnectionToDataBase.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    public ResultSet giveInvoicesInformationFromTypeInvoices(String TypeInvoices) {
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;

        try {
            check();
            preparedStatement = connection.prepareStatement("SELECT ID,NAMECUSTOMER,NAMETYPE,DATE,QUNTITY,PHONEOFSUPLLIER,TYPEINVOICES FROM "
                    + "Invoices WHERE TYPEINVOICES='" + (TypeInvoices.equals("Sold") ? "0" : "1") + "'");
            resultSet = preparedStatement.executeQuery();
            return resultSet;
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionToDataBase.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {

            } catch (Exception ex) {
                Logger.getLogger(ConnectionToDataBase.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    public ResultSet giveInvoicesInformationFromTypeName(String TypeName) {
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;

        try {
            check();
            preparedStatement = connection.prepareStatement("SELECT ID,NAMECUSTOMER,NAMETYPE,DATE,QUNTITY,PHONEOFSUPLLIER,TYPEINVOICES FROM Invoices WHERE NAMETYPE='" + TypeName + "'");
            resultSet = preparedStatement.executeQuery();
            return resultSet;
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionToDataBase.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {

            } catch (Exception ex) {
                Logger.getLogger(ConnectionToDataBase.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    public ResultSet giveInvoicesInformationWithId(String ID) {
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;

        try {
            check();
            preparedStatement = connection.prepareStatement("SELECT ID,NAMECUSTOMER,NAMETYPE,DATE,QUNTITY,PHONEOFSUPLLIER,TYPEINVOICES FROM Invoices WHERE ID='" + ID + "'");
            resultSet = preparedStatement.executeQuery();
            return resultSet;
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionToDataBase.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {

            } catch (Exception ex) {
                Logger.getLogger(ConnectionToDataBase.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    public void addInvoices(String TYPEINVOICES, String NAMECUSTOMER, String NAMETYPE, String DATE, String QUNTITY, String PHONEOFSUPLLIER) {
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;

        try {
            check();
            preparedStatement = connection.prepareStatement("INSERT INTO Invoices VALUES(?,?,?,?,?,?,?)");
            preparedStatement.setString(2, TYPEINVOICES);
            preparedStatement.setString(3, NAMECUSTOMER);
            preparedStatement.setString(4, NAMETYPE);
            preparedStatement.setString(5, DATE);
            preparedStatement.setString(6, QUNTITY);
            preparedStatement.setString(7, PHONEOFSUPLLIER);
            preparedStatement.executeUpdate();
            changgeTheNotificationOfAdmin("Your Employee Added New Invoices Sucssefully!!^^", true);
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionToDataBase.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
            } catch (Exception ex) {
                Logger.getLogger(ConnectionToDataBase.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void addSalce(String NAMEEMPLOYEE, String NAMETYPE, String DATE, String QUNTITY, String PHONEOFEMPLOYEE) {
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;

        try {
            check();
            preparedStatement = connection.prepareStatement("INSERT INTO Salce VALUES(?,?,?,?,?,?)");
            preparedStatement.setString(2, NAMETYPE);
            preparedStatement.setString(3, QUNTITY);
            preparedStatement.setString(4, DATE);
            preparedStatement.setString(5, PHONEOFEMPLOYEE);
            preparedStatement.setString(6, NAMEEMPLOYEE);
            preparedStatement.executeUpdate();
            changgeTheNotificationOfAdmin("Your Employee: " + NAMEEMPLOYEE + "Buy Salce with Quntity: " + QUNTITY + "!!^^", true);
            changgeTheNotificationOfEmployee("You Employee: "
                    + NAMEEMPLOYEE + "Buy Salce with Quntity: " + QUNTITY + "!!^^" + "^^", "all", true);

        } catch (SQLException ex) {
            Logger.getLogger(ConnectionToDataBase.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
            } catch (Exception ex) {
                Logger.getLogger(ConnectionToDataBase.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public ArrayList<String> giveCategoriesType() {
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        ArrayList<String> list = new ArrayList<>();

        try {
            check();
            preparedStatement = connection.prepareStatement("SELECT TYPE FROM Categories");
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                list.add(resultSet.getString("TYPE"));
            }
            return list;
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionToDataBase.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {

            } catch (Exception ex) {
                Logger.getLogger(ConnectionToDataBase.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    public void deleteSalceWithId(int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM Salce WHERE ID='" + id + "'");
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionToDataBase.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void deleteInvoicesWithId(int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM Invoices WHERE ID='" + id + "'");
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionToDataBase.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void deleteInvoicesWithType(String Type) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM Invoices WHERE NAMETYPE='" + Type + "'");
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionToDataBase.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ResultSet giveAllSalceInformation() {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM Salce");
            resultSet = preparedStatement.executeQuery();
            return resultSet;
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionToDataBase.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public String getEmployeeName(String name) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            check();
            st = connection.prepareStatement("SELECT NAME FROM EmployeeInformation WHERE EMAIL='" + name + "'");
            rs = st.executeQuery();
            if (rs.next()) {
                return rs.getString("NAME");
            }

        } catch (SQLException ex) {
            Logger.getLogger(ConnectionToDataBase.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                st.close();
                rs.close();
            } catch (Exception ex) {
                Logger.getLogger(ConnectionToDataBase.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;

    }

    public String getEmployeePhone(String name) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            check();
            st = connection.prepareStatement("SELECT PHONENUMBER FROM EmployeeInformation WHERE NAME='" + name + "'");
            rs = st.executeQuery();
            if (rs.next()) {
                return rs.getString("PHONENUMBER");
            }

        } catch (SQLException ex) {
            Logger.getLogger(ConnectionToDataBase.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                st.close();
                rs.close();
            } catch (Exception ex) {
                Logger.getLogger(ConnectionToDataBase.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;

    }

    public boolean isEmployeeNameFound(String name) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            check();
            st = connection.prepareStatement("SELECT NAME FROM EmployeeInformation");
            rs = st.executeQuery();
            while (rs.next()) {
                String name1 = rs.getString("NAME");
                if (name1.equals(name)) {
                    return true;
                }
            }
            return false;

        } catch (SQLException ex) {
            Logger.getLogger(ConnectionToDataBase.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                st.close();
                rs.close();
            } catch (Exception ex) {
                Logger.getLogger(ConnectionToDataBase.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return false;

    }

    public String CategoriesNameCountLess(String name) {
        PreparedStatement st = null;
        ResultSet rs = null;
        String count = null;
        try {
            check();
            st = connection.prepareStatement("SELECT COUNT FROM Categories WHERE TYPE='" + name + "'");
            rs = st.executeQuery();
            if (rs.next()) {
                count = rs.getString("COUNT");
                return count;
            }

        } catch (SQLException ex) {
            Logger.getLogger(ConnectionToDataBase.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                st.close();
                rs.close();
            } catch (Exception ex) {
                Logger.getLogger(ConnectionToDataBase.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return count;

    }

}
