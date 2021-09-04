package models;

import constants.MedicineType;

import java.util.Date;
import java.util.List;

public class Medicine {
    private int medicineID,manufacturerID;
    private String title,description;
    private Manufacturer manufacturer;
    private MedicineType medicineType;
    private List<MedComponent> medComponentList;
    private double price;
    private Date created;
    private boolean childrenSafe;

    //region GET%SET

    public int getMedicineID() {
        return medicineID;
    }

    public void setMedicineID(int medicineID) {
        this.medicineID = medicineID;
    }

    public int getManufacturerID() {
        return manufacturerID;
    }

    public void setManufacturerID(int manufacturerID) {
        this.manufacturerID = manufacturerID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Manufacturer getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(Manufacturer manufacturer) {
        this.manufacturer = manufacturer;
    }

    public MedicineType getMedicineType() {
        return medicineType;
    }

    public void setMedicineType(String t) {

        for(MedicineType type: MedicineType.values()){
            if(t.equals(type.name())){
                medicineType = type;
            }
        }

    }

    public List<MedComponent> getMedComponentList() {
        return medComponentList;
    }

    public void setMedComponentList(List<MedComponent> medComponentList) {
        this.medComponentList = medComponentList;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public boolean isChildrenSafe() {
        return childrenSafe;
    }

    public void setChildrenSafe(boolean childrenSafe) {
        this.childrenSafe = childrenSafe;
    }

    //endregion

    public void addToCart(){

    }
}
