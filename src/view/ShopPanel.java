package view;

import constants.Buttons;
import constants.SearchOptions;
import helpers.DataManager;
import models.AppCache;
import models.Medicine;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;

public class ShopPanel extends JPanel {

    private final AppCache appCache;
    private final DataManager dataManager;

    public ShopPanel (ActionListener cartButton){
        setLayout(new BorderLayout());
        appCache = AppCache.getInstance();
        dataManager = DataManager.getInstance();

//        setSize(400,0);

//        JPanel shopGrid = new JPanel(new GridLayout(0,5,1,1));
//        shopGrid.setSize(new Dimension(getWidth(),0));



//        add(BorderLayout.NORTH,searchSection(e -> System.out.println("lol")));
        add(BorderLayout.CENTER, body(dataManager.getMedicineMap()));
        add(BorderLayout.SOUTH,footer(cartButton));

//        add(shopGrid);

    }

    //region Search
    private JPanel searchSection(ActionListener listener){
        JPanel searchSection = new JPanel(new FlowLayout(FlowLayout.CENTER,50,10));
        searchSection.setBackground(Color.lightGray);
        GridLayout optionsLayout = new GridLayout(2,4);
        optionsLayout.setHgap(20);
        JPanel searchOptions = new JPanel(optionsLayout);
        searchOptions.setOpaque(false);
        JTextField byName = new JTextField(SearchOptions.BY_NAME.name(), 10);
        byName.setBorder(new LineBorder(Color.GRAY));
        byName.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                byName.setText("");
            }
        });
        JTextField byBrand = new JTextField(SearchOptions.BY_MANUFACTURER.name(), 10);
        byBrand.setBorder(new LineBorder(Color.GRAY));
        byBrand.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                byBrand.setText("");
            }
        });

        JTextField byOwner = new JTextField(SearchOptions.BY_NAME.name(), 10);
        byOwner.setBorder(new LineBorder(Color.GRAY));
        byOwner.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                byOwner.setText("");
            }
        });



        JLabel numPlateLabel = new JLabel(SearchOptions.BY_NAME.name());
        numPlateLabel.setHorizontalAlignment(JLabel.CENTER);
        JLabel brandLabel = new JLabel(SearchOptions.BY_NAME.name());
        brandLabel.setHorizontalAlignment(JLabel.CENTER);
        JLabel ownerLabel = new JLabel(SearchOptions.BY_NAME.name());
        ownerLabel.setHorizontalAlignment(JLabel.CENTER);


        JButton searchVehicle = new JButton("Search");
        searchVehicle.setActionCommand(Buttons.SEARCH_VEHICLE);
        searchVehicle.addActionListener(listener);

        searchOptions.add(numPlateLabel);
        searchOptions.add(ownerLabel);
        searchOptions.add(brandLabel);
        searchOptions.add(byName);
        searchOptions.add(byOwner);
        searchOptions.add(byBrand);
//        searchOptions.add(databaseSelection);

        searchSection.add(searchOptions);
        searchSection.add(searchVehicle);

        return searchSection;
    }
    //endregion

    //region Body

    private JPanel body(HashMap<Integer, Medicine> medMap){

        JPanel body = new JPanel();
        JPanel bodyItemsGrid = new JPanel(new GridLayout(0,5));

        JScrollPane scrollPane = new JScrollPane(bodyItemsGrid);
        scrollPane.setPreferredSize(new Dimension(830,400));
        scrollPane.setBorder(null);

        for (Medicine med: medMap.values()){
            bodyItemsGrid.add(item(med));
        }

        body.add(scrollPane);
        return body;
    }

    private JPanel item(Medicine m){
        JPanel item = new JPanel();
        item.setPreferredSize(new Dimension(100,150));
//        item.setBorder(new LineBorder(Color.black));
//        item.setMaximumSize(new Dimension(50,100));



        //Icon
        ImageIcon cartIcon = new ImageIcon("src/resources/icons/pill.jpg");
        Image image = cartIcon.getImage(); // transform it
        Image newimg = image.getScaledInstance(25, 25,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
        cartIcon = new ImageIcon(newimg);

        JPanel buttonLayout = new JPanel(new GridLayout(0,1));
        buttonLayout.setBorder(new LineBorder(Color.black));
        buttonLayout.setOpaque(false);
        String buttonName = (m.getTitle().contains("("))? m.getTitle().substring(0,m.getTitle().indexOf("(")) : m.getTitle();
        JLabel buttonNameLabel = new JLabel(buttonName);
        buttonNameLabel.setHorizontalAlignment(JLabel.CENTER);
        buttonLayout.add(buttonNameLabel);
        buttonLayout.add(new JLabel(cartIcon));

        JLabel itemPrice = new JLabel(String.valueOf(m.getPrice()));
        itemPrice.setHorizontalAlignment(JLabel.CENTER);
        buttonLayout.add(itemPrice);


        JButton addToCart = new JButton("Add to cart");
        addToCart.addActionListener(this::addToCartListener);
        addToCart.setActionCommand(Integer.toString(m.getMedicineID()));
        buttonLayout.add(addToCart);


        JButton button = new JButton();
        button.add(buttonLayout);
//        button.setIcon(cartIcon);
//        button.add(new JLabel("item name"));

        item.add(buttonLayout);


        return item;
    }

    private void addToCartListener(ActionEvent e){
        appCache.addToCart(dataManager.getMedicineMap().get(Integer.parseInt(e.getActionCommand())));
    }
    //endregion

    //region Footer
    private JPanel footer(ActionListener cartButton){
        JPanel footer = new JPanel(new BorderLayout());

        JButton viewCart = new JButton("View Cart");
        viewCart.setActionCommand(Buttons.SHOW_CART);
        viewCart.addActionListener(cartButton);
        viewCart.setBackground(Color.green);


        footer.add(BorderLayout.EAST,viewCart);

        return footer;

    }

    //endregion


}
