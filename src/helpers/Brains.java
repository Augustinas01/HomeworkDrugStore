package helpers;

import constants.Buttons;
import models.AppCache;
import models.Customer;
import view.MainWindow;

import java.awt.event.ActionEvent;
import java.util.Arrays;

public class Brains {

    AppCache appCache;
    DataManager dataManager;
    MainWindow view;

    public Brains(MainWindow view){
        appCache = AppCache.getInstance();
        appCache.setMainWindowButtonsListener(this::buttonsListener);
        dataManager = DataManager.getInstance();

        this.view = view;

        view.init();

        view.setVisible(true);

//        dataManager.fillMedicine();


    }

    public void buttonsListener(ActionEvent e) {
        switch (e.getActionCommand()){
            case Buttons.LOGIN -> login();
            case Buttons.SIGN_UP -> signUp();
        }
    }

    private void login(){

        if(dataManager.login(view.getLoginPanel().getUsername(), view.getLoginPanel().getPassword())){
            view.showMainMenu();
        }

    }
    private void signUp(){
        System.out.println("signup");
    }


}
