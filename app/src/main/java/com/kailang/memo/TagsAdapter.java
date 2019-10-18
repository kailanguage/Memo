package com.kailang.memo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class TagsAdapter extends RecyclerView.Adapter<TagsAdapter.TagsViewHolder> {
    private List<String> tagList = new ArrayList<>();
    private static ClickListener clickListener;

    void setTagList(List<String> list){
        for (String s:list) {
            tagList.add(s);
        }
    }
     String getSelectedTag(){
        return tagList.get(tagList.size()-1);
    }

    @NonNull
    @Override
    public TagsViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        final View itemView = layoutInflater.inflate(R.layout.tag_card,parent,false);

        final TagsViewHolder tagsViewHolder = new TagsViewHolder(itemView);
        tagsViewHolder.onClick(itemView);
//        tagsViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //
//            }
//        });
        return tagsViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TagsViewHolder holder, int position) {
        holder.tv_tags.setText(tagList.get(position));
    }

    @Override
    public int getItemCount() {
        return tagList.size();
    }


    static class TagsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView tv_tags;
        TagsViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_tags=itemView.findViewById(R.id.tv_tag_card);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            clickListener.onItemClick(getAdapterPosition(),v);
        }
    }
    public void setOnItemClickListener(ClickListener clickListener){
        this.clickListener=clickListener;
    }

    public interface ClickListener {
        void onItemClick(int position, View v);
        //void onItemLongClick(int position, View v);
    }
}
