package com.teenspirit88.taskx;

import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.teenspirit88.taskx.entity.Order;
import com.teenspirit88.taskx.urldata.DataAccess;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class HttpTask extends AsyncTask<Void, Void, List<Order>> {
    @Override
    protected List<Order> doInBackground(Void... voids) {
        String st = null;
        try {
            st = DataAccess.getDataFromUrl("https://www.roxiemobile.ru/careers/test/orders.json");
        } catch (IOException e) {
            e.printStackTrace();
        }
        Type listtype = new TypeToken<ArrayList<Order>>(){}.getType();
        return new Gson().fromJson(st, listtype);
    }

    @Override
    protected void onPostExecute(List<Order> orders) {
        super.onPostExecute(orders);

    }
}
