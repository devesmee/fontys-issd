package com.example.issd_application.adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.issd_application.fragments.EditReturnRequestFragment;
import com.example.issd_application.models.BorrowedItem;
import com.example.issd_application.R;

import java.util.ArrayList;

public class BorrowedItemsAdapter extends BaseAdapter {
    LayoutInflater inflater;
    ArrayList<BorrowedItem> borrowedItems;
    int resourceLayout;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    public BorrowedItemsAdapter(Context context, int resource, ArrayList<BorrowedItem> borrowedItems) {
        this.resourceLayout = resource;
        this.borrowedItems = borrowedItems;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
        this.fragmentTransaction = fragmentManager.beginTransaction();
    }

    @Override
    public int getCount() {
        return borrowedItems.size();
    }

    @Override
    public BorrowedItem getItem(int position) {
        return borrowedItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(resourceLayout, parent, false);
        }

        TextView titleItem = convertView.findViewById(R.id.borrowedItemTitle);
        TextView dateUntilItem = convertView.findViewById(R.id.borrowedItemDate);
        Button editReturnDateRequestItem = convertView.findViewById(R.id.editReturnRequestButton);

        titleItem.setText(getItem(position).title);
        dateUntilItem.setText(getItem(position).returnDate);
        editReturnDateRequestItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditReturnRequestFragment editReturnRequestFragment = new EditReturnRequestFragment();
                Bundle passItemToRequest = new Bundle();
                passItemToRequest.putParcelable("item", getItem(position));
                editReturnRequestFragment.setArguments(passItemToRequest);

                fragmentTransaction.replace(R.id.fragment_container, editReturnRequestFragment);
                fragmentTransaction.commit();
            }
        });

        return convertView;
    }
}
