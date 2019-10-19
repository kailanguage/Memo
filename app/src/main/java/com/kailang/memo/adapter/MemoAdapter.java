package com.kailang.memo.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kailang.memo.viewmodel.MemoViewModel;
import com.kailang.memo.R;
import com.kailang.memo.data.Memo;

import java.util.ArrayList;
import java.util.List;

public class MemoAdapter extends RecyclerView.Adapter<MemoAdapter.MyViewHolder> {
    private List<Memo> allMemo = new ArrayList<>();
    private MemoViewModel memoViewModel;
    private static ClickListener clickListener;

    public MemoAdapter(MemoViewModel memoViewModel) {
        this.memoViewModel = memoViewModel;
    }

    public void setAllMemo(List<Memo> allMemo) {
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
    static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView tv_headline,tv_content,tv_date;
        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_headline = itemView.findViewById(R.id.tv_headline);
            tv_content = itemView.findViewById(R.id.tv_content);
            tv_date = itemView.findViewById(R.id.tv_date);
        }

        @Override
        public void onClick(View v) {
            clickListener.onItemClick(getAdapterPosition(),v);
        }
    }

    public void setOnItemClickListener(ClickListener clickListener){
        this.clickListener=clickListener;
    }

    //点击事件接口
    public interface ClickListener {
        void onItemClick(int position, View v);
        //void onItemLongClick(int position, View v);
    }

}
