package uk.co.tobymellor.compress.models;

import android.util.Pair;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

abstract public class Manager {
    private final static String BASE_URL = "http://46.101.28.103/api";

    public abstract void add(Object object);

    public abstract void remove(Object object);

    protected String formUrl(String endpoint, HashMap<String, String> params) {
        StringBuilder paramString = new StringBuilder();

        for (Map.Entry<String, String> param : params.entrySet()) {
            if (paramString.length() == 0) {
                paramString.append('?');
            } else {
                paramString.append('&');
            }

            paramString.append(param.getKey()).append('=').append(param.getValue());
        }

        return String.format("%s/%s%s", Manager.BASE_URL, endpoint, paramString.toString().replace(" ", "+"));
    }
}
