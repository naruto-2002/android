package com.example.helloworld;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import DATABASE.SQLiteHelper;
import adapter.PhanLoaiGDAdapter;
import adapter.SpinerLSAdapter;
import model.GiaoDich;
import model.LoaiGD;

public class LichSuGiaoDich extends AppCompatActivity implements PhanLoaiGDAdapter.PLGDListen{
    private ImageView backhome,home;
    private TextView texttest,editTextDate2,editTextDate3;
    private EditText editTextPhone,editTextPhone1;
    private Button button2;
    private Spinner spinner;
    private RecyclerView recyclerView;
    private PhanLoaiGDAdapter phanLoaiGDAdapter;
    private SQLiteHelper db;
    private List<LoaiGD> listLoaigd;
    private List<GiaoDich> listgd;
    private String idloaigd="0",sotienmin="",sotienmax="",ngayStart="",ngayEnd="";
    static final int REQUEST_CODE_ACTIVITY_2 = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lich_su_giao_dich);
        initView();
        backhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LichSuGiaoDich.this, MainActivity.class);
                startActivity(intent);
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LichSuGiaoDich.this, MainActivity.class);
                startActivity(intent);
            }
        });
        SpinerLSAdapter spinerLSAdapter = new SpinerLSAdapter(listLoaigd,this);
        spinner.setAdapter(spinerLSAdapter);

        phanLoaiGDAdapter = new PhanLoaiGDAdapter(listgd);
        phanLoaiGDAdapter.setPlgdListen(this);
        recyclerView.setAdapter(phanLoaiGDAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),1));
    }

    private void initView(){
        backhome = findViewById(R.id.backhome);
        home = findViewById(R.id.home);
        spinner = findViewById(R.id.listLoaigd);
        db = new SQLiteHelper(getApplicationContext());
        listLoaigd = db.getAllLoaiGD();
        listgd = db.getAllGD("");
        texttest = findViewById(R.id.texttest);
        button2 = findViewById(R.id.button2);
        editTextDate3 = findViewById(R.id.editTextDate3);
        editTextDate2 = findViewById(R.id.editTextDate2);
        recyclerView = findViewById(R.id.recyclerView);
        editTextPhone = findViewById(R.id.editTextPhone);
        editTextPhone1 = findViewById(R.id.editTextPhone1);
    }
    public void search(View v){
        idloaigd = spinner.getSelectedItem().toString();
        Toast.makeText(getApplicationContext(),idloaigd,Toast.LENGTH_LONG).show();
        sotienmin = editTextPhone.getText().toString();
        sotienmax = editTextPhone1.getText().toString();
        ngayStart = editTextDate2.getText().toString();
        ngayEnd = editTextDate3.getText().toString();
        listgd = db.getListOrderGD(idloaigd,sotienmin,sotienmax,ngayStart,ngayEnd);
        phanLoaiGDAdapter = new PhanLoaiGDAdapter(listgd);
        phanLoaiGDAdapter.setPlgdListen(this);
        recyclerView.setAdapter(phanLoaiGDAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),1));
    }
    public void showDatePickerDialogStart(View v) {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                String selectedDate = String.format("%d-%02d-%02d", year, monthOfYear + 1, dayOfMonth);
                editTextDate2.setText(selectedDate);
            }
        };
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, dateSetListener, year, month, day);
        datePickerDialog.show();
    }
    public void showDatePickerDialogEnd(View v) {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                String selectedDate = String.format("%d-%02d-%02d", year, monthOfYear + 1, dayOfMonth);
                editTextDate3.setText(selectedDate);
            }
        };
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, dateSetListener, year, month, day);
        datePickerDialog.show();
    }

    @Override
    public void onClickGD(View view, int positon) {
        GiaoDich giaoDich = listgd.get(positon);
        System.out.println(giaoDich.getMota()+" Mo ta giao dich");
        Intent intent = new Intent(LichSuGiaoDich.this, ChiTiet_GiaoDich.class);
        Bundle args = new Bundle();
        args.putParcelable("keyGD",giaoDich);
        args.putParcelable("keyLoaiGD",giaoDich.getLoaiGD());

        intent.putExtra("giaodich",args);
        intent.putExtra("activity","LichSuGiaoDich");

        intent.putExtra("idloaigd",idloaigd);
        intent.putExtra("sotienmin",sotienmin);
        intent.putExtra("sotienmax",sotienmax);
        intent.putExtra("ngayStart",ngayStart);
        intent.putExtra("ngayEnd",ngayEnd);
        startActivityForResult(intent,REQUEST_CODE_ACTIVITY_2);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_ACTIVITY_2) {
            if (resultCode == ChiTiet_GiaoDich.RESULT_OK) {
                String idloaigd = data.getStringExtra("idloaigd");
                String sotienmin = data.getStringExtra("sotienmin");
                String sotienmax = data.getStringExtra("sotienmax");
                String ngayStart = data.getStringExtra("ngayStart");
                String ngayEnd = data.getStringExtra("ngayEnd");

                System.out.println("back "+idloaigd+" "+sotienmin+" "+sotienmax+" "+ngayStart+" "+ngayEnd);

                spinner.setSelection(Integer.valueOf(idloaigd)-1);
                editTextPhone.setText(sotienmin);
                editTextPhone1.setText(sotienmax);
                editTextDate2.setText(ngayStart);
                editTextDate3.setText(ngayEnd);

                search(getCurrentFocus());
            }
        }
    }
}