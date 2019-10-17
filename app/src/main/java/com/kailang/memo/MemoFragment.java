package com.kailang.memo;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;


import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

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
    private List<Memo> allMemos;

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
            case R.id.app_bar_search:

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_main, menu);

        SearchView searchView = (SearchView) menu.findItem(R.id.app_bar_search).getActionView();
        //searchView.setMaxWidth(1000);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                final String pattern = newText.trim();
                memos.removeObservers(getViewLifecycleOwner());
                memos=memoViewModel.findMemoWithPattern(pattern);
                memos.observe(getViewLifecycleOwner(), new Observer<List<Memo>>() {
                    @Override
                    public void onChanged(List<Memo> memos) {
                        int temp = myAdapter.getItemCount();
                        myAdapter.setAllMemo(memos);
                        if(temp!=memos.size()){
                            myAdapter.notifyDataSetChanged();
                        }
                    }
                });
                return true;
            }
        });
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
        memoViewModel=ViewModelProviders.of(getActivity()).get(MemoViewModel.class);
        recyclerView = requireActivity().findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
        myAdapter = new MemoAdapter(memoViewModel);
        recyclerView.setAdapter(myAdapter);
        memos=memoViewModel.getAllMemosLive();
        memos.observe(getViewLifecycleOwner(), new Observer<List<Memo>>() {
            @Override
            public void onChanged(List<Memo> memos) {
                allMemos=memos;
                int tmp = myAdapter.getItemCount();
                myAdapter.setAllMemo(memos);
                if(tmp!=memos.size())
                    myAdapter.notifyDataSetChanged();
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                final Memo memoToDelete=allMemos.get(viewHolder.getAdapterPosition());
                memoViewModel.deleteMemo(memoToDelete);
                Snackbar.make(requireActivity().findViewById(R.id.fragment_memo),"已删除一个备忘录",Snackbar.LENGTH_SHORT).setAction("撤销", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        memoViewModel.insertMemo(memoToDelete);
                    }
                }).show();
            }
        }).attachToRecyclerView(recyclerView);

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
