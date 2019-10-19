package com.kailang.memo.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.kailang.memo.R;
import com.kailang.memo.data.Memo;
import com.kailang.memo.viewmodel.MemoViewModel;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 */
public class EditFragment extends Fragment {
    private TextView edit_tag;
    private EditText edit_headline,edit_content;
    private MemoViewModel memoViewModel;
    private LiveData<List<Memo>> memos;
    private List<Memo> allMemos;
    private Memo memoToEdit;

    public EditFragment() {
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
            String headline=edit_headline.getText().toString().trim();
            String content=edit_content.getText().toString().trim();
            if(!headline.isEmpty()&&!content.isEmpty()) {
                memoToEdit.setHeadline(headline);
                memoToEdit.setContent(content);
                memoViewModel.updateMemo(memoToEdit);
                Navigation.findNavController(requireActivity().getCurrentFocus()).navigate(R.id.action_editFragment_to_memoFragment);
                InputMethodManager imm = (InputMethodManager) requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(requireActivity().getCurrentFocus().getWindowToken(), 0);
            }else{
                Toast.makeText(requireContext(),"标题或内容不能为空",Toast.LENGTH_SHORT).show();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit, container, false);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        memoViewModel = ViewModelProviders.of(requireActivity()).get(MemoViewModel.class);
        memos=memoViewModel.getAllMemosLive();
        allMemos= memos.getValue();

        //获取传递数据
        memoToEdit=allMemos.get(getArguments().getInt("memo"));
        String tag=memoToEdit.getType();
        String headline=memoToEdit.getHeadline();
        String content=memoToEdit.getContent();

        edit_tag=requireActivity().findViewById(R.id.edit_tag);
        edit_headline=requireActivity().findViewById(R.id.edit_headline);
        edit_content=requireActivity().findViewById(R.id.edit_content);
        edit_tag.setText(tag);
        edit_headline.setText(headline);
        edit_content.setText(content);

        //获取焦点，弹出键盘
        InputMethodManager imm = (InputMethodManager) requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        edit_headline.requestFocus();
        imm.showSoftInput(edit_headline, 0);



    }
}
