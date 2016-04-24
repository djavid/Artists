package com.android.djavid.artists.ResponseHandler;

import android.util.Log;
import android.widget.ArrayAdapter;
import com.android.djavid.artists.Model.Artist;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.JsonHttpResponseHandler;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.List;
import cz.msebera.android.httpclient.Header;


public class ArtistsResponseHandler extends JsonHttpResponseHandler {
    private ArrayAdapter<Artist> adapter;
    private List<Artist> artists;

    public ArtistsResponseHandler(ArrayAdapter<Artist> adapter, List<Artist> artists) {
        this.adapter = adapter;
        this.artists = artists;
    }

    @Override
    public void onSuccess(int statusCode, Header[] headers, JSONArray response) {

        try {
            Artist artist;
            Gson gson = new GsonBuilder().create();

            JSONArray jsonArray = response;
            artists.clear();

            for (int i = 0; i < jsonArray.length(); i++) {
                artist = gson.fromJson(jsonArray.get(i).toString(), Artist.class);
                artists.add(artist);
            }

            adapter.notifyDataSetChanged();

        } catch (JSONException e) {
            System.out.println(e.toString());
        }
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
        super.onFailure(statusCode, headers, responseString, throwable);

        Log.e("ArtistsResponseHandler", throwable.toString());
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
        super.onFailure(statusCode, headers, throwable, errorResponse);

        Log.e("ArtistsResponseHandler", throwable.toString());
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
        super.onFailure(statusCode, headers, throwable, errorResponse);

        Log.e("ArtistsResponseHandler", throwable.toString());
    }
}
