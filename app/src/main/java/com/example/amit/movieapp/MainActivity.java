package com.example.amit.movieapp;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.google.gson.GsonBuilder;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

import butterknife.BindView;
import butterknife.ButterKnife;

// TMDb link  https://api.themoviedb.org/3/movie/550?api_key=

//api TMDb --

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private String API_KEY = "YOUR kEY";

    private String[] image_paths;
    private String[] Titles = null;
    private Integer[] MovieIds = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


        new Thread() {
            @Override
            public void run() {
                try {
                    image_paths = getCurrentMovies();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        GridLayoutManager gridLayoutManager = new GridLayoutManager(MainActivity.this, 2);
                        //LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this,LinearLayoutManager.VERTICAL,false);
                        recyclerView.setLayoutManager(gridLayoutManager);

                        ImageAdapter imageAdapter = new ImageAdapter(MainActivity.this, image_paths, Titles,MovieIds);
                        recyclerView.setAdapter(imageAdapter);

                    }
                });

            }
        }.start();


    }

    private String[] getCurrentMovies() throws IOException {
        String strURL = "https://api.themoviedb.org/3/movie/now_playing?api_key=" + API_KEY + "&language=en-US&page=undefined";
        URL url = new URL(strURL);
        HttpsURLConnection httpURLConnection = (HttpsURLConnection) url.openConnection();
        httpURLConnection.connect();
        InputStream inputStream = httpURLConnection.getInputStream();
        String response = IOUtils.toString(inputStream,"UTF-8");
       // Log.i("Amit",response);
        String[] image_paths = parseResponse(response);
        return image_paths;
    }

    private String[] parseResponse(String response) {
        if(response!=null) {
            Movie movie = new GsonBuilder().create().fromJson(response, Movie.class);
            Log.i("Amit", movie.results[0].original_title);
            Log.i("Amit", String.valueOf(movie.results[0].id));
            Log.i("Amit", movie.results[0].poster_path);
            ArrayList<String> temp = new ArrayList<String>();
            ArrayList<String> tempTitle = new ArrayList<String>();
            ArrayList<Integer> tempIds = new ArrayList<Integer>();

            for (Result result:movie.results){
                temp.add("https://image.tmdb.org/t/p/w780" + result.backdrop_path);
                tempTitle.add(result.title);
                tempIds.add(result.id);
            }
//            Log.i("Amit",temp.get(4));
            Titles = tempTitle.toArray(new String[tempTitle.size()]);
            String[] image_paths = temp.toArray(new String[temp.size()]);
            MovieIds = tempIds.toArray(new Integer[tempIds.size()]);
            return image_paths;
        }
        else {
            return null;
        }
    }

}