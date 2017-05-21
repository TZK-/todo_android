package todo.gte.utils;

import android.app.Activity;
import com.github.asifmujteba.easyvolley.ASFRequest;
import com.github.asifmujteba.easyvolley.ASFRequestBuilder;
import com.github.asifmujteba.easyvolley.ASFRequestListener;
import com.github.asifmujteba.easyvolley.EasyVolley;

import java.util.HashMap;
import java.util.Map;

public class RestClient {

    public static String BASE_URL = "http://35.176.29.160/";

    protected Map<String, String> mHeaders;
    protected Map<String, String> mParams;
    protected Activity mActivity;

    public RestClient() {
        mHeaders = new HashMap<>();
        mParams = new HashMap<>();
        mActivity = null;
    }

    public RestClient setSubscriber(Activity activity) {
        mActivity = activity;

        return this;
    }

    protected void request(String url, ASFRequest.METHOD method, ASFRequestListener callback) throws NullPointerException {
        if (mActivity == null) {
            throw new NullPointerException("Activity must be provided.");
        }

        ASFRequestBuilder request = EasyVolley.withGlobalQueue()
                .load(method, BASE_URL + url);

        for (Map.Entry<String, String> entry : mHeaders.entrySet()) {
            request.addHeader(entry.getKey(), entry.getValue());
        }

        for (Map.Entry<String, String> entry : mParams.entrySet()) {
            request.addParam(entry.getKey(), entry.getValue());
        }

        request.asJsonObject()
                .setSubscriber(mActivity)
                .setCallback(callback)
                .start();
    }

    public void get(String url, ASFRequestListener callback) {
        request(url, ASFRequest.METHOD.GET, callback);
    }

    public void post(String url, ASFRequestListener callback) {
        request(url, ASFRequest.METHOD.POST, callback);
    }

    public void put(String url, ASFRequestListener callback) {
        request(url, ASFRequest.METHOD.PUT, callback);
    }

    public void delete(String url, ASFRequestListener callback) {
        request(url, ASFRequest.METHOD.DELETE, callback);
    }

    public RestClient addHeader(String header, String value) {
        mHeaders.put(header, value);

        return this;
    }

    public RestClient addParam(String header, String value) {
        mParams.put(header, value);

        return this;
    }

}
