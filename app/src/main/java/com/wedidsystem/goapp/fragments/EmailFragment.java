package com.wedidsystem.goapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.wedidsystem.goapp.helper.EmailReader;
import com.wedidsystem.goapp.R;

public class EmailFragment extends Fragment {
    ListView listView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.activity_custom_list, container, false);

        Toolbar toolbar = (Toolbar) root.findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.backgroundcolor));
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        listView = root.findViewById(R.id.listView);

        EmailReader reader = new EmailReader(getContext(), listView);
        reader.execute();

        return root;
    }
}