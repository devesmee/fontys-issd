package com.example.issd_application.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.issd_application.models.BorrowedItem;
import com.example.issd_application.adapters.BorrowedItemsAdapter;
import com.example.issd_application.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    View currentView;
    ListView listViewBorrowedItems;
    ImageButton cartButton;
    TextView borrowedItemsEmptyText;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance() {
        ProfileFragment fragment = new ProfileFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentManager = getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        // Inflate the layout for this fragment
        currentView = inflater.inflate(R.layout.fragment_profile, container, false);

        listViewBorrowedItems = currentView.findViewById(R.id.listviewBorrowedItems);
        borrowedItemsEmptyText = currentView.findViewById(R.id.borrowedItemsEmptyText);
        borrowedItemsEmptyText.setVisibility(View.GONE);
        cartButton = currentView.findViewById(R.id.ibToCart);

        ArrayList<BorrowedItem> borrowedItems = new ArrayList<BorrowedItem>();

        borrowedItems.add(new BorrowedItem("Macbook Pro", "5/4/2021", 0, "laptops", "others"));
        borrowedItems.add(new BorrowedItem("iPad Air 2", "5/4/2021", 3, "tablets", "others"));
        borrowedItems.add(new BorrowedItem("USB-C Cable", "19/4/2021", 4, "chargers", "others"));
        borrowedItems.add(new BorrowedItem("Camera", "23/4/2021", 5, "cameras", "prop"));

        BorrowedItemsAdapter borrowedItemsAdapter = new BorrowedItemsAdapter(getContext(), R.layout.list_item_borrowed_item, borrowedItems);
        listViewBorrowedItems.setAdapter(borrowedItemsAdapter);

        if(borrowedItems.isEmpty())
        {
            listViewBorrowedItems.setVisibility(View.GONE);
            borrowedItemsEmptyText.setVisibility(View.VISIBLE);
        }

        setToolbarNavigation();

        return currentView;
    }

    public void setToolbarNavigation() {
        cartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentTransaction.replace(R.id.fragment_container, new CartFragment());
                fragmentTransaction.commit();
            }
        });
    }
}