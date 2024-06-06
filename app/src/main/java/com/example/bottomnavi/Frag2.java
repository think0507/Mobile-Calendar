package com.example.bottomnavi;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TimePicker;
import android.content.Intent;
import android.widget.Button;
import androidx.appcompat.app.ActionBar;
import android.widget.CompoundButton;
import android.widget.TimePicker;
import android.widget.Switch;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class Frag2 extends Fragment {
    private View view;
    private String result;
    private MainActivity activity; // MainActivity 에 접근하기 위한 참조 변수

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (MainActivity) context; // MainActivity 에 접근하기 위한 참조 변수 초기화
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag2, container, false);

        EditText editText1 = view.findViewById(R.id.EditText1);
        EditText editText2 = view.findViewById(R.id.EditText2);
        EditText editText3 = view.findViewById(R.id.EditText3);
        EditText editText4 = view.findViewById(R.id.EditText4);
        Switch switchButton = view.findViewById(R.id.switch1);
        TimePicker timePicker1 = view.findViewById(R.id.tp_timepicker1);
        TimePicker timePicker2 = view.findViewById(R.id.tp_timepicker2);
        Button button1 = view.findViewById(R.id.button1);
        Button button2 = view.findViewById(R.id.button2);

        String sharedData = activity.getSharedData();
        editText1.setText(sharedData);

        switchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    timePicker1.setEnabled(false);
                    timePicker2.setEnabled(false);
                } else {
                    timePicker1.setEnabled(true);
                    timePicker2.setEnabled(true);
            }
            }
        });

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editText1.setText(null);
                editText2.setText(null);
                editText3.setText(null);
                editText4.setText(null);
                switchButton.setChecked(false);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //현재 현재 분 시간 가져오기
                int starthour = timePicker1.getCurrentHour();
                int startminute = timePicker1.getCurrentMinute();
                int endhour = timePicker2.getCurrentHour();
                int endminute = timePicker2.getCurrentMinute();


                String text1 = editText1.getText().toString(); // editText1의 텍스트 가져오기
                String text2 = editText2.getText().toString(); // editText2의 텍스트 가져오기
                String text3 = editText3.getText().toString(); // editText1의 텍스트 가져오기
                String text4 = editText4.getText().toString(); // editText2의 텍스트 가져오기

                // Frag3에 전달할 데이터를 담을 Bundle 생성
                Bundle bundle = new Bundle();
                bundle.putString("fromFrag2_text1", text1); // editText1의 텍스트를 "fromFrag2_text1" 키로 저장
                bundle.putString("fromFrag2_text2", text2); // editText2의 텍스트를 "fromFrag2_text2" 키로 저장
                bundle.putString("fromFrag2_text3", text3); // editText2의 텍스트를 "fromFrag2_text2" 키로 저장
                bundle.putString("fromFrag2_text4", text4); // editText2의 텍스트를 "fromFrag2_text2" 키로 저장
                bundle.putInt("starthour", starthour);
                bundle.putInt("startminute", startminute);
                bundle.putInt("endhour", endhour);
                bundle.putInt("endminute", endminute);




                // MainActivity의 참조 변수를 통해 Shared Data 업데이트
                String data =  editText1.getText().toString() + "\n" +
                        "위치 : " + editText2.getText().toString() + "\n" +
                        "인증자 : " + editText3.getText().toString() + "\n" +
                        "시작 예정 시간: " +  starthour + "시 " + startminute + "분\n" +
                        "목표 종료 시간 : " + endhour + "시 " +endminute + "분";
                activity.setSharedData(data);

                // Frag3 인스턴스 생성 및 데이터(bundle) 설정
                Frag3 frag3 = new Frag3();
                frag3.setArguments(bundle);

                // 선택된 메뉴 아이템 ID 저장
                MainActivity mainActivity = (MainActivity) getActivity();
                int selectedItemId = mainActivity.bottomNavigationView.getSelectedItemId();

                // Frag3로 이동 후 선택된 메뉴 아이템 유지
                mainActivity.bottomNavigationView.setSelectedItemId(selectedItemId + 8);

                // Frag3로 이동
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.main_frame, frag3);
                transaction.commit();
            }
        });

        return view;
    }


}
