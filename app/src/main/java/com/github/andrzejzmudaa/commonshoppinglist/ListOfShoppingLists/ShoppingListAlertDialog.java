package com.github.andrzejzmudaa.commonshoppinglist.ListOfShoppingLists;

import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.github.andrzejzmudaa.commonshoppinglist.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;
import java.util.Timer;


public class ShoppingListAlertDialog extends AlertDialog {

    static final private String DATABASE_KEY = "ShoppingListsID";
    static private String tempText;
    static private String accountName;
    static private FirebaseDatabase database = FirebaseDatabase.getInstance();
    static private DatabaseReference ListRef = database.getReference(DATABASE_KEY);
    static String listID;
    static Context context;

    protected ShoppingListAlertDialog(@NonNull Context context) {
        super(context);
    }

    public static void buildInputAlertDialog(String accountNameSent, Context sentcontext, int whichCase) {
        accountName=accountNameSent;
        context=sentcontext;
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
        ShoppingListItem ShoppingListItem = new ShoppingListItem(listName,accountName);
        ListRef.push().setValue(ShoppingListItem);
        Log.d("DATABASE_CONNECTION","Account name passed "+accountName);
    }

    private static void joinToExistingList(String sentlistID) {
        listID=sentlistID;
        ListRef.orderByPriority().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Map<String,ShoppingListItem> map = (Map) dataSnapshot.getValue();
                if (map != null)
                    if (map.size() != 0)
                        for (Map.Entry<String, ShoppingListItem> entry : map.entrySet()) {
                            if (listID.equals(entry.getKey()))
                                addToMyList(listID);
                            else
                                Toast.makeText(context, context.getResources().getString(R.string.ListNotAdded) + listID, Toast.LENGTH_SHORT).show();
                        }




                //-LzJ0n_9uWrBo-cJfTpn
                        


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }

    private static void addToMyList(String listID){
       Log.d("FIREBASE_MESSAGE","listID: "+listID);

    };


    }





