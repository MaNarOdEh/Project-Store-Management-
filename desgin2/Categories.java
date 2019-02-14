package desgin2;

import javafx.beans.property.SimpleStringProperty;

public class Categories {

    private SimpleStringProperty ID;
    private SimpleStringProperty name;
    private SimpleStringProperty Type;
    private SimpleStringProperty price;
    private SimpleStringProperty count;
    private SimpleStringProperty color;
    private SimpleStringProperty gender;

    public Categories(String ID, String name, String Type, String price, String count, String color, String gender) {
        this.ID = new SimpleStringProperty(ID);
        this.name = new SimpleStringProperty(name);
        this.Type = new SimpleStringProperty(Type);
        this.price = new SimpleStringProperty(price);
        this.count = new SimpleStringProperty(count);
        this.color = new SimpleStringProperty(color);
        this.gender = new SimpleStringProperty(gender);
    }

    public String getID() {
        return ID.get();
    }

    public String getName() {
        return name.get();
    }

    public String getType() {
        return Type.get();
    }

    public String getPrice() {
        return price.get();
    }

    public String getCount() {
        return count.get();
    }

    public String getColor() {
        return color.get();
    }

    public String getGender() {
        return gender.get();
    }

    public void setID(String ID) {
        this.ID.set(ID);
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public void setType(String Type) {
        this.Type.set(Type);
    }

    public void setPrice(String price) {
        this.price.set(price);
    }

    public void setCount(String count) {
        this.count.set(count);
    }

    public void setColor(String color) {
        this.color.set(color);
    }

    public void setGender(String gender) {
        this.gender.set(gender);
    }

}
