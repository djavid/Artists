package com.android.djavid.artists.Server;

import android.util.Log;

import com.loopj.android.http.*;


public class ServerArtists {
    private static final String URL = "http://download.cdn.yandex.net/mobilization-2016/";

    private static AsyncHttpClient client = new AsyncHttpClient();

    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.setConnectTimeout(30000);
        client.setMaxRetriesAndTimeout(10, 1000);
        try {
            client.get(getAbsoluteUrl(url), params, responseHandler);
        } catch (Exception e) {
            Log.e("ServerArtists", e.toString());
        }

    }

    private static String getAbsoluteUrl(String relativeUrl) {
        return URL + relativeUrl;
    }
}
