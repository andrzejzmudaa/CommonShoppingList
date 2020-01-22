package com.github.andrzejzmudaa.commonshoppinglist.verifyGoogleServices;

import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.github.andrzejzmudaa.commonshoppinglist.R;
import com.google.android.gms.common.AccountPicker;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;


public class checkGoogleServicesActivity extends AppCompatActivity {

    final static int REQUEST_CODE_EMAIL = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        if (isGooglePlayServicesAvailable(this))
        {
            chooseAccountPopup(true);
        }
        else
            this.finish();
    }

    private void chooseAccountPopup(boolean showAlways) {
        Intent i = AccountPicker.newChooseAccountIntent(null, null,
                null, showAlways, null, null, null, null);
        startActivityForResult(i, REQUEST_CODE_EMAIL);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_EMAIL && resultCode == RESULT_OK) {
            String accountName = data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
            SharedPreferences.Editor editor = getSharedPreferences(this.getString(R.string.GlobalSharedPreferencesString), MODE_PRIVATE).edit();
            editor.putString(this.getString(R.string.ACCOUNT_NAME_KEY_STRING), accountName);
            editor.commit();
            Toast.makeText(this, "Account " + accountName + " will be used to connect with server!", Toast.LENGTH_LONG).show();
        }
        finish();
    }

    public boolean isGooglePlayServicesAvailable(Activity activity) {
        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();
        int status = googleApiAvailability.isGooglePlayServicesAvailable(activity);
        if (status != ConnectionResult.SUCCESS) {
            if (googleApiAvailability.isUserResolvableError(status)) {
                googleApiAvailability.getErrorDialog(activity, status, 2404).show();
            }
            Toast.makeText(this, "GoogleServices are not available on your phone !", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }


}
