package com.teenspirit88.taskx.urldata;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

public class DataAccess {
    static URL obj;
    static StringBuilder sb = new StringBuilder();
    static HostnameVerifier hostnameVerifier;

    static {
        hostnameVerifier = new HostnameVerifier() {
            @Override
            public boolean verify(String s, SSLSession sslSession) {
                HostnameVerifier hv = HttpsURLConnection.getDefaultHostnameVerifier();
                return hv.verify("roxiemobile.ru", sslSession);
            }
        };
    }

    public static String getDataFromUrl(String url) throws IOException {
        obj = new URL(url);
        HttpsURLConnection httpsURLConnection = (HttpsURLConnection) obj.openConnection();
        httpsURLConnection.setHostnameVerifier(hostnameVerifier);
        httpsURLConnection.connect();
        String st = null;

        try(
                BufferedReader buffread = new BufferedReader(new InputStreamReader(httpsURLConnection.getInputStream()))
        )
        {
//            while(buffread.ready()) {
//                sb.append((char) buffread.read());
//            }
            while((st = buffread.readLine()) != null) {
                sb.append(st);
            }
        }
        finally {
            httpsURLConnection.disconnect();
        }

        return sb.toString();
    }
}
