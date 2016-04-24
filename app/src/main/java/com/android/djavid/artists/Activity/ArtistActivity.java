package com.android.djavid.artists.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ScrollView;
import android.widget.TextView;
import com.android.djavid.artists.FeedFragment;
import com.android.djavid.artists.R;
import com.loopj.android.image.SmartImageView;


public class ArtistActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //getting input args from intent
        String Name = getIntent().getStringExtra("name");
        String Description = getIntent().getStringExtra("description");
        int Albums = getIntent().getIntExtra("albums", 0);
        int Tracks = getIntent().getIntExtra("tracks", 0);
        String[] Genres = getIntent().getStringArrayExtra("genres");
        String img_url = getIntent().getStringExtra("img_url");

        getSupportActionBar().setTitle(Name);
        ScrollView scroll = (ScrollView) findViewById(R.id.scrollView);
        scroll.setSmoothScrollingEnabled(true);

        //getting references to views
        TextView genres_more = (TextView) findViewById(R.id.genres_more);
        TextView albums_tracks_more = (TextView) findViewById(R.id.albums_tracks_more);
        TextView description = (TextView) findViewById(R.id.description);
        SmartImageView cover_big = (SmartImageView) findViewById(R.id.cover_big);

        //preparing vars for views
        String sAlbums_tracks = FeedFragment.ConvertAlbumsTracks(Albums, Tracks);
        String sGenres = "";
        for (int i = 0; i < Genres.length; i++) {
            if (i != Genres.length - 1) {
                sGenres += Genres[i] + ", ";
            } else {
                sGenres += Genres[i];
            }
        }

        //setting values to views
        genres_more.setText(sGenres);
        cover_big.setImageUrl(img_url);
        albums_tracks_more.setText(sAlbums_tracks);
        description.setText(Description);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
