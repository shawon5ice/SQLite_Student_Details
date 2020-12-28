package com.ssquare.sqliteanisul;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.ssquare.sqliteanisul.Utils.Util;

public class DataBaseHelper extends SQLiteOpenHelper {
    private static final String DROP_TABLE = "DROP TABLE "+Util.TABLE_NAME;
    private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS "+Util.TABLE_NAME+"("+Util.KEY_ID+" INTEGER PRIMARY KEY," +
            Util.KEY_NAME+" VARCHAR, "+Util.KEY_AGE+" INTEGER, "+Util.KEY_GENDER+" VARCHAR);";
    private Context context;
    public DataBaseHelper(Context context) {
        super(context, Util.DATABASE_NAME, null, Util.DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            Toast.makeText(context,"Database created successfully",Toast.LENGTH_LONG);
//            db.execSQL(CREATE_TABLE);
            db.execSQL("CREATE TABLE tableinfo(id INTEGER PRIMARY KEY, name VARCHAR, age INTEGER)");
        }catch (Exception e){
            Toast.makeText(context,"Database creation failed "+e.getMessage(),Toast.LENGTH_LONG);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            db.execSQL(DROP_TABLE);
            Toast.makeText(context,"Database upgraded successfully",Toast.LENGTH_LONG);
            onCreate(db);

        }catch (Exception e){
            Toast.makeText(context,"Database upgrade failed",Toast.LENGTH_LONG);
        }
    }
}
