package view;

import constants.Buttons;
import helpers.DataManager;
import models.AppCache;
import models.Medicine;
import models.Transaction;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ShoppingCartPanel extends JPanel {

    private final AppCache appCache;
    private final DataManager dataManager;
    private final ActionListener l;



    public ShoppingCartPanel(ActionListener buttonListener){
        appCache = AppCache.getInstance();
        dataManager = DataManager.getInstance();
        l = buttonListener;

        setLayout(new BorderLayout());

        add(BorderLayout.CENTER, body());
        add(BorderLayout.SOUTH,footer());
//        setVisible(true);

    }

    //region Body

    private JPanel body(){

        JPanel body = new JPanel();

        JPanel bodyItemsGrid = new JPanel(new GridLayout(0,1));

        JScrollPane scrollPane = new JScrollPane(bodyItemsGrid);
        scrollPane.setPreferredSize(new Dimension(830,400));
        scrollPane.setBorder(null);

        for (Medicine med: appCache.getShoppingCart().keySet()){
            bodyItemsGrid.add(item(med,l));
        }

        body.add(scrollPane);
        return body;
    }

    private JPanel item(Medicine m, ActionListener l){

        JPanel item = new JPanel();

        //Icon
        ImageIcon cartIcon = new ImageIcon("src/resources.icons/pill.jpg");
        Image image = cartIcon.getImage(); // transform it
        Image newimg = image.getScaledInstance(50, 50,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
        cartIcon = new ImageIcon(newimg);

        JPanel rowLayout = new JPanel(new GridLayout(1,0));
        rowLayout.setOpaque(false);

//        JLabel buttonName = new JLabel("Item name");
//        buttonName.setHorizontalTextPosition(JLabel.CENTER);
        rowLayout.add(new JLabel(cartIcon));

        JPanel itemDescription = new JPanel(new GridLayout(0,1));

        String buttonName = (m.getTitle().contains("("))? m.getTitle().substring(0,m.getTitle().indexOf("(")) : m.getTitle();

        itemDescription.add(new JLabel(buttonName));
        itemDescription.add(new JLabel("Item description"));

        rowLayout.add(itemDescription);

        rowLayout.add(new JLabel());

        JPanel itemPriceArea = new JPanel(new GridLayout(0,1));

        itemPriceArea.add(new JLabel(String.format("Quantity: %s",appCache.getShoppingCart().get(m))));
        double totalPrice = 0;
        for(int i = 0;i<appCache.getShoppingCart().get(m);i++){
            totalPrice += m.getPrice();
        }
        itemPriceArea.add(new JLabel(String.format("Total price: %s$",totalPrice)));

        rowLayout.add(itemPriceArea);
        rowLayout.add(new JLabel());


        JButton removeItem = new JButton("Remove");
        removeItem.setActionCommand(Buttons.SHOW_CART);
        removeItem.addActionListener(e -> {
            appCache.getShoppingCart().remove(m);
            l.actionPerformed(e);
        });
        rowLayout.add(removeItem);


        item.add(rowLayout);


        return item;
    }


    //endregion

    //region Footer
    private JPanel footer(){
        JPanel footer = new JPanel();

        JButton addToCart = new JButton("Purchase");
        addToCart.setActionCommand(Buttons.SHOW_CART);
        addToCart.setBackground(Color.green);
        addToCart.addActionListener(this::purchase);

        JButton goBack = new JButton("Main menu");
        goBack.setActionCommand(Buttons.MAIN_MENU);
        goBack.addActionListener(l);

        footer.add(goBack);
        footer.add(addToCart);

        return footer;

    }

    private void purchase(ActionEvent e){
        dataManager.putTransaction(new Transaction());
        appCache.getShoppingCart().clear();
        l.actionPerformed(e);

    }

    //endregion



}
