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
        Intent receivedIntent = getIntent();
        accountName=receivedIntent.getStringExtra(this.getString(R.string.ACCOUNT_NAME_KEY_STRING));
        setContentView(R.layout.activity_shopping_lists);
        FloatingActionButton addButton = findViewById(R.id.floating_action_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle(R.string.AddButtonMenu)
                        .setItems(R.array.AddButtonOptions, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                       new ShoppingListAlertDialog(ShoppingListsActivity.this).buildInputAlertDialog(accountName,which);
                            }
                        });
                builder.create().show();
            }
        });



        if (savedInstanceState==null&&false){
           FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
           fragmentTransaction.add(R.id.container,ShoppingListsFragment.getInstance(),"my-shopping-lists");
           fragmentTransaction.commit();
        }

    }

}
