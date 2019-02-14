package desgin2;

import javafx.beans.property.SimpleStringProperty;

public class Invoices {

    private SimpleStringProperty ID;
    private SimpleStringProperty typeInvoices;
    private SimpleStringProperty nameCustomer;
    private SimpleStringProperty nameType;
    private SimpleStringProperty date;
    private SimpleStringProperty quntity;
    private SimpleStringProperty price;
    private SimpleStringProperty phoneOfSupllier;

    public Invoices(String ID, String nameCustomer, String nameType, String date, String quntity, String price, String phoneOfSupllier) {
        this.ID = new SimpleStringProperty(ID);
        this.nameCustomer = new SimpleStringProperty(nameCustomer);
        this.nameType = new SimpleStringProperty(nameType);
        this.date = new SimpleStringProperty(date);
        this.quntity = new SimpleStringProperty(quntity);
        this.price = new SimpleStringProperty(price);
        this.phoneOfSupllier = new SimpleStringProperty(phoneOfSupllier);
    }

    public Invoices(String ID, String nameCustomer, String nameType, String date, String quntity, String price, String phoneOfSupllier, String typeInvoices) {
        this.ID = new SimpleStringProperty(ID);
        this.nameCustomer = new SimpleStringProperty(nameCustomer);
        this.nameType = new SimpleStringProperty(nameType);
        this.date = new SimpleStringProperty(date);
        this.quntity = new SimpleStringProperty(quntity);
        this.price = new SimpleStringProperty(price);
        this.phoneOfSupllier = new SimpleStringProperty(phoneOfSupllier);
        this.typeInvoices = new SimpleStringProperty(typeInvoices);
    }

    public String getID() {
        return ID.get();
    }

    public void setID(String ID) {
        this.ID.set(ID);
    }

    public String getTypeInvoices() {
        return typeInvoices.get();
    }

    public void setTypeInvoices(String typeInvoices) {
        this.typeInvoices.set(typeInvoices);
    }

    public String getNameCustomer() {
        return nameCustomer.get();
    }

    public void setNameCustomer(String nameCustomer) {
        this.nameCustomer.set(nameCustomer);
    }

    public String getNameType() {
        return nameType.get();
    }

    public void setNameType(String nameType) {
        this.nameType.set(nameType);
    }

    public String getDate() {
        return date.get();
    }

    public void setDate(String date) {
        this.date.set(date);
    }

    public String getQuntity() {
        return quntity.get();
    }

    public void setQuntity(String quntity) {
        this.quntity.set(quntity);
    }

    public String getPrice() {
        return price.get();
    }

    public void setPrice(String price) {
        this.price.set(price);
    }

    public String getPhoneOfSupllier() {
        return phoneOfSupllier.get();
    }

    public void setPhoneOfSupllier(String phoneOfSupllier) {
        this.phoneOfSupllier.set(phoneOfSupllier);
    }

}
