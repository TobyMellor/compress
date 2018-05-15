package uk.co.tobymellor.compress;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

import uk.co.tobymellor.compress.models.Manager;

public abstract class ComPressAsyncLoader<D> extends AsyncTaskLoader<D> {
    public final static String BASE_URL = MainActivity.BASE_URL + "api";

    public ComPressAsyncLoader(Context context) {
        super(context);
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public abstract D loadInBackground();

    @Override
    public void deliverResult(D data) {
        super.deliverResult(data);
    }

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

        return String.format("%s/%s%s", ComPressAsyncLoader.BASE_URL, endpoint, finalParamString);
    }

    protected void populateFromJSON(
            Manager manager,
            Class<?> ModelType,
            Class<?> JSONInputType,
            String json
    ) throws JSONException, ReflectiveOperationException {
        JSONObject jsonObject = new JSONObject(json); // TODO: Deal with this asynchronously instead of calling .get

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
