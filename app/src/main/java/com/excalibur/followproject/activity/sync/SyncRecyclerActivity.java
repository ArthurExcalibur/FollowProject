package com.excalibur.followproject.activity.sync;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.excalibur.followproject.R;
import com.excalibur.followproject.view.sync.AsyncListAdapter;

/**
 *
 */
public class SyncRecyclerActivity extends AppCompatActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sync);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler);
        AsyncListAdapter adapter = new AsyncListAdapter(this, recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);
    }
}
