package com.github.andrzejzmudaa.commonshoppinglist.ListOfShoppingLists;

import android.content.Context;
import android.content.DialogInterface;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
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

    private String accountName;
    private Context context;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference shoppingListsRef;
    private DatabaseReference usersListsRef;
    private EditText inputText;

    protected ShoppingListAlertDialog(@NonNull Context context) {
        super(context);
        this.context = context;
        shoppingListsRef = database.getReference(context.getResources().getString(R.string.SHOPPING_LISTS_KEY));
        usersListsRef = database.getReference(context.getResources().getString(R.string.USERS_SHOPPING_LISTS_KEY));
    }

    public void buildInputAlertDialog(String accountNameSent, int whichCase) {
        accountName=accountNameSent;
        //accountName="test24wppl";
        inputText = new EditText(context);
        inputText.setMaxLines(1);
        inputText.setInputType(InputType.TYPE_CLASS_TEXT);
        final int internalWhichCase = whichCase;
        String[] questions =context.getResources().getStringArray(R.array.QuestionsRelatedToAddButtonOptions);
        String displayQuestionText=questions[whichCase];
        Builder builder = new Builder(context);
        builder.setTitle(displayQuestionText)
                .setView(inputText)
                .setPositiveButton("Apply", new OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .setNegativeButton("Cancel", new OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        final AlertDialog dialog = builder.show();
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(inputText.getText().toString())) {
                    inputText.setError(context.getResources().getString(R.string.EMPTY_STRING_ERROR_MESSAGE));
                    return;
                }
                else
                    switch (internalWhichCase) {
                        case 0:
                            joinToExistingList(inputText.getText().toString());
                            break;
                        case 1:
                            createNewShoppingList(inputText.getText().toString());
                            break;
                    }
                dialog.dismiss();

            }
        });
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









