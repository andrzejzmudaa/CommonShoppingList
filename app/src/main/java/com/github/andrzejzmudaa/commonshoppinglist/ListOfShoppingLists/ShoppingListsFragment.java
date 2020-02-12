package com.github.andrzejzmudaa.commonshoppinglist.ListOfShoppingLists;


import android.app.Application;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.andrzejzmudaa.commonshoppinglist.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


/**
 * A simple {@link Fragment} subclass.
 */
public class ShoppingListsFragment extends Fragment {

    ShoppingListsAdapter shoppingListsAdapter;
    RecyclerView shoppingListsRecyclerView;



    public ShoppingListsFragment() {
        // Required empty public constructor
    }

    public static ShoppingListsFragment getInstance(){
        return new ShoppingListsFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_shopping_lists, container, false);
        attachFAB(view);
        shoppingListsRecyclerView = view.findViewById(R.id.ShoppingListsRecyclerView);
        shoppingListsAdapter = new ShoppingListsAdapter();
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        shoppingListsRecyclerView.setLayoutManager(mLayoutManager);
        shoppingListsRecyclerView.setItemAnimator(new DefaultItemAnimator());
        shoppingListsRecyclerView.setAdapter(shoppingListsAdapter);

        shoppingListsAdapter.ShoppingLists.add(new ShoppingListItem("test","Andrzej"));
        shoppingListsAdapter.ShoppingLists.add(new ShoppingListItem("test2","Andrzej"));
        shoppingListsAdapter.ShoppingLists.add(new ShoppingListItem("test3","Andrzej"));
        shoppingListsAdapter.ShoppingLists.add(new ShoppingListItem("test4","Andrzej"));
        shoppingListsAdapter.notifyDataSetChanged();




        return view;
    }

    private void attachFAB(View view) {
        final ShoppingListsActivity activity = (ShoppingListsActivity) getActivity();
        FloatingActionButton addButton = view.findViewById(R.id.floating_action_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle(R.string.AddButtonMenu)
                        .setItems(R.array.AddButtonOptions, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                new ShoppingListAlertDialog(getContext()).buildInputAlertDialog(activity.getAccountName(),which);
                            }
                        });
                builder.create().show();
            }
        });
    }

}
