package com.kailang.memo;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;


import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MemoFragment extends Fragment {
    private MemoViewModel memoViewModel;
    private RecyclerView recyclerView;
    private MemoAdapter myAdapter;
    private FloatingActionButton floatingActionButton;
    private LiveData<List<Memo>> memos;

    public MemoFragment() {
        // Required empty public constructor
        setHasOptionsMenu(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.app_bar_about:
                break;
            case R.id.app_bar_tags:
                NavController navController = Navigation.findNavController(requireActivity().getCurrentFocus());
                navController.navigate(R.id.action_memoFragment_to_tagsFragment);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_main, menu);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_memo, container, false);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //memoViewModel = ViewModelProviders.of(requireActivity()).get(MemoViewModel.class);
        memoViewModel=ViewModelProviders.of(getActivity()).get(MemoViewModel.class);
        recyclerView = requireActivity().findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
        myAdapter = new MemoAdapter(memoViewModel);
        recyclerView.setAdapter(myAdapter);
        memos=memoViewModel.getAllMemosLive();
        memos.observe(requireActivity(), new Observer<List<Memo>>() {
            @Override
            public void onChanged(List<Memo> memos) {
                int tmp = myAdapter.getItemCount();
                myAdapter.setAllMemo(memos);
                if(tmp!=memos.size())
                    myAdapter.notifyDataSetChanged();
            }
        });
        floatingActionButton = requireActivity().findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(v);
                navController.navigate(R.id.action_memoFragment_to_addFragment);
            }
        });
    }

}
