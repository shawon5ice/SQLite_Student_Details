package com.ssquare.sqliteanisul.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.ssquare.sqliteanisul.models.Student;
import com.ssquare.sqliteanisul.utils.Util;

public class DataBaseHelper extends SQLiteOpenHelper {
    private static final String DROP_TABLE = "DROP TABLE "+Util.TABLE_NAME;
    private static final String SHOW_ALL_DATA = "SELECT * FROM "+Util.TABLE_NAME;
    private static final String SHOW_SINGLE_DATA = "SELECT * FROM "+Util.TABLE_NAME +" WHERE "+Util.KEY_ID+"=";
    private static final String CREATE_TABLE = "CREATE TABLE "+Util.TABLE_NAME+"("+Util.KEY_ID+" INTEGER PRIMARY KEY, "+Util.KEY_NAME+" VARCHAR, "+Util.KEY_AGE+" INTEGER, "+Util.KEY_GENDER+" VARCHAR(10))";
    private final Context context;

    public DataBaseHelper(Context context) {
        super(context, Util.DATABASE_NAME, null, Util.DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            Toast.makeText(context,"Database created successfully",Toast.LENGTH_LONG).show();
            db.execSQL(CREATE_TABLE);
            Log.i("DB", "onCreate: "+CREATE_TABLE);
        }catch (Exception e){
            Toast.makeText(context,"Database creation failed "+e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            db.execSQL(DROP_TABLE);
            Toast.makeText(context,"Database upgraded successfully",Toast.LENGTH_LONG).show();
            onCreate(db);

        }catch (Exception e){
            Toast.makeText(context,"Database upgrade failed",Toast.LENGTH_LONG).show();
        }
    }

    public long addStudent(Student student){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Util.KEY_NAME,student.getName());
        values.put(Util.KEY_AGE,student.getAge());
        values.put(Util.KEY_GENDER,student.getGender());
        long rowId = database.insert(Util.TABLE_NAME,null,values);
        database.close();
        return rowId;
    }

    public int updateStudent(Student student,String id){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Util.KEY_ID,id);
        values.put(Util.KEY_NAME,student.getName());
        values.put(Util.KEY_AGE,student.getAge());
        values.put(Util.KEY_GENDER,student.getGender());
        return database.update(Util.TABLE_NAME, values, Util.KEY_ID + "=?",
                new String[]{id});
    }
    public void deleteStudent(String id){
        SQLiteDatabase database = this.getWritableDatabase();
        database.delete(Util.TABLE_NAME, Util.KEY_ID + "=?",
                new String[]{id});
        database.close();
    }

    public Cursor showAlldata(){
        SQLiteDatabase database = this.getReadableDatabase();
        return database.rawQuery(SHOW_ALL_DATA,null);
    }

    public Student getSingleElement(int id){
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery(SHOW_SINGLE_DATA+id,null);
        cursor.moveToFirst();
        return new Student(cursor.getString(1),cursor.getString(2),cursor.getString(3));
    }
}
