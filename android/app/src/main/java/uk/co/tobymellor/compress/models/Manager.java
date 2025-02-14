package uk.co.tobymellor.compress.models;

import android.app.Application;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

import uk.co.tobymellor.compress.MainActivity;

abstract public class Manager {
    public final static String BASE_URL = MainActivity.BASE_URL + "api";

    public abstract void add(Object object);

    public abstract void remove(Object object);

    public abstract String getEndpoint();

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

        String finalParamString = paramString.toString().replace(" ", "+");

        return String.format("%s/%s%s", Manager.BASE_URL, endpoint, finalParamString);
    }

    protected void populateFromJSON(
            Manager manager,
            Class<?> ModelType,
            Class<?> JSONInputType,
            String json
    ) throws JSONException, ReflectiveOperationException {
        JSONObject jsonObject = new JSONObject(json);

        JSONArray jsonElements = jsonObject.getJSONArray(manager.getEndpoint());

        for (int i = 0; i < jsonElements.length(); i++) {
            JSONObject jsonElement = jsonElements.getJSONObject(i);

            Constructor<?> modelConstructor = ModelType.getConstructor(JSONInputType.getInterfaces()[0]);
            Constructor<?> inputConstructor = JSONInputType.getConstructor(JSONObject.class);

            Object object = modelConstructor.newInstance(inputConstructor.newInstance(jsonElement));

            manager.add(object);
        }
    }

    protected void populateFromXML(Manager manager, Class<?> XMLInput, String json) {
        //
    }
}
