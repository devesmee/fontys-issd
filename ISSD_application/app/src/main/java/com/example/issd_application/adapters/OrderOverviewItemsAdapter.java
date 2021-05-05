package com.example.issd_application.adapters;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.issd_application.R;
import com.example.issd_application.models.BorrowedItem;

import java.util.ArrayList;
import java.util.Calendar;

public class OrderOverviewItemsAdapter extends BaseAdapter {
    LayoutInflater inflater;
    ArrayList<BorrowedItem> orderOverviewItems;
    int resourceLayout;
    Context mContext;

    TextView titleItem;
    TextView pickupDate;
    TextView pickupTime;
    TextView returnDate;
    TextView returnTime;
    ImageButton pickupDateCalendar;
    ImageButton returnDateCalendar;
    ImageButton removeItemButton;
    final Calendar returnCalendar = Calendar.getInstance();
    final Calendar pickupCalendar = Calendar.getInstance();
    final Calendar pickupTimeCalendar = Calendar.getInstance();
    final Calendar returnTimeCalendar = Calendar.getInstance();

    public OrderOverviewItemsAdapter(Context context, int resource, ArrayList<BorrowedItem> orderOverviewItems) {
        this.resourceLayout = resource;
        this.orderOverviewItems = orderOverviewItems;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return orderOverviewItems.size();
    }

    @Override
    public BorrowedItem getItem(int position) {
        return orderOverviewItems.get(position);
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

        titleItem = convertView.findViewById(R.id.itemTitle);
        pickupDate = convertView.findViewById(R.id.pickupItemDate);
        pickupTime = convertView.findViewById(R.id.pickupItemTime);
        returnDate = convertView.findViewById(R.id.returnItemDate);
        returnTime = convertView.findViewById(R.id.returnItemTime);
        pickupDateCalendar = convertView.findViewById(R.id.pickupItemCalendarButton);
        returnDateCalendar = convertView.findViewById(R.id.returnItemCalendarButton);
        removeItemButton = convertView.findViewById(R.id.removeItemButton);

        titleItem.setText(getItem(position).title);
        pickupDate.setText(getItem(position).pickupDate);
        pickupTime.setText(getItem(position).pickupTime);
        returnDate.setText(getItem(position).returnDate);
        returnTime.setText(getItem(position).returnTime);

        setReturnCalendarInteraction(getItem(position));
        setPickUpCalendarInteraction(getItem(position));
        setRemoveItemButtonInteraction(getItem(position));

        return convertView;
    }

    public void setRemoveItemButtonInteraction(BorrowedItem item) {
        removeItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orderOverviewItems.remove(item);
                notifyDataSetChanged();
            }
        });
    }

    public void setPickUpCalendarInteraction(BorrowedItem item) {
        String originalPickupDate = item.pickupDate;
        String[] pickupDateParts = originalPickupDate.split("/");

        int pickupDateYear = Integer.parseInt(pickupDateParts[2]);
        int pickupDateMonth = Integer.parseInt(pickupDateParts[1]);
        int pickupDateDay = Integer.parseInt(pickupDateParts[0]);

        String originalPickupTime = item.pickupTime;
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
                item.pickupTime = hourString + ":" + minuteString;
                notifyDataSetChanged();
            }
        };

        DatePickerDialog.OnDateSetListener pickupDateListener = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                pickupCalendar.set(Calendar.YEAR, year);
                pickupCalendar.set(Calendar.MONTH, month);
                pickupCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                item.pickupDate = dayOfMonth + "/" + (month+1) + "/" + year;
                new TimePickerDialog(mContext, pickupTimeListener, pickupTimeHour, pickupTimeMinute, true).show();
            }
        };

        pickupDateCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(mContext, pickupDateListener, pickupDateYear, (pickupDateMonth-1),pickupDateDay).show();
            }
        });

    }

    public void setReturnCalendarInteraction(BorrowedItem item) {
        String originalReturnDate = item.returnDate;
        String[] parts = originalReturnDate.split("/");

        int returnDateYear = Integer.parseInt(parts[2]);
        int returnDateMonth = Integer.parseInt(parts[1]);
        int returnDateDay = Integer.parseInt(parts[0]);

        String originalReturnTime = item.returnTime;
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
                item.returnTime = hourString + ":" + minuteString;
                notifyDataSetChanged();
            }
        };

        DatePickerDialog.OnDateSetListener returnDateListener = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                returnCalendar.set(Calendar.YEAR, year);
                returnCalendar.set(Calendar.MONTH, month);
                returnCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                item.returnDate = dayOfMonth + "/" + (month+1) + "/" + year;
                new TimePickerDialog(mContext, returnTimeListener, returnTimeHour, returnTimeMinute, true).show();
            }
        };

        returnDateCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(mContext, returnDateListener, returnDateYear, (returnDateMonth-1),returnDateDay).show();
            }
        });
    }


}
