package com.example.prm392_homebuddy_app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.prm392_homebuddy_app.model.Service;

import java.util.List;

public class ServiceAdapter extends ArrayAdapter<Service> {

    private final List<Service> items;
    private final Context context;

    public ServiceAdapter(Context context, List<Service> items) {
        super(context, 0, items);
        this.context = context;
        this.items = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_cleaning_supply, parent, false);
        }

        Service item = items.get(position);

        ImageView imageView = convertView.findViewById(R.id.item_image);
        TextView nameView = convertView.findViewById(R.id.item_name);
        TextView priceView = convertView.findViewById(R.id.item_price);

        nameView.setText(item.getName());
        priceView.setText("$" + item.getPrice());
        imageView.setImageResource(item.getImageUrl());

        return convertView;
    }
}
