package com.kailang.memo;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.util.Date;

public class AddFragment extends Fragment {

    private TextView tv_tag_add_new;
    private EditText et_content,et_headline;
    private ImageButton imageButton;
    private MemoViewModel memoViewModel;

    public AddFragment() {
        // Required empty public constructor
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_add, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.app_bar_add_save){
            String headline = et_headline.getText().toString().trim();
            String content = et_content.getText().toString().trim();
            String type =tv_tag_add_new.getText().toString().trim();
            if(!headline.isEmpty()&&!type.isEmpty()) {
                Date date = new Date();
                Memo memo = new Memo(headline, content, date.toString(), type);
                memoViewModel.insertMemo(memo);
                NavController navController = Navigation.findNavController(requireActivity().getCurrentFocus());
                navController.navigateUp();
                InputMethodManager imm = (InputMethodManager) requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(requireActivity().getCurrentFocus().getWindowToken(), 0);

            }else{
                if(headline.isEmpty()){
                    Toast.makeText(requireActivity(),"标题不能为空",Toast.LENGTH_SHORT).show();
                }else if(type.isEmpty()){
                    Toast.makeText(requireActivity(),"请选择标签",Toast.LENGTH_SHORT).show();
                }
            }
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        memoViewModel= ViewModelProviders.of(requireActivity()).get(MemoViewModel.class);
        et_headline=requireActivity().findViewById(R.id.tv_headline_add);
        et_content=requireActivity().findViewById(R.id.tv_content_add);
        tv_tag_add_new=requireActivity().findViewById(R.id.tv_tag_add_new);

        tv_tag_add_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(requireActivity().getCurrentFocus());
                navController.navigate(R.id.action_addFragment_to_tagsFragment);
                //添加新的tag
            }
        });
    }
}
