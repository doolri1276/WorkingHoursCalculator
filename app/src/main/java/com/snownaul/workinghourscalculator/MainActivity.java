package com.snownaul.workinghourscalculator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper databaseHelper;

    RecyclerView recycler;
    CardAdapter cardAdapter;
    ArrayList cardsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseHelper = new DatabaseHelper(this);
        cardsList = databaseHelper.getAllCards();

        if(cardsList==null){
            cardsList=new ArrayList();
        }

        recycler = findViewById(R.id.recycler);
        cardAdapter=new CardAdapter();


        recycler.setAdapter(cardAdapter);

    }

    public void onClickBtn(View v){
        Toast.makeText(this, "카드가 추가되었습니다.", Toast.LENGTH_SHORT).show();
        Card card = new Card();
        databaseHelper.addCard(card);
        cardsList.add(0,card);
        cardAdapter.notifyItemInserted(0);
        recycler.scrollToPosition(0);
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
            VH vh= (VH) holder;
            Card card= (Card) cardsList.get(position);

            vh.tvId.setText(card.getTvId()+"");
            vh.tvStartingTime.setText(card.getStartingtime()+"");
            vh.tvFinishingTime.setText(card.getFinishingtime()+"");
            vh.tvDate.setText(card.getDate()+"");
            vh.tvDay.setText(card.getDay()+"");
            vh.tvDinner.setText(card.getDinner()+"");
            vh.tvMore.setText(card.getMore()+"");
            vh.tvNight.setText(card.getNight()+"");

        }

        @Override
        public int getItemCount() {
            return cardsList.size();
        }

        class VH extends RecyclerView.ViewHolder{
            TextView tvId, tvStartingTime, tvFinishingTime, tvDate, tvDay, tvDinner, tvMore, tvNight;
            LinearLayout clickDate, clickStartingTime, clickFinishingTime, clickDinner, clickMore, clickNight;

            public VH(@NonNull View itemView) {
                super(itemView);

                tvId = itemView.findViewById(R.id.tv_id);
                tvStartingTime=itemView.findViewById(R.id.tv_starting_time);
                tvFinishingTime=itemView.findViewById(R.id.tv_finishing_time);
                tvDate = itemView.findViewById(R.id.tv_date);
                tvDay = itemView.findViewById(R.id.tv_day);
                tvDinner=itemView.findViewById(R.id.tv_dinner);
                tvMore=itemView.findViewById(R.id.tv_more);
                tvNight=itemView.findViewById(R.id.tv_night);

                clickDate=itemView.findViewById(R.id.click_date);
                clickStartingTime=itemView.findViewById(R.id.click_starting_time);
                clickFinishingTime=itemView.findViewById(R.id.click_finishing_time);
                clickDinner=itemView.findViewById(R.id.click_dinner);
                clickMore=itemView.findViewById(R.id.click_more);
                clickNight=itemView.findViewById(R.id.click_night);

                clickStartingTime.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Card card = (Card) cardsList.get(getLayoutPosition());

                        int hour, min;

                            hour=card.startingHour;
                            min=card.startingMin;

                        TimePickerDialog dialog=new TimePickerDialog(MainActivity.this,listener,hour,min,false);
                        dialog.show();
                    }

                    private TimePickerDialog.OnTimeSetListener listener = new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            Toast.makeText(MainActivity.this, hourOfDay+"시 "+minute+"분", Toast.LENGTH_SHORT).show();
                            Card card = (Card) cardsList.get(getLayoutPosition());
                            card.setStartingTime(hourOfDay, minute);

                            cardAdapter.notifyItemChanged(getLayoutPosition());
                            databaseHelper.updateCard(card);

                        }
                    };
                });

                clickFinishingTime.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Card card = (Card) cardsList.get(getLayoutPosition());

                        int hour, min;

                        hour=card.finishingHour;
                        min=card.finishingMin;

                        TimePickerDialog dialog=new TimePickerDialog(MainActivity.this,listener,hour,min,false);
                        dialog.show();
                    }

                    private TimePickerDialog.OnTimeSetListener listener = new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            Toast.makeText(MainActivity.this, hourOfDay+"시 "+minute+"분", Toast.LENGTH_SHORT).show();
                            Card card = (Card) cardsList.get(getLayoutPosition());
                            card.setFinishingTime(hourOfDay, minute);

                            cardAdapter.notifyItemChanged(getLayoutPosition());
                            databaseHelper.updateCard(card);
                        }
                    };
                });

                clickDinner.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final Card card = (Card) cardsList.get(getLayoutPosition());
                        int dinnner = card.dinner;

                        AlertDialog.Builder ad = new AlertDialog.Builder(MainActivity.this);

                        View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.dinner_dialog,null);
                        final EditText etDinner = view.findViewById(R.id.et_dinner);
                        etDinner.setText(card.dinner+"");
                        ad.setView(view);

                        ad.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String str=etDinner.getText().toString()+"";

                                int dinner=0;

                                if(str.length()!=0)
                                    dinner = Integer.parseInt(str);

                                card.setDinner(dinner);

                                cardAdapter.notifyItemChanged(getLayoutPosition());
                                databaseHelper.updateCard(card);

                            }
                        });

                        ad.setNegativeButton("취소",null);

                        ad.create().show();

                    }
                });

                clickDate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final Card card = (Card)cardsList.get(getLayoutPosition());

                        AlertDialog.Builder date = new AlertDialog.Builder(MainActivity.this);

                        View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.date_picker_dialog,null);
                        final DatePicker dp = view.findViewById(R.id.dp);
                        dp.updateDate(card.y, card.m, card.d);

                        date.setView(view);

                        date.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                card.setDate(dp.getYear(), dp.getMonth(), dp.getDayOfMonth());
                                cardAdapter.notifyItemChanged(getLayoutPosition());
                                databaseHelper.updateCard(card);
                            }
                        });

                        date.setNegativeButton("취소",null);

                        date.create().show();

                    }
                });

                itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        final Card card = (Card)cardsList.get(getLayoutPosition());
                        AlertDialog.Builder removeDialog = new AlertDialog.Builder(MainActivity.this);

                        removeDialog.setMessage("이 카드를 삭제하시겠습니까?");
                        removeDialog.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                databaseHelper.removeCard(card);
                                cardsList.remove(card);
                                cardAdapter.notifyItemRemoved(getLayoutPosition());
                            }
                        });

                        removeDialog.setNegativeButton("취소",null);

                        removeDialog.create().show();
                        return true;
                    }
                });


            }
        }
    }
}
