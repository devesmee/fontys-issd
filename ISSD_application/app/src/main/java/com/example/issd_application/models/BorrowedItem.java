package com.example.issd_application.models;

import android.os.Parcel;
import android.os.Parcelable;

public class BorrowedItem implements Parcelable {
    public String title;
    public String pickupDate;
    public String pickupTime;
    public String returnDate;
    public String returnTime;
    public String technology;
    public String course;
    public int inStock;

    public BorrowedItem(String title, String returnDate, int inStock, String technology, String course) {
        this.title = title;
        this.returnDate = returnDate;
        this.pickupDate = "02/04/2021";
        this.pickupTime = "13:30";
        this.returnTime = "15:00";
        this.inStock = inStock;
        this.technology = technology;
        this.course = course;
    }

    protected BorrowedItem(Parcel in) {
        title = in.readString();
        pickupDate = in.readString();
        pickupTime = in.readString();
        returnDate = in.readString();
        returnTime = in.readString();
        inStock = in.readInt();
        technology = in.readString();
        course = in.readString();
    }

    public static final Creator<BorrowedItem> CREATOR = new Creator<BorrowedItem>() {
        @Override
        public BorrowedItem createFromParcel(Parcel in) {
            return new BorrowedItem(in);
        }

        @Override
        public BorrowedItem[] newArray(int size) {
            return new BorrowedItem[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(pickupDate);
        dest.writeString(pickupTime);
        dest.writeString(returnDate);
    }

}
