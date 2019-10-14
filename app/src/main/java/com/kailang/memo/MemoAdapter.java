package com.kailang.memo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MemoAdapter extends RecyclerView.Adapter<MemoAdapter.MyViewHolder> {
    private List<Memo> allMemo = new ArrayList<>();
    private MemoViewModel memoViewModel;

    MemoAdapter(MemoViewModel memoViewModel) {
        this.memoViewModel = memoViewModel;
    }

    void setAllMemo(List<Memo> allMemo) {
        this.allMemo = allMemo;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.memo_card,parent,false);

        final MyViewHolder holder = new MyViewHolder(itemView);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到编辑memo
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        final Memo memo = allMemo.get(position);

        holder.tv_headline.setText(memo.getHeadline());
        holder.tv_content.setText(memo.getContent());
        holder.tv_date.setText(memo.getCreate_date());
    }

    @Override
    public int getItemCount() {
        return allMemo.size();
    }

    //自定义ViewHolder:内部类，static 防止内存泄露
    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_headline,tv_content,tv_date;
        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_headline = itemView.findViewById(R.id.tv_headline);
            tv_content = itemView.findViewById(R.id.tv_content);
            tv_date = itemView.findViewById(R.id.tv_date);
        }
    }
}
