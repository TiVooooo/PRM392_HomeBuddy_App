package com.example.prm392_homebuddy_app.ui.cart;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.prm392_homebuddy_app.API.CartAPI;
import com.example.prm392_homebuddy_app.API.ServiceRepository;
import com.example.prm392_homebuddy_app.CartAcitivity;
import com.example.prm392_homebuddy_app.CheckoutActivity;
import com.example.prm392_homebuddy_app.MainActivity;
import com.example.prm392_homebuddy_app.OrderActivity;
import com.example.prm392_homebuddy_app.R;
import com.example.prm392_homebuddy_app.ViewModel.CheckoutViewModel;
import com.example.prm392_homebuddy_app.adapters.CartAdapter;
import com.example.prm392_homebuddy_app.model.Cart;
import com.example.prm392_homebuddy_app.model.CartResponse;
import com.example.prm392_homebuddy_app.ui.order.OrderFragment;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartFragment extends Fragment {

    private CartViewModel mViewModel;
    private MainActivity mainActivity;
    private CheckoutViewModel checkoutViewModel;

    private ListView listViewCart;
    private CartAdapter cartAdapter;
    private List<Cart> cartItems;
    private CartAPI cartService;

    private Button btnPurchase;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivity = (MainActivity) getActivity();
        checkoutViewModel = new ViewModelProvider(requireActivity()).get(CheckoutViewModel.class);

        cartService = ServiceRepository.getCartAPI();
        cartItems = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);

        listViewCart = view.findViewById(R.id.listCart);
        btnPurchase = view.findViewById(R.id.buttonPurchase);

        cartAdapter = new CartAdapter(requireContext(), cartItems);
        listViewCart.setAdapter(cartAdapter);

        int userId = 1; // Assuming userId
        loadCartData(userId);

        btnPurchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireContext(), CartAcitivity.class);
                startActivity(intent);
            }
        });
        return view;
    }

    private void loadCartData(int userId) {
        try {
            Call<CartResponse> call = cartService.getCartItems(userId);

            call.enqueue(new Callback<CartResponse>() {
                @Override
                public void onResponse(Call<CartResponse> call, Response<CartResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        CartResponse cartResponse = response.body();

                        cartItems.clear();
                        cartItems.addAll(cartResponse.getCartItems());
                        cartAdapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onFailure(Call<CartResponse> call, Throwable t) {
                    Toast.makeText(requireContext(), "Failed to load cart data", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception ex) {
            Log.d("Error", ex.getMessage());
        }
    }

}