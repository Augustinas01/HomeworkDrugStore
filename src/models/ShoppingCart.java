package models;

import java.util.ArrayList;
import java.util.HashMap;

public class ShoppingCart extends ArrayList<Medicine> {

    HashMap<Medicine,Integer> quantity = new HashMap<>();


    @Override
    public boolean add(Medicine medicine) {
//        quantity.put(medicine, )
        return super.add(medicine);
    }
}
