package com.github.andrzejzmudaa.commonshoppinglist.ListOfShoppingLists;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.github.andrzejzmudaa.commonshoppinglist.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class ShoppingListsFragment extends Fragment {


    public ShoppingListsFragment() {
        // Required empty public constructor
    }

    public static ShoppingListsFragment getInstance(){
        return new ShoppingListsFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_shopping_lists, container, false);
    }

}
