package com.wedidsystem.goapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wedidsystem.goapp.R;
import com.wedidsystem.goapp.model.History;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class HistoryListAdapter extends RecyclerView.Adapter<HistoryListAdapter.ViewHolder> {

    private Context context;
    private List<History> historyList;

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView keyword, period;

        public ViewHolder(View itemView) {
            super(itemView);
            keyword = itemView.findViewById(R.id.keyword);
            period = itemView.findViewById(R.id.period);
        }
    }


    public HistoryListAdapter(Context context, List<History> historyList) {
        this.context = context;
        this.historyList = historyList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.history_list_item, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (historyList != null) {
            History item = historyList.get(position);

            holder.keyword.setText(item.getKeyword());
            holder.period.setText(item.getPeriod());
        }
    }

    @Override
    public int getItemCount() {
        return historyList.size();
    }


}
