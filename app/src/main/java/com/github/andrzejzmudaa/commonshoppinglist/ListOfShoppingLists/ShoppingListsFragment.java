package com.github.andrzejzmudaa.commonshoppinglist.ListOfShoppingLists;


import android.app.Application;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.andrzejzmudaa.commonshoppinglist.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class ShoppingListsFragment extends Fragment implements ChildEventListener {

    ShoppingListsAdapter shoppingListsAdapter;
    RecyclerView shoppingListsRecyclerView;
    String accountName;
    DatabaseReference reference;



    public ShoppingListsFragment() {
        // Required empty public constructor
    }

    public static ShoppingListsFragment getInstance(){
        return new ShoppingListsFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        accountName = ((ShoppingListsActivity)getActivity()).getAccountName();
        View view = inflater.inflate(R.layout.fragment_shopping_lists, container, false);
        attachFAB(view);
        shoppingListsRecyclerView = view.findViewById(R.id.ShoppingListsRecyclerView);
        shoppingListsAdapter = new ShoppingListsAdapter();
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        shoppingListsRecyclerView.setLayoutManager(mLayoutManager);
        shoppingListsRecyclerView.setItemAnimator(new DefaultItemAnimator());
        shoppingListsRecyclerView.setAdapter(shoppingListsAdapter);
        return view;
    }

    private void attachFAB(View view) {
        FloatingActionButton addButton = view.findViewById(R.id.floating_action_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle(R.string.AddButtonMenu)
                        .setItems(R.array.AddButtonOptions, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                new ShoppingListAlertDialog(getContext()).buildInputAlertDialog(accountName,which);
                            }
                        });
                builder.create().show();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        reference = FirebaseDatabase.getInstance().getReference(getContext().getResources().getString(R.string.USERS_SHOPPING_LISTS_KEY)).child(accountName);
        reference.addChildEventListener(this);
    }

    @Override
    public void onPause() {
        reference.removeEventListener(this);
        super.onPause();
    }

    @Override
    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
        Log.d("onChildAdded",dataSnapshot.getValue().toString());
        FirebaseDatabase.getInstance().getReference(getContext().getResources().getString(R.string.SHOPPING_LISTS_KEY)).child(dataSnapshot.getValue().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ShoppingListItem item = dataSnapshot.getValue(ShoppingListItem.class);
                shoppingListsAdapter.ShoppingLists.add(item);
                shoppingListsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

    }

    @Override
    public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

    }

    @Override
    public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {

    }
}
