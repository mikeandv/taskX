package com.teenspirit88.taskx;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.teenspirit88.taskx.entity.Order;


public class OrderInfoActivity extends AppCompatActivity {
    public static final String ORDER_ID = "id";
    private TextView orderId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_info);

        orderId = findViewById(R.id.order_id);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            Order order = (Order) bundle.get("order");
            orderId.setText(Integer.toString(order.getId()));
        }

    }
}
