package com.ssquare.sqliteanisul.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ssquare.sqliteanisul.MainActivity;
import com.ssquare.sqliteanisul.R;
import com.ssquare.sqliteanisul.data.DataBaseHelper;
import com.ssquare.sqliteanisul.models.Student;
import com.ssquare.sqliteanisul.ui.AddStudent;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private final Context context;
    private final List<Student> studentList;

    private final int[] colorList = {R.drawable.circle,R.drawable.circle2,R.drawable.circle3,R.drawable.circle4};

    public RecyclerViewAdapter(Context context, List<Student> studentList) {
        this.context = context;
        this.studentList = studentList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(this.context).inflate(R.layout.student_row,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Student student = studentList.get(position);
        if(position%4==0){
            holder.title.setBackgroundResource(colorList[3]);
        }else if(position%3==0){
            holder.title.setBackgroundResource(colorList[2]);
        }else if(position%2==0){
            holder.title.setBackgroundResource(colorList[1]);
        }else {
            holder.title.setBackgroundResource(colorList[0]);
        }
        holder.name.setText(student.getName());
        holder.age.setText("Age: "+student.getAge()+" years");
        holder.gender.setText("Gender: "+student.getGender());
        String[] titles = student.getName().split(" ");
        StringBuilder t = new StringBuilder();
        for (int i=0;i<titles.length;i++){
            t.append(String.copyValueOf(new char[]{titles[i].charAt(0)}).toUpperCase());
            if(i==2){
                break;
            }
        }
        holder.title.setText(t.toString());
    }


    @Override
    public int getItemCount() {
        return studentList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{
        TextView name,age,title,gender;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.nameTVId);
            age = itemView.findViewById(R.id.ageTVId);
            title = itemView.findViewById(R.id.titleTVId);
            gender = itemView.findViewById(R.id.genderTVId);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            Student student = studentList.get(position);
            Intent intent = new Intent(context, AddStudent.class);
            intent.putExtra("id", String.valueOf(student.getId()));
            intent.putExtra("name", student.getName());
            intent.putExtra("age", student.getAge());
            intent.putExtra("gender", student.getGender());
            intent.putExtra("position", String.valueOf(getAdapterPosition()));
            context.startActivity(intent);
        }

        @Override
        public boolean onLongClick(View v) {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
                        alertDialog.setPositiveButton("Yes", (dialog, which) -> {
                            DataBaseHelper dataBaseHelper = new DataBaseHelper(context);
                            int pos = getAdapterPosition();
                            dataBaseHelper.deleteStudent(String.valueOf(studentList.get(pos).getId()));
                            studentList.remove(getAdapterPosition());
                            Log.i("del", "onClick: removed"+getAdapterPosition());
                            MainActivity.recyclerViewAdapter.notifyItemRemoved(getAdapterPosition());
                        })
                        .setNegativeButton("No",null)
                        .setMessage("Do you want to delete this item?")
                        .setTitle("Warning!")
                        .setIcon(android.R.drawable.stat_sys_warning).create()
                        .show();
            return false;
        }
    }



}
