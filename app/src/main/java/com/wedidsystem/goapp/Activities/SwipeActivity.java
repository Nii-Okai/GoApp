package com.wedidsystem.goapp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.wedidsystem.goapp.R;
import com.wedidsystem.goapp.adapter.KeywordListAdapter;
import com.wedidsystem.goapp.helper.CustomDialog;
import com.wedidsystem.goapp.helper.SimpleDividerItemDecoration;
import com.wedidsystem.goapp.model.Item;
import com.wedidsystem.goapp.helper.RecyclerItemTouchHelper;
import com.wedidsystem.goapp.helper.SQLiteHelper;

import java.util.ArrayList;
import java.util.List;

public class SwipeActivity extends AppCompatActivity implements RecyclerItemTouchHelper.RecyclerItemTouchHelperListener,
CustomDialog.CustomDialogListener{

    private RecyclerView recyclerView;
    private List<Item> keywordList;
    public static KeywordListAdapter mAdapter;
    private LinearLayout linearLayout;
    static SQLiteHelper sqLiteHelper;
    Cursor data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe);

        recyclerView = findViewById(R.id.recycler_view);

        linearLayout = findViewById(R.id.linearSwipe);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.backgroundcolor));
        setSupportActionBar(toolbar);

        sqLiteHelper = new SQLiteHelper(this);



        keywordList = new ArrayList<>();
        keywordList.clear();

        data = sqLiteHelper.getKeywords();

        if (data.getCount() == 0){
            Toast.makeText(this, "There are no contents in this list!", Toast.LENGTH_LONG).show();
        }else {
            while(data.moveToNext()){

                keywordList.add(new Item(data.getInt(0), data.getString(1), data.getString(2), data.getLong(3)));

                mAdapter = new KeywordListAdapter(this, keywordList);

                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                recyclerView.setLayoutManager(mLayoutManager);
                //recyclerView.setItemAnimator(new DefaultItemAnimator());
                //recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
                recyclerView.addItemDecoration(new SimpleDividerItemDecoration(getResources()));
                recyclerView.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();

            }
        }

        // adding item touch helper

        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);

    }

    public static void RemoveFromDatabase(int id, String keyword, String period){
        //make entry in history table
        sqLiteHelper.insertHistory(keyword, period);

        ArrayList<Integer> delKeywordList = new ArrayList<>();
        Cursor check = sqLiteHelper.getKeywords();

        while (check.moveToNext()) {
            //Toast.makeText(this, "Index: "+ check.getInt(0), Toast.LENGTH_LONG).show();
            delKeywordList.add(check.getInt(0));
        }

        sqLiteHelper.deleteKeyword(String.valueOf(delKeywordList.get(id)));

    }

    public static void RemoveHistoryFromDatabase(int id, String keyword, String period){
        //make entry in history table
        sqLiteHelper.insertHistory(keyword, period);

        sqLiteHelper.deleteKeyword(String.valueOf(id));

    }

    public void AddData(String keyword, String period, long timer) {

        boolean insertData = sqLiteHelper.insertKeyword(keyword, period, timer);

        if(insertData == true){
            Toast.makeText(this, "Data Successfully Inserted!", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show();
        }
    }

    public void openDialog() {
        CustomDialog customDialog = new CustomDialog();
        customDialog.show(getSupportFragmentManager(), "custom dialog");
    }

    @Override
    public String applyData(String Keyword, String Validity) {
        String newEntry = Keyword;

        if(newEntry.length() < 5){
            Toast.makeText(this, "Enter correct keyword", Toast.LENGTH_LONG).show();
        }else if(Validity.length() == 0){
            Toast.makeText(this, "Choose validity period", Toast.LENGTH_LONG).show();
        }else {
            if (Validity.equalsIgnoreCase("1 day")){
                AddData(Keyword, Validity, System.currentTimeMillis() + 40000);
            }else if (Validity.equalsIgnoreCase("3 days")){
                AddData(Keyword, Validity, System.currentTimeMillis() + 80000);
            }else if (Validity.equalsIgnoreCase("1 week")){
                AddData(Keyword, Validity, System.currentTimeMillis() + 120000);
            }else if (Validity.equalsIgnoreCase("2 weeks")){
                AddData(Keyword, Validity, System.currentTimeMillis() + 240000);
            }else if (Validity.equalsIgnoreCase("1 month")){
                AddData(Keyword, Validity, System.currentTimeMillis() + 300000);
            }

        }

        finish();
        overridePendingTransition(0, 0);
        startActivity(getIntent());
        overridePendingTransition(0, 0);

        return null;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (viewHolder instanceof KeywordListAdapter.MyViewHolder) {
            // get the removed item name to display it in snack bar
            String keyword = keywordList.get(viewHolder.getAdapterPosition()).getKeyword();
            String period = keywordList.get(viewHolder.getAdapterPosition()).getPeriod();

            // backup of removed item for undo purpose
            final Item deletedItem = keywordList.get(viewHolder.getAdapterPosition());
            final int deletedIndex = viewHolder.getAdapterPosition();

            // remove the item from recycler view
            mAdapter.removeItem(deletedIndex);

            //insert into history table and remove from keyword table
            RemoveFromDatabase(deletedIndex, keyword, period);

            mAdapter.notifyDataSetChanged();


            // showing snack bar with Undo option
            Snackbar snackbar = Snackbar
                    .make(linearLayout, keyword + " removed from keyword!  " + deletedIndex, Snackbar.LENGTH_LONG);
            snackbar.setAction("UNDO", new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    //update record in keyword table
                    //sqLiteHelper.updateKeyword(String.valueOf(deletedIndex), deletedItem.getKeyword(), deletedItem.getPeriod());

                    // undo is selected, restore the deleted item
                    mAdapter.restoreItem(deletedItem, deletedIndex);

                }
            });
            snackbar.setActionTextColor(Color.YELLOW);
            snackbar.show();
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
                //startActivity(new Intent(this, SettingsActivity.class));
                openDialog();
                break;
            case R.id.action_search:
                finish();
                startActivity(new Intent(this, HistoryActivity.class));
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }
}
