package com.github.andrzejzmudaa.commonshoppinglist;

import androidx.appcompat.app.AppCompatActivity;

import android.accounts.AccountManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.github.andrzejzmudaa.commonshoppinglist.ListOfShoppingLists.ShoppingListsActivity;
import com.github.andrzejzmudaa.commonshoppinglist.verifyGoogleServices.checkGoogleServicesActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    SharedPreferences globalSharedPreferences;
    final static String defaultAccountName = "Account name not registered";
    Button launchChooseUserActivityButton;
    String accountName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //sendDatabaseTest();

        try {
            globalSharedPreferences = getSharedPreferences(this.getString(R.string.GlobalSharedPreferencesString), Context.MODE_PRIVATE);
        } catch (Exception e) {
        }




    }

    @Override
    protected void onResume() {
        super.onResume();
        accountName = globalSharedPreferences.getString(this.getString(R.string.ACCOUNT_NAME_KEY_STRING),defaultAccountName);
        checkIfProperAccountName();
        launchChooseUserActivityButton = findViewById(R.id.launchChooseUserActivityButton);
        launchChooseUserActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startCheckGoogleServicesActivity();
            }
        });
    }

    private void checkIfProperAccountName() {

        if (accountName != defaultAccountName &&accountName !=null) {
            Toast.makeText(this, "Account " + accountName + " will be used to connect with server!", Toast.LENGTH_LONG).show();
            startShoppingListActivity();
        } else {
            Toast.makeText(this, "User not choosen yet!", Toast.LENGTH_LONG).show();
        }
    }
        public void startCheckGoogleServicesActivity() {
            Intent intent = new Intent(this, checkGoogleServicesActivity.class);
            startActivity(intent);
        }
        public void startShoppingListActivity() {
            Intent intent = new Intent(this, ShoppingListsActivity.class);
            intent.putExtra(this.getString(R.string.ACCOUNT_NAME_KEY_STRING),accountName);
            startActivity(intent);
        }



    private void sendDatabaseTest() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("TestDateRequest");

        myRef.push().setValue("Database Test request date: "+ java.util.Calendar.getInstance().getTime());
    }


}
