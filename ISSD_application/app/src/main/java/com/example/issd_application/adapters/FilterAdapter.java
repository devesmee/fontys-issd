package com.example.issd_application.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.issd_application.FilterManager;
import com.example.issd_application.R;

public class FilterAdapter extends BaseAdapter {
    LayoutInflater inflater;
    FilterManager filterManager;
    int resourceLayout;

    public FilterAdapter(Context context, int resource) {
        this.resourceLayout = resource;
        filterManager = FilterManager.singleton();
        Log.e("filter manager categories: ", filterManager.categories.toString());
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return filterManager.categories.size();
    }

    @Override
    public String getItem(int position) {
        return filterManager.categories.get(position);
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

        TextView filterLabel = convertView.findViewById(R.id.filterLabel);
        ImageButton cancelButton = convertView.findViewById(R.id.cancelButton);

        filterLabel.setText(getItem(position));
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterManager.categories.remove(position);
                notifyDataSetChanged();
            }
        });

        return convertView;
    }

}
