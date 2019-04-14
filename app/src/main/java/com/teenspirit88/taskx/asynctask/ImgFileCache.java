package com.teenspirit88.taskx.asynctask;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.ExecutionException;

public class ImgFileCache {

    public static boolean checkFileInCache(final Context context, final String fileName) {

        try {
            return (new AsyncTask<String, Void, Boolean>() {
                @Override
                protected Boolean doInBackground(String... params) {

                    File dir = context.getCacheDir();

                    //Поиск файлов с заданным названием в директории
                    File[] matchingFiles = dir.listFiles(new FileFilter() {
                        @Override
                        public boolean accept(File file) {
                            return file.getName().equals(fileName);//(file.getName().startsWith(maskName) && file.getName().endsWith(suffix));
                        }
                    });
                    return matchingFiles.length == 1;

                }
            }).execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void saveToCache(final Context context, final Bitmap bitmap, final String imageName) {

        new Thread(new Runnable() {
            @Override
            public void run() {

                //Получение пути к папке кэша приложения
                File cacheDir = context.getCacheDir();
                //Создание файла в кэш директории с указанным названием
                File file = new File(cacheDir, imageName);

                try (OutputStream out = new FileOutputStream(file)) {
                    //Запись изображения в поток
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public static Bitmap getImage(final Context context, final String imageName) {
        try {
            return (new AsyncTask<String, Void, Bitmap>() {
                @Override
                protected Bitmap doInBackground(String ...params) {
                    File cacheDir = context.getCacheDir();
                    File file = new File(cacheDir, imageName);

                    try(InputStream in = new FileInputStream(file))
                    {
                        //Создание объекта из стрима
                        return BitmapFactory.decodeStream(in);

                    } catch (IOException e) {
                        e.printStackTrace();
                        return null;
                    }
                }
            }).execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
