package view;

import constants.Buttons;
import models.AppCache;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class LoginPanel extends JPanel {
    JTextField username,companyID;
    JPasswordField password;
    JPanel body, personLogin,companyLogin, loginJP;

    private final ActionListener buttonListener = AppCache.getInstance().getMainWindowButtonsListener();



    //region Getters

    public String getUsername() {
        return username.getText();
    }

    public char[] getPassword() {
        return password.getPassword();
    }

    public JTextField getCompanyID() {
        return companyID;
    }

    public JPanel getPersonLoginJP() {
        return personLogin;
    }

    public JPanel getCompanyLoginJP() {
        return companyLogin;
    }

    public JPanel getLoginJP() {
        return loginJP;
    }
    //endregion


    public LoginPanel(){
        this.setLayout(new BorderLayout());

        this.personLogin = personLogin();
        this.loginJP = loginJP();
        this.add(BorderLayout.NORTH,new JLabel("Drug store",SwingConstants.CENTER));
        this.add(BorderLayout.CENTER,body());
    }

    //region Body
    private JPanel body(){
        this.body = new JPanel();
        this.body.setLayout(new FlowLayout(FlowLayout.CENTER,1000,5));

        //Content


        JPanel loginSignUpButtons = new JPanel();
        JButton login = new JButton(Buttons.LOGIN);
        JButton signUp = new JButton(Buttons.SIGN_UP);


        loginSignUpButtons.add(login);
        loginSignUpButtons.add(signUp);


        //Content Settings

        login.addActionListener(buttonListener);
        signUp.addActionListener(buttonListener);

        //Addding Content
        this.body.add(this.loginJP);
        this.body.add(loginSignUpButtons);


        return this.body;
    }
    //endregion

    private JPanel loginJP(){
        this.loginJP = new JPanel();
        this.loginJP.add(this.personLogin);

        return this.loginJP;
    }

    private JPanel personLogin(){
        this.personLogin = new JPanel(new FlowLayout(FlowLayout.CENTER,1000,5));
        this.personLogin.setPreferredSize(new Dimension(200,100));
        JLabel firstNameLabel = new JLabel("First name");
        JLabel lastNameLabel = new JLabel("Password");
        this.username = new JTextField(10);
        this.password = new JPasswordField(10);

        this.personLogin.add(firstNameLabel);
        this.personLogin.add(this.username);
        this.personLogin.add(lastNameLabel);
        this.personLogin.add(this.password);

        return this.personLogin;
    }

}
