package com.example.prm392_homebuddy_app.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prm392_homebuddy_app.BookingDetailActivity;
import com.example.prm392_homebuddy_app.R;
import com.example.prm392_homebuddy_app.UserBookingDetailActivity;
import com.example.prm392_homebuddy_app.constants.Constants;
import com.example.prm392_homebuddy_app.model.BookingResponse;
import com.example.prm392_homebuddy_app.model.CreateBookingRequest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.BookingViewHolder> {
    private Context context;
    private List<BookingResponse> bookingResponses;
    private String condition;

    public BookingAdapter(Context context, String condition)
    {
        this.context = context;
        this.condition = condition;
    }

    @NonNull
    @Override
    public BookingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.booking_item,
                parent, false);
        return new BookingAdapter.BookingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookingViewHolder holder, int position) {
        BookingResponse bookingResponse = bookingResponses.get(position);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            holder.bind(bookingResponse);
        }
    }

    @Override
    public int getItemCount() {
        if (bookingResponses == null) {
            return 0;
        }
        return bookingResponses.size();
    }

    public List<BookingResponse> getTasks() {
        return bookingResponses;
    }

    public void setTasks(List<BookingResponse> bookingResponses) {
        this.bookingResponses = bookingResponses;
        notifyDataSetChanged();
    }

    class BookingViewHolder extends RecyclerView.ViewHolder {
        private TextView serviceDateTextView;
        private TextView serviceTimeTextView;
        private TextView serviceNameTextView;
        private Button btnDetail;

        public BookingViewHolder(@NonNull View itemView) {
            super(itemView);

            serviceDateTextView = itemView.findViewById(R.id.textViewServiceDate);
            serviceTimeTextView = itemView.findViewById(R.id.textViewServiceTime);
            serviceNameTextView = itemView.findViewById(R.id.textViewServiceName);
            btnDetail = itemView.findViewById(R.id.btnDetail);

            btnDetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    BookingResponse bookingResponse = bookingResponses.get(getAdapterPosition());

                    if (condition.equals(Constants.HELPER_ID)) {
                        Intent i = new Intent(context, BookingDetailActivity.class);
                        i.putExtra(Constants.DETAIL_HELPER_BOOKING, bookingResponse);
                        context.startActivity(i);
                    } else if (condition.equals(Constants.USER_ID)) {
                        Intent i = new Intent(context, UserBookingDetailActivity.class);
                        i.putExtra(Constants.DETAIL_USER_BOOKING, bookingResponse);
                        context.startActivity(i);
                    }
                }
            });
        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        public void bind(BookingResponse bookingResponse) {
            String serviceDateTime = bookingResponse.getServiceDate();

            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

                LocalDateTime dateTime = LocalDateTime.parse(serviceDateTime, formatter);

                String formattedDate = dateTime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                String formattedTime = dateTime.format(DateTimeFormatter.ofPattern("HH:mm"));

                serviceDateTextView.setText(formattedDate);
                serviceTimeTextView.setText(formattedTime);
                serviceNameTextView.setText(bookingResponse.getServiceName());
            } catch (DateTimeParseException e) {
                e.printStackTrace();
            }
        }
    }
}
