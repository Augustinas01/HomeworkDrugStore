package models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import constants.Gender;
import models.Transaction;

public class Customer {

    private int costumerID;
    private double discount;
    private String firstName,lastName,email;
    private Gender gender;
    private Date dateOfBirth;
    private List<Transaction> transactionList;





    public Customer(){
        transactionList = new ArrayList<>();
        discount = ThreadLocalRandom.current().nextDouble(0,20);
    }

    //region SET&GET


    public double getDiscount() {
        return discount;
    }

    public int getCostumerID() {
        return costumerID;
    }

    public void setCostumerID(int costumerID) {
        this.costumerID = costumerID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(String gender) {

        this.gender = (gender.equals(Gender.MEN.name().toLowerCase()))? Gender.MEN:Gender.WOMEN;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public List<Transaction> getTransactionList() {
        return transactionList;
    }

    public void setTransactionList(List<Transaction> transactionList) {
        this.transactionList = transactionList;
    }

    //endregion

    public void printPurchaseHistory(){

    }
    public void makePurchase(){

    }

}
