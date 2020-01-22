package com.github.andrzejzmudaa.commonshoppinglist.ListOfShoppingLists;


import java.security.SecureRandom;


public class ShoppingListItem {

    String shoppingListName;
    String whoAdded;
    String uniqueID;

    public ShoppingListItem(String shoppingListName,String whoAdded){
        this.shoppingListName=shoppingListName;
        this.whoAdded=whoAdded;
        this.uniqueID= generateRandomID();
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

