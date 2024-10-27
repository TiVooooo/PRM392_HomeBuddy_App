package com.example.prm392_homebuddy_app.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.prm392_homebuddy_app.model.Service;

import java.util.List;

public class CheckoutViewModel extends ViewModel {
    private final MutableLiveData<List<Service>> cartItems = new MutableLiveData<>();

    public LiveData<List<Service>> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<Service> items) {
        cartItems.setValue(items);
    }
}
