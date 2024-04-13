package com.example.helloworld;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import DATABASE.SQLiteHelper;
import model.GiaoDich;
import model.LoaiGD;

public class ChiTiet_GiaoDich extends AppCompatActivity {
    private TextView idgd,sotien,date,loaigd,mota,kieugd;
    private ImageView back,home,imageView2;
    private String currentState;
    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_giao_dich);
        initView();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Class<?> previousActivity;
                if (currentState.equals("PhanLoai")) {
                    Intent intent1 = new Intent();
                    String idLoaigd = intent.getStringExtra("idLoaigd");
                    intent1.putExtra("idLoaigd", idLoaigd);
                    setResult(PhanLoai.RESULT_OK, intent1);
                    finish();
                } else {
                    Intent intent1 = new Intent();
                    String idloaigd = intent.getStringExtra("idloaigd");
                    String sotienmin = intent.getStringExtra("sotienmin");
                    String sotienmax = intent.getStringExtra("sotienmax");
                    String ngayStart = intent.getStringExtra("ngayStart");
                    String ngayEnd = intent.getStringExtra("ngayEnd");

                    intent1.putExtra("idloaigd",idloaigd);
                    intent1.putExtra("sotienmin",sotienmin);
                    intent1.putExtra("sotienmax",sotienmax);
                    intent1.putExtra("ngayStart",ngayStart);
                    intent1.putExtra("ngayEnd",ngayEnd);
                    setResult(LichSuGiaoDich.RESULT_OK, intent1);
                    finish();
                }

            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChiTiet_GiaoDich.this, MainActivity.class);
                startActivity(intent);
            }
        });
        Bundle args = intent.getBundleExtra("giaodich");
        GiaoDich giaoDich = (GiaoDich) args.getParcelable("keyGD");
        System.out.println(giaoDich.getMota()+" Mo ta giao dich chi tiet");
        LoaiGD loaiGD = (LoaiGD) args.getParcelable("keyLoaiGD");
        idgd.setText(" Mã giao dịch: "+ giaoDich.getID());
        sotien.setText(" Số tiền: "+giaoDich.getSotien()+" nghìn đồng");
        date.setText(" Ngày: "+giaoDich.getDate());
        loaigd.setText(""+loaiGD.getNameIcon());
        mota.setText(" Mô tả: "+giaoDich.getMota());
        imageView2.setImageResource(loaiGD.getSrcIcon());
        if(giaoDich.getKieugd()==1) kieugd.setText("Tiền ra");
        else kieugd.setText("Tiền vào");
    }
    private void initView(){
        intent = getIntent();
        currentState = intent.getStringExtra("activity");
        back = findViewById(R.id.back);
        home = findViewById(R.id.home);
        idgd = findViewById(R.id.idgd);
        sotien = findViewById(R.id.sotien);
        date = findViewById(R.id.date);
        loaigd = findViewById(R.id.loaigd);
        mota = findViewById(R.id.mota);
        kieugd = findViewById(R.id.kieugd);
        imageView2 = findViewById(R.id.imageView2);
    }
}