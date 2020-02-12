package com.github.andrzejzmudaa.commonshoppinglist.ListOfShoppingLists;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.github.andrzejzmudaa.commonshoppinglist.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ShoppingListsActivity extends AppCompatActivity {


    String accountName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_lists);
        Intent receivedIntent = getIntent();
        accountName=receivedIntent.getStringExtra(this.getString(R.string.ACCOUNT_NAME_KEY_STRING));

        if (savedInstanceState==null){
           FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
           fragmentTransaction.add(R.id.container,ShoppingListsFragment.getInstance(),"my_shopping_lists");
           fragmentTransaction.commit();
        }

    }

    public String getAccountName(){
        return accountName;
    }


}
