package com.github.andrzejzmudaa.commonshoppinglist.ListOfShoppingLists;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.github.andrzejzmudaa.commonshoppinglist.R;

import java.util.ArrayList;
import java.util.List;

public class ShoppingListsAdapter extends RecyclerView.Adapter<ShoppingListsAdapter.ListHolder> {
    public List<ShoppingListItem> ShoppingLists = new ArrayList<>();

    @NonNull
    @Override
    public ListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_shoppinglists,parent,false);
        return new ListHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ListHolder holder, int position) {
        ShoppingListItem listItem = ShoppingLists.get(position);
        holder.shoppingListName.setText(listItem.shoppingListName);
        holder.whoAdded.setText(listItem.whoAdded);
    }

    @Override
    public int getItemCount() {
        return ShoppingLists.size();
    }

    public class ListHolder extends RecyclerView.ViewHolder{
        public TextView shoppingListName;
        public TextView whoAdded;

        public ListHolder(@NonNull View itemView) {
            super(itemView);
            shoppingListName = itemView.findViewById(R.id.shoppingListName);
            whoAdded = itemView.findViewById(R.id.whoAdded);
        }
    }
}
