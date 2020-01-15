package com.mmc.lamandys.liba_datapick.activity;

import android.app.Activity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mmc.lamandys.liba_datapick.R;

public class BehaviorActivity extends Activity {

    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_behavior);

        mRecyclerView = findViewById(R.id.my_list);

        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(new BehaviorAdapter());
    }
}


class BehaviorAdapter extends RecyclerView.Adapter<BehaviorViewHolder> {

    @Override
    public BehaviorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BehaviorViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.behavior_item, parent, false));
    }

    @Override
    public void onBindViewHolder(BehaviorViewHolder holder, int position) {
        holder.mTv.setText(String.format("我是第%s个", position));
    }

    @Override
    public int getItemCount() {
        return 20;
    }
}

class BehaviorViewHolder extends RecyclerView.ViewHolder {
    public TextView mTv;

    public BehaviorViewHolder(View itemView) {
        super(itemView);
        mTv = itemView.findViewById(R.id.tv_content);
    }
}