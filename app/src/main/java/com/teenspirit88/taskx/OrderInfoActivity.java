package com.teenspirit88.taskx;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.teenspirit88.taskx.asynctask.HttpTaskImg;
import com.teenspirit88.taskx.asynctask.ImgFileCache;
import com.teenspirit88.taskx.entity.Order;
import java.util.concurrent.ExecutionException;


public class OrderInfoActivity extends AppCompatActivity {
    private static final String DAY_MONTH_TIME_FORMAT = "d MMMM, HH:mm";
    private Order order;
    private Bitmap carImage;
    private ImageView imageView;
    private TextView orderDateInfo;
    private TextView startAddrInfo;
    private TextView endAddrInfo;
    private TextView orderPriceinfo;
    private TextView orderDriver;
    private TextView orderVehicleModel;
    private TextView orderVehicleNumber;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_info);

        //Проверка наличия обьекта переданного из MainActivity
        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            order = (Order) bundle.get("order");

            loadData();
            initView();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void loadData() {

        //Получение изображения из кэша устройства (если есть) или с ресурса
        if(ImgFileCache.checkFileInCache(this, order.getVehicle().getPhoto())) {
            carImage = getVehicleImageCache(order.getVehicle().getPhoto());
        } else {
            carImage = getVehicleImage(order.getVehicle().getPhoto());
        }
    }

    private Bitmap getVehicleImage(String imgName) {

        Bitmap bitmap = null;

        //Создание Thread для загрузки изображения
        HttpTaskImg ht = new HttpTaskImg();
        ht.execute("images", imgName);

        try {

            //Получение обьекта из результат выполнения асинхронной задачи
            bitmap = ht.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //Сохранение в кэш полученного изображения
        ImgFileCache.saveToCache(this, bitmap, imgName);
        return bitmap;
    }

    private Bitmap getVehicleImageCache(String imageName) {
        return ImgFileCache.getImage(this, imageName);
    }

    private void initView() {

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        setSupportActionBar(toolbar);

        //Обработка кнопки toolbar'а назад
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        imageView = (ImageView) findViewById(R.id.car_img);
        orderDateInfo = (TextView) findViewById(R.id.order_date_info);
        startAddrInfo = (TextView) findViewById(R.id.start_addr_info);
        endAddrInfo = (TextView) findViewById(R.id.end_addr_info);
        orderPriceinfo = (TextView) findViewById(R.id.price_info);
        orderDriver = (TextView) findViewById(R.id.driver_name);
        orderVehicleModel = (TextView) findViewById(R.id.vehicle_model);
        orderVehicleNumber = (TextView) findViewById(R.id.vehicle_reg_number);

        imageView.setImageBitmap(carImage);
        orderDateInfo.setText(order.getFormatedOrderDate(DAY_MONTH_TIME_FORMAT));
        startAddrInfo.setText(order.getStartAddress().getAddress());
        endAddrInfo.setText(order.getEndAddress().getAddress());
        orderPriceinfo.setText(order.getprice().getAmountAndSymbol());
        orderDriver.setText(order.getVehicle().getDriverName());
        orderVehicleModel.setText(order.getVehicle().getModelName());
        orderVehicleNumber.setText(order.getVehicle().getRegNumber());
    }
}
