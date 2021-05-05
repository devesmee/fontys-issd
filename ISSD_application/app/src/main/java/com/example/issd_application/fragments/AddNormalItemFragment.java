package com.example.issd_application.fragments;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.issd_application.R;
import com.example.issd_application.models.BorrowedItem;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddNormalItemFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddNormalItemFragment extends Fragment {
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    
    BorrowedItem currentItem;
    View currentView;
    TextView itemTitle;
    TextView pickupDateText;
    TextView pickupTimeText;
    TextView returnDateText;
    TextView returnTimeText;
    ImageButton cartButton;
    ImageButton calendarPickupDate;
    ImageButton calendarReturnDate;
    final Calendar pickupCalendar = Calendar.getInstance();
    final Calendar returnCalendar = Calendar.getInstance();
    final Calendar pickupTimeCalendar = Calendar.getInstance();
    final Calendar returnTimeCalendar = Calendar.getInstance();

    public AddNormalItemFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment AddNormalItemFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddNormalItemFragment newInstance() {
        AddNormalItemFragment fragment = new AddNormalItemFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            currentItem = getArguments().getParcelable("item");
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentManager = getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        // Inflate the layout for this fragment
        currentView = inflater.inflate(R.layout.fragment_add_normal_item, container, false);

        cartButton = currentView.findViewById(R.id.ibToCart);
        itemTitle = currentView.findViewById(R.id.normalItemTitle);
        pickupDateText = currentView.findViewById(R.id.pickupDate);
        pickupTimeText = currentView.findViewById(R.id.pickupTime);
        returnDateText = currentView.findViewById(R.id.returnDate);
        returnTimeText = currentView.findViewById(R.id.returnTime);
        calendarPickupDate = currentView.findViewById(R.id.pickupCalendarButton);
        calendarReturnDate = currentView.findViewById(R.id.returnCalendarButton);

        itemTitle.setText(currentItem.title);
        setPickUpCalendarInteraction();
        setReturnCalendarInteraction();
        setToolbarNavigation();

        return currentView;
    }

    public void setPickUpCalendarInteraction() {
        String originalPickupDate = currentItem.pickupDate;
        String[] pickupDateParts = originalPickupDate.split("/");

        int pickupDateYear = Integer.parseInt(pickupDateParts[2]);
        int pickupDateMonth = Integer.parseInt(pickupDateParts[1]);
        int pickupDateDay = Integer.parseInt(pickupDateParts[0]);

        String originalPickupTime = currentItem.pickupTime;
        String[] pickupTimeParts = originalPickupTime.split(":");

        int pickupTimeHour = Integer.parseInt(pickupTimeParts[0]);
        int pickupTimeMinute = Integer.parseInt(pickupTimeParts[1]);

        TimePickerDialog.OnTimeSetListener pickupTimeListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                pickupTimeCalendar.set(Calendar.HOUR, hourOfDay);
                pickupTimeCalendar.set(Calendar.MINUTE, minute);
                String hourString;
                String minuteString;
                if(hourOfDay < 10) {
                    hourString = "0" + hourOfDay;
                } else {
                    hourString = String.valueOf(hourOfDay);
                }
                if(minute < 10){
                    minuteString = "0" + minute;
                } else {
                    minuteString = String.valueOf(minute);
                }
                currentItem.pickupTime = hourString + ":" + minuteString;
                pickupTimeText.setText(currentItem.pickupTime);
            }
        };

        DatePickerDialog.OnDateSetListener pickupDateListener = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                pickupCalendar.set(Calendar.YEAR, year);
                pickupCalendar.set(Calendar.MONTH, month);
                pickupCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                currentItem.pickupDate = dayOfMonth + "/" + (month+1) + "/" + year;
                pickupDateText.setText(currentItem.pickupDate);
                new TimePickerDialog(getContext(), pickupTimeListener, pickupTimeHour, pickupTimeMinute, true).show();
            }
        };

        calendarPickupDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(getContext(), pickupDateListener, pickupDateYear, (pickupDateMonth-1),pickupDateDay).show();
            }
        });

    }

    public void setReturnCalendarInteraction() {
        String originalReturnDate = currentItem.returnDate;
        String[] parts = originalReturnDate.split("/");

        int returnDateYear = Integer.parseInt(parts[2]);
        int returnDateMonth = Integer.parseInt(parts[1]);
        int returnDateDay = Integer.parseInt(parts[0]);

        String originalReturnTime = currentItem.returnTime;
        String[] returnTimeParts = originalReturnTime.split(":");

        int returnTimeHour = Integer.parseInt(returnTimeParts[0]);
        int returnTimeMinute = Integer.parseInt(returnTimeParts[1]);

        TimePickerDialog.OnTimeSetListener returnTimeListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                returnTimeCalendar.set(Calendar.HOUR, hourOfDay);
                returnTimeCalendar.set(Calendar.MINUTE, minute);
                String hourString;
                String minuteString;
                if(hourOfDay < 10) {
                    hourString = "0" + hourOfDay;
                } else {
                    hourString = String.valueOf(hourOfDay);
                }
                if(minute < 10){
                    minuteString = "0" + minute;
                } else {
                    minuteString = String.valueOf(minute);
                }
                currentItem.returnTime = hourString + ":" + minuteString;
                returnTimeText.setText(currentItem.returnTime);
            }
        };

        DatePickerDialog.OnDateSetListener returnDateListener = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                returnCalendar.set(Calendar.YEAR, year);
                returnCalendar.set(Calendar.MONTH, month);
                returnCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                currentItem.returnDate = dayOfMonth + "/" + (month+1) + "/" + year;
                returnDateText.setText(currentItem.returnDate);
                new TimePickerDialog(getContext(), returnTimeListener, returnTimeHour, returnTimeMinute, true).show();
            }
        };

        calendarReturnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(getContext(), returnDateListener, returnDateYear, (returnDateMonth-1),returnDateDay).show();
            }
        });
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