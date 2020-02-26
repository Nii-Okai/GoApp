package com.wedidsystem.goapp.adapter;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wedidsystem.goapp.R;
import com.wedidsystem.goapp.model.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class KeywordListAdapter extends RecyclerView.Adapter<KeywordListAdapter.MyViewHolder> {
    private Context context;
    private final List<MyViewHolder> lstHolders;
    private List<Item> keyWordList;
    private Handler mHandler = new Handler();

    private Runnable updateRemainingTimeRunnable = new Runnable() {
        @Override
        public void run() {
            synchronized (lstHolders) {
                long currentTime = System.currentTimeMillis();
                for (MyViewHolder holder : lstHolders) {
                    holder.updateTimeRemaining(currentTime);
                }
            }
        }
    };

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView keyword, period, timeRemaining;
        public RelativeLayout viewBackground;
        public LinearLayout viewForeground;
        Item itemModel;

        public MyViewHolder(View view) {
            super(view);
            keyword = view.findViewById(R.id.keyword);
            period = view.findViewById(R.id.period);
            timeRemaining = view.findViewById(R.id.timeRemaining);
            viewBackground = view.findViewById(R.id.view_background);
            viewForeground = view.findViewById(R.id.view_foreground);
        }

        public void setData(Item item) {
            itemModel = item;

            keyword.setText(item.getKeyword());
            period.setText(item.getPeriod());
            //tvTimeRemaining.setText("₹" + item.getExpirationTime());
            updateTimeRemaining(System.currentTimeMillis());

        }

        public void updateTimeRemaining(long currentTime) {
            long timeDiff = itemModel.getExpirationTime() - currentTime;
            int id = itemModel.getId();
            String keyword = itemModel.getKeyword();
            String period = itemModel.getPeriod();

            if (timeDiff > 0) {
                int seconds = (int) (timeDiff / 1000) % 60;
                int minutes = (int) ((timeDiff / (1000 * 60)) % 60);
                int hours = (int) ((timeDiff / (1000 * 60 * 60)) % 24);

                timeRemaining.setText(hours + " hrs " + minutes + " mins " + seconds + " sec");


            } else
                timeRemaining.setText("Expired!!");

               // SwipeActivity.RemoveHistoryFromDatabase(id, keyword, period);

            }
        }


    public KeywordListAdapter(Context context, List<Item> cartList) {
        this.context = context;
        this.keyWordList = cartList;

        lstHolders = new ArrayList<>();
        startUpdateTimer();
    }

    private void startUpdateTimer() {
        Timer tmr = new Timer();
        tmr.schedule(new TimerTask() {
            @Override
            public void run() {
                mHandler.post(updateRemainingTimeRunnable);
            }
        }, 1000, 1000);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.keyword_list_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        final Item item = keyWordList.get(position);
        holder.setData(item);

        synchronized (lstHolders) {
            lstHolders.add(holder);
        }
        holder.updateTimeRemaining(System.currentTimeMillis());
       // holder.timeRemaining.setText("finish");

    }

    @Override
    public int getItemCount() {
        return keyWordList.size();
    }

    public void removeItem(int position) {
        keyWordList.remove(position);



        // notify the item removed by position
        // to perform recycler view delete animations
        // NOTE: don't call notifyDataSetChanged()
        notifyItemRemoved(position);
    }

    public void restoreItem(Item item, int position) {
        keyWordList.add(position, item);
        // notify item added by position
        notifyItemInserted(position);
    }
}
