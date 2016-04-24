package com.android.djavid.artists.Server;

import android.widget.ArrayAdapter;
import com.android.djavid.artists.Model.Artist;
import com.android.djavid.artists.ResponseHandler.ArtistsResponseHandler;
import org.json.JSONException;
import java.util.List;


public class Query {
    private static final String ARTISTS_QUERY = "artists.json";

    public static void getArtist(ArrayAdapter<Artist> adapter, List<Artist> artists) throws JSONException {
        ServerArtists.get(ARTISTS_QUERY, null, new ArtistsResponseHandler(adapter, artists));
    }
}
