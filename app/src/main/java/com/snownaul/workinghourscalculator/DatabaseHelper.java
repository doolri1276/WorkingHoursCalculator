package com.snownaul.workinghourscalculator;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String CARD_ID = "card_id";
    public static final String STARTING_HOUR = "starting_hour";
    public static final String STARTING_MIN = "starting_min";
    public static final String FINISHING_HOUR = "finishing_hour";
    public static final String FINISHING_MIN = "finishing_min";
    public static final String DINNER = "dinner";
    public static final String MORE = "more";
    public static final String NIGHT = "night";
    public static final String DATE = "date";
    public static final String DAY = "day";
    public static final String Y = "y";
    public static final String M = "m";
    public static final String D = "d";


    private static final String DATABASE_NAME = "workingHourCalculator";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_CARD = "card";

    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE "+TABLE_CARD+" ("
                +CARD_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "
                +STARTING_HOUR+" INT NOT NULL, "
                +STARTING_MIN+" INT NOT NULL, "
                +FINISHING_HOUR+" INT NOT NULL, "
                +FINISHING_MIN+" INT NOT NULL, "
                +DINNER+" INT NOT NULL, "
                +MORE+" INT NOT NULL, "
                +NIGHT+" INT NOT NULL, "
                +DATE+" TEXT NOT NULL, "
                +DAY+" TEXT NOT NULL, "
                +Y+" INT NOT NULL, "
                +M+" INT NOT NULL, "
                +D+" INT NOT NULL );";

        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String DROP_TABLE_CARD = "DROP TABLE IF EXISTS "+TABLE_CARD;
        db.execSQL(DROP_TABLE_CARD);

        onCreate(db);
    }

    public void addCard(Card card){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(STARTING_HOUR, card.startingHour);
        values.put(STARTING_MIN, card.startingMin);
        values.put(FINISHING_HOUR, card.finishingHour);
        values.put(FINISHING_MIN, card.finishingMin);
        values.put(DINNER, card.dinner);
        values.put(MORE, card.night);
        values.put(NIGHT, card.night);
        values.put(DATE, card.date);
        values.put(DAY, card.day);
        values.put(Y, card.y);
        values.put(M, card.m);
        values.put(D, card.d);

        db.insert(TABLE_CARD, null, values);

        String LAST_ROW_INSERTED = "SELECT last_insert_rowid()";

        Cursor cursor = db.rawQuery(LAST_ROW_INSERTED, null);
        int idLastInsertedRow = 0;

        if(cursor!=null){
            try{
                if(cursor.moveToFirst()){
                    idLastInsertedRow=cursor.getInt(0);
                    card.cardID=idLastInsertedRow;
                }
            }finally{
                cursor.close();
            }
        }

        db.close();
    }

    public ArrayList<Card> getAllCards(){
        ArrayList<Card> cardList = new ArrayList<>();

        String SELECT_ALL = "SELECT * FROM "+TABLE_CARD+" ORDER BY "+CARD_ID+" DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(SELECT_ALL,null);

        if(cursor.moveToFirst()){
            do{
                Card card = new Card(Integer.parseInt(cursor.getString(0)), Integer.parseInt(cursor.getString(1)),Integer.parseInt(cursor.getString(2)), Integer.parseInt(cursor.getString(3)), Integer.parseInt(cursor.getString(4)), Integer.parseInt(cursor.getString(5)), Integer.parseInt(cursor.getString(6)), Integer.parseInt(cursor.getString(7)),cursor.getString(8),cursor.getString(9), Integer.parseInt(cursor.getString(10)), Integer.parseInt(cursor.getString(11)), Integer.parseInt(cursor.getString(12)));
                cardList.add(card);
            }while (cursor.moveToNext());
        }

        return cardList;
    }

    public void updateCard(Card card){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(STARTING_HOUR, card.startingHour);
        values.put(STARTING_MIN, card.startingMin);
        values.put(FINISHING_HOUR, card.finishingHour);
        values.put(FINISHING_MIN, card.finishingMin);
        values.put(DINNER, card.dinner);
        values.put(MORE, card.night);
        values.put(NIGHT, card.night);
        values.put(DATE, card.date);
        values.put(DAY, card.day);
        values.put(Y, card.y);
        values.put(M, card.m);
        values.put(D, card.d);

        db.update(TABLE_CARD, values, CARD_ID+"="+card.cardID,null);
        db.close();
    }

    public void removeCard(Card card){
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_CARD, CARD_ID+"="+card.cardID,null);
    }

}
