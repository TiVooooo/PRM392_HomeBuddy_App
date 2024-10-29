package com.example.prm392_homebuddy_app.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.prm392_homebuddy_app.R;
import com.example.prm392_homebuddy_app.model.Cart;

import java.util.List;

public class CartAdapter extends ArrayAdapter<Cart> {
    private Context context;
    private List<Cart> cartItems;

    public CartAdapter(Context context, List<Cart> cartItems) {
        super(context, 0, cartItems);
        this.context = context;
        this.cartItems = cartItems;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.cart_item, parent, false);
        }

        Cart cartItem = cartItems.get(position);
        TextView quantity = convertView.findViewById(R.id.textView2);
        ImageView imageCart = convertView.findViewById(R.id.imageCart);
        TextView title = convertView.findViewById(R.id.title);
        TextView price = convertView.findViewById(R.id.price);

        quantity.setText(cartItem.getQuantity() + " x ");
        title.setText(cartItem.getServiceName());
        price.setText("$" + cartItem.getServicePrice());

        Glide.with(context).load(cartItem.getServiceImage()).into(imageCart);

        return convertView;
    }
}
