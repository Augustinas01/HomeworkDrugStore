package view;

import constants.Titles;
import models.AppCache;

import javax.swing.*;
import java.awt.*;


public class MainWindow extends JFrame {

    private AppCache appCache = AppCache.getInstance();
    private LoginPanel loginPanel;
    private MainMenuPanel mainMenu;



    public MainWindow(){
        setTitle(Titles.APP_TITLE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setSize(850,600);
        setResizable(false);
        setLocationRelativeTo(null);
    }

    public void init(){
        loginPanel = new LoginPanel();
        add(BorderLayout.CENTER,loginPanel);
    }

    public LoginPanel getLoginPanel(){
        return loginPanel;
    }

    public void showMainMenu(){
        loginPanel.setVisible(false);
        remove(loginPanel);
        mainMenu = new MainMenuPanel();
        this.add(BorderLayout.CENTER,mainMenu);
        mainMenu.setVisible(true);

    }



}
