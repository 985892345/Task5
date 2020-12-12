package com.ndhzs.task5.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ndhzs.task5.ContentActivity;
import com.ndhzs.task5.R;
import com.ndhzs.task5.WebActivity;

import java.util.List;

public class Content_RecyclerViewAdapter extends RecyclerView.Adapter<Content_RecyclerViewAdapter.RecyclerViewHolder> {

    private final ContentActivity activity;
    private final List<String[]> data;

    public Content_RecyclerViewAdapter(ContentActivity activity, List<String[]> data) {
        this.activity = activity;
        this.data = data;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_main_recycler, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        holder.tvTop.setText(data.get(position)[0]);
        holder.tvMiddle.setText(data.get(position)[1]);
        holder.tvBottom.setText(data.get(position)[2]);

        holder.tvTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(activity, holder.tvTop.getText(), Toast.LENGTH_SHORT).show();
            }
        });

        holder.tvBottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = holder.tvBottom.getText().toString();
                Intent intent = new Intent(activity, WebActivity.class);
                intent.putExtra("url", url);
                activity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder {

        TextView tvTop;
        TextView tvMiddle;
        TextView tvBottom;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTop = itemView.findViewById(R.id.content_rv_item_tvTop);
            tvMiddle = itemView.findViewById(R.id.content_rv_item_tvMiddle);
            tvBottom = itemView.findViewById(R.id.content_rv_item_tvBottom);
        }
    }
}
