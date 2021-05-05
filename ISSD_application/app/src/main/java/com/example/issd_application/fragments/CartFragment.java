package com.example.issd_application.fragments;

import android.database.DataSetObserver;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.issd_application.R;
import com.example.issd_application.adapters.BorrowedItemsAdapter;
import com.example.issd_application.adapters.OrderOverviewItemsAdapter;
import com.example.issd_application.models.BorrowedItem;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CartFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CartFragment extends Fragment {
    View currentView;
    ListView listViewOrderItems;
    Button submitOrderButton;
    TextView orderOverviewItemsEmptyText;
    ArrayList<BorrowedItem> orderOverviewItems;

    public CartFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment CartFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CartFragment newInstance() {
        CartFragment fragment = new CartFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        currentView = inflater.inflate(R.layout.fragment_cart, container, false);

        listViewOrderItems = currentView.findViewById(R.id.listviewOrderOverview);
        orderOverviewItemsEmptyText = currentView.findViewById(R.id.orderOverviewItemsEmptyText);
        orderOverviewItemsEmptyText.setVisibility(View.GONE);
        submitOrderButton = currentView.findViewById(R.id.submitOrderButton);

        orderOverviewItems = new ArrayList<BorrowedItem>();
        orderOverviewItems.add(new BorrowedItem("iPad Air 2", "5/4/2021", 3, "tablets", "others"));
        orderOverviewItems.add(new BorrowedItem("USB-C Cable", "19/4/2021", 4, "chargers", "others"));
        orderOverviewItems.add(new BorrowedItem("Camera", "23/4/2021", 5, "cameras", "prop"));

        OrderOverviewItemsAdapter orderOverviewItemsAdapter = new OrderOverviewItemsAdapter(getContext(), R.layout.list_item_order_overview_item, orderOverviewItems);
        orderOverviewItemsAdapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                if(orderOverviewItems.isEmpty())
                {
                    checkIfListEmpty();
                }
            }
        });
        listViewOrderItems.setAdapter(orderOverviewItemsAdapter);

        checkIfListEmpty();

        return currentView;
    }

    public void checkIfListEmpty() {
        if(orderOverviewItems.isEmpty())
        {
            submitOrderButton.setVisibility(View.GONE);
            listViewOrderItems.setVisibility(View.GONE);
            orderOverviewItemsEmptyText.setVisibility(View.VISIBLE);
        }
    }
}