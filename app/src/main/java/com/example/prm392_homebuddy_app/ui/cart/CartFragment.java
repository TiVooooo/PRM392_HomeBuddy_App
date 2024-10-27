package com.example.prm392_homebuddy_app.ui.cart;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.prm392_homebuddy_app.CheckoutActivity;
import com.example.prm392_homebuddy_app.MainActivity;
import com.example.prm392_homebuddy_app.R;
import com.example.prm392_homebuddy_app.ViewModel.CheckoutViewModel;
import com.example.prm392_homebuddy_app.ui.order.OrderFragment;

public class CartFragment extends Fragment {

    private CartViewModel mViewModel;
    private MainActivity mainActivity;
    private CheckoutViewModel checkoutViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivity = (MainActivity) getActivity();
        checkoutViewModel = new ViewModelProvider(requireActivity()).get(CheckoutViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);

        Button proceedButton = view.findViewById(R.id.btnCheckout);
        proceedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cartData = "Example cart data";
                //checkoutViewModel.setCartItems(cartData);

                Intent intent = new Intent(getActivity(), CheckoutActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

}