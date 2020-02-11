package com.github.andrzejzmudaa.commonshoppinglist.ListOfShoppingLists;


import android.util.Log;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;


public class ShoppingListItem {

    public String shoppingListName;
    public String whoAdded;

    //public String uniqueID;

    public ShoppingListItem(){};

    public ShoppingListItem(String shoppingListName,String whoAdded){
        this.shoppingListName=shoppingListName;
        this.whoAdded=whoAdded;
        //this.uniqueID= generateRandomID();
        //Log.d("ITEM_ID","ITEM_ID generated :"+uniqueID);
    }



    String generateRandomID(){
        int idStringLenght=10;
        String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        SecureRandom rnd = new SecureRandom();
        StringBuilder sb = new StringBuilder( idStringLenght );
        for( int i = 0; i < idStringLenght; i++ )
            sb.append( AB.charAt( rnd.nextInt(AB.length()) ) );
        return sb.toString();
    }


    }

