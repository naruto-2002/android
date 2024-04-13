package com.example.helloworld;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

import DATABASE.SQLiteHelper;
import adapter.NhapThuChiAdapter;
import model.LoaiGD;

public class NhapThuChi extends AppCompatActivity implements NhapThuChiAdapter.NhapThuChiListener {
    private ImageView backhome,home;
    private TextView textView;
    private Button chiphi,thunhap,next;
    private int checkCPTN;
    private LoaiGD idLoaiGd;
    private List<LoaiGD> loaiGDS;
    private RecyclerView recyclerView;
    private NhapThuChiAdapter nhapThuChiAdapter;
    private SQLiteHelper db;
    static final int REQUEST_CODE_ACTIVITY_2 = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nhap_thu_chi);
        initView();
        backhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NhapThuChi.this, MainActivity.class);
                startActivity(intent);
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NhapThuChi.this, MainActivity.class);
                startActivity(intent);
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NhapThuChi.this, Nhap_date_mota.class);
                EditText editTextPhone2 = findViewById(R.id.editTextPhone2);
                Float sotien = Float.valueOf(editTextPhone2.getText().toString());
                int kieugd = checkCPTN;
                intent.putExtra("sotien",sotien);
                intent.putExtra("kieugd",kieugd);
                Bundle args = new Bundle();
                args.putParcelable("loaigd",idLoaiGd);
                intent.putExtra("args",args);
                startActivityForResult(intent,REQUEST_CODE_ACTIVITY_2);
            }
        });
        chiphi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkCPTN=1;

                ViewCompat.setBackgroundTintList(chiphi,
                        ColorStateList.valueOf(ContextCompat.getColor(getApplicationContext(),
                                R.color.bg_button_on)));
                ViewCompat.setBackgroundTintList(thunhap,
                        ColorStateList.valueOf(ContextCompat.getColor(getApplicationContext(),
                                R.color.bg_button_off)));
            }
        });
        thunhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkCPTN=2;

                ViewCompat.setBackgroundTintList(thunhap,
                        ColorStateList.valueOf(ContextCompat.getColor(getApplicationContext(),
                                R.color.bg_button_on)));
                ViewCompat.setBackgroundTintList(chiphi,
                        ColorStateList.valueOf(ContextCompat.getColor(getApplicationContext(),
                                R.color.bg_button_off)));
            }
        });

        loaiGDS = db.getAllLoaiGD();
        idLoaiGd = loaiGDS.get(1);
        nhapThuChiAdapter = new NhapThuChiAdapter(this,loaiGDS);
        nhapThuChiAdapter.setNhapThuChiListener(this);
        recyclerView.setAdapter(nhapThuChiAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this,3));
    }
    private void initView(){
        backhome = findViewById(R.id.backhome);
        home = findViewById(R.id.home);
        recyclerView = findViewById(R.id.listGiaodich);
        textView = findViewById(R.id.textView17);
        chiphi = findViewById(R.id.chiphi);
        thunhap = findViewById(R.id.thunhap);
        next = findViewById(R.id.next);
        ViewCompat.setBackgroundTintList(chiphi,
                ColorStateList.valueOf(ContextCompat.getColor(this,
                        R.color.bg_button_on)));
        ViewCompat.setBackgroundTintList(thunhap,
                ColorStateList.valueOf(ContextCompat.getColor(this,
                        R.color.bg_button_off)));
        checkCPTN=1;
        db=new SQLiteHelper(getApplicationContext());
    }

    @Override
    public void onClick(View view, int postition) {
        LoaiGD loaiGD = loaiGDS.get(postition);
        idLoaiGd = loaiGD;
        textView.setText(loaiGD.getNameIcon());
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_ACTIVITY_2) {
            if (resultCode == Nhap_date_mota.RESULT_OK) {
                Bundle args = data.getBundleExtra("args");
                LoaiGD loaiGD1 = args.getParcelable("loaigd");
                Float sotien = data.getFloatExtra("sotien",0);
                int kieugd = data.getIntExtra("kieugd",1);
                if(kieugd==1){
                    chiphi.callOnClick();
                }else thunhap.callOnClick();
                EditText editTextPhone2 = findViewById(R.id.editTextPhone2);
                editTextPhone2.setText(sotien.toString());
                textView.setText(loaiGD1.getNameIcon());
            }
        }
    }
}