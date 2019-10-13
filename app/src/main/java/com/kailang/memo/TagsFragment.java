package com.kailang.memo;


import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
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
                    SharedPreferences shp = requireActivity().getSharedPreferences(TAGS, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = shp.edit();
                    String tmp;
                    tmp =input.getText().toString().trim();
                    if(!tagsList.contains(tmp)&&!tmp.isEmpty()){
                        tagsList.add(tmp);
                        editor.putString(tagsList.size()+"",tmp);
                        editor.commit();
                        tagsAdapter.setTagList(tagsList);
                        tagsAdapter.notifyDataSetChanged();
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
        recyclerView=requireActivity().findViewById(R.id.recyclerView_tags);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
        tagsAdapter=new TagsAdapter();
        recyclerView.setAdapter(tagsAdapter);
        tagsList=new ArrayList<>();
        SharedPreferences shp = requireActivity().getSharedPreferences(TAGS, Context.MODE_PRIVATE);
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
        tmp=shp.getString(t+"","nothing");
        while (!tmp.equals("nothing")){
            tagsList.add(tmp);t++;
            tmp=shp.getString(t+"","nothing");
        }
        tagsAdapter.setTagList(tagsList);
        int tt=tagsAdapter.getItemCount();
        if(tagsList.size()!=tt)
            tagsAdapter.notifyDataSetChanged();

    }
}
