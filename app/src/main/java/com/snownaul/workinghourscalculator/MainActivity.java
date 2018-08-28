package com.snownaul.workinghourscalculator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recycler;
    CardAdapter cardAdapter;
    ArrayList cardsList = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recycler = findViewById(R.id.recycler);
        cardAdapter=new CardAdapter();

        cardsList.add(new Card());
        cardsList.add(new Card());
        cardsList.add(new Card());


        recycler.setAdapter(cardAdapter);

    }

    public void onClickBtn(View v){
        Toast.makeText(this, "카드가 추가되었습니다.", Toast.LENGTH_SHORT).show();
        cardsList.add(0,new Card());

        cardAdapter.notifyItemInserted(0);

    }

    class CardAdapter extends RecyclerView.Adapter{


        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.day_card_layout,parent,false);
            return new VH(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return cardsList.size();
        }

        class VH extends RecyclerView.ViewHolder{
            TextView tvStartingTime, tvFinishingTime, tvDinner, tvMore, tvNight;
            LinearLayout clickDate, clickStartingTime, clickFinishingTime, clickDinner, clickMore, clickNight;

            public VH(@NonNull View itemView) {
                super(itemView);

                tvStartingTime=itemView.findViewById(R.id.tv_starting_time);
                tvFinishingTime=itemView.findViewById(R.id.tv_finishing_time);
                tvDinner=itemView.findViewById(R.id.tv_dinner);
                tvMore=itemView.findViewById(R.id.tv_more);
                tvNight=itemView.findViewById(R.id.tv_night);

                clickDate=itemView.findViewById(R.id.click_date);
                clickStartingTime=itemView.findViewById(R.id.click_starting_time);
                clickFinishingTime=itemView.findViewById(R.id.tv_finishing_time);
                clickDinner=itemView.findViewById(R.id.click_dinner);
                clickMore=itemView.findViewById(R.id.click_more);
                clickNight=itemView.findViewById(R.id.click_night);

                tvStartingTime.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TimePickerDialog dialog=new TimePickerDialog(MainActivity.this,listener,9,40,false);
                        dialog.show();
                    }

                    private TimePickerDialog.OnTimeSetListener listener = new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            Toast.makeText(MainActivity.this, hourOfDay+"시 "+minute+"분", Toast.LENGTH_SHORT).show();
                        }
                    };
                });

            }
        }
    }
}
