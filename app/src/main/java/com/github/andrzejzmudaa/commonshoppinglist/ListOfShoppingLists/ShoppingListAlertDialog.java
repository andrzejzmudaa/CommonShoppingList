package com.github.andrzejzmudaa.commonshoppinglist.ListOfShoppingLists;

import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.github.andrzejzmudaa.commonshoppinglist.R;


public class ShoppingListAlertDialog extends AlertDialog {

    static private String tempText;
    static private String accountName;

    protected ShoppingListAlertDialog(@NonNull Context context) {
        super(context);
    }

    public static void buildInputAlertDialog(String accountNameSent, Context context, int whichCase) {
        accountName=accountNameSent;
        final EditText inputText = new EditText(context);
        final int internalWhichCase = whichCase;
        String[] questions =context.getResources().getStringArray(R.array.QuestionsRelatedToAddButtonOptions);
        String displayQuestionText=questions[whichCase];
        tempText=null;
        Builder builder = new Builder(context);
        builder.setTitle(displayQuestionText)
                .setView(inputText)
                .setPositiveButton("Apply", new OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        tempText = inputText.getText().toString();
                        switch (internalWhichCase) {
                            case 0:
                                joinToExistingList(tempText);
                                break;
                            case 1:
                                createNewShoppingList(tempText);
                                break;
                        }
                    }
                })
                .setNegativeButton("Cancel", new OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        builder.show();

    }
    private static void createNewShoppingList(String listName) {



        Log.d("DATABASE_CONNECTION","Account name passed "+accountName);
    }

    private static void joinToExistingList(String listName) {


    }


}
