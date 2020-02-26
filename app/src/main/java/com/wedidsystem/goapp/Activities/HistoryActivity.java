package com.wedidsystem.goapp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.wedidsystem.goapp.R;
import com.wedidsystem.goapp.adapter.HistoryListAdapter;
import com.wedidsystem.goapp.helper.SQLiteHelper;
import com.wedidsystem.goapp.helper.SimpleDividerItemDecoration;
import com.wedidsystem.goapp.model.History;

import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<History> historyList;
    private HistoryListAdapter mAdapter;
    SQLiteHelper sqLiteHelper;
    Cursor data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.backgroundcolor));
        setSupportActionBar(toolbar);

        sqLiteHelper = new SQLiteHelper(this);

        recyclerView = findViewById(R.id.recycler_view);

        historyList = new ArrayList<>();
        historyList.clear();

        data = sqLiteHelper.getHistory();

        if (data.getCount() == 0){
            Toast.makeText(this, "There are no contents in this list!", Toast.LENGTH_LONG).show();
        }else {
            while(data.moveToNext()){

                historyList.add(new History(data.getInt(0), data.getString(1), data.getString(2)));

                mAdapter = new HistoryListAdapter(this, historyList);

                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.setHasFixedSize(true);
                //recyclerView.setItemAnimator(new DefaultItemAnimator());
                //recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
                recyclerView.addItemDecoration(new SimpleDividerItemDecoration(getResources()));
                recyclerView.setAdapter(mAdapter);

                mAdapter.notifyDataSetChanged();

            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.add:
                finish();
                startActivity(new Intent(this, SwipeActivity.class));
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }
}
