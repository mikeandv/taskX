package com.teenspirit88.taskx.urldata;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URL;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;


//Класс реализует получение данных с русура, реализованы два метода получение Json и изображения
public class DataAccess {
    static HostnameVerifier hostnameVerifier;

    static {
        //Определяем верификатор для доступа к сайту по https
        hostnameVerifier = new HostnameVerifier() {
            @Override
            public boolean verify(String s, SSLSession sslSession) {
                HostnameVerifier hv = HttpsURLConnection.getDefaultHostnameVerifier();
                return hv.verify("roxiemobile.ru", sslSession);
            }
        };
    }

    public static String getDataFromUrl(String url) throws IOException {
        URL obj = new URL(url);
        StringBuilder sb = new StringBuilder();

        //Создаем соединение по URL
        HttpsURLConnection httpsURLConnection = (HttpsURLConnection) obj.openConnection();
        httpsURLConnection.setHostnameVerifier(hostnameVerifier);
        httpsURLConnection.connect();

        //Получаем InputStream
        try(BufferedInputStream bis = new BufferedInputStream(httpsURLConnection.getInputStream()))
        {
            byte[] buffer = new byte[1024];
            int len = 0;

            //Читаем байсты из буфера входного потока и дописываем их в StringBuilder
            while((len = bis.read(buffer)) != -1) {
                sb.append(new String(buffer, 0, len));
            }
        }
        finally {
            //Закрываем соединение
            httpsURLConnection.disconnect();
        }
        return sb.toString();
    }

    public static Bitmap getBitmapFromUrl(String url) throws IOException {
        URL obj = new URL(url);
        Bitmap bitmap;

        //Создаем соединение по URL
        HttpsURLConnection httpsURLConnection = (HttpsURLConnection) obj.openConnection();
        httpsURLConnection.setHostnameVerifier(hostnameVerifier);
        httpsURLConnection.connect();

        //Получаем InputStream
        try(BufferedInputStream bis = new BufferedInputStream(httpsURLConnection.getInputStream()))
        {
            //Передаем байты из потока в фабрику Bitmap
            bitmap = BitmapFactory.decodeStream(bis);

        }
        finally {
            //Закрываем соединение
            httpsURLConnection.disconnect();
        }
        return bitmap;
    }
}
