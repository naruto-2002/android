package com.example.helloworld;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import DATABASE.SQLiteHelper;
import model.GiaoDich;

public class BieuDoXuHuongTaiChinh extends AppCompatActivity {
    Spinner spinnerXHTC1, spinnerXHTC2;
    private ImageView back,home;
    private Button buttonXHTC1;
    private SQLiteHelper db;
    private List<GiaoDich> listgd;
    private ArrayList<String> months = new ArrayList<>();
    private ArrayList<String> years = new ArrayList<>();
    private String month, year;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bieu_do_xu_huong_tai_chinh);
        initView();
        handleSpinner();
        handleEvent();
    }

    private void handleSpinner() {
        for(int i = 0; i < listgd.size(); i++) {
           Date d = listgd.get(i).getDate();
           Calendar c = Calendar.getInstance();
           c.setTime(d);

           String m = c.get(Calendar.MONTH) + 1 + "";
           String y = c.get(Calendar.YEAR) + "";

           if(!months.contains(m)) {
               months.add(m);
           }

           if(!years.contains(y)) {
               years.add(y);
           }
        }

        months.sort(Collections.reverseOrder());
        years.sort(Collections.reverseOrder());

        ArrayAdapter<String> monthAdapter = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, months);
        ArrayAdapter<String> yearAdapter = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, years);

        spinnerXHTC1.setAdapter(monthAdapter);
        spinnerXHTC2.setAdapter(yearAdapter);
    }
    private void handleEvent() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BieuDoXuHuongTaiChinh.this, MainActivity.class);
                startActivity(intent);
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BieuDoXuHuongTaiChinh.this, MainActivity.class);
                startActivity(intent);
            }
        });
        buttonXHTC1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BieuDoXuHuongTaiChinh.this, ChiTiet_BieuDoXuHuongTaiChinh.class);
                intent.putExtra("month", month);
                intent.putExtra("year", year);
                startActivity(intent);
            }
        });
        // Thiết lập sự kiện khi chọn một item trên Spinner
        spinnerXHTC1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Lấy dữ liệu được chọn từ Spinner
                month = parent.getItemAtPosition(position).toString();
//                Toast.makeText(BieuDoXuHuongTaiChinh.this, "Selected item: " + month, Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Được gọi khi không có item nào được chọn
                month = months.get(0);
            }
        });
        spinnerXHTC2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Lấy dữ liệu được chọn từ Spinner
                year = parent.getItemAtPosition(position).toString();
//                Toast.makeText(BieuDoXuHuongTaiChinh.this, "Selected item: " + year, Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Được gọi khi không có item nào được chọn
                year = years.get(0);
            }
        });

    }
    private void initView() {
        db = new SQLiteHelper(getApplicationContext());
        listgd = db.getAllGD("");

        back = findViewById(R.id.back);
        home = findViewById(R.id.home);
        buttonXHTC1 = findViewById(R.id.button_xhtc1);
        spinnerXHTC1 = findViewById(R.id.spinner_xhtc1);
        spinnerXHTC2 = findViewById(R.id.spinner_xhtc2);

    }
}