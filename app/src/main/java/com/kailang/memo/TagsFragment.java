package com.kailang.memo;


import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class TagsFragment extends Fragment {
    private RecyclerView recyclerView;
    private TagsAdapter tagsAdapter;
    private final String TAGS="TAGS";
    private List<String> tagsList;
    private SharedPreferences shp;
    private TagSelectedViewModel tagSelectedViewModel;
    private LiveData<String> tagLive;
    public TagsFragment() {
        // Required empty public constructor
        setHasOptionsMenu(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.app_bar_tags_add){
            final EditText input = new EditText(getContext());
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle(R.string.add_new).setIcon(R.drawable.ic_storage_black_24dp).setView(input)
                    .setNegativeButton(R.string.cancel, null);
            builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    String tmp;
                    tmp =input.getText().toString().trim();
                    if(!tmp.isEmpty()&&!tagsList.contains(tmp)){
                        tagsList.add(tmp);
                        SharedPreferences.Editor editor = shp.edit();
                        int tt=tagsAdapter.getItemCount();
                        if(tt!=tagsList.size()) {
                            editor.putString(tagsList.size()+"",tmp);
                            editor.commit();
                            List<String> tempList = new ArrayList<>();
                            tempList.add(tmp);
                            tagsAdapter.setTagList(tempList);
                            tagsAdapter.notifyDataSetChanged();

                        }
                    }
                }
            });
            builder.show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_tags, menu);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tags, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        tagSelectedViewModel = ViewModelProviders.of(getActivity()).get(TagSelectedViewModel.class);
        recyclerView=requireActivity().findViewById(R.id.recyclerView_tags);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
        tagsAdapter=new TagsAdapter();
        tagSelectedViewModel=ViewModelProviders.of(requireActivity()).get(TagSelectedViewModel.class);

        tagLive=tagSelectedViewModel.getSelected();
        tagLive.observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Log.e("xxxx",s);
            }
        });
        recyclerView.setAdapter(tagsAdapter);

        tagsAdapter.setOnItemClickListener(new TagsAdapter.ClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                Log.e("xxxxx",position+"");
                if(position>-1)
                tagSelectedViewModel.select(tagsList.get(position));
            }
        });
        tagsList=new ArrayList<>();
        shp = requireActivity().getSharedPreferences(TAGS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = shp.edit();
        if(shp.getAll().isEmpty()){
            editor.putString("0","学习");
            editor.putString("1","生活");
            editor.putString("2","工作");
            editor.putString("3","日记");
            editor.putString("4","旅行");
            editor.commit();
        }
        String tmp;
        int t=1;
        tmp=shp.getString(t+"","XxXxxXx");
        while (!tmp.equals("XxXxxXx")&&!tagsList.contains(tmp)){
            tagsList.add(tmp);t++;
            tmp=shp.getString(t+"","XxXxxXx");
        }
        int tt=tagsAdapter.getItemCount();
        if(tt!=tagsList.size()) {
            tagsAdapter.setTagList(tagsList);
            tagsAdapter.notifyDataSetChanged();
        }

    }
}
