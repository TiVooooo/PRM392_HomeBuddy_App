package com.example.prm392_homebuddy_app;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class PaymentResult extends AppCompatActivity {
    ImageView imageView;
    TextView txtView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.payment_result);

        imageView = (ImageView)findViewById(R.id.imageIcon);
        txtView = (TextView) findViewById(R.id.txtNotification);

        String status = getIntent().getStringExtra("status");
        if(!TextUtils.isEmpty(status)) {
            switch (status) {
                case "success":
                    //imageView.setImageResource(R.drawable.success);
                    txtView.setText("Thanh toán đơn hàng thành công");
                    break;

                case "cancel":
                    //imageView.setImageResource(R.drawable.discard);
                    txtView.setText("Bạn đã hủy thanh toán");
                    break;

                case "error":
                    //imageView.setImageResource(R.drawable.warning);
                    txtView.setText("Thanh toán đơn hàng thất bại");
                    break;
            }
        }
    }
}
