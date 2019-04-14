package com.teenspirit88.taskx.asynctask;

import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.teenspirit88.taskx.entity.Order;
import com.teenspirit88.taskx.urldata.DataAccess;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class HttpTaskJson extends AsyncTask<String, Void, List<Order>> {
    @Override
    protected List<Order> doInBackground(String... params) {
        String st = null;
        try {
            Type listType = new TypeToken<ArrayList<Order>>(){}.getType();
            return new Gson().fromJson(DataAccess.getDataFromUrl("https://www.roxiemobile.ru/careers/test/" + params[0]), listType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(List<Order> orders) {
        super.onPostExecute(orders);
    }
}
