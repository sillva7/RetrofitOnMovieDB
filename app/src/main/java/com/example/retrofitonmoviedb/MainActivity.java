package com.example.retrofitonmoviedb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.Toast;

import com.example.retrofitonmoviedb.API.ApiFactory;
import com.example.retrofitonmoviedb.API.ApiService;
import com.example.retrofitonmoviedb.clASSes.Example;
import com.example.retrofitonmoviedb.clASSes.Movie;
import com.google.android.material.switchmaterial.SwitchMaterial;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {


    public Disposable disposable;
    public SwitchCompat switchS;
    public RecyclerView recyclerView;
    public MovieAdapter movieAdapter;
    public List<Movie> movieList;
    public ApiService apiService;
    public ApiFactory apiFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        switchS = findViewById(R.id.switch1);
        recyclerView = findViewById(R.id.recView);
        movieAdapter = new MovieAdapter();
        movieList = new ArrayList<>();
        movieAdapter.setMovieList(movieList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        apiFactory = ApiFactory.getInstance();
        apiService = apiFactory.getApiService();

        switchS.setChecked(true);
        switchS.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    disposable = getMovies(0);
                } else {
                    disposable = getMovies(1);
                }
            }
        });
        switchS.setChecked(false);

        recyclerView.setAdapter(movieAdapter);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (disposable != null) {
            disposable.dispose();

        }
    }

    private Disposable getMovies(int methodOfSort) {
        Disposable disposable1;
        if (methodOfSort == 0) {
            disposable1 = apiService.getExamplesPopular()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<Example>() {
                        @Override
                        public void accept(Example example) throws Exception {
                            Log.i("565656", "accept: " + example.getMovies().toString());
                            movieAdapter.setMovieList(example.getMovies());
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            Log.d("565656", "accept: " + throwable.getMessage());
                            Log.d("565656", "acceptError: " + throwable.getMessage());
                        }
                    });
        } else  {
            disposable1 = apiService.getExamplesLatest()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<Example>() {m
                        @Override
                        public void accept(Example example) throws Exception {
                            Log.i("565656", "accept: " + example.getMovies().toString());
                            movieAdapter.setMovieList(example.getMovies());
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            Log.d("565656", "acceptError: " + throwable.getMessage());

                            Log.d("565656", "acceptError: " + throwable.getMessage());
                        }
                    });
        }
        return disposable1;
    }
}