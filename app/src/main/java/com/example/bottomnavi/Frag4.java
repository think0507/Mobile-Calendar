package com.example.bottomnavi;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

public class Frag4 extends Fragment {
    private ListView listView;
    private ArrayAdapter<String> appointmentAdapter;
    private List<String> appointmentList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag4, container, false);
        listView = view.findViewById(R.id.listView);

        appointmentList = new ArrayList<>();

        Bundle bundle = getArguments();
        if (bundle != null) {
            String sharedData = bundle.getString("sharedData");
            int index = bundle.getInt("index");

            index++;

            Bundle newBundle = new Bundle();
            newBundle.putString("sharedData", sharedData);
            newBundle.putInt("index", index);
            setArguments(newBundle);

            String text = "Index: " + index + "\n" + sharedData;

            appointmentList.add(text);

            if (appointmentAdapter == null) {
                appointmentAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, appointmentList);
                listView.setAdapter(appointmentAdapter);
            } else {
                appointmentAdapter.notifyDataSetChanged();
            }
        }

        return view;
    }
}
