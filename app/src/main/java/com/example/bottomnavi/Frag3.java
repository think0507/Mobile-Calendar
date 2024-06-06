package com.example.bottomnavi;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ShareCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.bluehomestudio.luckywheel.LuckyWheel;
import com.bluehomestudio.luckywheel.OnLuckyWheelReachTheTarget;
import com.bluehomestudio.luckywheel.WheelItem;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

public class Frag3 extends Fragment {


    private String result;
    private String newText;
    private MainActivity activity; // MainActivity 에 접근하기 위한 참조 변수
    int index = 0;              //for frag4_list_index
    //선언
    private LuckyWheel luckyWheel;
    List<WheelItem> wheelItems ;
    String point;
    private Button buttonCheck;
    private View view;
    Bundle bundle = getArguments();

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (MainActivity) context; // MainActivity 에 접근하기 위한 참조 변수 초기화
    }
    private void generateWheelItems() {

        wheelItems = new ArrayList<>();

        @SuppressLint("UseCompatLoadingForDrawables") Drawable d = getResources().getDrawable(R.drawable.ic_money, null);

        Bitmap bitmap = drawableToBitmap(d);

        wheelItems.add(new WheelItem(Color.parseColor("#F44336"), bitmap, "벌칙1"));

        wheelItems.add(new WheelItem(Color.parseColor("#E91E63"), bitmap, "벌칙2"));

        wheelItems.add(new WheelItem(Color.parseColor("#9C27B0"), bitmap, "벌칙3"));

        wheelItems.add(new WheelItem(Color.parseColor("#3F51B5"), bitmap, "벌칙4"));

        wheelItems.add(new WheelItem(Color.parseColor("#1E88E5"), bitmap, "벌칙5"));

        wheelItems.add(new WheelItem(Color.parseColor("#009688"), bitmap, "벌칙6"));

        //점수판에 데이터 넣기
        luckyWheel.addWheelItems(wheelItems);
    }

    public static Bitmap drawableToBitmap (Drawable drawable) {

        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable)drawable).getBitmap();
        }

        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag3, container, false);

        Bundle bundle = getArguments();

        TextView tv_frag1 = view.findViewById(R.id.textview1);
        String sharedData = activity.getSharedData();
        tv_frag1.setText(sharedData);

        LinearLayout planListLayout = view.findViewById(R.id.plan_list);
        planListLayout.setVisibility(View.VISIBLE); // plan_list 레이아웃을 보이도록 설정




        luckyWheel = view.findViewById(R.id.luck_wheel);
        //점수판 데이터 생성
        generateWheelItems();
        //점수판 타겟 정해지면 이벤트 발생
        luckyWheel.setLuckyWheelReachTheTarget(new OnLuckyWheelReachTheTarget() {
            public void onReachTarget() {

                //아이템 변수에 담기
                WheelItem wheelItem = wheelItems.get(Integer.parseInt(point)-1);

                //아이템 텍스트 변수에 담기
                String money = wheelItem.text;

                //메시지
                Toast.makeText(activity, money, Toast.LENGTH_SHORT).show();
                //bundle.putString("penalty", money);
                String existingText = tv_frag1.getText().toString();
                newText = existingText + "\n벌칙 결과: " + money;
                tv_frag1.setText(newText);
            }
        });

        //시작버튼
        Button start = view.findViewById(R.id.spin_btn);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Random random = new Random();
                point = String.valueOf(random.nextInt(6)+1); // 1 ~ 6
                luckyWheel.rotateWheelTo(Integer.parseInt(point));
            }
        });


        buttonCheck = view.findViewById(R.id.ButtonCheck);
        Button ButtonCheck2 = view.findViewById(R.id.spin_btn2);
        Button saveButton = view.findViewById(R.id.ButtonCheck2);
        buttonCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (bundle != null) {
                    String plan = bundle.getString("fromFrag2_text1");
                    String where = bundle.getString("fromFrag2_text2");
                    String person = bundle.getString("fromFrag2_text3");
                    String memo = bundle.getString("fromFrag2_text4");
                    int starthour = bundle.getInt("starthour");
                    int startminute = bundle.getInt("startminute");
                    int endhour = bundle.getInt("endhour");
                    int endminute = bundle.getInt("endminute");

                    //logcat 로그
                    Log.e("계획", plan);
                    Log.e("장소", where);
                    Log.e("사람", person);
                    Log.e("메모", memo);
                    Log.e("시작시간",  String.valueOf(starthour));
                    Log.e("시작분",  String.valueOf(startminute));
                    Log.e("끝시간",  String.valueOf(endhour));
                    Log.e("끝분",  String.valueOf(endminute));

                    bundle.putString("plan", plan);
                    bundle.putString("where", where);
                    bundle.putString("person", person);
                    bundle.putString("memo", memo);
                    bundle.putInt("starthour", starthour);
                    bundle.putInt("startminute", startminute);
                    bundle.putInt("endhour", endhour);
                    bundle.putInt("endminute", endminute);


                    // 현재 시간 가져오기
                    Calendar calendar = Calendar.getInstance();
                    int currentHour = calendar.get(Calendar.HOUR_OF_DAY); // 24시간 형식의 현재 시간
                    int currentMinute = calendar.get(Calendar.MINUTE); // 현재 분

                    // 비교할 시간 값 설정
                    int targetHour = endhour;
                    int targetMinute = endminute;

                    // 현재 시간과 비교하여 조건문 작성
                    if (currentHour < targetHour) {
                        // 아직 시간이 남은 경우
                        Log.e("Time Comparison", "현재 시간보다 이후입니다.");

                        //현재 버튼 frag4로 넘어가는 버튼으로 바꾸기
                        success();

                    } else if (currentHour > targetHour) {
                        // 이미 지난 경우
                        Log.e("Time Comparison", "이미 지났습니다.");

                        // 벌칙 룰렛 보이기
                        fail();

                    } else {
                        // 시간은 같은 경우, 분 비교
                        if (currentMinute < targetMinute) {
                            // 아직 시간이 남은 경우
                            Log.e("Time Comparison", "현재 시간보다 이후입니다.");

                            //현재 버튼 frag4로 넘어가는 버튼으로 바꾸기
                            success();

                        } else if (currentMinute > targetMinute) {
                            // 이미 지난 경우
                            Log.e("Time Comparison", "이미 지났습니다.");
                            //벌칙 룰렛 보이기
                            fail();

                        } else {
                            // 시간과 분이 동일한 경우
                            Log.e("Time Comparison", "현재 시간과 동일합니다.");

                            //현재 버튼 frag4로 넘어가는 버튼으로 바꾸기
                            success();

                        }
                    }

                }
            }
        });
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 데이터 저장 및 전달

                String sharedData; // 변수를 if-else 문 이전에 선언합니다.
                if (newText == null) {
                    sharedData = tv_frag1.getText().toString();
                } else {
                    sharedData = newText;
                }
                Fragment frag4 = new Frag4();
                Bundle bundle = new Bundle();
                bundle.putString("sharedData", sharedData);
                bundle.putInt("index", index);
                frag4.setArguments(bundle);

                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.main_frame, frag4);
                transaction.commit();
            }
        });
        ButtonCheck2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent shareIntent = ShareCompat.IntentBuilder.from(getActivity())
                        .setType("text/plain")
                        .setText(newText)
                        .getIntent();

                // 카카오톡 실행을 위한 패키지명
                String kakaoPackageName = "com.kakao.talk";

                Toast.makeText(getActivity(), "카카오톡으로 이동합니다.", Toast.LENGTH_SHORT).show();
                shareIntent.setPackage(kakaoPackageName);
                startActivity(shareIntent);
            }
        });

        return view;
    }

    // 버튼 클릭 성공 시 실행될 메소드
    private void success() {
        //순서 if문 먼저
        Toast.makeText(activity, "성공했습니다.", Toast.LENGTH_SHORT).show(); // 토스트 메시지 출력
        if(buttonCheck.getText().equals("공유")){
            // frag4로 전환
            Fragment frag4 = new Frag4();
            frag4.setArguments(bundle);
            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
            transaction.replace(R.id.main_frame, frag4);
            transaction.commit();
        }
        buttonCheck.setBackgroundColor(Color.GREEN); // 버튼 배경색 초록색으로 변경
        buttonCheck.setText("공유"); // 버튼 텍스트 변경
    }

    // 버튼 클릭 실패 시 실행될 메소드
    private void fail() {
        RelativeLayout spinView = view.findViewById(R.id.spin_view);
        spinView.setVisibility(View.VISIBLE); // spin_view 화면을 보이도록 설정
        buttonCheck.setBackgroundColor(Color.RED); // 버튼 배경색 빨간색으로 변경
        Toast.makeText(activity, "실패했습니다. 벌칙 룰렛을 돌려주세요", Toast.LENGTH_SHORT).show(); // 토스트 메시지 출력
    }
    private boolean isPackageInstalled(String packageName) {
        try {
            getActivity().getPackageManager().getPackageInfo(packageName, 0);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
