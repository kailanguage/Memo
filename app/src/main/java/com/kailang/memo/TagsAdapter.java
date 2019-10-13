package com.kailang.memo;

import android.util.Log;
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

    void setTagList(List<String> list){
        for (String s:list) {
            tagList.add(s);
        }
    }

    @NonNull
    @Override
    public TagsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.tag_card,parent,false);

        final TagsViewHolder tagsViewHolder = new TagsViewHolder(itemView);
        tagsViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
            }
        });
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


    static class TagsViewHolder extends RecyclerView.ViewHolder {
        TextView tv_tags;
        TagsViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_tags=itemView.findViewById(R.id.tv_tag_card);
        }
    }
}
