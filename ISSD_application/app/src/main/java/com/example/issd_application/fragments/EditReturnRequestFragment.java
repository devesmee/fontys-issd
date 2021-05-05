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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.issd_application.R;
import com.example.issd_application.models.BorrowedItem;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditReturnRequestFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditReturnRequestFragment extends Fragment {
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    View currentView;
    BorrowedItem currentItem;
    ImageButton cartButton;
    TextView itemTitle;
    TextView returnDateText;
    TextView returnTimeText;
    TextView requestedReturnDateText;
    TextView requestedReturnTimeText;
    ImageButton calendarRequestReturnDate;
    Button sendRequestButton;
    final Calendar requestedDateCalendar = Calendar.getInstance();
    final Calendar requestedTimeCalender = Calendar.getInstance();

    public EditReturnRequestFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment EditReturnRequestFragment.
     */
    public static EditReturnRequestFragment newInstance() {
        EditReturnRequestFragment fragment = new EditReturnRequestFragment();
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
        currentView = inflater.inflate(R.layout.fragment_edit_return_request, container, false);

        cartButton = currentView.findViewById(R.id.ibToCart);
        itemTitle = currentView.findViewById(R.id.itemTitle);
        returnDateText = currentView.findViewById(R.id.returnDate);
        returnTimeText = currentView.findViewById(R.id.returnTime);
        requestedReturnDateText = currentView.findViewById(R.id.requestedReturnDate);
        requestedReturnTimeText = currentView.findViewById(R.id.requestedReturnTime);
        calendarRequestReturnDate = currentView.findViewById(R.id.requestedReturnCalendarButton);
        sendRequestButton = currentView.findViewById(R.id.sendEditReturnDateRequestButton);

        setText();
        setCalendarInteraction();
        setToolbarNavigation();

        return currentView;
    }

    public void setText() {
        itemTitle.setText(currentItem.title);
        returnDateText.setText(currentItem.returnDate);
        returnTimeText.setText(currentItem.returnTime);
        requestedReturnDateText.setText(currentItem.returnDate);
        requestedReturnTimeText.setText(currentItem.returnTime);
    }

    public void setCalendarInteraction() {
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
                requestedTimeCalender.set(Calendar.HOUR, hourOfDay);
                requestedTimeCalender.set(Calendar.MINUTE, minute);
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
                String requestedReturnDate = hourString + ":" + minuteString;
                requestedReturnTimeText.setText(requestedReturnDate);
            }
        };

        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                requestedDateCalendar.set(Calendar.YEAR, year);
                requestedDateCalendar.set(Calendar.MONTH, month);
                requestedDateCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                String requestedReturnDate = dayOfMonth + "/" + (month+1) + "/" + year;
                requestedReturnDateText.setText(requestedReturnDate);
                new TimePickerDialog(getContext(), returnTimeListener, returnTimeHour, returnTimeMinute, true).show();
            }
        };

        calendarRequestReturnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(getContext(), date, returnDateYear, (returnDateMonth-1),returnDateDay).show();
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