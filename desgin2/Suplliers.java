package desgin2;

import javafx.beans.property.SimpleStringProperty;

public class Suplliers {

    private SimpleStringProperty ID;
    private SimpleStringProperty name;
    private SimpleStringProperty phone;
    private SimpleStringProperty email;

    public Suplliers() {

    }

    public Suplliers(String id, String name, String phone, String email) {

        this.ID = new SimpleStringProperty(id);
        this.name = new SimpleStringProperty(name);
        this.phone = new SimpleStringProperty(phone);
        this.email = new SimpleStringProperty(email);
        

    }

    public String getID() {
        return ID.get();
    }

    public String getEmail() {
        return email.get();
    }

    public String getName() {
        return name.get();
    }

    public String getPhone() {
        return phone.get();
    }

    public void setID(String id) {
        ID.set(id);
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public void setPhone(String phone) {
        this.phone.set(phone);
    }

}
