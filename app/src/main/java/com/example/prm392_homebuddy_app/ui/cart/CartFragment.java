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
    private TextView subtotalTextView;
    private TextView total;
    private CartAdapter cartAdapter;
    private List<Cart> cartItems;
    private CartAPI cartService;
    private Button btnCheckOut;

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
        View view = inflater.inflate(R.layout.activity_cart, container, false);

        listViewCart = view.findViewById(R.id.listCart);
        subtotalTextView = view.findViewById(R.id.subtotal);
        total = view.findViewById(R.id.total);
        btnCheckOut = view.findViewById(R.id.btnCheckOut);

        cartAdapter = new CartAdapter(requireContext(), cartItems);
        listViewCart.setAdapter(cartAdapter);

        int userId = 1; // Assuming userId
        loadCartData(userId);

        btnCheckOut.setOnClickListener(v -> {
            Intent intent = new Intent(requireActivity(), CheckoutActivity.class);
            startActivity(intent);
            requireActivity().finish();
        });

        listViewCart.setOnItemClickListener((parent, view1, position, id) -> {
            Cart selectedCartItem = cartItems.get(position);

            // Create a bundle with the data
            Bundle bundle = new Bundle();
            bundle.putString("serviceName", selectedCartItem.getServiceName());
            bundle.putDouble("price", selectedCartItem.getPrice());

            // Navigate to OrderFragment
            Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main).navigate(R.id.action_cartFragment_to_orderFragment, bundle);
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

                        subtotalTextView.setText("$" + cartResponse.getSubtotal());
                        total.setText("$" + cartResponse.getSubtotal());
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