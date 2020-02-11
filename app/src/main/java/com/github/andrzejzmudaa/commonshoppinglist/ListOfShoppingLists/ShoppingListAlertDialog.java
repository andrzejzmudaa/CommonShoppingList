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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class ShoppingListAlertDialog extends AlertDialog {

    final private String SHOPPING_LISTS_KEY = "Shopping_Lists_ID";
    final private String USERS_SHOPPING_LISTS_KEY = "UsersShoppingLists";
    private String accountName;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference shoppingListsRef = database.getReference(SHOPPING_LISTS_KEY);
    private DatabaseReference usersListsRef = database.getReference(USERS_SHOPPING_LISTS_KEY);
    private Context context;
    private EditText inputText;

    protected ShoppingListAlertDialog(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    public void buildInputAlertDialog(String accountNameSent, int whichCase) {
        accountName=accountNameSent;
        //accountName="test24wppl";
        inputText = new EditText(context);
        final int internalWhichCase = whichCase;
        String[] questions =context.getResources().getStringArray(R.array.QuestionsRelatedToAddButtonOptions);
        String displayQuestionText=questions[whichCase];
        Builder builder = new Builder(context);
        builder.setTitle(displayQuestionText)
                .setView(inputText)
                .setPositiveButton("Apply", new OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (internalWhichCase) {
                            case 0:
                                joinToExistingList(inputText.getText().toString());
                                break;
                            case 1:
                                createNewShoppingList(inputText.getText().toString());
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
    private void createNewShoppingList(String listName) {
        shoppingListsRef.push().setValue(new ShoppingListItem(listName,accountName), new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                String createdListKey = databaseReference.getKey();
                addToMyList(createdListKey);
                if(databaseError!=null)
                   Log.d("DB_CONNECTION_ERROR","Error occurred during connection to database: "+databaseError.getMessage());
            }
        });
        Log.d("DB_CONNECTION","Account name passed "+accountName);
    }

    private void joinToExistingList(String sentlistID) {
        final String listID=sentlistID;
        shoppingListsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot shoppingListData : dataSnapshot.getChildren()) {
                    String key = shoppingListData.getKey();
                    if (listID.equals(key)) {
                        addToMyList(key);
                        return;
                    }}
                        Toast.makeText(context, context.getResources().getString(R.string.ListNotAdded) + listID, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(context,"Error occurred during connection to database: "+databaseError, Toast.LENGTH_SHORT).show();
            }
        });





    }

    private void addToMyList(String listID){
        final String innerListID = listID;

        usersListsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!dataSnapshot.hasChild(accountName)) {
                    usersListsRef.child(accountName).push().setValue(innerListID);
                    return;
                }

                boolean listAlreadyAppeared = false;
                for (DataSnapshot child : dataSnapshot.child(accountName).getChildren()){
                    if(innerListID.equals(child.getValue(String.class)))
                        listAlreadyAppeared = true;}
                    if(!listAlreadyAppeared)
                        usersListsRef.child(accountName).push().setValue(innerListID);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }


            });


    }
}









