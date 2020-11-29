package com.ndhzs.task5;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.ndhzs.task5.MyDrawerLayout.MyDrawerLayout;
import com.ndhzs.task5.adapter.Content_RecyclerViewAdapter;

public class ContentActivity extends AppCompatActivity {

    private final String[] data = {"a", "b", "c", "d", "e", "f", "g", "h", "i",
            "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);

        new MyDrawerLayout(this);
        RecyclerView mRecycler = findViewById(R.id.content_recycler);
        mRecycler.setLayoutManager(new GridLayoutManager(this, 2));
        mRecycler.setAdapter(new Content_RecyclerViewAdapter(this, data));
    }
}