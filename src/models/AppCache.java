package models;

import constants.UserType;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

public class AppCache {

    private static AppCache appCache_instance = null;


    private ActionListener mainWindowButtonsListener;

    private Customer user;

    private HashMap<Medicine,Integer> shoppingCart;


    private AppCache(){
        shoppingCart = new HashMap<>();

    }


    //region Getters
    public static AppCache getInstance(){

        if(appCache_instance == null){
            appCache_instance = new AppCache();
        }

        return appCache_instance;
    }



    public Customer getUser() {
        return user;
    }



    public ActionListener getMainWindowButtonsListener() {
        return mainWindowButtonsListener;
    }

    public HashMap<Medicine, Integer> getShoppingCart() {
        return shoppingCart;
    }

    //endregion

    public void setUser(Customer c){
        user = c;
    }


    public void setMainWindowButtonsListener(ActionListener mainWindowButtonsListener) {
        this.mainWindowButtonsListener = mainWindowButtonsListener;
    }


    public void addToCart(Medicine med){
        if (shoppingCart.containsKey(med)) {
            shoppingCart.put(med, (shoppingCart.get(med) + 1));
        } else {
            shoppingCart.put(med, 1);
        }
    }



}
