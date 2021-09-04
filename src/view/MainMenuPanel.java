package view;

import constants.Buttons;
import models.AppCache;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;


public class MainMenuPanel extends JPanel {

    public static final String SHOP_PANEL = "Shop";
    public static final String CART_PANEL = "Cart";

    AppCache appCache = AppCache.getInstance();
    ShopPanel shopPanel;
    ShoppingCartPanel shoppingCartPanel;
    JPanel body;





    public MainMenuPanel(){
        this.setLayout(new BorderLayout());
        this.setOpaque(false);
        shopPanel = new ShopPanel(this::menuListener);


        //Content
        add(BorderLayout.NORTH,header());
//        shoppingCartPanel.setVisible(false);
        add(BorderLayout.CENTER,body());
//        add(BorderLayout.CENTER,shopPanel);


    }


    //region Header
    private JPanel header(){
//        JPanel header = new JPanel(new FlowLayout(FlowLayout.CENTER,150,20));
        JPanel header = new JPanel(new GridLayout(1,3));


        //Content
        JLabel title = new JLabel("DRUG STORE");
        title.setHorizontalAlignment(SwingConstants.CENTER);

        JButton goRegister = new JButton();
        goRegister.setBackground(Color.lightGray);
        goRegister.setOpaque(false);
//        goRegister.setMargin(new Insets(100,100,100,100));
        goRegister.setBorder(null);
        ImageIcon cartIcon = new ImageIcon("src/resources.icons/cart.png");
        Image image = cartIcon.getImage(); // transform it
        Image newimg = image.getScaledInstance(25, 25,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
        cartIcon = new ImageIcon(newimg);  // transform it back
        goRegister.setIcon(cartIcon);
//        goRegister.setPreferredSize(new Dimension(25,25));
        goRegister.setHorizontalAlignment(SwingConstants.RIGHT);
        goRegister.setActionCommand(Buttons.REGISTER);
        goRegister.addActionListener(appCache.getMainWindowButtonsListener());



//        JButton goBack = new JButton("Main menu");
//        goBack.setHorizontalAlignment(SwingConstants.RIGHT);
//        goBack.setActionCommand(Buttons.MAIN_MENU);
//        goBack.addActionListener(appCache.getMainWindowButtonsListener());


        //Addding Content
        header.add(userInfo());
        header.add(title);
//        header.add(goBack);
        header.add(goRegister);

        return header;
    }

    private JPanel userInfo(){
        JPanel userInfo = new JPanel(new GridLayout(2,1));

        JLabel nickname = new JLabel(String.format("User: %s", appCache.getUser().getFirstName()));
        JLabel gender = new JLabel(String.format("Gender: %s", appCache.getUser().getGender().name()));

        userInfo.add(nickname);
        userInfo.add(gender);
        return userInfo;

    }
    //endregion

    private JPanel body(){
        body = new JPanel(new CardLayout());
        body.add(shopPanel, SHOP_PANEL);

        CardLayout cl = (CardLayout) body.getLayout();
        cl.show(body, SHOP_PANEL);
        return body;
    }

    public void menuListener(ActionEvent e){
        switch (e.getActionCommand()){
            case Buttons.SHOW_CART ->{
                shoppingCartPanel = new ShoppingCartPanel(this::menuListener);
                body.add(shoppingCartPanel,CART_PANEL);
                CardLayout cl = (CardLayout) body.getLayout();
                cl.show(body, CART_PANEL);
            }
            case Buttons.MAIN_MENU ->{
                CardLayout cl = (CardLayout) body.getLayout();
                cl.show(body, SHOP_PANEL);
            }
        }


    }


}
