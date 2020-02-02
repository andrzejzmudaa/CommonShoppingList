package com.github.andrzejzmudaa.commonshoppinglist.ListOfShoppingLists;

import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.github.andrzejzmudaa.commonshoppinglist.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.List;
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
        accountName="test24@wp.pl";
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
                boolean listFound=false;
                for(DataSnapshot shoppingListData : dataSnapshot.getChildren()){
                    if (listID.equals(shoppingListData.getKey())) {
                        addToMyList(shoppingListData);
                        listFound=true;}
                }
                if(!listFound)
                    Toast.makeText(context, context.getResources().getString(R.string.ListNotAdded) + listID, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(context,"Error occurred during connection to database: "+databaseError, Toast.LENGTH_SHORT).show();
            }
        });





    }

    private static void addToMyList(DataSnapshot data){
        ShoppingListItem item = data.getValue(ShoppingListItem.class);
        if(!item.usersJoinedToList.contains(accountName)){
            item.usersJoinedToList.add(accountName);
            Log.d("FIREBASE_MESSAGE","User Joined list"+item.usersJoinedToList);
            Map<String, Object> childUpdates = new HashMap<>();
            childUpdates.put(data.getKey()+"/usersJoinedToList/",item.usersJoinedToList);
            ListRef.updateChildren(childUpdates, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                    if (databaseError!=null)
                        Toast.makeText(context,"Error occured during savind data :"+databaseError, Toast.LENGTH_SHORT).show();
                }
            });

            //ListRef.setValue(item);
        }
    };



    }





