package com.ljy.bottombar.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ljy.bottombar.R;

/**
 * Created by lijunyan on 2017/1/16.
 */

public class CustomFragment extends Fragment {

    private static final String TAG = "CustomFragment";
    private TextView textView;
    private String title;

    public static CustomFragment newInstance(String title) {
        CustomFragment newFragment = new CustomFragment();
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        newFragment.setArguments(bundle);
        return newFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            title = args.getString("title");
        }
        Log.i(TAG, "onCreate: title = "+title);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView: title = "+title);
        View view =  inflater.inflate(R.layout.fragment_custom, container, false);
        textView = (TextView)view.findViewById(R.id.custom_fragment_tv);
        textView.setText(title);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i(TAG, "onDestroyView: title = "+title);
    }
}
