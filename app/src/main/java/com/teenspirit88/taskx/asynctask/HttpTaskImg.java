package com.teenspirit88.taskx.asynctask;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import com.teenspirit88.taskx.urldata.DataAccess;

import java.io.IOException;


public class HttpTaskImg extends AsyncTask<String, Void, Bitmap> {
    @Override
    protected Bitmap doInBackground(String... params) {

        try {
            return DataAccess.getBitmapFromUrl("https://www.roxiemobile.ru/careers/test/" + params[0] + "/" + params[1]);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
