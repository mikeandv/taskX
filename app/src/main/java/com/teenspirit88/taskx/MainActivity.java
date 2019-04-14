package com.teenspirit88.taskx;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;


import com.teenspirit88.taskx.asynctask.HttpTaskJson;
import com.teenspirit88.taskx.entity.Order;
import com.teenspirit88.taskx.recycler.ItemOffsetDecoration;
import com.teenspirit88.taskx.recycler.ListDataAdapter;
import com.teenspirit88.taskx.recycler.OnClickListener;

import java.io.File;
import java.io.FileFilter;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    private final long CACHE_LIFETIME = 600_000L; //Срок хранения изображения в кэше 10 минут
    private RecyclerView recyclerView;
    private ScheduledExecutorService service;
    private ListDataAdapter listAdapter;
    private Toolbar toolbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Смана темы после отображения заставки
        setTheme(R.style.AppTheme);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Установка toolbar'а в качестве actionBar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Инициализация RecyclerView
        initRecyclerView();
        loadData();
        cleanCacheFiles(this);
    }

    private void initRecyclerView() {

        recyclerView = (RecyclerView) findViewById(R.id.recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //Передача обьекта order по клику на элемент списка
        OnClickListener onClickListener = new OnClickListener() {
            @Override
            public void onItemClick(Order order) {
                Intent intent = new Intent(MainActivity.this, OrderInfoActivity.class);
                intent.putExtra("order", order);
                startActivity(intent);
            }
        };

        listAdapter = new ListDataAdapter(onClickListener);

        //Установка декоратора для элемента RecyclerView
        recyclerView.addItemDecoration(new ItemOffsetDecoration(8, 16));
        recyclerView.setAdapter(listAdapter);
    }

    private void loadData() {
        List<Order> orders = getOrdersList();
        listAdapter.setItems(orders);
    }

    private List<Order> getOrdersList() {

        //Создание задачи на загрузку данных с ресура (JSON)
        HttpTaskJson ht = new HttpTaskJson();
        ht.execute("orders.json");
        List<Order> orders;

        try {
            orders = ht.get();
            Collections.sort(orders, new Comparator<Order>() {
                @Override
                public int compare(Order order, Order t1) {
                    return Long.compare(order.getDateAsLong(), t1.getDateAsLong());
                }
            });
            Collections.reverse(orders);
            return orders;
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
    private void cleanCacheFiles(final Context context) {
        //Создаем ExecutorService c одним потокм и перезапуском каждые 10 минут
        service = Executors.newSingleThreadScheduledExecutor();
        service.scheduleAtFixedRate(new Runnable() {

            @Override
            public void run() {
                File chDir = context.getCacheDir();
                File[] matchingFiles = chDir.listFiles(new FileFilter() {
                    @Override
                    public boolean accept(File file) {
                        //Выбираем из директории файлы дата модификации которых больше 10 минут
                        return file.lastModified() < (System.currentTimeMillis() - CACHE_LIFETIME);
                    }
                });
                if(matchingFiles.length > 0) {
                    for(File file : matchingFiles ) {
                        //Удаляем файл в директории
                        file.delete();
                    }
                }
            }
        }, 0, CACHE_LIFETIME/60_000, TimeUnit.MINUTES);
    }

}
