package com.android.djavid.artists;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import com.android.djavid.artists.Activity.ArtistActivity;
import com.android.djavid.artists.Activity.MainActivity;
import com.android.djavid.artists.Model.Artist;
import com.android.djavid.artists.Server.Query;
import com.loopj.android.image.SmartImageView;
import org.json.JSONException;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;


public class FeedFragment extends ListFragment implements OnItemClickListener {
    private List<Artist> artists = new ArrayList<Artist>();
    private ArrayAdapter<Artist> adapter;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        adapter = new ArtistAdapter(getActivity(), R.layout.list_main);
        setListAdapter(adapter);
        getListView().setOnItemClickListener(this);

        if(isNetworkAvailable()) {
            try {
                Query.getArtist(adapter, artists);
            } catch (JSONException e) {
                System.out.println(e.toString());
            }
        } else {
            Toast.makeText(getActivity().getApplicationContext(),
                    "No internet connection!", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean isNetworkAvailable(){
        ConnectivityManager cm =
                (ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

        return isConnected;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Artist artist = artists.get(position);

        //putting required args to intent
        Intent intent = new Intent(getActivity(), ArtistActivity.class);
        intent.putExtra("name", artist.getName());
        intent.putExtra("description", artist.getDescription());
        intent.putExtra("albums", artist.getAlbums());
        intent.putExtra("tracks", artist.getTracks());
        intent.putExtra("genres", artist.getGenres());
        intent.putExtra("img_url", artist.getCover().getBig());

        startActivity(intent);
    }

    public static String ConvertAlbumsTracks(int albums, int tracks) {
        //converting values to readable string
        String sAlbums_tracks = albums + " ";
        switch (albums % 10) {
            case 1: sAlbums_tracks += "альбом, "; break;
            case 2: sAlbums_tracks += "альбома, "; break;
            case 3: sAlbums_tracks += "альбома, "; break;
            case 4: sAlbums_tracks += "альбома, "; break;
            case 5: sAlbums_tracks += "альбомов, "; break;
            case 6: sAlbums_tracks += "альбомов, "; break;
            case 7: sAlbums_tracks += "альбомов, "; break;
            case 8: sAlbums_tracks += "альбомов, "; break;
            case 9: sAlbums_tracks += "альбомов, "; break;
            case 0: sAlbums_tracks += "альбомов, "; break;
        }
        sAlbums_tracks += tracks + " ";
        switch (tracks % 10) {
            case 1: sAlbums_tracks += "песня"; break;
            case 2: sAlbums_tracks += "песни"; break;
            case 3: sAlbums_tracks += "песни"; break;
            case 4: sAlbums_tracks += "песни"; break;
            case 5: sAlbums_tracks += "песен"; break;
            case 6: sAlbums_tracks += "песен"; break;
            case 7: sAlbums_tracks += "песен"; break;
            case 8: sAlbums_tracks += "песен"; break;
            case 9: sAlbums_tracks += "песен"; break;
            case 0: sAlbums_tracks += "песен"; break;
        }

        return sAlbums_tracks;
    }

    public class ArtistAdapter extends ArrayAdapter<Artist> {

        public ArtistAdapter(Context context, int layout) {
            super(context, layout, artists);
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            if (view == null) {
                view = LayoutInflater.from(getContext()).inflate(R.layout.list_main, null);
            }

            Artist artist = getItem(position);

            //preparing values for views
            String sAlbums_tracks = ConvertAlbumsTracks(artist.getAlbums(), artist.getTracks());
            String sGenres = "";
            for (int i = 0; i < artist.getGenres().length; i++) {
                if (i != artist.getGenres().length - 1) {
                    sGenres += artist.getGenres()[i] + ", ";
                } else {
                    sGenres += artist.getGenres()[i];
                }
            }

            //getting references to views
            TextView name = (TextView) view.findViewById(R.id.name);
            TextView genres = (TextView) view.findViewById(R.id.genres);
            TextView albums_tracks = (TextView) view.findViewById(R.id.albums_tracks);
            SmartImageView cover_small = (SmartImageView) view.findViewById(R.id.cover_small);

            //setting values to views
            name.setText(artist.getName());
            genres.setText(sGenres);
            albums_tracks.setText(sAlbums_tracks);
            cover_small.setImageUrl(artist.getCover().getSmall());

            return view;
        }
    }
}
