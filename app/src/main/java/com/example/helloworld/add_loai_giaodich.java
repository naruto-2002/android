package com.example.helloworld;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import DATABASE.SQLiteHelper;
import adapter.SpinnerAddLoaiGd;
import model.LoaiGD;

public class add_loai_giaodich extends AppCompatActivity {
    private ImageView backhome,home;
    private SQLiteHelper db;
    private Spinner spinner;
    private SpinnerAddLoaiGd spinnerAddLoaiGd;
    private EditText editTextText;
    private List<LoaiGD> list;
    private Button  button4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_loai_giaodich);
        initView();
        backhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(add_loai_giaodich.this, MainActivity.class);
                startActivity(intent);
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(add_loai_giaodich.this, MainActivity.class);
                startActivity(intent);
            }
        });
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editTextText.getText().toString();
                int srcIcon = list.get(spinner.getSelectedItemPosition()).getSrcIcon();
//                LoaiGD loaiGD = list.get(spinner.getSelectedItemPosition());
                long x = db.insertLoaiGiaoDich(new LoaiGD(1,srcIcon,name));
                Toast.makeText(getApplicationContext(),""+x
                        ,Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(add_loai_giaodich.this, MainActivity.class);
                startActivity(intent);
            }
        });
        list = new ArrayList<>();
        list.add(new LoaiGD(1,R.drawable.letter_a,"1"));
        list.add(new LoaiGD(1,R.drawable.letter_b,"2"));
        list.add(new LoaiGD(1,R.drawable.letter_c,"3"));
        list.add(new LoaiGD(1,R.drawable.letter_d,"4"));
        spinnerAddLoaiGd = new SpinnerAddLoaiGd(list,this);
        spinner.setAdapter(spinnerAddLoaiGd);
    }
    private void initView(){
        backhome = findViewById(R.id.backhome);
        home = findViewById(R.id.home);
        spinner = findViewById(R.id.spinner);
        editTextText = findViewById(R.id.editTextText);
        button4 = findViewById(R.id.button4);
        db=new SQLiteHelper(getApplicationContext());
    }
}