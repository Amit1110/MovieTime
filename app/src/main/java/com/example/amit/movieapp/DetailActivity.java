package com.example.amit.movieapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.GsonBuilder;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class DetailActivity extends AppCompatActivity {

    private TextView titleText;
    private TextView overviewText;
    private TextView linkText;
    private TextView taglineText;
    private int id;
    private RelativeLayout layoutContainer;
    private String API_KEY = "YOUR KEY";

    private Bitmap bmImg;

    private MovieDetail mMovieDetail = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        titleText = findViewById(R.id.titleText);
        overviewText = findViewById(R.id.overviewText);
        taglineText = findViewById(R.id.taglineText);
        linkText = findViewById(R.id.linkText);

        layoutContainer = findViewById(R.id.layoutContainer);

        Bundle bundle = getIntent().getExtras();
        id = bundle.getInt("id");

        new Thread(){
            @Override
            public void run() {
                try {
                   mMovieDetail = getMovieDetail();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        titleText.setText(mMovieDetail.original_title);
                        overviewText.setText(mMovieDetail.overview);
                        linkText.setText(mMovieDetail.homepage);
                        taglineText.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                taglineText.setText(mMovieDetail.tagline);
                            }
                        });
                    }
                });

            }

        }.start();




    }

    private MovieDetail getMovieDetail() throws IOException {
        String strUrl = "https://api.themoviedb.org/3/movie/" + id + "?api_key="+ API_KEY;
        final URL url = new URL(strUrl);
        HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();

        httpsURLConnection.connect();
        InputStream inputStream = httpsURLConnection.getInputStream();
        String response = IOUtils.toString(inputStream,"UTF-8");
        Log.i("Amit",response);
        MovieDetail movieDetail = new GsonBuilder().create().fromJson(response,MovieDetail.class);
        Log.i("Amit",movieDetail.overview);

        final String background_path = "https://image.tmdb.org/t/p/w780" + movieDetail.poster_path;

        new Thread(){
            @Override
            public void run() {
                try {
                    getImage(background_path);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();

        return movieDetail;
    }

    private void getImage(String background_path) throws IOException {
        URL img_url = new URL(background_path);
        HttpsURLConnection httpsURLConnection1 = (HttpsURLConnection) img_url.openConnection();
        httpsURLConnection1.connect();
        InputStream img_is = httpsURLConnection1.getInputStream();
        bmImg = BitmapFactory.decodeStream(img_is);
        final BitmapDrawable bitmapDrawable = new BitmapDrawable(bmImg);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                layoutContainer.setBackground(bitmapDrawable);
            }
        });
    }

}
