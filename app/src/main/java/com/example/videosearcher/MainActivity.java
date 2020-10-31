package com.example.videosearcher;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MainActivity extends AppCompatActivity{


    private EditText searchInput;
    private CustomAdapter customAdapter;
    private RecyclerView recyclerView;
    private Handler handler;
    private List<VideoItem> searchResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchInput = (EditText)findViewById(R.id.search_input);
        recyclerView = (RecyclerView) findViewById(R.id.videos_recycler_view);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        handler = new Handler();

        searchInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if(actionId == EditorInfo.IME_ACTION_SEARCH){

                    searchOnYoutube(v.getText().toString());
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                            InputMethodManager.RESULT_UNCHANGED_SHOWN);

                    return false;
                }
                return true;
            }
        });

    }

    private void searchOnYoutube(final String keywords){

        new Thread(){

            public void run(){

                YoutubeConnector yc = new YoutubeConnector(MainActivity.this);
                searchResults = yc.search(keywords);

                handler.post(new Runnable(){
                    public void run(){

                        fillYoutubeVideos();
                    }
                });
            }
        }.start();
    }

    private void fillYoutubeVideos(){

        customAdapter = new CustomAdapter(getApplicationContext(),searchResults);
        recyclerView.setAdapter(customAdapter);
        customAdapter.notifyDataSetChanged();
    }
}