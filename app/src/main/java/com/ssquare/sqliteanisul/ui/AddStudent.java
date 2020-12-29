package com.ssquare.sqliteanisul.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.ssquare.sqliteanisul.data.DataBaseHelper;
import com.ssquare.sqliteanisul.MainActivity;
import com.ssquare.sqliteanisul.models.Student;
import com.ssquare.sqliteanisul.R;

public class AddStudent extends AppCompatActivity {

    private String gender,name,age,id;
    EditText editName,editAge;
    boolean update = false;
    Spinner spinner;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);
        getSupportActionBar().setTitle("Add Student");
        spinner = findViewById(R.id.spinner);
        editName = findViewById(R.id.nameId);
        editAge = findViewById(R.id.ageId);
        button = findViewById(R.id.submit);
        DataBaseHelper dataBaseHelper = new DataBaseHelper(this);


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.gender_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String[] g = {"Male","Female"};

        if(bundle!=null){
            bundeleFinder(intent);
            int pos = 0;
            if(gender.equals(g[1])){
                pos=1;
            }
            spinner.setSelection(pos);
            button.setOnClickListener(new View.OnClickListener() {
                int success;
                @Override
                public void onClick(View v) {
                    name = editName.getText().toString();
                    age = editAge.getText().toString();
                    gender = spinner.getSelectedItem().toString();
                    Student student = new Student(name,age,gender);
                    success = dataBaseHelper.updateStudent(student,id);
                    Log.i("success", "onClick: Success"+success);
                    if(success!=-1){
                        Toast.makeText(AddStudent.this,"Values updated",Toast.LENGTH_SHORT).show();
                        update();
                        MainActivity.studentList.clear();
                        MainActivity.recyclerViewAdapter.notifyDataSetChanged();
                    }
                }
            });
        }

        if(!update){
            button.setOnClickListener(v -> {
                gender = spinner.getSelectedItem().toString();
                name = editName.getText().toString();
                age = editAge.getText().toString();

                if(name.length()>0 && age.length()>0 && gender.length()>0){
                    Student student = new Student(name,age,gender);
                    long data = dataBaseHelper.addStudent(student);
                    if(data==-1){
                        Toast.makeText(AddStudent.this,"Failed to insert data",Toast.LENGTH_LONG).show();
                    }else {
                        Toast.makeText(AddStudent.this,"Successfully inserted data",Toast.LENGTH_LONG).show();
                    }
                    Intent intent1 = new Intent(AddStudent.this, MainActivity.class);
                    startActivity(intent1);
                    finish();
                }else {
                    Toast.makeText(AddStudent.this,"Please insert data in all fields",Toast.LENGTH_LONG).show();
                }
            });
        }



    }

    private void bundeleFinder(Intent intent) {
        String position;
        name = intent.getStringExtra("name");
        id = intent.getStringExtra("id");
        age = intent.getStringExtra("age");
        gender = intent.getStringExtra("gender");
        position = intent.getStringExtra("position");
        update = true;
        button.setText("Update");
        getSupportActionBar().setTitle("Update window");
        editName.setText(name);
        editAge.setText(age);
    }

    public void update(){
        Intent intent = new Intent(this,MainActivity.class);
        intent.putExtra("update",1);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        update();
    }
}