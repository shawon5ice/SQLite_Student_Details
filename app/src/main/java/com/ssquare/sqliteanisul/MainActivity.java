package com.ssquare.sqliteanisul;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ssquare.sqliteanisul.adapter.RecyclerViewAdapter;
import com.ssquare.sqliteanisul.data.DataBaseHelper;
import com.ssquare.sqliteanisul.models.Student;
import com.ssquare.sqliteanisul.ui.AddStudent;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    DataBaseHelper dataBaseHelper;
    FloatingActionButton floatingActionButton;
    private RecyclerView recyclerView;
    public static RecyclerViewAdapter recyclerViewAdapter;
    public static List<Student> studentList = new ArrayList<>();
    Spinner spinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dataBaseHelper = new DataBaseHelper(this);
        SQLiteDatabase  database = dataBaseHelper.getReadableDatabase();



        //setting recycler view
        recyclerView = findViewById(R.id.recyclerViewId);
        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        floatingActionButton = findViewById(R.id.floatingActionButton);


        populateData();

        //setting recyclerview adaptor


        floatingActionButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddStudent.class);
            startActivity(intent);
        });
    }


    public void populateData(){
        Cursor cursor = this.dataBaseHelper.showAlldata();
        studentList.clear();
        if(cursor.getColumnCount()!=0){
            while (cursor.moveToNext()){
                Student student = new Student(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3));
                studentList.add(student);
            }
        }
        recyclerViewAdapter = new RecyclerViewAdapter(this,studentList);
        recyclerView.setAdapter(recyclerViewAdapter);
    }
}