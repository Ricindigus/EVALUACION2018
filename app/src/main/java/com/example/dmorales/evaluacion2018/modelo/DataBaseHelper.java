package com.example.dmorales.evaluacion2018.modelo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper{

    private final Context myContext;
    private SQLiteDatabase myDataBase;

    public DataBaseHelper(Context context) {
        super(context, SQLConstantes.DB_NAME, null, 1);
        this.myContext = context;
    }



    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        try{
//            createDataBase();
//        }catch (IOException e){
//            e.printStackTrace();
//        }
    }


}
