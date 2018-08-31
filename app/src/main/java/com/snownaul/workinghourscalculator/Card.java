package com.snownaul.workinghourscalculator;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Card {


    int cardID;
    int startingHour;
    int startingMin;
    int finishingHour;
    int finishingMin;
    int dinner;
    int more;
    int night;

    String date;
    String day;

    int y, m, d;

    public Card() {
        long now = System.currentTimeMillis();
        Date d = new Date(now);
        int hour,min;
        hour = d.getHours();
        min = d.getMinutes();

        setStartingTime(hour,min);

        finishingHour=hour+9;

        setFinishingTime(hour+9, min);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        date = sdf.format(d);

        Calendar cal = Calendar.getInstance();
        cal.setTime(d);

        y=cal.get(Calendar.YEAR);
        m=cal.get(Calendar.MONTH);
        this.d=cal.get(Calendar.DAY_OF_MONTH);

        Log.d("date", "y : "+y+", m : "+m+", d : "+d);


        switch (cal.get(Calendar.DAY_OF_WEEK)){
            case 1:
                day="일";
                break;
            case 2:
                day="월";
                break;
            case 3:
                day="화";
                break;
            case 4:
                day="수";
                break;
            case 5:
                day="목";
                break;
            case 6:
                day="금";
                break;
            case 7:
                day="토";
                break;

        }

        dinner=0;
        more=0;
        night=0;

    }

    public Card(int cardID, int startingHour, int startingMin, int finishingHour, int finishingMin, int dinner, int more, int night, String date, String day, int y, int m, int d) {
        this.cardID = cardID;
        this.startingHour = startingHour;
        this.startingMin = startingMin;
        this.finishingHour = finishingHour;
        this.finishingMin = finishingMin;
        this.dinner = dinner;
        this.more = more;
        this.night = night;
        this.date = date;
        this.day = day;
        this.y = y;
        this.m = m;
        this.d = d;
    }

    public String getStartingtime(){
        StringBuffer tmpStartingTime=new StringBuffer();
        if(startingHour>12) startingHour-=12; //무조건 오전으로 전환..
        if(startingHour<10) tmpStartingTime.append("0");
        tmpStartingTime.append(startingHour+":");
        if(startingMin==0) tmpStartingTime.append("00");
        else if(startingMin<10) tmpStartingTime.append("0"+startingMin);
        else tmpStartingTime.append(startingMin);
        return tmpStartingTime.toString();
    }

    public String getFinishingtime(){
        int finishingH = finishingHour;
        StringBuffer tmpFinishingTime=new StringBuffer();
        if(finishingH>12) finishingH-=12;
        if(finishingH>12) finishingH-=12;
        if(finishingH<10) tmpFinishingTime.append("0");
        tmpFinishingTime.append(finishingH+":");
        if(finishingMin==0) tmpFinishingTime.append("00");
        else if(finishingMin<10) tmpFinishingTime.append("0"+finishingMin);
        else tmpFinishingTime.append(finishingMin);
        return tmpFinishingTime.toString();
    }

    public String getDate() {
        return date;
    }

    public String getDay() {
        return day;
    }

    public String getDinner() {
        String tmpDinner = dinner+"";
        StringBuffer strDinner=new StringBuffer();
        if(tmpDinner.length()==4){
            strDinner.append(tmpDinner.charAt(0));
            strDinner.append(",");
            tmpDinner = tmpDinner.substring(1);
        }

        strDinner.append(tmpDinner);

        return strDinner.toString();
    }

    public void setStartingTime(int hour, int min){
        startingHour=hour;
        if(min==0){
            startingMin=min;
        }else if(min<=15){
            startingMin=15;
        }else if(min<=30){
            startingMin=30;
        }else if(min<=45){
            startingMin=45;
        }else{
            startingMin=0;
            startingHour++;
        }

        if(finishingHour-startingHour<9){
            finishingHour=startingHour+9;
            finishingMin=startingMin;
        }else if(finishingHour-startingHour==9&&finishingMin<startingMin){
            finishingMin=startingMin;
        }

        calculateMoreAndNight();
    }

    public void setFinishingTime(int hour, int min){
        finishingHour=hour;
        if(min==0){
            finishingMin=min;
        }else if(min<=15){
            finishingMin=15;
        }else if(min<=30){
            finishingMin=30;
        }else if(min<=45){
            finishingMin=45;
        }else{
            finishingMin=0;
            finishingHour++;
        }

        if(finishingHour-startingHour<9){
            finishingHour=startingHour+9;
            finishingMin=startingMin;
        }else if(finishingHour-startingHour==9&&finishingMin<startingMin){
            finishingMin=startingMin;
        }

        calculateMoreAndNight();

    }

    public void setDate(int year, int month, int dayOfMonth) {
        y=year;
        m=month;
        d=dayOfMonth;

        Calendar cal = Calendar.getInstance();
        cal.set(y,m,d);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = cal.getTime();
        this.date=sdf.format(date);

        switch (cal.get(Calendar.DAY_OF_WEEK)){
            case 1:
                day="일";
                break;
            case 2:
                day="월";
                break;
            case 3:
                day="화";
                break;
            case 4:
                day="수";
                break;
            case 5:
                day="목";
                break;
            case 6:
                day="금";
                break;
            case 7:
                day="토";
                break;

        }

    }

    public void setDinner(int dinner) {
        if(dinner>6000){
            this.dinner=6000;
        }else{
            this.dinner=dinner;
        }
    }

    public void calculateMoreAndNight(){
        more=0;
        night=0;
        int totalMin = (finishingHour*60+finishingMin)-(startingHour*60+startingMin);
        totalMin=totalMin-(9*60);

        //저녁 시간 뺌

        if(totalMin<=180){
            more=totalMin-60;
        }else{
            more=120;
            totalMin-=180;
            if(totalMin>60){
                totalMin-=60;
                night=totalMin;
            }

        }

        if(totalMin<0&&totalMin>=-60) more=0;

    }

    public int getMore() {
        return more;
    }

    public int getNight() {
        return night;
    }

    public String getTvId(){
        return cardID+"";
    }
}
