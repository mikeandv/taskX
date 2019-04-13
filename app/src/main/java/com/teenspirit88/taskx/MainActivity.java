package com.teenspirit88.taskx;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;


import com.teenspirit88.taskx.entity.Order;
import com.teenspirit88.taskx.recycler.ItemOffsetDecoration;
import com.teenspirit88.taskx.recycler.ListDataAdapter;
import com.teenspirit88.taskx.recycler.OnClickListener;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ListDataAdapter listAdapter;
    private Toolbar toolbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initRecyclerView();
        loadData();




//        DataAdapter dataAdapter = new DataAdapter(this, orders);
//
//        listView = (ListView) findViewById(R.id.listview);
//        listView.setAdapter(dataAdapter);
//        TextView txt = (TextView) findViewById(R.id.);

//        recyclerView = (RecyclerView) findViewById(R.id.recycler_v);
//        layoutManager = new LinearLayoutManager(this);
//        recyclerView.setLayoutManager(layoutManager);
    }

    private void initRecyclerView() {
        recyclerView = (RecyclerView) findViewById(R.id.recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        OnClickListener onClickListener = new OnClickListener() {
            @Override
            public void onItemClick(Order order) {
                Intent intent = new Intent(MainActivity.this, OrderInfoActivity.class);
                intent.putExtra("order", order);
                startActivity(intent);
            }
        };

        listAdapter = new ListDataAdapter(onClickListener);
        recyclerView.addItemDecoration(new ItemOffsetDecoration(20));
        recyclerView.setAdapter(listAdapter);
    }

    private void loadData() {
        List<Order> orders = getOrdersList();
        listAdapter.setItems(orders);
    }

    private List<Order> getOrdersList() {
        HttpTask ht = new HttpTask();
        ht.execute();

        List<Order> orders = null;

        try {
            orders = ht.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return orders;
    }

}
