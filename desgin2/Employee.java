package desgin2;

import javafx.beans.property.SimpleStringProperty;

public class Employee {

    private SimpleStringProperty ID;
    private SimpleStringProperty name;
    private SimpleStringProperty phone;
    private SimpleStringProperty email;
    private SimpleStringProperty salary;
    private SimpleStringProperty hour;
    private SimpleStringProperty dateStart;
    private SimpleStringProperty dateEnd;

    public Employee() {
    }

    public Employee(String ID, String name, String phone,String email ,String salary, String hour, String dateStart, String dateEnd) {
        this.ID = new SimpleStringProperty(ID);
        this.name =  new SimpleStringProperty(name);
        this.phone = new SimpleStringProperty(phone);
        this.email=new SimpleStringProperty(email);
        this.salary = new SimpleStringProperty(salary);
        this.hour = new SimpleStringProperty(hour);
        this.dateStart = new SimpleStringProperty(dateStart);
        this.dateEnd = new SimpleStringProperty(dateEnd);
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

    public String getSalary() {
        return salary.get();
    }

    public String getHour() {
        return hour.get();
    }

    public String getDateStart() {
        return dateStart.get();
    }

    public String getDateEnd() {
        return dateEnd.get();
    }

    public void setID(String id) {
        ID.set(id);
    }public void setEmail(String email) {
        ID.set(email);
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public void setPhone(String phone) {
        this.phone.set(phone);
    }

    public void setSalary(String salary) {
        this.salary.set(salary);
    }

    public void setHour(String hour) {
        this.hour.set(hour);
    }

    public void setDateStart(String dataStart) {
        this.dateStart.set(dataStart);
    }

    public void setDateEnd(String dateEnd) {
        this.dateEnd.set(dateEnd);
    }

}
