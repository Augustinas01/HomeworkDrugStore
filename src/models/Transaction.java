package models;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class Transaction {

    private UUID transactionID;
    private int costumerID;
    private double totalPrice,discount;
    private LocalDateTime dateOfTransaction;
    private HashMap<Medicine,Integer> listOfMedicine;


    public Transaction(){
        AppCache appCache = AppCache.getInstance();
        transactionID = UUID.randomUUID();
        costumerID = appCache.getUser().getCostumerID();
        discount = appCache.getUser().getDiscount();
        listOfMedicine = appCache.getShoppingCart();
        calculateTotalPrice();
    }

    //region G&S
    public UUID getTransactionID() {
        return transactionID;
    }

    public int getCostumerID() {
        return costumerID;
    }

    public double getDiscount() {
        return discount;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public LocalDateTime getDateOfTransaction() {
        return dateOfTransaction;
    }

    public HashMap<Medicine,Integer> getListOfMedicine() {
        return listOfMedicine;
    }

    //endregion


    private void print(){
        printPurchase();
    }
    public void printPurchase(){

    }

    private void calculateTotalPrice(){
        listOfMedicine.forEach((m,q) -> {
            totalPrice += m.getPrice()*q;
        });
        totalPrice -= discount;
    }
}
